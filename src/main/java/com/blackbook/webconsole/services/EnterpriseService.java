package com.blackbook.webconsole.services;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.el.stream.Stream;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blackbook.webconsole.entities.AdvancedSecurityOverridesE;
import com.blackbook.webconsole.entities.AppAutoUpdatePolicyE;
import com.blackbook.webconsole.entities.ApplicationsPolicyE;
import com.blackbook.webconsole.entities.PasswordRequirementsE;
import com.blackbook.webconsole.entities.PermissionPolicyE;
import com.blackbook.webconsole.entities.PolicyE;
import com.blackbook.webconsole.entities.PolicyEnforcementRulesE;
import com.blackbook.webconsole.entities.SystemUpdateE;
import com.blackbook.webconsole.pojo.ManagedConfigurationTemplateE;
import com.blackbook.webconsole.repositories.ApplicationRepository;
import com.blackbook.webconsole.repositories.DebuggingRepository;
import com.blackbook.webconsole.repositories.PasswordRepository;
import com.blackbook.webconsole.repositories.PermissionPolicyRepository;
import com.blackbook.webconsole.repositories.PolicyEnforcementRulesRepository;
import com.blackbook.webconsole.repositories.SafeBootRepository;
import com.blackbook.webconsole.repositories.SystemUpdateRepository;
import com.blackbook.webconsole.repositories.ManagedConfigurationTemplateRepository;
import com.blackbook.webconsole.repositories.AdvancedSecurityOverridesRepository;
import com.blackbook.webconsole.repositories.AppAutoUpdateRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidenterprise.model.AdministratorWebToken;
import com.google.api.services.androidenterprise.model.AdministratorWebTokenSpec;
import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.AndroidManagement.Enterprises;
import com.google.api.services.androidmanagement.v1.AndroidManagement.Enterprises.WebTokens.Create;
import com.google.api.services.androidmanagement.v1.model.AdvancedSecurityOverrides;
import com.google.api.services.androidmanagement.v1.model.ApplicationPolicy;
import com.google.api.services.androidmanagement.v1.model.BlockAction;
import com.google.api.services.androidmanagement.v1.model.Command;
import com.google.api.services.androidmanagement.v1.model.Device;
import com.google.api.services.androidmanagement.v1.model.EnrollmentToken;
import com.google.api.services.androidmanagement.v1.model.Enterprise;
import com.google.api.services.androidmanagement.v1.model.FreezePeriod;
import com.google.api.services.androidmanagement.v1.model.ListDevicesResponse;
import com.google.api.services.androidmanagement.v1.model.ManagedConfigurationTemplate;
import com.google.api.services.androidmanagement.v1.model.Operation;
import com.google.api.services.androidmanagement.v1.model.PasswordRequirements;
import com.google.api.services.androidmanagement.v1.model.PermissionGrant;
import com.google.api.services.androidmanagement.v1.model.Policy;
import com.google.api.services.androidmanagement.v1.model.PolicyEnforcementRule;
import com.google.api.services.androidmanagement.v1.model.SignupUrl;
import com.google.api.services.androidmanagement.v1.model.SystemUpdate;
import com.google.api.services.androidmanagement.v1.model.WebToken;
import com.google.api.services.androidmanagement.v1.model.WipeAction;

//Order of operations

@Service
public class EnterpriseService implements EnterpriseI {

	public static AndroidManagement androidManagementClient;
	private SignupUrl signupUrl = null;
	private String PROJECT_ID = "tryemm";
	private String CALLBACK_URL = "https://localhost:8443/enterpriseToken";
	private String OAUTH_SCOPE = "https://www.googleapis.com/auth/androidmanagement";
	private String APP_NAME = "Viv";
	public static String ENTERPRISE_TOKEN;
	public static String ENROLLMENT_TOKEN;
	private String POLICY_ID = "policyB";
	private HashMap<String, String> hm = new HashMap<>();
	private HashMap<String, String> managedConfigMap = new HashMap<>();
	private static final Logger LOG = LoggerFactory.getLogger(EnterpriseService.class);
	private List<Device> listOfDevices = new ArrayList<>();
	@Autowired
	private PasswordRepository passwordPolicyRepo;
	@Autowired
	private PolicyEnforcementRulesRepository policyEnforcementRulesRepo;
	@Autowired
	private SystemUpdateRepository systemUpdateRepo;
	@Autowired
	private PermissionPolicyRepository permissionPolicyRepo;
	@Autowired
	AdvancedSecurityOverridesRepository advancedSecurityOverridesRepo;
	@Autowired
	AppAutoUpdateRepository appUpdateRepo;

