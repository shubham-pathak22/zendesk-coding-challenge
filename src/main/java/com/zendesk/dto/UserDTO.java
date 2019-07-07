package com.zendesk.dto;

import lombok.Getter;
import lombok.Setter;

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
    OrganizationDTO organizationDTO;

    //ticket
    List<TicketDTO> assignedTickets;
    List<TicketDTO> submittedTickets;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("_id").append("\t").append(_id).append("\n")
         .append("url").append("\t").append(url).append("\n")
         .append("external_id").append("\t").append(external_id).append("\n")
         .append("name").append("\t").append(name).append("\n")
         .append("alias").append("\t").append(alias).append("\n")
         .append("created_at").append("\t").append(created_at).append("\n")
         .append("active").append("\t").append(active).append("\n")
         .append("verified").append("\t").append(verified).append("\n")
         .append("shared").append("\t").append(shared).append("\n")
         .append("locale").append("\t").append(locale).append("\n")
         .append("timezone").append("\t").append(timezone).append("\n")
         .append("last_login_at").append("\t").append(last_login_at).append("\n")
         .append("email").append("\t").append(email).append("\n")
         .append("phone").append("\t").append(phone).append("\n")
         .append("signature").append("\t").append(signature).append("\n")
         .append("organization_id").append("\t").append(organization_id).append("\n")
         .append("tags").append("\t").append(tags).append("\n")
         .append("suspended").append("\t").append(suspended).append("\n")
         .append("role").append("\t").append(role).append("\n");
          if(organizationDTO != null){
            s.append("organization_name").append("\t").append(organizationDTO.getName()).append("\n");
          }
        int i = 0;
         for(TicketDTO t : assignedTickets){
             s.append("assigned_ticket_"+ ++i).append("\t").append(t.getDescription()).append("\n");
         }
         i=0;
        for(TicketDTO t : submittedTickets){
            s.append("submitted_ticket_"+ ++i).append("\t").append(t.getDescription()).append("\n");
        }
        return s.toString();
    }
}
