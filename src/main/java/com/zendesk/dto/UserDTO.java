package com.zendesk.dto;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserDTO {

    //user
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

    //organization
    OrganizationDTO organization;

    //ticket
    List<TicketDTO> assignedTickets;
    List<TicketDTO> submittedTickets;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                if (f.getName().equals("assignedTickets")) {
                    int i = 0;
                    for (TicketDTO t : assignedTickets) {
                        s.append(String.format("%-20s%s%n", "assignedTicket_" + ++i, t.getSubject()));
                    }
                } else if (f.getName().equals("submittedTickets")) {
                    int i = 0;
                    for (TicketDTO t : submittedTickets) {
                        s.append(String.format("%-20s%s%n", "submittedTicket_" + ++i, t.getSubject()));
                    }
                }else if (f.getName().equals("organization")) {
                    s.append(String.format("%-20s%s%n", "organization",organization == null ? null : organization.getName()));
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
