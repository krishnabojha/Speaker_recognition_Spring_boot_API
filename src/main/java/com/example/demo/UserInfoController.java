package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.UserRepo;
import com.example.demo.model.UserInfo;

@RestController
public class UserInfoController {

	@Autowired
	
	UserRepo repo;
	
	@PostMapping("/addUser")
	public UserInfo addUser(@RequestBody UserInfo info) {
		System.out.println(repo);
		repo.save(info);
		return info;
	}
	
	@RequestMapping(value = "/trainVoice", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> trainVoice(@RequestParam("file") MultipartFile file) throws IOException {
		
//		save audio file to the directory 
		String dir_train_name = "Train_voice_data/";
		File convertfile = new File(dir_train_name+ file.getOriginalFilename());
		convertfile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertfile);
		fout.write(file.getBytes());
		fout.close();
		
//		make a Spectrogram of the audio file to train the voice flask api call
		CallPrediction trainVoice = new CallPrediction();
		String response = trainVoice.makePrediction(dir_train_name + file.getOriginalFilename(), "train");
		System.out.println("this is train response: "+ response);
		
		return new ResponseEntity<>("Audio file uploaded to Train", HttpStatus.OK);

}
	@RequestMapping(value = "/testVoice", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> testVoice(@RequestParam("file") MultipartFile file) throws IOException {
		
		String dir_test_name = "Test_voice_data/";
//		save the audio file sent for the test the user
		File convertfile = new File(dir_test_name+ file.getOriginalFilename());
		convertfile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertfile);
		fout.write(file.getBytes());		
		fout.close();
		
//		make a Spectrogram of the audio file sent to test the voice
		CallPrediction trainVoice = new CallPrediction();
		String response = trainVoice.makePrediction(dir_test_name + file.getOriginalFilename(), "test");
		System.out.println("this is test response: "+ response);
		
//		String[] splited_user_code = file.getOriginalFilename().split("_", 3);
//		String merged_code = splited_user_code[0]+'_'+splited_user_code[1];
//		boolean login_status = false;
//		System.out.println(response);
//		System.out.println(merged_code);
//		if (merged_code == response) {
//			login_status = true;
//			System.out.println("Entered to the positive state");
//		}
//		System.out.println("this is user code: "+ merged_code);
		return new ResponseEntity<>("The Speaker unique Code : "+ response, HttpStatus.OK);

}
	
}