	@Autowired
	DebuggingRepository debugRepo;
	@Autowired
	SafeBootRepository safebootRepo;
	@Autowired
	private ApplicationRepository applicationRepo;
	@Autowired
	ManagedConfigurationTemplateRepository managedConfigRepo;

	Policy myPolicy;
	String enterpriseToken;
	private Enterprise enterprise;
	JSONObject onproductselect = new JSONObject();

	// Step 1
	@Override
	public HashMap<String, String> enterpriseSignUp() {
		JSONObject credentialsJson = null;

		InputStream input;
		try {
			input = new FileInputStream("sharkcred.json");
			credentialsJson = new JSONObject(new JSONTokener(input));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		LOG.info("credentialsJson is %s \n" + credentialsJson.toString());

		String str = credentialsJson.toString();
		LOG.info(str);

		InputStream is = new ByteArrayInputStream(str.getBytes());
		GoogleCredential credential = null;

		try {
			credential = GoogleCredential.fromStream(is).createScoped(Collections.singleton(OAUTH_SCOPE));
		} catch (IOException e) {
			e.printStackTrace();
		}
		androidManagementClient = new AndroidManagement.Builder(
				new com.google.api.client.http.javanet.NetHttpTransport(), JacksonFactory.getDefaultInstance(),
				credential).setApplicationName(APP_NAME).build();
		LOG.info("credential is " + credential.getServiceAccountId());
		LOG.info("credentials are " + credential);

		try {
			LOG.info("android management client is " + androidManagementClient.signupUrls().create());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			signupUrl = androidManagementClient.signupUrls().create().setProjectId(PROJECT_ID)
					.setCallbackUrl(CALLBACK_URL).execute();
			LOG.info("made it to signUpUrl");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String url = signupUrl.getUrl();

		String token = displayUrlToAdmin("Please complete the sign up flow by going to the following URL: " + url);

		token = token.split("token=")[1];

		hm.put("signUpUrl", url);

		return hm;
	}

	// Displays the enterprise name
	private String displayUrlToAdmin(final String url) {
		return url;
	}

	@Override
	public Enterprise getEnterpriseByToken(String enterpriseToken) {
		if (enterpriseToken != null) {

			try {
				enterprise = androidManagementClient.enterprises().create(new Enterprise()).setProjectId(PROJECT_ID)
						.setSignupUrlName(signupUrl.getName()).setEnterpriseToken(enterpriseToken).execute();
				System.out.print("EnterpriseToken in EnterpriseService.getEnterpriseByToken() is " + enterpriseToken);
				String enterpriseName = enterprise.getName();
				ENTERPRISE_TOKEN = enterpriseToken;
				setPolicy(enterpriseName, POLICY_ID, getPolicy());
//				androidManagementClient.enterprises().devices();
				LOG.info("enterpriseName in getEnterpriseByToken is: " + enterpriseName);
				return enterprise;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Device> getDevices(String enterpriseToken) {
		if (enterpriseToken != null) {

			try {
				enterprise = androidManagementClient.enterprises().create(new Enterprise()).setProjectId(PROJECT_ID)
						.setSignupUrlName(signupUrl.getName()).setEnterpriseToken(enterpriseToken).execute();
				String enterpriseName = enterprise.getName();

//				LOG.info("policy in policies.get is: " + androidManagementClient.enterprises().policies().get(enterpriseName));
				// Create an enrollment token to enroll the device.
				ENROLLMENT_TOKEN = createEnrollmentToken(enterpriseName, POLICY_ID);

				LOG.info("EnrollmentToken in EnterpriseService.getDevices() is: " + ENROLLMENT_TOKEN);

				// Set the policy to be used by the device.
				setPolicy(enterpriseName, POLICY_ID, getPolicy());
				LOG.info("enterpriseName is: " + enterpriseName);

				List<Device> devices = listDevices(enterpriseName);
				LOG.info("devices are " + devices.isEmpty());

				return devices;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	/** Here's where I set the stage to build my policy. */
	@Override
	public Policy getPolicy() {

		List<String> categories = new ArrayList<>();
		categories.add("android.intent.category.HOME");
		categories.add("android.intent.category.DEFAULT");
		List<ApplicationsPolicyE> applicationPolicy = applicationRepo.findAll();

		ArrayList<ApplicationPolicy> applications = new ArrayList<>();

		for (int i = 0; i < applicationPolicy.size(); i++) {
			ApplicationsPolicyE result = applicationPolicy.get(i);
			ApplicationPolicy appPolicy = new ApplicationPolicy().setPackageName(result.getPackageName())
					.setInstallType(result.getInstallType())
					.setDefaultPermissionPolicy(result.getDefaultPermissionPolicy())
//					.setManagedConfiguration(result.getManagedConfigurationMap())
					.setManagedConfigurationTemplate(getManagedConfigurationTemplate())
					.setDisabled(result.getDisabled());
			applications.add(appPolicy);
		}

		return new Policy().setPermissionGrants(getPermissionGrants(1L)).setSystemUpdate(getSystemUpdatePolicy(1L))
				.setPasswordRequirements(getPasswordRequirements(1L))
				.setAdvancedSecurityOverrides(getAdvancedSecurityOverrides(1L))
				.setPolicyEnforcementRules(getPolicyEnforcementRules(1L)).setApplications(applications)
				.setSafeBootDisabled(
						getSafeBootOverride(1L) == null ? Boolean.FALSE : getSafeBootOverride(1L).getSafeBootDisabled())
				.setDebuggingFeaturesAllowed(getDebuggingOverride(1L) == null ? Boolean.FALSE
						: getDebuggingOverride(1L).getDebuggingFeaturesAllowed());
	}

	/**
	 * Sets the policy of the given id to the given value.
	 * 
	 * @return
	 */
	@Override
	public void setPolicy(String enterpriseName, String policyId, Policy policy) {
		LOG.info("Setting policy...");
		String name = enterpriseName + "/policies/" + policyId;
		LOG.info("Name in setPolicy is " + name);
		try {
			Policy p = androidManagementClient.enterprises().policies().patch(name, policy).execute();
			LOG.info("androidManagementClient in setPolicy() is: " + androidManagementClient);
			LOG.info("Policy returned back from execute {} : ", p.toString());
//			LOG.info(
//					"getCosuPolicy()'s application policies here are: " + getPolicy().);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
			LOG.error("This is an error{} {}", e.getMessage(), "another argument");
		}
	}

	/** Creates an enrollment token. */
	private String createEnrollmentToken(String enterpriseName, String policyId) throws IOException {
		LOG.info("Creating enrollment token...");
		EnrollmentToken token = new EnrollmentToken().setPolicyName(policyId).setDuration("2592000s");
		LOG.info("EnrollmentToken is: " + token);
		return androidManagementClient.enterprises().enrollmentTokens().create(enterpriseName, token).execute()
				.getValue();
	}

	/** Lists the first page of devices for an enterprise. */
	private List<Device> listDevices(String enterpriseName) throws IOException {
		LOG.info("Listing devices...");
		ListDevicesResponse response = androidManagementClient.enterprises().devices().list(enterpriseName).execute();
		listOfDevices = response.getDevices();
		LOG.info("enterprises.devices.get is: " + androidManagementClient.enterprises().devices()
				.get("enterprises/LC0199557j/devices/3a73b9c22843ed23"));
		return listOfDevices;
	}

	/**
	 * Reboots my device for the standard wipe feature. Note that reboot only works
	 * on Android N+.
	 */
	@Override
	public void wipeDevice(String deviceName) throws IOException {
		androidManagementClient.enterprises().devices().delete(deviceName).execute();
	}

	/** Locks my device. */
	@Override
	public Operation lockDevice(String deviceName) throws IOException {
		LOG.info("I'm running the lock command now!");
		Command command = new Command().setType("LOCK");
		LOG.info("Yay! I made it!");
		return androidManagementClient.enterprises().devices().issueCommand(deviceName, command).execute();
	}


	@Override
	public Operation relinquishOwnership(String deviceName) throws IOException{
		Command command = new Command().setType("RELINQUISH_OWNERSHIP");
		return androidManagementClient.enterprises().devices().issueCommand(deviceName,  command).execute();
		
	}
	
	@Override
	public PasswordRequirements getPasswordRequirements(Long id) {
		Optional<PasswordRequirementsE> passwordRequirement = passwordPolicyRepo.findById(id);
		PasswordRequirements pr = new PasswordRequirements();
		if (passwordRequirement.isPresent()) {
			PasswordRequirementsE result = passwordRequirement.get();
			pr.setMaximumFailedPasswordsForWipe(result.getMaximumFailedPasswordsForWipe())
					.setPasswordExpirationTimeout(result.getPasswordExpirationTimeout())
					.setPasswordHistoryLength(result.getPasswordHistoryLength())
					.setPasswordMinimumLength(result.getPasswordMinLength())
					.setPasswordMinimumLetters(result.getPasswordMinimumLetters())
					.setPasswordMinimumLowerCase(result.getPasswordMinimumLowerCase())
					.setPasswordMinimumNonLetter(result.getPasswordMinimumLowerCase())
					.setPasswordMinimumNumeric(result.getPasswordMinimumNumeric())
					.setPasswordMinimumSymbols(result.getPasswordMinimumSymbols())
					.setPasswordMinimumUpperCase(result.getPasswordMinimumUpperCase())
					.setPasswordQuality(result.getPasswordQuality()).setPasswordScope(result.getPasswordScope())
					.setRequirePasswordUnlock(null);
			LOG.info(result.toString());
			return pr;
		} else {
			return null;
		}
	}

	@Override
	public String createIframe() {
		List<String> enabledFeaturesArray = new ArrayList<>();
		enabledFeaturesArray.add("WEB_APPS");
		enabledFeaturesArray.add("PLAY_SEARCH");
		enabledFeaturesArray.add("MANAGED_CONFIGURATIONS");
		enabledFeaturesArray.add("STORE_BUILDER");
		enabledFeaturesArray.add("PRIVATE_APPS");

		List<String> webTokenPerm = new ArrayList<>();
		webTokenPerm.add("APPROVE_APPS");

		WebToken webTokenContent = new WebToken();
		webTokenContent.setName("enterprises/LC0199557j");
		webTokenContent.setParentFrameUrl("https://localhost:8443/");
		webTokenContent.setPermissions(webTokenPerm);
		webTokenContent.setEnabledFeatures(enabledFeaturesArray);

		LOG.info("webTokenContent is: " + webTokenContent);
		try {
			Create value = androidManagementClient.enterprises().webTokens().create("enterprises/LC0199557j",
					webTokenContent);
			WebToken responseWebToken = value.execute();
			LOG.info("value is: " + value);
			LOG.info("webToken value is: " + responseWebToken.getValue());// Here is our webToken
			String iframeWebToken = responseWebToken.getValue();
			return iframeWebToken;
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} catch (NullPointerException e) {
			LOG.error(e.getMessage());
			LOG.error("webtokens is null {}", androidManagementClient.enterprises().webTokens());
		}
		return null;
	}

	/*
	 * In case we ever want to explore this route
	 * 
	 * public Policy silentlyUninstall() { return new
	 * Policy().setApplications(Collections.singletonList(new ApplicationPolicy()
	 * .setInstallType("BLOCKED")));
	 * 
	 * }
	 */

	@Override
	public AdvancedSecurityOverrides getAdvancedSecurityOverrides(Long id) {
		Optional<AdvancedSecurityOverridesE> advancedSecurityOverrides = advancedSecurityOverridesRepo.findById(id);
		AdvancedSecurityOverrides advancedSecurityOverride = new AdvancedSecurityOverrides();
		if (advancedSecurityOverrides.isPresent()) {
			AdvancedSecurityOverridesE result = advancedSecurityOverrides.get();
			advancedSecurityOverride.setUntrustedAppsPolicy(result.getUntrustedAppsPolicy());
			return advancedSecurityOverride;
		}
		return null;
	}

	@Override
	public AdvancedSecurityOverridesE getDebuggingOverride(Long id) {
		Optional<AdvancedSecurityOverridesE> advancedDebuggingOverrides = debugRepo.findById(id);
		AdvancedSecurityOverridesE advancedDebuggingOverride = new AdvancedSecurityOverridesE();
		if (advancedDebuggingOverrides.isPresent()) {
			AdvancedSecurityOverridesE result = advancedDebuggingOverrides.get();
			advancedDebuggingOverride.setDebuggingFeaturesAllowed(result.getDebuggingFeaturesAllowed());
			return advancedDebuggingOverride;
		}
		return null;
	}

	@Override
	public AdvancedSecurityOverridesE getSafeBootOverride(Long id) {
		Optional<AdvancedSecurityOverridesE> advancedSafeBootOverrides = safebootRepo.findById(id);
		AdvancedSecurityOverridesE advancedSafeBootOverride = new AdvancedSecurityOverridesE();
		if (advancedSafeBootOverrides.isPresent()) {
			AdvancedSecurityOverridesE result = advancedSafeBootOverrides.get();
			advancedSafeBootOverride.setSafeBootDisabled(result.getSafeBootDisabled());
			return advancedSafeBootOverride;
		}
		return null;
	}

	@Override
	public SystemUpdate getSystemUpdatePolicy(Long id) {
		Optional<SystemUpdateE> systemUpdates = systemUpdateRepo.findById(id);
		SystemUpdate systemUpdate = new SystemUpdate();
		if (systemUpdates.isPresent()) {
			SystemUpdateE result = systemUpdates.get();
			systemUpdate.setStartMinutes(result.getStartMin());
			systemUpdate.setEndMinutes(result.getEndMin());
			systemUpdate.setType(result.getType());
			return systemUpdate;
		}
		return null;
	}

	@Override
	public AppAutoUpdatePolicyE getAppAutoUpdatePolicy(Long id) {
		Optional<AppAutoUpdatePolicyE> appUpdates = appUpdateRepo.findById(id);
		AppAutoUpdatePolicyE appUpdate = new AppAutoUpdatePolicyE();
		if (appUpdates.isPresent()) {
			AppAutoUpdatePolicyE result = appUpdates.get();
			appUpdate.setAppAutoUpdatePolicy(result.getAppAutoUpdatePolicy());
			return appUpdate;
		}
		return null;
	}

	@Override
	public ManagedConfigurationTemplate getManagedConfigurationTemplate() {
		List<ManagedConfigurationTemplateE> tp = managedConfigRepo.findAll();
		if (tp.size() == 0) {
			return null;
		}
		ManagedConfigurationTemplateE latestTemplate = tp.get(tp.size() - 1);
		ManagedConfigurationTemplate templateConfig = new ManagedConfigurationTemplate();
		templateConfig.setTemplateId(latestTemplate.getTemplateId());// Has been set in the JDBC
		templateConfig.setConfigurationVariables(latestTemplate.getConfigurationVariables());// Has been set in the JDBC
		return templateConfig;
	}

	@Override
	public List<PermissionGrant> getPermissionGrants(Long id) {
		Optional<PermissionPolicyE> permissionGrants = permissionPolicyRepo.findById(id);
		List<PermissionGrant> permissionGrantsList = new ArrayList<>();
		PermissionGrant grant = new PermissionGrant();
		if (permissionGrants.isPresent()) {
			PermissionPolicyE result = permissionGrants.get();
			permissionGrantsList.add(grant.setPermission(result.getPermission()).setPolicy(result.getPolicy()));
			return permissionGrantsList;
		}
		return null;
	}

	private List<PolicyEnforcementRule> getPolicyEnforcementRules(Long id) {
		Optional<PolicyEnforcementRulesE> policyEnforcementRules = policyEnforcementRulesRepo.findById(id);
		List<PolicyEnforcementRule> policyEnforcementRuleList = new ArrayList<>();
		PolicyEnforcementRule rule = new PolicyEnforcementRule();
		BlockAction block = new BlockAction();
		WipeAction wipe = new WipeAction();
		if (policyEnforcementRules.isPresent()) {
			PolicyEnforcementRulesE result = policyEnforcementRules.get();
			rule.setSettingName(result.getSettingName());
			block.setBlockAfterDays(result.getBlockAfterDays());
			block.set("blockScope", result.getBlockScope());
			wipe.setWipeAfterDays(result.getWipeAfterDays());
			wipe.setPreserveFrp(result.getPreserveFrp());
			rule.setBlockAction(block);
			rule.setWipeAction(wipe);
			policyEnforcementRuleList.add(rule);
			LOG.info("policyEnforcementRuleList is: " + policyEnforcementRuleList.get(0).getBlockAction());
			return policyEnforcementRuleList;
		}
		return null;
	}

}
