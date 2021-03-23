package com.blackbook.webconsole.pojo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.blackbook.webconsole.entities.AuditModel;
import com.blackbook.webconsole.entities.SystemUpdateE;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Freeze_Periods")
public class FreezePeriodE extends AuditModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7455782581657947572L;
	
	//create POJO for the Freeze Period object and then inject it into 
	//the System update policy then set it in Enterprise Service
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Integer startMonth;
	private Integer startDay;
	private Integer endMonth;
	private Integer endDay;
	
	@JsonIgnore
	@JoinColumn(name = "sys_update_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private SystemUpdateE systemUpdatePolicyE;
	
	
	public FreezePeriodE() {
		
	}

	public Long getId() {
		return id;
	}


	public FreezePeriodE setId(Long id) {
		this.id = id;
		return this;
	}



	public Integer getStartMonth() {
		return startMonth;
	}


	public FreezePeriodE setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
		return this;
	}



	public Integer getStartDay() {
		return startDay;
	}


	public FreezePeriodE setStartDay(Integer startDay) {
		this.startDay = startDay;
		return this;
	}


	public Integer getEndMonth() {
		return endMonth;
	}


	public FreezePeriodE setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
		return this;
	}


	public Integer getEndDay() {
		return endDay;
	}


	public FreezePeriodE setEndDay(Integer endDay) {
		this.endDay = endDay;
		return this;
	}


	public SystemUpdateE getSystemUpdatePolicyE() {
		return systemUpdatePolicyE;
	}


	public FreezePeriodE setSystemUpdatePolicyE(SystemUpdateE systemUpdatePolicyE) {
		this.systemUpdatePolicyE = systemUpdatePolicyE;
		return this;
	}		
	
	
}
