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
@Table(name = "Advanced_Security_Overrides")
public class AdvancedSecurityOverridesE extends AuditModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1455166446885588287L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // possible conjunction variable
	private Boolean debuggingFeaturesAllowed;
	private String untrustedAppsPolicy;
	private Boolean safeBootDisabled;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false) //this is a bi-directional relationship
	@JoinColumn(name = "id", nullable = false)
	private PolicyE policyE;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUntrustedAppsPolicy() {
		return untrustedAppsPolicy;
	}
	public void setUntrustedAppsPolicy(String untrustedAppsPolicy) {
		this.untrustedAppsPolicy = untrustedAppsPolicy;
	}
	public PolicyE getPolicyE() {
		return policyE;
	}
	public void setPolicyE(PolicyE policyE) {
		this.policyE = policyE;
	}

	public Boolean getDebuggingFeaturesAllowed() {
		return debuggingFeaturesAllowed;
	}
	public void setDebuggingFeaturesAllowed(Boolean debuggingFeaturesAllowed) {
		this.debuggingFeaturesAllowed = debuggingFeaturesAllowed;
	}
	
	public Boolean getSafeBootDisabled() {
		return safeBootDisabled;
	}
	public void setSafeBootDisabled(Boolean safeBootDisabled) {
		this.safeBootDisabled = safeBootDisabled;
	}


}
