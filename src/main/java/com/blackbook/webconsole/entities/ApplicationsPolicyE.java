package com.blackbook.webconsole.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.api.services.androidmanagement.v1.model.AppTrackInfo;
import com.google.api.services.androidmanagement.v1.model.ApplicationPolicy;

@Entity
@Table(name = "Application_Policy")
public class ApplicationsPolicyE extends AuditModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1171778842682802851L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NonNull
	private String packageName;
	
	private String installType;
	private String defaultPermissionPolicy;
	private String permission;
	private String policy;
	private Boolean disabled;
	private Integer minimumVersionCode;
	private String templateId;
	
	@Convert(converter = HashMapConverter.class)
	private Map<String, String> configurationVariables;

	@ElementCollection
	private List<String> delegatedScopes;
	
	@Convert(converter = HashMapConverter.class)
	private Map<String, Object> managedConfigurationMap;

	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false) // bi-directional relationship
	@JoinColumn(name = "policy_id", nullable = false /*insertable = false, updatable = false*/)
	private PolicyE policyE;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "applicationPolicy")
	private List<AppTrackInfoE> accessibleTrackIds;

	public ApplicationsPolicyE() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getInstallType() {
		return installType;
	}
	public void setInstallType(String installType) {
		this.installType = installType;
	}
	public String getDefaultPermissionPolicy() {
		return defaultPermissionPolicy;
	}
	public void setDefaultPermissionPolicy(String defaultPermissionPolicy) {
		this.defaultPermissionPolicy = defaultPermissionPolicy;
	}
	
	public Boolean getDisabled() {
		return disabled;
	}
	
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Integer getMinimumVersionCode() {
		return minimumVersionCode;
	}
	public void setMinimumVersionCode(Integer minimumVersionCode) {
		this.minimumVersionCode = minimumVersionCode;
	}
	public List<String> getDelegatedScopes() {
		return delegatedScopes;
	}
	public void setDelegatedScopes(List<String> delegatedScopes) {
		this.delegatedScopes = delegatedScopes;
	}
	

//	public List<String> getAccessibleTrackIds() {
//		return accessibleTrackIds;
//	}
//	public void setAccessibleTrackIds(List<String> accessibleTrackIds) {
//		this.accessibleTrackIds = accessibleTrackIds;
//	}
	
	
	public PolicyE getPolicyE() {
		return policyE;
	}
	public void setPolicyE(PolicyE policyE) {
		this.policyE = policyE;
	}
	public Map<String, Object> getManagedConfigurationMap() {
		return managedConfigurationMap;
	}
	public void setManagedConfigurationMap(Map<String, Object> managedConfigurationMap) {
		this.managedConfigurationMap = managedConfigurationMap;
	}

	public List<AppTrackInfoE> getAccessibleTrackIds() {
		return accessibleTrackIds;
	}

	public void setAccessibleTrackIds(List<AppTrackInfoE> accessibleTrackIds) {
		this.accessibleTrackIds = accessibleTrackIds;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public Map<String, String> getConfigurationVariables() {
		return configurationVariables;
	}
	public void setConfigurationVariables(Map<String, String> configurationVariables) {
		this.configurationVariables = configurationVariables;
	}

	
	
}
