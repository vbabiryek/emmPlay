package com.blackbook.webconsole.controllers.urls;

public class Urls {
	public static final String HOME_PAGE = "/";
	
	public static final String ENTERPRISE_TOKEN = "/enterpriseToken";
	
	public static final String ENTERPRISE_SIGN_UP = "/enterpriseSignUpFlo";
	
	public static final String GET_WEB_TOKEN = "/getWebToken";
	public static final String GET_MANAGED_CONFIG_WEB_TOKEN = "/getManagedConfigToken";
	
	//passwordPolicy RestAPI
	public static final String GET_PASSWORD_POLICY = "/getPasswordPolicy";
	public static final String ADD_PASSWORD_POLICY = "/addPasswordPolicy";
	
	//applicationPolicy RestAPI
	public static final String ADD_APPLICATION_POLICY = "/addApplicationPolicy";
	public static final String ADD_APPLICATION_POLICIES = "/addApplicationPolicies";
	public static final String GET_APPLICATION_POLICY = "/getApplicationPolicy";
	//public static final String UNINSTALL_APP = "/getSilentUninstall"; in case we ever want to explore this route
	
	//devices RestAPI
	public static final String GET_POLICY = "/getPolicy";
	public static final String DELETE_DEVICE = "/deleteDevice/{enterpriseId}/{deviceId}";
	public static final String WIPE_DEVICE = "/wipeDevice/{enterpriseId}/{deviceName}";
	public static final String LOCK_DEVICE = "/lockDevice/{enterpriseId}/{deviceName}";
	public static final String GET_DEVICES = "/devices";
	
	//advanced security overrides RestAPI
	public static final String GET_UNTRUSTED_APPS = "/getUntrustedAppsPolicy";
	public static final String ADD_UNTRUSTED_APPS = "/addUntrustedAppsPolicy";
	public static final String GET_DEBUGGING = "/getDebuggingPolicy";
	public static final String ADD_DEBUGGING = "/addDebuggingPolicy";
	
	//policyEnforcementRules RestAPI
	public static final String GET_POLICY_ENFORCEMENT_RULES = "/getPolicyEnforcementPolicy";
	public static final String ADD_POLICY_ENFORCEMENT_RULES = "/addPolicyEnforcementPolicy";
	
	//systemUpdate RestAPI
	public static final String GET_SYSTEM_UPDATE = "/getSystemUpdatePolicy";
	public static final String ADD_SYSTEM_UPDATE = "/addSystemUpdatePolicy";
	
	//appAutoUpdate RestAPI
	public static final String ADD_APP_AUTO_UPDATE_POLICY = "/addAppAutoUpdatePolicy";
	public static final String GET_APP_AUTO_UPDATE_POLICY = "/getAppAutoUpdatePolicy";


	//permissions Rest API
	public static final String GET_PERMISSIONS = "/getPermissionPolicy";
	public static final String ADD_PERMISSIONS = "/addPermissionPolicy";
	
	//COSUPolicy Rest API
	public static final String GET_COSU_POLICY = "/getCosuPolicy";
	
	//Enrollment Token
	public static final String GET_ENROLLMENT_TOKEN = "/enrollmentToken";
	
	
	
	
	
	
	
	
	
	
	
}
