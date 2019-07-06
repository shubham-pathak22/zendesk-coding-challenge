package com.zendesk.utility;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.util.Date;


public class DateDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String date = jsonElement.getAsString();
        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss ZZ");
        return df.parseDateTime(date).withZone(DateTimeZone.forID("Pacific/Honolulu")).toLocalDateTime().toDate();
    }
}
