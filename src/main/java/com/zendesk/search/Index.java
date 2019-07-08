package com.zendesk.search;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.zendesk.annotation.Id;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Index<T> implements Searchable<T> {


    private Class<T> clazz;
    private File file;
    private String id;

    private List<T> entries;
    private Map<String,Multimap<String,Integer>> index;
    private Map<String,Method> getter;
    private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");


    public Index(String filePath,Class<T> clazz){
        this.file = new File(filePath);
        this.clazz = clazz;
        this.index = Maps.newHashMap();
        this.getter = Maps.newHashMap();
        this.entries = Lists.newArrayList();
        buildIndex();
    }

    private void buildIndex() {
        try {
            Field[] fields = this.clazz.getDeclaredFields();
            for (Field f : fields) {
                Annotation[] annotations = f.getAnnotations();
                for(Annotation a :annotations){
                    if(a.annotationType().equals(Id.class)){
                        this.id = f.getName();
                    }
                }
                this.getter.put(f.getName(), new PropertyDescriptor(f.getName(), this.clazz).getReadMethod());
            }

            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
            String lines = FileUtils.readFileToString(this.file, "UTF-8");
            List<T>  jsonList = gson.fromJson(lines, TypeToken.getParameterized(List.class, clazz).getType());
            for (T jsonObject : jsonList) {
                this.entries.add(jsonObject);
                for (Field f : fields){
                    Multimap<String,Integer> multimap = this.index.get(f.getName());
                    if(multimap == null){
                        multimap = MultimapBuilder.hashKeys().arrayListValues().build();
                        this.index.put(f.getName(),multimap);
                    }
                    Object o = this.getter.get(f.getName()).invoke(jsonObject);
                    if(o != null){
                        if(f.getType().equals(Date.class)){
                            Date date = (Date) o;
                            multimap.put(YYYYMMDD.format(date),this.entries.size()-1);
                        }else if (f.getType().equals(List.class)){
                            List list = (List) o;
                            for(Object l:list){
                                multimap.put(l.toString().toLowerCase(),this.entries.size()-1);
                            }
                        }else{
                            multimap.put(o.toString().toLowerCase(),this.entries.size()-1);
                        }
                    }else{
                        multimap.put(null,this.entries.size()-1);
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String date = jsonElement.getAsString();
            DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss ZZ");
            return df.parseDateTime(date).withZone(DateTimeZone.forID("Pacific/Honolulu")).toLocalDateTime().toDate();
        }

    }

    @Override
    public T searchById(String id) {
        List<T> list = searchByTermValue(this.id,id);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<T> searchByTermValue(String term, String value) {
       return this.index.containsKey(term) ? this.index.get(term).get(value == null ? null :value.toLowerCase())
               .stream()
               .map(i -> this.entries.get(i))
               .collect(Collectors.toList()):null;
    }

    @Override
    public Set<String> getSearchableFields() {
        return this.index.keySet().stream().collect(Collectors.toSet());
    }

}
