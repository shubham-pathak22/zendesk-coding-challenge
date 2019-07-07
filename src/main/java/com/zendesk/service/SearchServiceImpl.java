package com.zendesk.service;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.dto.TicketDTO;
import com.zendesk.dto.UserDTO;
import com.zendesk.model.Organization;
import com.zendesk.model.Ticket;
import com.zendesk.model.User;
import com.zendesk.search.FileBasedSearch;
import com.zendesk.search.Searchable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService{

    private Searchable<User,Integer> user;
    private Searchable<Ticket,String> ticket;
    private Searchable<Organization,Integer> organization;


    public SearchServiceImpl(){
        user = new FileBasedSearch<>("/Users/shubham/projects/users.json",User.class);
        ticket = new FileBasedSearch<>("/Users/shubham/projects/tickets.json",Ticket.class);
        organization = new FileBasedSearch<>("/Users/shubham/projects/organizations.json",Organization.class);

    }
    @Override
    public List<UserDTO> searchByUser(String term, String val) {
        List<User> userList = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        if(term.equals(user.getId())){
            userList.add(user.searchById(Integer.parseInt(val)));
        }else{
            userList.addAll(user.searchByTermValue(term,val));
        }
        for(User user : userList){
            UserDTO userDTO = toUserDTO(user);
            userDTO.setOrganizationDTO(toOrganizationDTO(organization.searchById(userDTO.getOrganization_id())));
            List<TicketDTO> assignedTickets = ticket.searchByTermValue("assignee_id",userDTO.get_id().toString())
                    .stream().map(t -> toTicketDTO(t)).collect(Collectors.toList());
            userDTO.setAssignedTickets(assignedTickets);
            List<TicketDTO> submittedTickets = ticket.searchByTermValue("submitter_id",userDTO.get_id().toString())
                    .stream().map(t -> toTicketDTO(t)).collect(Collectors.toList());
            userDTO.setSubmittedTickets(submittedTickets);
            userDTO.setOrganizationDTO(toOrganizationDTO(organization.searchById(userDTO.getOrganization_id())));
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public List<OrganizationDTO> searchByOrganization(String term, String val) {
        List<Organization> organizationList = new ArrayList<>();
        List<OrganizationDTO> organizationDTOList = new ArrayList<>();
        if(term.equals(organization.getId())){
            organizationList.add(organization.searchById(Integer.parseInt(val)));
        }else{
            organizationList.addAll(organization.searchByTermValue(term,val));
        }
        for(Organization organization : organizationList){
            OrganizationDTO organizationDTO = toOrganizationDTO(organization);
            List<TicketDTO> tickets = ticket.searchByTermValue("organization_id",organizationDTO.get_id().toString())
                    .stream().map(t -> toTicketDTO(t)).collect(Collectors.toList());
            organizationDTO.setTickets(tickets);
            List<UserDTO> users = user.searchByTermValue("organization_id",organizationDTO.get_id().toString())
                    .stream().map(u -> toUserDTO(u)).collect(Collectors.toList());
            organizationDTO.setUsers(users);
            organizationDTOList.add(organizationDTO);
        }
        return organizationDTOList;
    }

    @Override
    public List<TicketDTO> searchByTicket(String term, String val) {
        List<Ticket> ticketList = new ArrayList<>();
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        if(term.equals(ticket.getId())){
            ticketList.add(ticket.searchById(val));
        }else{
            ticketList.addAll(ticket.searchByTermValue(term,val));
        }
        for(Ticket ticket : ticketList){
            TicketDTO ticketDTO = toTicketDTO(ticket);
            ticketDTO.setOrganization(toOrganizationDTO(organization.searchById(ticketDTO.getOrganization_id())));
            ticketDTO.setSubmitter(toUserDTO(user.searchById(ticketDTO.getSubmitter_id())));
            ticketDTO.setAssignee(toUserDTO(user.searchById(ticketDTO.getAssignee_id())));
            ticketDTOList.add(ticketDTO);
        }
        return ticketDTOList;
    }

    private UserDTO toUserDTO(User user){
        if(user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.set_id(user.get_id());
        userDTO.setActive(user.getActive());
        userDTO.setAlias(user.getAlias());
        userDTO.setCreated_at(user.getCreated_at());
        userDTO.setEmail(user.getEmail());
        userDTO.setExternal_id(user.getExternal_id());
        userDTO.setLast_login_at(user.getLast_login_at());
        userDTO.setLocale(user.getLocale());
        userDTO.setName(user.getName());
        userDTO.setOrganization_id(user.getOrganization_id());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        userDTO.setShared(user.getShared());
        userDTO.setSignature(user.getSignature());
        userDTO.setSuspended(user.getSuspended());
        userDTO.setTags(user.getTags());
        userDTO.setTimezone(user.getTimezone());
        userDTO.setUrl(user.getUrl());
        userDTO.setVerified(user.getVerified());
        return userDTO;
    }

    private TicketDTO toTicketDTO(Ticket ticket){
        if(ticket == null) return null;
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.set_id(ticket.get_id());
        ticketDTO.setAssignee_id(ticket.getAssignee_id());
        ticketDTO.setCreated_at(ticket.getCreated_at());
        ticketDTO.setDescription(ticket.getDescription());
        ticketDTO.setDue_at(ticket.getDue_at());
        ticketDTO.setExternal_id(ticket.getExternal_id());
        ticketDTO.setHas_incidents(ticket.getHas_incidents());
        ticketDTO.setOrganization_id(ticket.getOrganization_id());
        ticketDTO.setPriority(ticket.getPriority());
        ticketDTO.setStatus(ticket.getStatus());
        ticketDTO.setSubject(ticket.getSubject());
        ticketDTO.setSubmitter_id(ticket.getSubmitter_id());
        ticketDTO.setTags(ticket.getTags());
        ticketDTO.setType(ticket.getType());
        ticketDTO.setUrl(ticket.getUrl());
        ticketDTO.setVia(ticket.getVia());
        return ticketDTO;
    }

    private OrganizationDTO toOrganizationDTO(Organization organization){
        if(organization == null) return null;
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.set_id(organization.get_id());
        organizationDTO.setCreated_at(organization.getCreated_at());
        organizationDTO.setDetails(organization.getDetails());
        organizationDTO.setDomain_names(organization.getDomain_names());
        organizationDTO.setExternal_id(organization.getExternal_id());
        organizationDTO.setName(organization.getName());
        organizationDTO.setShared_tickets(organization.getShared_tickets());
        organizationDTO.setTags(organization.getTags());
        organizationDTO.setUrl(organization.getUrl());
        return organizationDTO;
    }

}
