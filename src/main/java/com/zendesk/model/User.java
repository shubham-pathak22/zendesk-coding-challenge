package com.zendesk.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    private Integer _id;
    private String url;
    private String external_id;
    private String name;
    private String alias;
    private Date created_at;
    private Boolean active;
    private Boolean verified;
    private Boolean shared;
    private String locale;
    private String timezone;
    private Date last_login_at;
    private String email;
    private String phone;
    private String signature;
    private Integer organization_id;
    private List<String> tags;
    private Boolean suspended;
    private String role;

}
