package com.blackbook.webconsole.entities;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.blackbook.webconsole.pojo.FreezePeriodE;

@Entity(name = "POLICY")
@Table(name = "POLICY")
public class PolicyE extends AuditModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7410923776799837826L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "permission_id", referencedColumnName = "id")
	private PermissionPolicyE perm;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "app_auto_update_policy_id", referencedColumnName = "id")
	private AppAutoUpdatePolicyE appUpdate;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "pass_req_id", referencedColumnName = "id")
	private PasswordRequirementsE passReq;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "policy_enforce_id", referencedColumnName = "id")
	private PolicyEnforcementRulesE policyEnforeRules;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "sys_update_id", referencedColumnName = "id")
	private SystemUpdateE systemUp;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "unknown_source_id", referencedColumnName = "id")
	private AdvancedSecurityOverridesE advancedSecurityOverridesEPolicy;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "usb_debugging_id", referencedColumnName = "id")
	private AdvancedSecurityOverridesE usbDebuggingPolicy;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	@JoinColumn(name = "safe_boot_disabled", referencedColumnName = "id")
	private AdvancedSecurityOverridesE safeBoot;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyE")
	private List<ApplicationsPolicyE> applicationPolicy;
	

	private String packageName = "com.blackbook.webconsole.entities";

	public PolicyE() {
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PermissionPolicyE getPerm() {
		return perm;
	}

	public void setPerm(PermissionPolicyE perm) {
		this.perm = perm;
	}

	public PasswordRequirementsE getPassReq() {
		return passReq;
	}

	public void setPassReq(PasswordRequirementsE passReq) {
		this.passReq = passReq;
	}

	public PolicyEnforcementRulesE getPolicyEnforeRules() {
		return policyEnforeRules;
	}

	public void setPolicyEnforeRules(PolicyEnforcementRulesE policyEnforeRules) {
		this.policyEnforeRules = policyEnforeRules;
	}

	public SystemUpdateE getSystemUp() {
		return systemUp;
	}

	public void setSystemUp(SystemUpdateE systemUp) {
		this.systemUp = systemUp;
	}

	public AdvancedSecurityOverridesE getAdvancedSecurityOverridesEPolicy() {
		return advancedSecurityOverridesEPolicy;
	}

	public void setAdvancedSecurityOverridesEPolicy(AdvancedSecurityOverridesE advancedSecurityOverridesEPolicy) {
		this.advancedSecurityOverridesEPolicy = advancedSecurityOverridesEPolicy;
	}

	public List<ApplicationsPolicyE> getApplicationPolicy() {
		return applicationPolicy.stream().filter(pol -> !pol.getPackageName().isEmpty()).collect(Collectors.toList());
	}

	public void setApplicationPolicy(List<ApplicationsPolicyE> applicationPolicy) {
		this.applicationPolicy = applicationPolicy;
	}


	public AppAutoUpdatePolicyE getAppUpdate() {
		return appUpdate;
	}

	public void setAppUpdate(AppAutoUpdatePolicyE appUpdate) {
		this.appUpdate = appUpdate;
	}

	public AdvancedSecurityOverridesE getUsbDebuggingPolicy() {
		return usbDebuggingPolicy;
	}

	public void setUsbDebuggingPolicy(AdvancedSecurityOverridesE usbDebuggingPolicy) {
		this.usbDebuggingPolicy = usbDebuggingPolicy;
	}
	
	public AdvancedSecurityOverridesE getSafeBoot() {
		return safeBoot;
	}

	public void setSafeBoot(AdvancedSecurityOverridesE safeBoot) {
		this.safeBoot = safeBoot;
	}
	

	@Override
	public String toString() {
		return "PolicyE [id=" + id + ", perm=" + perm + ", appUpdate=" + appUpdate + ", passReq=" + passReq
				+ ", policyEnforeRules=" + policyEnforeRules + ", systemUp=" + systemUp
				+ ", advancedSecurityOverridesEPolicy=" + advancedSecurityOverridesEPolicy + ", usbDebuggingPolicy="
				+ usbDebuggingPolicy + ", safeBoot=" + safeBoot + ", applicationPolicy=" + applicationPolicy
				+ ", packageName=" + packageName + "]";
	}
	

	

}
