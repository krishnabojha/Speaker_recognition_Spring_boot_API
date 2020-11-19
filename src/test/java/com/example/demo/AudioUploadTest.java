package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AudioUploadTest {  
                                                 
    @MockBean                           
    private UserInfoController userService; 
    
    @MockBean
    private CallPrediction callFlaskAPI;	                                                      
                                            
    @Test
    public void uploadAudioTest() throws Exception {
    	
    	String audioPath = "Train_voice_data/0_george_0.wav";
    	String state = "train";
    	
    	when(callFlaskAPI.makePrediction(audioPath, state)).thenReturn("200");
    	
    	assertEquals("200", callFlaskAPI.makePrediction(audioPath, state));
    	
    	
    }
}
