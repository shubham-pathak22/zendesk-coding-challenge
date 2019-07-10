package com.zendesk.service;

import com.zendesk.dto.OrganizationDTO;
import com.zendesk.dto.TicketDTO;
import com.zendesk.dto.UserDTO;
import com.zendesk.exception.TermNotPresentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;

public class SearchServiceImplTest {

    private SearchService searchService;

    @Before
    public void init() {
        File user = new File(this.getClass().getResource("/usersSample.json").getFile());
        File ticket = new File(this.getClass().getResource("/ticketsSample.json").getFile());
        File org = new File(this.getClass().getResource("/organizationsSample.json").getFile());
        searchService = new SearchServiceImpl(user,ticket,org);
    }

    @Test
    public void testSearchUser_WithID_shouldReturnUserDTOWithOrgAndTicketDTOs() throws TermNotPresentException {
        Set<UserDTO> userDTOS =  searchService.searchByUser("_id","1");
        List<UserDTO> userDTOList = new ArrayList<>(userDTOS);

        Assert.assertEquals(1,userDTOS.size());
        Assert.assertNotNull(userDTOList.get(0).getOrganization());
        Assert.assertNotNull(userDTOList.get(0).getSubmittedTickets());
        Assert.assertEquals(1,userDTOList.get(0).get_id().intValue());
        Assert.assertEquals(userDTOList.get(0).getOrganization_id(),userDTOList.get(0).getOrganization().get_id());
        userDTOList.get(0).getSubmittedTickets().stream().forEach(s -> Assert.assertEquals(userDTOList.get(0).get_id(),s.getSubmitter_id()));
    }

    @Test
    public void testSearchTicket_WithID_shouldReturnTicketDTOWithOrgAndUserDTOs() throws TermNotPresentException {
        Set<TicketDTO> ticketDTOS =  searchService.searchByTicket("_id","436bf9b0-1147-4c0a-8439-6f79833bff5b");
        List<TicketDTO> ticketDTOList = new ArrayList<>(ticketDTOS);

        Assert.assertEquals(1,ticketDTOS.size());
        Assert.assertNotNull(ticketDTOList.get(0).getOrganization());
        Assert.assertNotNull(ticketDTOList.get(0).getSubmitter());
        Assert.assertNotNull(ticketDTOList.get(0).getAssignee());
        Assert.assertEquals("436bf9b0-1147-4c0a-8439-6f79833bff5b",ticketDTOList.get(0).get_id());
        Assert.assertEquals(ticketDTOList.get(0).getOrganization_id(),ticketDTOList.get(0).getOrganization().get_id());
        Assert.assertEquals(ticketDTOList.get(0).getSubmitter_id(),ticketDTOList.get(0).getSubmitter().get_id());
        Assert.assertEquals(ticketDTOList.get(0).getAssignee_id(),ticketDTOList.get(0).getAssignee().get_id());

    }

    @Test
    public void testSearchOrganization_WithID_shouldReturnOrganizationDTOWithUserAndTicketDTOs() throws TermNotPresentException {
        Set<OrganizationDTO> organizationDTOS =  searchService.searchByOrganization("_id","101");
        List<OrganizationDTO> organizationDTOList = new ArrayList<>(organizationDTOS);

        Assert.assertEquals(1,organizationDTOS.size());
        Assert.assertNotNull(organizationDTOList.get(0).getTickets());
        Assert.assertNotNull(organizationDTOList.get(0).getUsers());
        Assert.assertEquals(101,organizationDTOList.get(0).get_id().intValue());
        organizationDTOList.get(0).getUsers().stream().forEach(s -> Assert.assertEquals(organizationDTOList.get(0).get_id(),s.getOrganization_id()));
        organizationDTOList.get(0).getTickets().stream().forEach(s -> Assert.assertEquals(organizationDTOList.get(0).get_id(),s.getOrganization_id()));
    }

    @Test
    public void getSearchableFieldsUser_ValidInitialization_shouldReturnNonEmptySet(){
        Assert.assertNotNull(searchService.getUserFields());
        Assert.assertTrue(searchService.getUserFields().size() > 0);
    }

    @Test
    public void getSearchableFieldsTicket_ValidInitialization_shouldReturnNonEmptySet(){
        Assert.assertNotNull(searchService.getTicketFields());
        Assert.assertTrue(searchService.getTicketFields().size() > 0);
    }

    @Test
    public void getSearchableFieldsOrganization_ValidInitialization_shouldReturnNonEmptySet(){
         Assert.assertNotNull(searchService.getOrganizationFields());
         Assert.assertTrue(searchService.getOrganizationFields().size() > 0);
    }

    @Test
    public void testSearchUser_InvalidTerm_shouldThrowException() {
        try {
            searchService.searchByUser("invalidTerm","invalidValue");
            Assert.fail("TermNotPresentException should be thrown");
        } catch (TermNotPresentException e) {
            Assert.assertThat(e.getMessage(),is("Term invalidTerm is not present in Users"));
        }
    }

    @Test
    public void testSearchTicket_InvalidTerm_shouldThrowException() {
        try {
            searchService.searchByTicket("invalidTerm","invalidValue");
            Assert.fail("TermNotPresentException should be thrown");
        } catch (TermNotPresentException e) {
            Assert.assertThat(e.getMessage(),is("Term invalidTerm is not present in Tickets"));
        }
    }

    @Test
    public void testSearchOrganization_InvalidTerm_shouldThrowException() {
        try {
            searchService.searchByOrganization("invalidTerm","invalidValue");
            Assert.fail("TermNotPresentException should be thrown");
        } catch (TermNotPresentException e) {
            Assert.assertThat(e.getMessage(),is("Term invalidTerm is not present in Organizations"));
        }
    }
}
