package com.blackbook.webconsole.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.blackbook.webconsole.entities.AdvancedSecurityOverridesE;
import com.blackbook.webconsole.entities.AppAutoUpdatePolicyE;
import com.blackbook.webconsole.entities.PolicyE;
import com.google.api.services.androidmanagement.v1.model.AdvancedSecurityOverrides;
import com.google.api.services.androidmanagement.v1.model.Device;
import com.google.api.services.androidmanagement.v1.model.Enterprise;
import com.google.api.services.androidmanagement.v1.model.ManagedConfigurationTemplate;
import com.google.api.services.androidmanagement.v1.model.Operation;
import com.google.api.services.androidmanagement.v1.model.PasswordRequirements;
import com.google.api.services.androidmanagement.v1.model.PermissionGrant;
import com.google.api.services.androidmanagement.v1.model.Policy;
import com.google.api.services.androidmanagement.v1.model.SystemUpdate;

public interface EnterpriseI {
	
	public HashMap<String, String> enterpriseSignUp();
	public Enterprise getEnterpriseByToken(String enterpriseToken);
	public List<Device> getDevices(String enrollmentToken);
	public Policy getPolicy();
	public PasswordRequirements getPasswordRequirements(Long id);
	public void setPolicy(String enterpriseName, String policyId, Policy policy);
	public SystemUpdate getSystemUpdatePolicy(Long id);
	public List<PermissionGrant> getPermissionGrants(Long id);
	public AdvancedSecurityOverrides getAdvancedSecurityOverrides(Long id);
	public String createIframe();
	
	public void wipeDevice(String deviceName) throws IOException;
	public Operation lockDevice(String deviceName) throws IOException;
	public AdvancedSecurityOverridesE getDebuggingOverride(Long id);
	public AppAutoUpdatePolicyE getAppAutoUpdatePolicy(Long id);
	public AdvancedSecurityOverridesE getSafeBootOverride(Long id);
	public ManagedConfigurationTemplate getManagedConfigurationTemplate();
	public Operation relinquishOwnership(String deviceName) throws IOException;
	
}
