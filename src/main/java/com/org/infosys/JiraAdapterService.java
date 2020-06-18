package com.org.infosys;

import java.net.URI;
import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import java.io.IOException;
import java.net.URI;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import com.org.infosys.model.JiraResponse;

public class JiraAdapterService implements IJiraAdapterService {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Override
	public List<JiraResponse> pullFromJiraProject(String url, String userName, String password) {
		List<JiraResponse> jiraTaskStore = new ArrayList<>();
		com.atlassian.jira.rest.client.api.JiraRestClient client = null;
		ObjectMapper obm = new ObjectMapper();
		String taskStore=null;
		try {

			AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
			URI uri = new URI(url);
			client = factory.createWithBasicHttpAuthentication(uri, userName, password);
			IssueRestClient issueClient = client.getIssueClient();
			SearchRestClient searchRestClient = client.getSearchClient();
			String jql = "project = iSolve_VSUPP1816";

			int maxPerQuery = 7;
			int startIndex = 0;

			String test = "";
			Promise<SearchResult> searchResult = searchRestClient.searchJql(jql, maxPerQuery, startIndex, null);
			SearchResult results = searchResult.claim();

			for (BasicIssue issue1 : ((SearchResult) results).getIssues()) {
				JiraResponse jts = new JiraResponse();
				Issue issue = issueClient.getIssue(issue1.getKey()).claim();
				jts.setKey(issue.getKey());
				jts.setSummary(issue.getSummary());
				jts.setDescription(issue.getDescription());
				jts.setStatusName(issue.getStatus().getName());
				jts.setIssueType(issue.getIssueType().getName());
				jts.setAsigneeDisplayName(issue.getAssignee().getDisplayName());
				jts.setReporterDisplayName(issue.getReporter().getDisplayName());
				jts.setCreationDate(issue.getCreationDate().toString());
				jts.setClosureDate(issue.getUpdateDate().toString());
				if (null != issue.getResolution())
					jts.setResolution(issue.getResolution().toString());
				else
					jts.setResolution("");
				if (null != issue.getPriority())
					jts.setPriority(issue.getPriority().toString());
					//jts.setPriority("");
				else
					jts.setPriority("");
				jts.setProject(issue.getProject().toString());
				jiraTaskStore.add(jts);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
		    taskStore = obm.writeValueAsString(jiraTaskStore);
			
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(taskStore);
		return jiraTaskStore;
	}
}
