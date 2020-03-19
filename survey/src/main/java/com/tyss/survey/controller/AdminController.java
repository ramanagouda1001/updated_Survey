package com.tyss.survey.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.executable.ValidateOnExecution;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyss.survey.dto.AdminResponse;
import com.tyss.survey.dto.Response;
import com.tyss.survey.dto.Survey;
import com.tyss.survey.exception.AdminException;
import com.tyss.survey.service.AdminService;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("tyss")
@ValidateOnExecution
public class AdminController {

	@Autowired
	AdminService adminServices;

	@Autowired
	ServletContext context;

	@PostMapping(path = "/add-survey-details")
	public ResponseEntity<AdminResponse> addSurvey(@RequestParam("file") MultipartFile file,
			@RequestParam("user") String user) throws IOException {
		Survey survey = new ObjectMapper().readValue(user, Survey.class);
		survey.setFileName(file.getOriginalFilename());

		boolean isExist = new File(context.getRealPath("/surveyProfile/")).exists();
		if (isExist) {
			new File(context.getRealPath("/surveyProfile")).mkdir();
		}
		String fileName = file.getOriginalFilename();
		String modifString = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "."
				+ FilenameUtils.getExtension(fileName);
		File serverFile = new File(context.getRealPath("/surveyProfile/" + File.separator + modifString));
		try {
			FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
		} catch (Exception e) {
			throw new AdminException("Not a compatble file");
		}
		survey.setFileName("surveyProfile/" + modifString);
		if (adminServices.addSurvey(survey)) {
			AdminResponse response = new AdminResponse();
			response.setError(false);
			response.setData("Survey is added");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			AdminResponse response = new AdminResponse();
			response.setError(true);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/update-survey-details", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public AdminResponse updateSurvey(@RequestBody Survey survey) {
		AdminResponse adminResponse = new AdminResponse();
		if (adminServices.updateSurvey(survey)) {
			adminResponse.setError(false);
			adminResponse.setData("Survey is updated");
		}
		return adminResponse;
	}

	@GetMapping(path = "/survey-details", produces = MediaType.APPLICATION_JSON_VALUE)
	public AdminResponse getSurvey() {
		AdminResponse adminResponse = new AdminResponse();
		List<Survey> list = adminServices.retrive();
		if (!list.isEmpty()) {
			adminResponse.setError(false);
			adminResponse.setData(list);
		} else {
			adminResponse.setError(true);
			adminResponse.setMessage("Your Surveys are coming soon...");
		}
		return adminResponse;
	}

	@GetMapping(path = "/{surveyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AdminResponse getSurveyName(@PathVariable(name = "surveyId") int surveyId) {
		AdminResponse adminResponse = new AdminResponse();
		Survey survey = adminServices.retriveSurvey(surveyId);
		if (survey != null) {
			adminResponse.setError(false);
			adminResponse.setData(survey);
		}
		return adminResponse;
	}

	@DeleteMapping(path = "/{surveyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public AdminResponse deleteSurveyName(@PathVariable int surveyId) {
		AdminResponse adminResponse = new AdminResponse();

		if (adminServices.removeSurvey(surveyId)) {
			adminResponse.setError(false);
			adminResponse.setMessage("Survey is deleted");
		}
		return adminResponse;
	}

	@PostMapping(path = "saving-response", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public AdminResponse savingResponse(@RequestBody Response response) {
		AdminResponse adminResponse = new AdminResponse();

		if (adminServices.addResponse(response)) {
			System.out.println(response);
			adminResponse.setError(false);
			adminResponse.setData("Your Survey is Successfully Recorded");
		}
		return adminResponse;
	}
	
	
	@GetMapping(path ="survey-response",produces = MediaType.APPLICATION_JSON_VALUE)
	public AdminResponse getingResponse()
	{
		AdminResponse response=new AdminResponse();
		List<Response> list=adminServices.fectchResponse();
		if(list!=null)
		{
			response.setError(false);
			response.setData(list);
		}
		return response;
	}
}