package com.blackbook.webconsole.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blackbook.webconsole.controllers.urls.Urls;
import com.blackbook.webconsole.entities.AdvancedSecurityOverridesE;
import com.blackbook.webconsole.entities.AppAutoUpdatePolicyE;
import com.blackbook.webconsole.entities.AppTrackInfoE;
import com.blackbook.webconsole.entities.ApplicationsPolicyE;
import com.blackbook.webconsole.entities.PasswordRequirementsE;
import com.blackbook.webconsole.entities.PermissionPolicyE;
import com.blackbook.webconsole.entities.PolicyEnforcementRulesE;
import com.blackbook.webconsole.entities.SystemUpdateE;
import com.blackbook.webconsole.repositories.AdvancedSecurityOverridesRepository;
import com.blackbook.webconsole.repositories.AppAutoUpdateRepository;
import com.blackbook.webconsole.repositories.ApplicationRepository;
import com.blackbook.webconsole.repositories.PasswordRepository;
import com.blackbook.webconsole.repositories.PermissionPolicyRepository;
import com.blackbook.webconsole.repositories.PolicyEnforcementRulesRepository;
import com.blackbook.webconsole.repositories.PolicyRepository;
import com.blackbook.webconsole.repositories.SystemUpdateRepository;
import com.blackbook.webconsole.repositories.ManagedConfigurationTemplateRepository;
import com.blackbook.webconsole.services.EnterpriseI;
import com.blackbook.webconsole.services.EnterpriseService;
import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Device;
import com.google.api.services.androidmanagement.v1.model.Empty;
import com.google.api.services.androidmanagement.v1.model.Operation;
import com.google.api.services.androidmanagement.v1.model.Policy;

@RestController
public class EnterpriseController {

	@Autowired
	public EnterpriseI enterpriseImpl;
	public String enterpriseNameT;
	public String policyIdT;
	@Autowired
	public PasswordRepository passwordRepo;
	@Autowired
	public SystemUpdateRepository systemUpdateRepo;
	@Autowired
	public PermissionPolicyRepository permPolicyRepo;
	@Autowired
	private AdvancedSecurityOverridesRepository advancedSecurityOverridesRepo;
	@Autowired
	public PolicyEnforcementRulesRepository policyEnforcementPolicyRepo;
	@Autowired
	public ApplicationRepository applicationRepo;
	@Autowired
	AppAutoUpdateRepository appUpdateRepo;
	@Autowired
	public PolicyRepository policyRepo;
	private static final Logger LOG = LoggerFactory.getLogger(EnterpriseController.class);
	AndroidManagement androidManagementClient;
	
	@Autowired
	private ManagedConfigurationTemplateRepository templateIdRepository;
	

	@RequestMapping(value = Urls.ENTERPRISE_SIGN_UP, method = RequestMethod.GET)
	public HashMap<String, String> enterpriseCreationAndSignUp() {
		return enterpriseImpl.enterpriseSignUp();
	}

	

	@RequestMapping(value = Urls.GET_DEVICES, method = RequestMethod.GET)
	public List<Device> getDevices() {
		return enterpriseImpl.getDevices(EnterpriseService.ENTERPRISE_TOKEN);
	}

	@RequestMapping(value = Urls.GET_COSU_POLICY, method = RequestMethod.GET)
	public Policy getCosuPolicy() {
		LOG.info("Cosu policies are " + enterpriseImpl.getPolicy());
		return enterpriseImpl.getPolicy();
	}
	
	@PostMapping(value = Urls.ADD_PASSWORD_POLICY, consumes = { MediaType.ALL_VALUE })
	public PasswordRequirementsE addPolicy(@RequestBody PasswordRequirementsE passwordRequirements) {
		Optional<PasswordRequirementsE> foundPolicy = passwordRepo.findById(1L);
		if (foundPolicy.isPresent()) {
			PasswordRequirementsE policy = foundPolicy.get();
			policy.setPasswordExpirationTimeout(passwordRequirements.getPasswordExpirationTimeout())
					.setPasswordHistoryLength(passwordRequirements.getPasswordHistoryLength())
					.setPasswordMinimumLength(passwordRequirements.getPasswordMinLength())
					.setPasswordMinimumLetters(passwordRequirements.getPasswordMinimumLetters())
					.setPasswordMinimumLowerCase(passwordRequirements.getPasswordMinimumLowerCase())
					.setPasswordMinimumNonLetter(passwordRequirements.getPasswordMinimumNonLetter())
					.setPasswordMinimumNumeric(passwordRequirements.getPasswordMinimumNumeric())
					.setPasswordMinimumSymbols(passwordRequirements.getPasswordMinimumSymbols())
					.setPasswordMinimumUpperCase(passwordRequirements.getPasswordMinimumUpperCase())
					.setPasswordScope(passwordRequirements.getPasswordScope())
					.setRequirePasswordUnlock(passwordRequirements.getRequirePasswordUnlock())
					.setPasswordQuality(passwordRequirements.getPasswordQuality());
			return passwordRepo.save(policy);
		} else {
			return passwordRepo.save(passwordRequirements);
		}
	}
	
