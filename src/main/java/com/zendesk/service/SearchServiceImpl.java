package com.zendesk.service;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.dto.TicketDTO;
import com.zendesk.dto.UserDTO;
import com.zendesk.exception.TermNotPresentException;
import com.zendesk.mapper.OrganizationToOrganizationDTOMapper;
import com.zendesk.mapper.TicketToTicketDTOMapper;
import com.zendesk.mapper.UserToUserDTOMapper;
import com.zendesk.model.Organization;
import com.zendesk.model.Ticket;
import com.zendesk.model.User;
import com.zendesk.search.InvertedIndex;
import com.zendesk.search.Searchable;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService{

    private Searchable<User,Integer> user;
    private Searchable<Ticket,String> ticket;
    private Searchable<Organization,Integer> organization;


    public SearchServiceImpl(File user, File ticket, File organization){
        this.user = new InvertedIndex<>(user,User.class);
        this.ticket = new InvertedIndex<>(ticket,Ticket.class);
        this.organization = new InvertedIndex<>(organization,Organization.class);

    }
    @Override
    public Set<UserDTO> searchByUser(String term, String val) throws TermNotPresentException {
        if(!user.getSearchableFields().contains(term)){
            throw new TermNotPresentException("Term " + term + " is not present in Users");
        }
        Set<User> userSet = new LinkedHashSet<>();
        Set<UserDTO> userDTOSet = new LinkedHashSet<>();
        userSet.addAll(user.searchByTermValue(term,val));
        for(User user : userSet){
            UserDTO userDTO = UserToUserDTOMapper.INSTANCE.userToUserDTO(user);
            if(userDTO.getOrganization_id() != null){
                userDTO.setOrganization(OrganizationToOrganizationDTOMapper.INSTANCE.organizationToOrganizationDTO(organization.searchById(userDTO.getOrganization_id())));
            }
            if(userDTO.get_id() != null) {
                List<TicketDTO> assignedTickets = ticket.searchByTermValue("assignee_id", userDTO.get_id().toString())
                        .stream().map(t -> TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(t)).collect(Collectors.toList());
                userDTO.setAssignedTickets(assignedTickets);

                List<TicketDTO> submittedTickets = ticket.searchByTermValue("submitter_id", userDTO.get_id().toString())
                        .stream().map(t -> TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(t)).collect(Collectors.toList());
                userDTO.setSubmittedTickets(submittedTickets);
            }
            userDTOSet.add(userDTO);
        }
        return userDTOSet;
    }

    @Override
    public Set<OrganizationDTO> searchByOrganization(String term, String val) throws TermNotPresentException {
        if(!organization.getSearchableFields().contains(term)){
            throw new TermNotPresentException("Term " + term + " is not present in Organizations");
        }
        Set<Organization> organizationSet = new LinkedHashSet<>();
        Set<OrganizationDTO> organizationDTOSet = new LinkedHashSet<>();
        organizationSet.addAll(organization.searchByTermValue(term,val));
        for(Organization organization : organizationSet){
            OrganizationDTO organizationDTO = OrganizationToOrganizationDTOMapper.INSTANCE.organizationToOrganizationDTO(organization);
            if(organizationDTO.get_id() != null) {
                List<TicketDTO> tickets = ticket.searchByTermValue("organization_id", organizationDTO.get_id().toString())
                        .stream().map(t -> TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(t)).collect(Collectors.toList());
                organizationDTO.setTickets(tickets);
                List<UserDTO> users = user.searchByTermValue("organization_id", organizationDTO.get_id().toString())
                        .stream().map(u -> UserToUserDTOMapper.INSTANCE.userToUserDTO(u)).collect(Collectors.toList());
                organizationDTO.setUsers(users);
            }
            organizationDTOSet.add(organizationDTO);
        }
        return organizationDTOSet;
    }

    @Override
    public Set<TicketDTO> searchByTicket(String term, String val) throws TermNotPresentException {
        if(!ticket.getSearchableFields().contains(term)){
            throw new TermNotPresentException("Term " + term + " is not present in Tickets");
        }
        Set<Ticket> ticketSet = new LinkedHashSet<>();
        Set<TicketDTO> ticketDTOSet = new LinkedHashSet<>();
        ticketSet.addAll(ticket.searchByTermValue(term,val));
        for(Ticket ticket : ticketSet){
            TicketDTO ticketDTO = TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(ticket);
            if(ticketDTO.getOrganization_id() != null){
                ticketDTO.setOrganization(OrganizationToOrganizationDTOMapper.INSTANCE.organizationToOrganizationDTO(organization.searchById(ticketDTO.getOrganization_id())));
            }
            if(ticketDTO.getSubmitter_id() != null) {
                ticketDTO.setSubmitter(UserToUserDTOMapper.INSTANCE.userToUserDTO(user.searchById(ticketDTO.getSubmitter_id())));
            }
            if(ticketDTO.getAssignee_id() != null) {
                ticketDTO.setAssignee(UserToUserDTOMapper.INSTANCE.userToUserDTO(user.searchById(ticketDTO.getAssignee_id())));
            }
            ticketDTOSet.add(ticketDTO);
        }
        return ticketDTOSet;
    }

    @Override
    public Set<String> getUserFields() {
        return user.getSearchableFields();
    }

    @Override
    public Set<String> getTicketFields() {
        return ticket.getSearchableFields();
    }

    @Override
    public Set<String> getOrganizationFields() {
        return organization.getSearchableFields();
    }


}
