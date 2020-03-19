package com.tyss.survey.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tyss.survey.dao.AdminDAO;

@SpringBootTest
class AdminServiceTest2 {

	
	@Autowired
	private AdminService service;
	
	@MockBean
	private AdminDAO dao;
	
	
//	@Test
//	void testAddSurvey() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testRetrive() {
//		fail("Not yet implemented");
//	}

	@Test
	void testRetriveSurvey() {
	//	when(dao.retrive()).thenReturn();
	}

//	@Test
//	void testRemoveSurvey() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testUpdateSurvey() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddResponse() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFectchResponse() {
//		fail("Not yet implemented");
//	}

}
