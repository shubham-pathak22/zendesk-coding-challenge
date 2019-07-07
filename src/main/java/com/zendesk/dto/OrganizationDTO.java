package com.zendesk.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrganizationDTO {

    private Integer _id;
    private String url;
    private String external_id;
    private String name;
    private List<String> domain_names;
    private Date created_at;
    private String details;
    private Boolean shared_tickets;
    private List<String> tags;

    List<TicketDTO> tickets;
    List<UserDTO> users;
}
