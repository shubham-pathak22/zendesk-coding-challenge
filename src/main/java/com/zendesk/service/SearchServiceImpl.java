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
import com.zendesk.search.Index;
import com.zendesk.search.Searchable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchServiceImpl implements SearchService{

    private Searchable<User> user;
    private Searchable<Ticket> ticket;
    private Searchable<Organization> organization;


    public SearchServiceImpl(){
        user = new Index<>("/Users/shubham/projects/zendeskcodingchallenge/src/main/resources/users.json",User.class);
        ticket = new Index<>("/Users/shubham/projects/zendeskcodingchallenge/src/main/resources/tickets.json",Ticket.class);
        organization = new Index<>("/Users/shubham/projects/zendeskcodingchallenge/src/main/resources/organizations.json",Organization.class);

    }
    @Override
    public List<UserDTO> searchByUser(String term, String val) throws TermNotPresentException {
        if(!user.getSearchableFields().contains(term)){
            throw new TermNotPresentException("Term " + term + " not present in Users");
        }
        List<User> userList = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.addAll(user.searchByTermValue(term,val));
        for(User user : userList){
            UserDTO userDTO = UserToUserDTOMapper.INSTANCE.userToUserDTO(user);
            if(userDTO.getOrganization_id() != null){
                userDTO.setOrganizationDTO(OrganizationToOrganizationDTOMapper.INSTANCE.organizationToOrganizationDTO(organization.searchById(userDTO.getOrganization_id().toString())));
            }
            List<TicketDTO> assignedTickets = ticket.searchByTermValue("assignee_id",userDTO.get_id().toString())
                    .stream().map(t -> TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(t)).collect(Collectors.toList());
            userDTO.setAssignedTickets(assignedTickets);
            List<TicketDTO> submittedTickets = ticket.searchByTermValue("submitter_id",userDTO.get_id().toString())
                    .stream().map(t -> TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(t)).collect(Collectors.toList());
            userDTO.setSubmittedTickets(submittedTickets);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public List<OrganizationDTO> searchByOrganization(String term, String val) throws TermNotPresentException {
        if(!organization.getSearchableFields().contains(term)){
            throw new TermNotPresentException("Term " + term + " not present in Organizations");
        }
        List<Organization> organizationList = new ArrayList<>();
        List<OrganizationDTO> organizationDTOList = new ArrayList<>();
        organizationList.addAll(organization.searchByTermValue(term,val));
        for(Organization organization : organizationList){
            OrganizationDTO organizationDTO = OrganizationToOrganizationDTOMapper.INSTANCE.organizationToOrganizationDTO(organization);
            List<TicketDTO> tickets = ticket.searchByTermValue("organization_id",organizationDTO.get_id().toString())
                    .stream().map(t -> TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(t)).collect(Collectors.toList());
            organizationDTO.setTickets(tickets);
            List<UserDTO> users = user.searchByTermValue("organization_id",organizationDTO.get_id().toString())
                    .stream().map(u -> UserToUserDTOMapper.INSTANCE.userToUserDTO(u)).collect(Collectors.toList());
            organizationDTO.setUsers(users);
            organizationDTOList.add(organizationDTO);
        }
        return organizationDTOList;
    }

    @Override
    public List<TicketDTO> searchByTicket(String term, String val) throws TermNotPresentException {
        if(!ticket.getSearchableFields().contains(term)){
            throw new TermNotPresentException("Term " + term + " not present in Tickets");
        }
        List<Ticket> ticketList = new ArrayList<>();
        List<TicketDTO> ticketDTOList = new ArrayList<>();
        ticketList.addAll(ticket.searchByTermValue(term,val));
        for(Ticket ticket : ticketList){
            TicketDTO ticketDTO = TicketToTicketDTOMapper.INSTANCE.ticketToTicketDTO(ticket);
            if(ticketDTO.getOrganization_id() != null){
                ticketDTO.setOrganization(OrganizationToOrganizationDTOMapper.INSTANCE.organizationToOrganizationDTO(organization.searchById(ticketDTO.getOrganization_id().toString())));
            }
            ticketDTO.setSubmitter(UserToUserDTOMapper.INSTANCE.userToUserDTO(user.searchById(ticketDTO.getSubmitter_id().toString())));
            ticketDTO.setAssignee(UserToUserDTOMapper.INSTANCE.userToUserDTO(user.searchById(ticketDTO.getAssignee_id().toString())));
            ticketDTOList.add(ticketDTO);
        }
        return ticketDTOList;
    }


}
