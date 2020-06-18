package com.org.infosys.model;

import lombok.Data;

@Data
public class JiraResponse {

	private String key;
	private String summary;
	private String description;
	private String statusName;
	private String issueTypeName;
	private String asigneeDisplayName;
	private String reporterDisplayName;
	private String creationDate;
	private String closureDate;
	private String resolution;
	private String priority;
	private String project;	
	private String issueType;

}
