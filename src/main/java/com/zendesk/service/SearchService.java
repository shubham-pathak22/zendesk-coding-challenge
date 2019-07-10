package com.zendesk.service;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.dto.TicketDTO;
import com.zendesk.dto.UserDTO;
import com.zendesk.exception.TermNotPresentException;

import java.util.Set;

public interface SearchService {

    Set<UserDTO> searchByUser(String term,String val) throws TermNotPresentException;
    Set<OrganizationDTO> searchByOrganization(String term, String val) throws TermNotPresentException;
    Set<TicketDTO> searchByTicket(String term, String val) throws TermNotPresentException;

    Set<String> getUserFields();
    Set<String> getTicketFields();
    Set<String> getOrganizationFields();
}
