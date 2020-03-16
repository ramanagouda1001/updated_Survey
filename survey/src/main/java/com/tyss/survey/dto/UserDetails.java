package com.tyss.survey.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "userName")
public class UserDetails {

	@Id
	@GeneratedValue
	@Column
	private int userId;
	
	@Column
	private int surveyId;

	@Column
	private String userName;

	@Column
	private String surveyName;
	
}
