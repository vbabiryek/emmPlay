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
@Table(name = "Auto_Update")
public class AppAutoUpdatePolicyE extends AuditModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2789599758850098706L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String appAutoUpdatePolicy;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "id", nullable = false)
	private PolicyE policyE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public PolicyE getPolicyE() {
		return policyE;
	}

	public void setPolicyE(PolicyE policyE) {
		this.policyE = policyE;
	}

	public String getAppAutoUpdatePolicy() {
		return appAutoUpdatePolicy;
	}

	public void setAppAutoUpdatePolicy(String appAutoUpdatePolicy) {
		this.appAutoUpdatePolicy = appAutoUpdatePolicy;
	}
	
}
