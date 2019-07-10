package com.zendesk.dto;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                if (f.getName().equals("submitter")) {
                    s.append(String.format("%-20s%s%n", "submitter",submitter == null ? null : submitter.getName()));
                } else if (f.getName().equals("assignee")) {
                    s.append(String.format("%-20s%s%n", "assignee",assignee == null ? null : assignee.getName()));
                } else if (f.getName().equals("organization")) {
                    s.append(String.format("%-20s%s%n", "organization",organization.getName()));
                } else {
                    s.append(String.format("%-20s%s%n", f.getName(), f.get(this)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return s.toString();
    }

}
