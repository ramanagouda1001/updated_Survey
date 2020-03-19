package com.tyss.survey.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tyss.survey.dto.Response;
import com.tyss.survey.dto.Survey;
import com.tyss.survey.dto.UserDetails;
import com.tyss.survey.exception.AdminException;

@Repository
public class AdminDAOImpl implements AdminDAO {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public boolean addSurvey(Survey survey) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			survey.setActive(true);
			survey.setFlag(true);
			survey.setActiveUser(true);
			entityManager.persist(survey);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Survey> retrive() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String jpql = "select e FROM Survey e  where e.isActive=:isActive ORDER BY startDate ";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("isActive", true);
		List<Survey> list = query.getResultList();
		LocalDate s = java.time.LocalDate.now();
		for (Survey survey : list) {
			if (survey.getStartDate().isBefore(s)) {
				if (survey.getEndDate().isAfter(s)) {
					survey.setFlag(true);
				} else {
					survey.setFlag(false);
				}
			} else if (survey.getStartDate().isAfter(s)) {
				survey.setFlag(false);
			}
			String jpql1="select u FROM UserDetails u where u.userName=:userName";
			Query query2 = entityManager.createQuery(jpql1);
			query2.setParameter("userName",survey.getUserName());
			List<UserDetails> list2=query2.getResultList();
			for(UserDetails userDetails:list2)
			{
				if(userDetails.getSurveyId()==survey.getSurveyId() )
					survey.setActiveUser(false);
			}
		}
		
		return list;
	}

	@Override
	public Survey retriveSurvey(int surveyId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String jpql = "from Survey where surveyId=:surveyId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("surveyId", surveyId);
		Survey survey = (Survey) query.getSingleResult();
		return survey;
	}

	@Override
	public boolean removeSurvey(int surveyId) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		String jpql = "from Survey where surveyId=:surveyId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("surveyId", surveyId);
		Survey survey = (Survey) query.getSingleResult();
		survey.setActive(false);
		entityManager.persist(survey);
		entityTransaction.commit();
		return true;

	}

	@Override
	public boolean updateSurvey(Survey survey) {
		try {
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction entityTransaction = entityManager.getTransaction();
			String jpql = "from Survey where surveyId=:surveyId";
			Query query = entityManager.createQuery(jpql);
			query.setParameter("surveyId", survey.getSurveyId());
			Survey surveyObject = (Survey) query.getSingleResult();
			entityTransaction.begin();
			Survey object = surveyObject;
			entityManager.remove(surveyObject);
			object.setDescription(survey.getDescription());
			object.setStartDate(survey.getStartDate());
			object.setEndDate(survey.getEndDate());
			object.setQuestions(survey.getQuestions());
			entityManager.persist(object);
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			throw new AdminException("The Survey name that you have not already exits !!");
		}
	}

	@Override
	public boolean addResponse(Response response) {
		System.out.println(response);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			UserDetails details=new UserDetails();
			details.setSurveyId(response.getSurveyId());
			details.setUserName(response.getUserName());
			details.setSurveyName(response.getSurveyName());
			entityManager.persist(details);
			entityManager.persist(response);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			return false;
		}
	}

	@Override
	public List<Response> fectchResponse() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		String jpql = "select r FROM Response r";
		Query query = entityManager.createQuery(jpql);
		List<Response> list = query.getResultList();
		return list;
	}
}
