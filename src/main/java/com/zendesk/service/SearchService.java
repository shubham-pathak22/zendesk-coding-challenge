package com.zendesk.service;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.dto.TicketDTO;
import com.zendesk.dto.UserDTO;

import java.util.List;

public interface SearchService {

    List<UserDTO> searchByUser(String term,String val);
    List<OrganizationDTO> searchByOrganization(String term, String val);
    List<TicketDTO> searchByTicket(String term, String val);
}
