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

}