	@RequestMapping(value = Urls.GET_PASSWORD_POLICY, method = RequestMethod.GET)
	public PasswordRequirementsE getPasswordPolicy() {
		return passwordRepo.findById(1L).isPresent() ? passwordRepo.findById(1L).get() : null;
	}
	
	@RequestMapping(value = Urls.GET_WEB_TOKEN, method = RequestMethod.GET)
	public String getMyWebToken() {
		return enterpriseImpl.createIframe();
	}
	
	@RequestMapping(value = Urls.GET_MANAGED_CONFIG_WEB_TOKEN, method = RequestMethod.GET)
	public String getManagedConfigWebToken() {
		return enterpriseImpl.createIframe();
	}

	@PostMapping(value = Urls.ADD_PERMISSIONS, consumes = { MediaType.ALL_VALUE })
	public PermissionPolicyE addPermissionPolicy(@RequestBody PermissionPolicyE permissionForm) {
		Optional<PermissionPolicyE> foundPermPolicy = permPolicyRepo.findById(1L);
		if (foundPermPolicy.isPresent()) {
			PermissionPolicyE permissionPolicy = foundPermPolicy.get();
			permissionPolicy.setPermission(permissionForm.getPermission());
			permissionPolicy.setPolicy(permissionForm.getPolicy());
//			permissionPolicy.setPolicyE(permissionForm.getPolicyE());
			return permPolicyRepo.save(permissionPolicy);
		} else {
			return permPolicyRepo.save(permissionForm);
		}
	}

	@RequestMapping(value = Urls.GET_PERMISSIONS, method = RequestMethod.GET)
	public PermissionPolicyE getPermissionPolicy() {
		return permPolicyRepo.findById(1L).isPresent() ? permPolicyRepo.findById(1L).get() : null;
	}

	@PostMapping(value = Urls.ADD_SYSTEM_UPDATE, consumes = { MediaType.ALL_VALUE })
	public SystemUpdateE addSystemUpdatePolicy(@RequestBody SystemUpdateE systemUpdateForm) {
		Optional<SystemUpdateE> foundSysUpPolicy = systemUpdateRepo.findById(1L);
		if (foundSysUpPolicy.isPresent()) {
			SystemUpdateE systemUpdatePolicy = foundSysUpPolicy.get();
			systemUpdatePolicy.setType(systemUpdateForm.getType());
			systemUpdatePolicy.setStartMin(systemUpdateForm.getStartMin());
			systemUpdatePolicy.setEndMin(systemUpdateForm.getEndMin());
			systemUpdatePolicy.setStartFreezePeriod(systemUpdateForm.getStartFreezePeriod());
			systemUpdatePolicy.setEndFreezePeriod(systemUpdateForm.getEndFreezePeriod());
			return systemUpdateRepo.save(systemUpdatePolicy);
		} else {
			return systemUpdateRepo.save(systemUpdateForm);
		}
	}

	@RequestMapping(value = Urls.GET_SYSTEM_UPDATE, method = RequestMethod.GET)
	public SystemUpdateE getSystemUpdatePolicy() {
		return systemUpdateRepo.findById(1L).isPresent() ? systemUpdateRepo.findById(1L).get() : null;
	}
	
