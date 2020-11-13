package com.blackbook.webconsole.pojo;

import com.blackbook.webconsole.entities.PasswordRequirementsE;
import com.blackbook.webconsole.entities.PermissionPolicyE;
import com.blackbook.webconsole.entities.PolicyEnforcementRulesE;
import com.blackbook.webconsole.entities.SystemUpdateE;

public class Policies {

	private PermissionPolicyE perm;
	private PasswordRequirementsE passReq;
	private PolicyEnforcementRulesE policyEnforeRules;
	private SystemUpdateE systemUp;

	
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

}
