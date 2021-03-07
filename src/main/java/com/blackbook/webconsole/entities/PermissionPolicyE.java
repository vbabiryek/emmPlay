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
@Table(name = "Permission_Grants")
public class PermissionPolicyE extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 470951508771302345L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String permission;
	private String policy;
	

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "id", nullable = false)
	private PolicyE policyE;


	public PermissionPolicyE() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public PolicyE getPolicyE() {
		return policyE;
	}

	public void setPolicyE(PolicyE policyE) {
		this.policyE = policyE;
	}

	

}
