package com.blackbook.webconsole.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "System_Update_Requirements")
public class SystemUpdateE extends AuditModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3096953704909097135L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String type;
	private Integer startMin;
	private Integer endMin;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startFreezePeriod;
	private LocalDate endFreezePeriod;
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "id", nullable = false)
	private PolicyE policyE;
	
	public SystemUpdateE() {
		
	}

	public PolicyE getPolicyE() {
		return policyE;
	}



	public void setPolicyE(PolicyE policyE) {
		this.policyE = policyE;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStartMin() {
		return startMin;
	}

	public void setStartMin(Integer startMin) {
		this.startMin = startMin;
	}

	public Integer getEndMin() {
		return endMin;
	}

	public void setEndMin(Integer endMin) {
		this.endMin = endMin;
	}

	public LocalDate getStartFreezePeriod() {
		return startFreezePeriod;
	}

	public void setStartFreezePeriod(LocalDate startFreezePeriod) {
		this.startFreezePeriod = startFreezePeriod;
	}

	public LocalDate getEndFreezePeriod() {
		return endFreezePeriod;
	}

	public void setEndFreezePeriod(LocalDate endFreezePeriod) {
		this.endFreezePeriod = endFreezePeriod;
	}

}
