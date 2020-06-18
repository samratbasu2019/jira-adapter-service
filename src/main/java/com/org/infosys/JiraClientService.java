package com.org.infosys;

import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.org.infosys.model.JiraResponse;

public class JiraClientService {
	
	public static void main(String[] args) {
		IJiraAdapterService iads = new JiraAdapterService();
		String url = "https://infosysjira/";
		String userName = "deepar";
		String password = "Docker123$$";		
		List<JiraResponse> jiradto;
		try {
			jiradto = iads.pullFromJiraProject(url, userName, password);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		

	}

}
