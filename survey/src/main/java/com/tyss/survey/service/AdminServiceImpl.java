package com.tyss.survey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.survey.dao.AdminDAO;
import com.tyss.survey.dto.Response;
import com.tyss.survey.dto.Survey;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO dao;

	@Override
	public boolean addSurvey(Survey survey) {
		return dao.addSurvey(survey);
	}

	@Override
	public List<Survey> retrive() {
		return dao.retrive();
	}

	@Override
	public Survey retriveSurvey(int surveyId) {
		return dao.retriveSurvey(surveyId);
	}

	@Override
	public boolean removeSurvey(int surveyId) {
		return dao.removeSurvey(surveyId);
	}

	@Override
	public boolean updateSurvey(Survey survey) {
		return dao.updateSurvey(survey);
	}

	@Override
	public boolean addResponse(Response response) {
		return dao.addResponse(response);
	}

	@Override
	public List<Response> fectchResponse() {
		return dao.fectchResponse();
	}
}