	@PostMapping(value = Urls.ADD_POLICY_ENFORCEMENT_RULES, consumes = {MediaType.ALL_VALUE})
	public PolicyEnforcementRulesE addPolicyEnforcementPolicy(@RequestBody PolicyEnforcementRulesE policyEnforcementForm) {
		Optional<PolicyEnforcementRulesE> foundEnforcementPolicy = policyEnforcementPolicyRepo.findById(1L);
		if(foundEnforcementPolicy.isPresent()) {
			PolicyEnforcementRulesE policyEnforcementPolicy = foundEnforcementPolicy.get();
			policyEnforcementPolicy.setSettingName(policyEnforcementForm.getSettingName());
			policyEnforcementPolicy.setBlockAfterDays(policyEnforcementForm.getBlockAfterDays());
			policyEnforcementPolicy.setBlockScope(policyEnforcementForm.getBlockScope());
			policyEnforcementPolicy.setWipeAfterDays(policyEnforcementForm.getWipeAfterDays());
			return policyEnforcementPolicyRepo.save(policyEnforcementPolicy);
		}else {
			return policyEnforcementPolicyRepo.save(policyEnforcementForm);
		}
	}
	
	@RequestMapping(value = Urls.GET_POLICY_ENFORCEMENT_RULES, method = RequestMethod.GET)
	public PolicyEnforcementRulesE getPolicyEnforcementPolicy() {
		return policyEnforcementPolicyRepo.findById(1L).isPresent() ? policyEnforcementPolicyRepo.findById(1L).get() : null;
	}
	
	@PostMapping(value = Urls.ADD_UNTRUSTED_APPS, consumes = {MediaType.ALL_VALUE})
	public AdvancedSecurityOverridesE addUnknownSourcesPolicy(@RequestBody AdvancedSecurityOverridesE advancedSecurityForm) {
		Optional<AdvancedSecurityOverridesE> foundUntrustedAppsPolicy = advancedSecurityOverridesRepo.findById(1L);
		if(foundUntrustedAppsPolicy.isPresent()) {
			AdvancedSecurityOverridesE advancedSecurityOverridesPolicy = foundUntrustedAppsPolicy.get();
			advancedSecurityOverridesPolicy.setUntrustedAppsPolicy(advancedSecurityForm.getUntrustedAppsPolicy());
			advancedSecurityOverridesPolicy.setDebuggingFeaturesAllowed(advancedSecurityForm.getDebuggingFeaturesAllowed());
			advancedSecurityOverridesPolicy.setSafeBootDisabled(advancedSecurityForm.getSafeBootDisabled());
			return advancedSecurityOverridesRepo.save(advancedSecurityOverridesPolicy);//existing already in db
			//parent must exist before you can store the child
		}
		return advancedSecurityOverridesRepo.save(advancedSecurityForm);//creates a new value 
	}
	
	@RequestMapping(value = Urls.GET_UNTRUSTED_APPS, method = RequestMethod.GET)
	public AdvancedSecurityOverridesE getUntrustedAppsPolicy() {
		return advancedSecurityOverridesRepo.findById(1L).isPresent() ? advancedSecurityOverridesRepo.findById(1L).get() : null;
	}
	
	
	@RequestMapping(value = Urls.GET_DEBUGGING, method = RequestMethod.GET)
	public AdvancedSecurityOverridesE getDebuggingPolicy() {
		return advancedSecurityOverridesRepo.findById(1L).isPresent() ? advancedSecurityOverridesRepo.findById(1L).get() : null;
	}
	
	@PostMapping(value = Urls.ADD_APP_AUTO_UPDATE_POLICY, consumes = {MediaType.ALL_VALUE})
	public AppAutoUpdatePolicyE addAppUpdatePolicy(@RequestBody AppAutoUpdatePolicyE appUpdateForm) {
		Optional<AppAutoUpdatePolicyE> appUpdates = appUpdateRepo.findById(1L);
		if (appUpdates.isPresent()) {
			AppAutoUpdatePolicyE result = appUpdates.get();
			result.setAppAutoUpdatePolicy(appUpdateForm.getAppAutoUpdatePolicy());
			return appUpdateRepo.save(result);
		}
		return appUpdateRepo.save(appUpdateForm);
	}
	
