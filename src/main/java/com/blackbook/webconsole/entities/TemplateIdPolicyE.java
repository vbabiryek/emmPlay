package com.blackbook.webconsole.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Template_Id")
public class TemplateIdPolicyE extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6239168042362114745L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String templateId;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "id", nullable = false)
	private PolicyE policyE;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
