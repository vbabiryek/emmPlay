package com.blackbook.webconsole.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.blackbook.webconsole.controllers.urls.Urls;
import com.blackbook.webconsole.entities.AdvancedSecurityOverridesE;
import com.blackbook.webconsole.entities.AppAutoUpdatePolicyE;
import com.blackbook.webconsole.entities.ApplicationsPolicyE;
import com.blackbook.webconsole.entities.PasswordRequirementsE;
import com.blackbook.webconsole.entities.PermissionPolicyE;
import com.blackbook.webconsole.entities.PolicyE;
import com.blackbook.webconsole.entities.PolicyEnforcementRulesE;
import com.blackbook.webconsole.entities.SystemUpdateE;
import com.blackbook.webconsole.repositories.PolicyRepository;
import com.blackbook.webconsole.services.EnterpriseI;
import com.blackbook.webconsole.services.EnterpriseService;

@Controller
public class HomeController {
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	public PolicyRepository policyRepo;
	
	@Autowired
	public EnterpriseI enterpriseImpl;
	
	
	//For views
	@GetMapping(Urls.HOME_PAGE)
	public ModelAndView home() {
		ModelAndView homeModel = new ModelAndView();
		homeModel.setViewName("home");
		Optional<PolicyE> policyE = policyRepo.findById(1L);	
		if(policyE.isPresent()) {
			PolicyE currentPolicyE = policyE.get();
			homeModel.addObject("passwordPolicy", currentPolicyE.getPassReq() == null ? new PasswordRequirementsE() : currentPolicyE.getPassReq());
			homeModel.addObject("permissionPolicy", currentPolicyE.getPerm() == null ? new PermissionPolicyE() : currentPolicyE.getPerm());
			homeModel.addObject("policyEnforcementRulesPolicy", currentPolicyE.getPolicyEnforeRules() == null ? new PolicyEnforcementRulesE() : currentPolicyE.getPolicyEnforeRules());
			homeModel.addObject("systemUpdatePolicy", currentPolicyE.getSystemUp() == null ? new SystemUpdateE() : currentPolicyE.getSystemUp());
			homeModel.addObject("advancedSecurityOverridesPolicy", currentPolicyE.getAdvancedSecurityOverridesEPolicy() == null ? new AdvancedSecurityOverridesE() : currentPolicyE.getAdvancedSecurityOverridesEPolicy());
			homeModel.addObject("debuggingPolicy", currentPolicyE.getUsbDebuggingPolicy() == null ? new AdvancedSecurityOverridesE() : currentPolicyE.getUsbDebuggingPolicy());
			homeModel.addObject("safeBootPolicy", currentPolicyE.getSafeBoot() == null ? new AdvancedSecurityOverridesE() : currentPolicyE.getSafeBoot());
			homeModel.addObject("appAutoUpdatePolicy", currentPolicyE.getAppUpdate() == null ? new AppAutoUpdatePolicyE() : currentPolicyE.getAppUpdate());
			homeModel.addObject("permissionPermissionPolicy", currentPolicyE.getPerm() == null ? new PermissionPolicyE() : currentPolicyE.getPerm().getPermission());
			homeModel.addObject("permissionPolicyPerm", currentPolicyE.getPerm() == null ? new PermissionPolicyE() : currentPolicyE.getPerm().getPolicy());
			List<ApplicationsPolicyE> applicationPolicy = currentPolicyE.getApplicationPolicy();
			homeModel.addObject("applicationPolicies", applicationPolicy == null ? new ApplicationsPolicyE() : applicationPolicy);
	
			
			if(applicationPolicy.size() > 0) {
				homeModel.addObject("managedConfigurationMap", applicationPolicy.get(0).getManagedConfigurationMap());
				homeModel.addObject("applicationPolicyAccessibleTrackIds", applicationPolicy.get(0).getAccessibleTrackIds());
				homeModel.addObject("applicationPolicyDisabled", applicationPolicy.get(0).getDisabled());
//				homeModel.addObject("applicationPolicyTemplateId", applicationPolicy.get(0).getTemplateId());
				homeModel.addObject("applicationPolicyMinimumVersionCode", applicationPolicy.get(0).getMinimumVersionCode());
				homeModel.addObject("applicationPolicyPermission", applicationPolicy.get(0).getPermission());
				homeModel.addObject("applicationPolicyPermissionGrantsStates", applicationPolicy.get(0).getPolicy());
				homeModel.addObject("applicationPolicyDelegatedScopes", getDelegatedScopesHtml(applicationPolicy.get(0).getDelegatedScopes()));
			}else {
				homeModel.addObject("managedConfigurationMap", new HashMap<>());
				homeModel.addObject("applicationPolicyDisabled", Boolean.valueOf(true));
				homeModel.addObject("applicationPolicyMinimumVersionCode", Integer.valueOf(-1));
				homeModel.addObject("applicationPolicyPermission", "");
				homeModel.addObject("applicationPolicyPermissionGrantsStates", "");
				homeModel.addObject("applicationPolicyTemplateId", "");
				homeModel.addObject("applicationPolicyDelegatedScopes", getDelegatedScopesHtml(new ArrayList<>()));
				homeModel.addObject("applicationPolicyAccessibleTrackIds", new ArrayList<>());
			}
		}else {
			PolicyE policye = new PolicyE();
			policyRepo.save(policye);
		}
		return homeModel;
	}
	
	@RequestMapping(value = Urls.ENTERPRISE_TOKEN, method = RequestMethod.GET)
	public String getEnterprise(@RequestParam String enterpriseToken) {
		enterpriseImpl.getEnterpriseByToken(enterpriseToken);
		return "redirect:/";
		
	}
	
	public String getDelegatedScopesHtml(List<String> delegatedScopes) {
		String listHtml = "<option value='DELEGATED_SCOPE_UNSPECIFIED'>DELEGATED_SCOPE_UNSPECIFIED</option>" + " "
				+ "<option value='CERT_INSTALL'>CERT_INSTALL</option> " + " "
						+ "<option value='MANAGED_CONFIGURATIONS'>MANAGED_CONFIGURATIONS</option>  " + ""
								+ "<option value='BLOCK_UNINSTALL'>BLOCK_UNINSTALL</option>  " + ""
										+ "<option value='PERMISSION_GRANT'>PERMISSION_GRANT</option>  " + ""
												+ "<option value='PACKAGE_ACCESS'>PACKAGE_ACCESS</option> " + " "
														+ "<option value='ENABLE_SYSTEM_APP'>ENABLE_SYSTEM_APP</option>";
		for(String delegatedScope: delegatedScopes) {
			listHtml = listHtml.replace(String.format(">%s",  delegatedScope), String.format(" selected >%s",  delegatedScope));
			//goes through the values of delegated scopes individually and determines if it's already in the list. If it is, it replaces it with the word selected
			//if there are no values, it will return the default. 
		}
		return listHtml;
	}
}
