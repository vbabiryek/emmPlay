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
@Table(name = "Policy_Enforcement_Rules")
public class PolicyEnforcementRulesE extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4687532627896869912L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer blockAfterDays;
	private String blockScope;
	private Integer wipeAfterDays;
	private Boolean preserveFrp;
	private String settingName;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "id", nullable = false)
	private PolicyE policyE;

	public PolicyEnforcementRulesE() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Integer getBlockAfterDays() {
		return blockAfterDays;
	}

	public void setBlockAfterDays(Integer blockAfterDays) {
		this.blockAfterDays = blockAfterDays;
	}

	public Integer getWipeAfterDays() {
		return wipeAfterDays;
	}

	public void setWipeAfterDays(Integer wipeAfterDays) {
		this.wipeAfterDays = wipeAfterDays;
	}

	public Boolean getPreserveFrp() {
		return preserveFrp;
	}

	public void setPreserveFrp(Boolean preserveFrp) {
		this.preserveFrp = preserveFrp;
	}

	public String getSettingName() {
		return settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public PolicyE getPolicyE() {
		return policyE;
	}

	public void setPolicyE(PolicyE policyE) {
		this.policyE = policyE;
	}


	public String getBlockScope() {
		return blockScope;
	}

	public void setBlockScope(String blockScope) {
		this.blockScope = blockScope;
	}
	
}
