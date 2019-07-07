package com.zendesk.search;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileBasedSearch<T, ID> implements Searchable<T, ID> {

    private Class<T> clazz;
    private File file;
    private String id;
    private Map<ID, T> map = new HashMap<>();
    private Map<String,Class> fields = new LinkedHashMap<>();
    private Map<String,Method> getter = new HashMap<>();

    public FileBasedSearch(String filePath, Class<T> clazz, String id) {
        this.clazz = clazz;
        this.file = new File(filePath);
        this.id = id;
        initialize();
    }

    private void initialize() {
        try {
            Field[] fields = this.clazz.getDeclaredFields();
            for (Field f : fields) {
                this.fields.put(f.getName(), f.getType());
                this.getter.put(f.getName(), new PropertyDescriptor(f.getName(), this.clazz).getReadMethod());
            }
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            String lines = FileUtils.readFileToString(this.file, "UTF-8");
            List<T>  jsonList = gson.fromJson(lines,TypeToken.getParameterized(List.class, clazz).getType());
            for (T jsonObject : jsonList) {
                this.map.put((ID) this.getter.get(id).invoke(jsonObject), jsonObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T searchById(ID id) {
        return this.map.get(id);
    }

    @Override
    public List<T> searchByTermValue(String term, String value) {
        List<T> list = new ArrayList<>();
        for(T p : this.map.values()){
                try {
                  Object r = this.getter.get(term).invoke(p);
                  if(isEqual(r,value,fields.get(term).getCanonicalName())){
                      list.add(p);
                  }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        return list;
    }

    private boolean isEqual(Object r, String value, String canonicalName) {
        switch (canonicalName){
            case "java.lang.Integer":
                  return r.equals(Integer.parseInt(value));
            case "java.lang.Double":
                 return r.equals(Double.parseDouble(value));
            case "java.lang.String":
                return r.equals(value);
            case "java.util.Date":
                Date originalDate = (Date)r;
                try {
                    Date valDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                    return originalDate.getYear() == valDate.getYear() &&
                            originalDate.getMonth() == valDate.getMonth() &&
                            originalDate.getDay() == valDate.getDay();
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            case "java.lang.Boolean":
                  return r.equals(new Boolean(value));
            case "java.util.List":
                  List list = (List)r;
                  return list.isEmpty() ? false : listContains(list,value);
            default: return false;
        }
    }

    private boolean listContains(List a, String value) {
        String canonicalName = a.get(0).getClass().getCanonicalName();
        switch (canonicalName){
            case "java.lang.Integer":
                return a.contains(Integer.parseInt(value));
            case "java.lang.String":
                return a.contains(value);
            case "java.lang.Boolean":
                return a.contains(Boolean.parseBoolean(value));
            case "java.lang.Double":
                return a.contains(Double.parseDouble(value));
            default: return false;
        }
    }

    @Override
    public Set<String> getSearchableFields() {
        return fields.keySet();
    }

    private class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String date = jsonElement.getAsString();
            DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss ZZ");
            return df.parseDateTime(date).withZone(DateTimeZone.forID("Pacific/Honolulu")).toLocalDateTime().toDate();
        }
    }
}