	@RequestMapping(value = Urls.GET_APP_AUTO_UPDATE_POLICY, method = RequestMethod.GET)
	public AppAutoUpdatePolicyE getAppUpdatePolicy() {
		return appUpdateRepo.findById(1L).isPresent() ? appUpdateRepo.findById(1L).get() : null;
	}
	
// Leave this here in case you choose to go this route
	
//	@PostMapping(value = Urls.SET_TEMPLATE_ID, consumes = {MediaType.ALL_VALUE})
//	public TemplateIdPolicyE addTemplateIdPolicy(@RequestBody TemplateIdPolicyE templateIdForm) {
//		Optional<TemplateIdPolicyE> templateIds = tempRepo.findById(1L);
//		if(templateIds.isPresent()) {
//			TemplateIdPolicyE result = templateIds.get();
//			result.setTemplateId(templateIdForm.getTemplateId());
//			return tempRepo.save(result);
//		}
//		return tempRepo.save(templateIdForm);
//	}
//	
//	@RequestMapping(value = Urls.GET_TEMPLATE_ID, method = RequestMethod.GET)
//	public TemplateIdPolicyE getTemplateIdPolicy() {
//		return tempRepo.findById(1L).isPresent() ? tempRepo.findById(1L).get() : null;
//	}
	
	
//	@PostMapping(value = Urls.ADD_APPLICATION_POLICY, consumes = {MediaType.ALL_VALUE})
//	public ApplicationsPolicyE addApplicationPolicy(@RequestBody ApplicationsPolicyE applicationsForm) {
//		Optional<ApplicationsPolicyE> foundApplicationsPolicy = applicationRepo.findById(applicationsForm.getId());
//		if(foundApplicationsPolicy.isPresent()) {
//			//if found in database, get it and then update it
//			ApplicationsPolicyE pol = foundApplicationsPolicy.get();
//			pol.setPolicyE(policyRepo.findById(1L).get());//automatically gets the parent and sets the parent here
//			pol.setPackageName(applicationsForm.getPackageName());
//			pol.setInstallType(applicationsForm.getInstallType());
//			pol.setDefaultPermissionPolicy(applicationsForm.getDefaultPermissionPolicy());
//			pol.setPermission(applicationsForm.getPermission());
//			pol.setPolicy(applicationsForm.getPolicy());
//			pol.setDisabled(applicationsForm.getDisabled());
//			pol.setMinimumVersionCode(applicationsForm.getMinimumVersionCode());
//			pol.setManagedConfigurationMap(applicationsForm.getManagedConfigurationMap());
//			pol.setDelegatedScopes(applicationsForm.getDelegatedScopes());
//			List<AppTrackInfoE> accessibleTrackIds = applicationsForm.getAccessibleTrackIds();
//			accessibleTrackIds = accessibleTrackIds.stream().map(x -> { 
//				x.setApplicationPolicy(pol);
//				return x;
//			}).collect(Collectors.toList());
//			pol.setAccessibleTrackIds(accessibleTrackIds);
//			return applicationRepo.save(pol);
//		}else {
//			//add a new applications object
//			applicationsForm.setPolicyE(policyRepo.findById(1L).get());
//			return applicationRepo.save(applicationsForm);
//			//this takes the data, tells it who the parent is and saves it in the repo
//		}	
//	}
	
//	Find a way to iterate through this - known bug *one application is sent at a time
	@PostMapping(value = Urls.ADD_APPLICATION_POLICIES, consumes = {MediaType.ALL_VALUE})
	public List<ApplicationsPolicyE> addApplicationPolicies(@RequestBody List<ApplicationsPolicyE> applicationsForms) {
		List<ApplicationsPolicyE> response = new ArrayList<>();
		for(ApplicationsPolicyE applicationsForm : applicationsForms) {
		Optional<ApplicationsPolicyE> foundApplicationsPolicy = applicationRepo.findById(applicationsForm.getId() == null ? -1L : applicationsForm.getId());
		if(foundApplicationsPolicy.isPresent()) {
			//if found in database, get it and then update it
			ApplicationsPolicyE pol = foundApplicationsPolicy.get();
			pol.setPolicyE(policyRepo.findById(1L).get());//automatically gets the parent and sets the parent here
			pol.setPackageName(applicationsForm.getPackageName());
			pol.setInstallType(applicationsForm.getInstallType());
			pol.setDefaultPermissionPolicy(applicationsForm.getDefaultPermissionPolicy());
			pol.setDisabled(applicationsForm.getDisabled());
			pol.setMinimumVersionCode(applicationsForm.getMinimumVersionCode());
//			pol.setManagedConfigurationMap(applicationsForm.getManagedConfigurationMap());
			pol.setDelegatedScopes(applicationsForm.getDelegatedScopes());
			
			List<AppTrackInfoE> accessibleTrackIds = applicationsForm.getAccessibleTrackIds();
			accessibleTrackIds = accessibleTrackIds.stream().map(x -> { 
				x.setApplicationPolicy(pol);
				return x;
			}).collect(Collectors.toList());
			pol.setAccessibleTrackIds(accessibleTrackIds);
			response.add(applicationRepo.save(pol));
			//saving via jdbc not jpa, saving must occur just like this!
			//application policy id is an external id that is supplied outside of this class so it must be set here because it is not generated
			applicationsForm.getManagedConfigurationTemplate().setApplicationPolicyId(1L);
			templateIdRepository.deleteByTemplateId(applicationsForm.getManagedConfigurationTemplate().getTemplateId());
			templateIdRepository.save(applicationsForm.getManagedConfigurationTemplate());
		}else {
			//add a new applications object
			
			applicationsForm.setPolicyE(policyRepo.findById(1L).get());
			List<AppTrackInfoE> accessibleTrackIds = applicationsForm.getAccessibleTrackIds();
			accessibleTrackIds = accessibleTrackIds.stream().map(x -> { 
				x.setApplicationPolicy(applicationsForm);
				return x;
			}).collect(Collectors.toList());
			applicationsForm.setAccessibleTrackIds(accessibleTrackIds);
			response.add(applicationRepo.save(applicationsForm));
			//this takes the data, tells it who the parent is and saves it in the repo
			//saving via jdbc not jpa, saving must occur just like this!
			applicationsForm.getManagedConfigurationTemplate().setApplicationPolicyId(1L);
			templateIdRepository.deleteByTemplateId(applicationsForm.getManagedConfigurationTemplate().getTemplateId());
			templateIdRepository.save(applicationsForm.getManagedConfigurationTemplate());
		}
		}
		return response;
	}
	
