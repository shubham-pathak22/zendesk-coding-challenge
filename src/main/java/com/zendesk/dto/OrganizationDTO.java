package com.zendesk.dto;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                if (f.getName().equals("tickets")) {
                    int i = 0;
                    for (TicketDTO t : tickets) {
                        s.append(String.format("%-20s%s%n", "ticket_" + ++i, t.getSubject()));
                    }
                } else if (f.getName().equals("users")) {
                    int i = 0;
                    for (UserDTO u : users) {
                        s.append(String.format("%-20s%s%n", "user_" + ++i, u.getName()));
                    }
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
