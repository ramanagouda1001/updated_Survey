package com.tyss.survey.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tyss.survey.dto.Options;
import com.tyss.survey.dto.Question;
import com.tyss.survey.dto.Response;
import com.tyss.survey.dto.ResponseOption;
import com.tyss.survey.dto.ResponseQuestion;
import com.tyss.survey.dto.Survey;
import com.tyss.survey.exception.AdminException;

@SpringBootTest
class AdminDAOTest {

	@Autowired
	private AdminDAO dao;
	
	@Test
	void testAddSurvey() {
		Survey survey=new Survey();
		Question question=new Question();
		Options option=new Options();
		option.setValue("hello");
		question.setQuestionName("what is your name?");
		question.setQuestionType("mcq");
		question.setOptions(Arrays.asList(option));
		survey.setSurveyName("hello");
		survey.setQuestions(Arrays.asList(question));
		survey.setDescription("fbgjhbsdgjsdgjsf");
		try {
			boolean flag=dao.addSurvey(survey);
			assertEquals(flag, true);
			}
			catch (Exception e) {
				assertThrows(AdminException.class, () -> {
					dao.addSurvey(survey);
			});
		}		
	}

	@Test
	void testRetrive() {
		assertNotNull(dao.retrive());
	}

	@Test
	void testRetriveSurvey() {
		assertNotNull(dao.retriveSurvey(1));
	}

	@Test
	void testRemoveSurvey() {
		try {
			boolean flag=dao.removeSurvey(1);
			assertEquals(flag, true);
			}
			catch (Exception e) {
				assertThrows(AdminException.class, () -> {
					dao.removeSurvey(1);
			});
		}		
	}

	@Test
	void testUpdateSurvey() {
		Survey survey=new Survey();
		Question question=new Question();
		Options option=new Options();
		option.setValue("hello");
		question.setQuestionName("what is your name?");
		question.setQuestionType("mcq");
		question.setOptions(Arrays.asList(option));
		survey.setSurveyName("hello");
		survey.setQuestions(Arrays.asList(question));
		survey.setDescription("fbgjhbsdgjsdgjsf");
		try {
			boolean flag=dao.updateSurvey(survey);
			assertEquals(flag, true);
			}
			catch (Exception e) {
				assertThrows(AdminException.class, () -> {
					dao.updateSurvey(survey);
			});
		}		
	}

	@Test
	void testAddResponse() {
		Response response=new Response();
		ResponseQuestion responseQuestion=new ResponseQuestion();
		ResponseOption responseOption=new ResponseOption();
		responseOption.setValue("hello");
		responseOption.setSelected(true);
		responseQuestion.setQuestionName("what is ypur name");
		responseQuestion.setQuestionType("mcq");
		responseQuestion.setOptions(Arrays.asList(responseOption));
		response.setSurveyName("helo");
		response.setQuestions(Arrays.asList(responseQuestion));
		try {
			boolean flag=dao.addResponse(response);
			assertEquals(flag, true);
			}
			catch (Exception e) {
				assertThrows(AdminException.class, () -> {
					dao.addResponse(response);
			});
		}	
	}

}