	public void deleteApplicationPolicy(Long id) {
		applicationRepo.delete(applicationRepo.findById(id).get());
	}

	@RequestMapping(value = Urls.GET_APPLICATION_POLICY, method = RequestMethod.GET)
	public ArrayList<ApplicationsPolicyE> getApplicationPolicy() {
//		List<ApplicationsPolicyE> foundApplicationsPolicy = applicationRepo.findAll();
		LOG.info("getting applications!");
		return applicationRepo.findAll();
//		return applicationRepo.findById(1L).isPresent() ? applicationRepo.findById(1L).get() : null;
	}

	@RequestMapping(value = Urls.GET_POLICY, method = RequestMethod.GET)
	public void getPolicy(String name) {
		try {
			androidManagementClient.enterprises().policies().get("enterprises/LC0199557j/policies/policy1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = Urls.DELETE_DEVICE, method = RequestMethod.GET)
	public Empty unenrollDevice(@PathVariable String enterpriseId, @PathVariable String deviceId) {
		try {
			LOG.info("enterpriseId {} deviceId {} ", enterpriseId, deviceId);
			return EnterpriseService.androidManagementClient.enterprises().devices().delete(String.format("enterprises/%s/devices/%s", enterpriseId, deviceId)).execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = Urls.WIPE_DEVICE, method = RequestMethod.GET)
	public void wipeDevice(@PathVariable String enterpriseId, @PathVariable String deviceName) {
		try {
			enterpriseImpl.wipeDevice(String.format("enterprises/%s/devices/%s", enterpriseId, deviceName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
//	In case we ever want to explore this route
//	@RequestMapping(value = Urls.UNINSTALL_APP, method = RequestMethod.GET)
//	public Policy silentlyUninstall() {
//		return enterpriseImpl.silentlyUninstall();
//	}
	
	@RequestMapping(value = Urls.LOCK_DEVICE, method = RequestMethod.GET)
	public Operation lockDevice(@PathVariable String enterpriseId, @PathVariable String deviceName) {
		try {
			return enterpriseImpl.lockDevice(String.format("enterprises/%s/devices/%s", enterpriseId, deviceName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping(value = Urls.GET_ENROLLMENT_TOKEN)
	public String getEnrollmentToken() {
		return EnterpriseService.ENROLLMENT_TOKEN;
	}
	
}


