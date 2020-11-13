package com.blackbook.webconsole.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "App_Track_Info")
public class AppTrackInfoE extends AuditModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6662393782974352264L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public String trackId;
	public String trackAlias;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "application_policy_id", nullable = false /*insertable = false, updatable = false*/)
	private ApplicationsPolicyE applicationPolicy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getTrackAlias() {
		return trackAlias;
	}

	public void setTrackAlias(String trackAlias) {
		this.trackAlias = trackAlias;
	}

	public ApplicationsPolicyE getApplicationPolicy() {
		return applicationPolicy;
	}

	public void setApplicationPolicy(ApplicationsPolicyE applicationPolicy) {
		this.applicationPolicy = applicationPolicy;
	}
	
	

}
