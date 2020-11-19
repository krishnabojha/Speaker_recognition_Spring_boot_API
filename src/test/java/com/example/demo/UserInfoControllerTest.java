package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.dao.UserRepo;
import com.example.demo.model.UserInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserInfoControllerTest {
	
	@Autowired                           
    private MockMvc mockMvc;  
                                                 
    @MockBean                           
    private UserInfoController userService; 
    
    @MockBean
    private UserRepo repo;	                                                      
                                            
    @Test
    public void addUserTest() throws Exception {
    	UserInfo obj = new UserInfo();
    	obj.setId(1);
    	obj.setUsername("nepali");
    	obj.setEmail("abc@gmail.com");
    	
    	String inputJson = this.mapToJson(obj);
    	String uri = "/addUser";
    	
    	Mockito.when(userService.addUser(Mockito.any(UserInfo.class))).thenReturn(obj);
    	
    	RequestBuilder requestbuilder = MockMvcRequestBuilders
    			.post(uri)
    			.accept(MediaType.APPLICATION_JSON).content(inputJson)
    			.contentType(MediaType.APPLICATION_JSON);
    	MvcResult result = mockMvc.perform(requestbuilder).andReturn();
    	MockHttpServletResponse response = result.getResponse();
    	
    	String outputInJson = response.getContentAsString();
    	
    	assertThat(outputInJson).isEqualTo(inputJson);
    
    }
    
    private String mapToJson(Object object) throws JsonProcessingException {
    	ObjectMapper objMapper = new ObjectMapper();
    	return objMapper.writeValueAsString(object);
    }
 
}

