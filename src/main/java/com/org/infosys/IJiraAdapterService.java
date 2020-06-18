package com.org.infosys;

import java.net.URISyntaxException;
import java.util.List;

import com.org.infosys.model.JiraResponse;

public interface IJiraAdapterService {
	
	public List<JiraResponse> pullFromJiraProject(String url, String userName, String password) throws URISyntaxException; 

}
