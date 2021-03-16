package com.blackbook.webconsole.pojo;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ManagedConfigurationTemplateE implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6239168042362114745L;

	@JsonIgnore
	private Long id;
	@JsonIgnore
	private Long applicationPolicyId;
	
	private String templateId;
	private Map<String, String> configurationVariables;
	
	public ManagedConfigurationTemplateE(Long id, Long applicationPolicyId, String templateId) {
		this.id = id;
		this.applicationPolicyId = applicationPolicyId;
		this.templateId = templateId;
	}
	

	public ManagedConfigurationTemplateE() {
		// TODO Auto-generated constructor stub
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
