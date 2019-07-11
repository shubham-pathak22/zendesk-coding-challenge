package com.zendesk.service;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.dto.TicketDTO;
import com.zendesk.dto.UserDTO;
import com.zendesk.exception.TermNotPresentException;

import java.util.Set;

/**
 *
 *  Service defining methods to search
 *  user, tickets and organizations
 *
 */
public interface SearchService {

    /**
     * Search by user attributes
     * @param term attribute/key
     * @param val  value to be searched by
     * @return {@link UserDTO} containing  user details and relevant ticket and organization information
     * @throws TermNotPresentException if the term is not present
     */
    Set<UserDTO> searchByUser(String term,String val) throws TermNotPresentException;

    /**
     * Search by organization attributes
     * @param term attribute/key
     * @param val  value to be searched by
     * @return {@link OrganizationDTO} containing organization details and relevant user and ticket information
     * @throws TermNotPresentException if the term is not present
     */
    Set<OrganizationDTO> searchByOrganization(String term, String val) throws TermNotPresentException;

    /**
     * Search by ticket attributes
     * @param term attribute/key
     * @param val  value to be searched by
     * @return {@link TicketDTO} containing ticket details and relevant user and organization information
     * @throws TermNotPresentException if the term is not present
     */
    Set<TicketDTO> searchByTicket(String term, String val) throws TermNotPresentException;

    /**
     *
     * @return {@link Set} containing searchable user fields
     */
    Set<String> getUserFields();

    /**
     *
     * @return {@link Set} containing searchable ticket fields
     */
    Set<String> getTicketFields();

    /**
     *
     * @return {@link Set} containing searchable organization fields
     */
    Set<String> getOrganizationFields();
}
