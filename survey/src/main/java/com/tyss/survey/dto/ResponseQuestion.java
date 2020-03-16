package com.tyss.survey.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name="ResponseQuestion")
public class ResponseQuestion  {


	@Id
	@GeneratedValue
	@Column
	private int responseQuestionId;
	
	@Column
	private String questionName;
	
	@Column
	private String questionType;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "surveyId")
	private Response response;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "response_question_id")
	private List<ResponseOption> options;
	
}
