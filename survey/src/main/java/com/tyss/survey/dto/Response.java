package com.tyss.survey.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "Response")
public class Response implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column
	@GeneratedValue
	private int responseId;
	
	@Column
	private int surveyId;

	
	@Transient
	private String userName;

	
	@Column
	private String surveyName;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "surveyId")
	private List<ResponseQuestion> questions;
}
