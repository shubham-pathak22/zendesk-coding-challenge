package com.zendesk.service;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.dto.TicketDTO;
import com.zendesk.dto.UserDTO;
import com.zendesk.exception.TermNotPresentException;

import java.util.List;

public interface SearchService {

    List<UserDTO> searchByUser(String term,String val) throws TermNotPresentException;
    List<OrganizationDTO> searchByOrganization(String term, String val) throws TermNotPresentException;
    List<TicketDTO> searchByTicket(String term, String val) throws TermNotPresentException;
}
