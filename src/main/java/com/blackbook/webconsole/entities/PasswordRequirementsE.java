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
@Table(name = "PasswordRequirements")
public final class PasswordRequirementsE extends AuditModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3658422715842016597L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer passwordMinLength;
	private Integer passwordMinimumLetters;
	private Integer passwordMinimumLowerCase;
	private Integer passwordMinimumNonLetter;
	private Integer passwordMinimumNumeric;
	private Integer passwordMinimumSymbols;
	private Integer passwordMinimumUpperCase;
	private String passwordQuality;
	private Integer passwordHistoryLength;
	private Integer maximumFailedPasswordsForWipe;
	private String passwordExpirationTimeout;
	private String passwordScope;
	private String requirePasswordUnlock;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "id", nullable = false)
	private PolicyE policyE;
	

	public PasswordRequirementsE() {
		
	}

	public Long getId() {
		return id;
	}
	public PasswordRequirementsE setId(Long id) {
		this.id = id;
		return this;
	}

	public PasswordRequirementsE setPasswordMinimumLength(Integer val) {
		this.passwordMinLength = val;
		return this;
	}
	
	public Integer getPasswordMinLength() {
		return passwordMinLength;
	}
	
	public Integer getPasswordMinimumLetters() {
		return passwordMinimumLetters;
	}

	public PasswordRequirementsE setPasswordMinimumLetters(Integer passwordMinimumLetters) {
		this.passwordMinimumLetters = passwordMinimumLetters;
		return this;
	}

	public Integer getPasswordMinimumLowerCase() {
		return passwordMinimumLowerCase;
	}

	public PasswordRequirementsE setPasswordMinimumLowerCase(Integer passwordMinimumLowerCase) {
		this.passwordMinimumLowerCase = passwordMinimumLowerCase;
		return this;
	}

	public Integer getPasswordMinimumNonLetter() {
		return passwordMinimumNonLetter;
	}

	public PasswordRequirementsE setPasswordMinimumNonLetter(Integer passwordMinimumNonLetter) {
		this.passwordMinimumNonLetter = passwordMinimumNonLetter;
		return this;
	}

	public Integer getPasswordMinimumNumeric() {
		return passwordMinimumNumeric;
	}

	public PasswordRequirementsE setPasswordMinimumNumeric(Integer passwordMinimumNumeric) {
		this.passwordMinimumNumeric = passwordMinimumNumeric;
		return this;
	}

	public Integer getPasswordMinimumSymbols() {
		return passwordMinimumSymbols;
	}

	public PasswordRequirementsE setPasswordMinimumSymbols(Integer passwordMinimumSymbols) {
		this.passwordMinimumSymbols = passwordMinimumSymbols;
		return this;
	}

	public Integer getPasswordMinimumUpperCase() {
		return passwordMinimumUpperCase;
	}

	public PasswordRequirementsE setPasswordMinimumUpperCase(Integer passwordMinimumUpperCase) {
		this.passwordMinimumUpperCase = passwordMinimumUpperCase;
		return this;
	}

	public String getPasswordQuality() {
		return passwordQuality;
	}

	public PasswordRequirementsE setPasswordQuality(String passwordQuality) {
		this.passwordQuality = passwordQuality;
		return this;
	}

	public Integer getPasswordHistoryLength() {
		return passwordHistoryLength;
	}

	public PasswordRequirementsE setPasswordHistoryLength(Integer passwordHistoryLength) {
		this.passwordHistoryLength = passwordHistoryLength;
		return this;
	}

	public Integer getMaximumFailedPasswordsForWipe() {
		return maximumFailedPasswordsForWipe;
	}

	public PasswordRequirementsE setMaximumFailedPasswordsForWipe(Integer maximumFailedPasswordsForWipe) {
		this.maximumFailedPasswordsForWipe = maximumFailedPasswordsForWipe;
		return this;
	}

	public String getPasswordExpirationTimeout() {
		return passwordExpirationTimeout;
	}

	public PasswordRequirementsE setPasswordExpirationTimeout(String string) {
		this.passwordExpirationTimeout = string;
		return this;
	}

	public String getPasswordScope() {
		return passwordScope;
	}

	public PasswordRequirementsE setPasswordScope(String passwordScope) {
		this.passwordScope = passwordScope;
		return this;
	}

	public String getRequirePasswordUnlock() {
		return requirePasswordUnlock;
	}

	public PasswordRequirementsE setRequirePasswordUnlock(String requirePasswordUnlock) {
		this.requirePasswordUnlock = requirePasswordUnlock;
		return this;
	}

	public PasswordRequirementsE setPasswordMinLength(Integer passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
		return this;
	}


	public PolicyE getPolicyE() {
		return policyE;
	}

	public void setPolicyE(PolicyE policyE) {
		this.policyE = policyE;
	}

	
}
