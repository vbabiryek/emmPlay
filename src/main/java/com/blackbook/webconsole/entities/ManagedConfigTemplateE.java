package com.blackbook.webconsole.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Managed_Config_Template_Policy")
public class ManagedConfigTemplateE extends AuditModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5848934859440522794L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String templateId;
	
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
