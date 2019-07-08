package com.zendesk.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TicketDTO {

    //ticket
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
    private Integer assignee_id;
    private Integer organization_id;
    private List<String> tags;
    private Boolean has_incidents;
    private Date due_at;
    private String via;

    //organization
    private OrganizationDTO organization;

    //user
    private UserDTO submitter;
    private UserDTO assignee;

}
