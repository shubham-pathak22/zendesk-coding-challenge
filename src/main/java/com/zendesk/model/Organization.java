package com.zendesk.model;

import com.zendesk.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Organization {

    @Id
    private Integer _id;
    private String url;
    private String external_id;
    private String name;
    private List<String> domain_names;
    private Date created_at;
    private String details;
    private Boolean shared_tickets;
    private List<String> tags;

    public static void main(String[] args) throws Exception {
        Field[] fields = Organization.class.getDeclaredFields();
        for(Field f : fields){
            // System.out.println(f.getName());
        }

        DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss ZZ");
        Date a = df.parseDateTime("2016-06-23T10:31:39 -10:00").withZone(DateTimeZone.forID("Pacific/Honolulu")).toLocalDateTime().toDate();

        Organization o = new Organization();
        o.set_id(1);
        o.setUrl("asd");
        o.setCreated_at(a);
        o.setShared_tickets(true);
        o.setTags(Arrays.asList("1","2"));

        //System.out.println(o);


        Object f = new PropertyDescriptor("shared_tickets", Organization.class).getReadMethod().invoke(o);
        System.out.println(f);
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse("2016-04-23");
        System.out.println(f.getClass());
        Date dd = (Date)f;
        System.out.println(d.getYear() == dd.getYear() && d.getMonth() == dd.getMonth() && dd.getDay() == d.getDay());
    }

}
