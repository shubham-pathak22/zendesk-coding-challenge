package com.zendesk.search;

import com.zendesk.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;

public class DictionaryTest {
    
    private File file;
    private Dictionary<User,Integer> dictionary;

    @Before
    public void init() {
        file = new File(this.getClass().getResource("/usersSample.json").getFile());
        dictionary = new Dictionary<>(file,User.class);
    }
    
    @Test
    public void testSearchById_idExists_shouldReturnObjectForId(){
        User user = dictionary.searchById(1);
        Assert.assertNotNull(user);
        Assert.assertEquals(1,user.get_id().intValue());
    }

    @Test
    public void testSearchById_idDoesNotExist_shouldReturnNull(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        User user = dictionary.searchById(10);
        Assert.assertNull(user);
    }

    @Test
    public void testSearchById_nullId_shouldThrowRuntimeException(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        try {
            dictionary.searchById(null);
            Assert.fail("IllegalArgumentException to be thrown");
        }catch (IllegalArgumentException e){
            Assert.assertThat(e.getMessage(),is("Id cannot be null"));
        }
    }

    @Test
    public void testSearchById_nullTerm_shouldThrowRuntimeException(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        try {
            dictionary.searchByTermValue(null,"test");
            Assert.fail("IllegalArgumentException to be thrown");
        }catch (IllegalArgumentException e){
            Assert.assertThat(e.getMessage(),is("Term cannot be null"));
        }
    }

    @Test
    public void testSearchTermValue_termExists_shouldReturnAllMatchingInList(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        List<User> list = dictionary.searchByTermValue("shared","false");
        Assert.assertNotNull(list);
        Assert.assertEquals(3,list.size());
        list.stream().forEach(l -> Assert.assertFalse(l.getShared()));
    }

    @Test
    public void testSearchTermValue_termNotExist_shouldReturnNull(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        Assert.assertNull(dictionary.searchByTermValue("z","false"));
    }

    @Test
    public void testSearchTermValue_valueNotExist_shouldReturnEmptyList(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        List<User> list = dictionary.searchByTermValue("shared","true");
        Assert.assertNotNull(list);
        Assert.assertEquals(0,list.size());
    }

    @Test
    public void testSearchTermValue_valueEmptyForATerm_shouldReturnAllMatchingInList(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        List<User> list = dictionary.searchByTermValue("alias","");
        Assert.assertNotNull(list);
        Assert.assertEquals(1,list.size());
        Assert.assertEquals("",list.get(0).getAlias());
    }

    @Test
    public void testSearchTermValue_valueCaseInsensitve_shouldReturnSameResult() {
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User, Integer> dictionary = new Dictionary<>(file, User.class);
        List<User> list1 = dictionary.searchByTermValue("shared", "false");
        List<User> list2 = dictionary.searchByTermValue("shared", "FALSE");
        Assert.assertEquals(3, list1.size());
        Assert.assertEquals(3, list2.size());
        for (int i = 0; i < list1.size(); i++) {
            Assert.assertEquals(list1.get(i).get_id(), list2.get(i).get_id());
        }
    }

    @Test
    public void testSearchTermValue_valueAsDateInYYYYMMDDFormat_shouldReturnAllMatchingYYYYMMDD() {
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User, Integer> dictionary = new Dictionary<>(file, User.class);
        List<User> list = dictionary.searchByTermValue("created_at", "2016-04-15");
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("2016-04-15",new SimpleDateFormat("YYYY-MM-dd").format(list.get(0).getCreated_at()));

    }

    @Test
    public void testSearchTermValue_valueInArray_shouldReturnAllMatchingValueInArray() {
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User, Integer> dictionary = new Dictionary<>(file, User.class);
        List<User> list = dictionary.searchByTermValue("tags", "Sutton");
        Assert.assertEquals(2, list.size());
        list.stream().forEach(l->l.getTags().contains("Sutton"));
    }


    @Test
    public void testGetSearchableFields_shouldReturnAllFieldsFromClass(){
        File file = new File(this.getClass().getResource("/usersSample.json").getFile());
        Dictionary<User,Integer> dictionary = new Dictionary<>(file,User.class);
        String[] fields = {"shared","last_login_at","role","signature","timezone","verified","created_at","active","external_id","locale","url","suspended","tags","phone","organization_id","name","alias","_id","email"};
        Set<String> fieldSet = new HashSet<>(Arrays.asList(fields));
        Assert.assertEquals(fieldSet, dictionary.getSearchableFields());
    }


}
