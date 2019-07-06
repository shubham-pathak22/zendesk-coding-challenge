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
public class Ticket {

    private String _id;
    private String url;
    private String external_id;
    private Date created_at;
    private String type;
    private String subject;
    private String description;
    private String priority;
    private String status;
    private Integer submitter_id;
    private Integer organization_id;
    private List<String> tags;
    private Boolean has_incidents;
    private Date due_at;
    private String via;

}
