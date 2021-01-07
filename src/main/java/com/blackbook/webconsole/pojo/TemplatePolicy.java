package com.blackbook.webconsole.pojo;

import java.io.Serializable;
import java.util.Map;


public class TemplatePolicy implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6239168042362114745L;

	private Long id;
	private Long applicationPolicyId;
	
	private String templateId;
	private Map<String, String> configurationVariables;
	
	public TemplatePolicy(Long id, Long applicationPolicyId, String templateId) {
		this.id = id;
		this.applicationPolicyId = applicationPolicyId;
		this.templateId = templateId;
	}
	

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Map<String, String> getConfigurationVariables() {
		return configurationVariables;
	}

	public void setConfigurationVariables(Map<String, String> configurationVariables) {
		this.configurationVariables = configurationVariables;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplicationPolicyId() {
		return applicationPolicyId;
	}

	public void setApplicationPolicyId(Long applicationPolicyId) {
		this.applicationPolicyId = applicationPolicyId;
	}


}
