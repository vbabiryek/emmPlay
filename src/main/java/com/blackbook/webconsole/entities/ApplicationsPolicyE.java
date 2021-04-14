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
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.blackbook.webconsole.pojo.ManagedConfigurationTemplateE;
import com.blackbook.webconsole.repositories.ManagedConfigurationTemplateRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Application_Policy")
@Component
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
	private Boolean disabled;
	private Integer minimumVersionCode;
	
	
	//using rdbms so it's connected through relations independent of each other
	//directly controlled by the user through jdbc
	//will always need a getter method to retrieve the entire managedConfigTemplate jdbc template object so it can be injected
	@Transient
	private ManagedConfigurationTemplateE managedConfigurationTemplate;
	
	@JsonIgnore
	@Transient
	private static ManagedConfigurationTemplateRepository templateIdRepository;
	
	@ElementCollection
	private List<String> delegatedScopes;
	
//	better practice to demonstrate the template over the managedConfig object	
//	@Convert(converter = HashMapConverter.class)
//	private Map<String, Object> managedConfigurationMap;

	
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
//	public Map<String, Object> getManagedConfigurationMap() {
//		return managedConfigurationMap;
//	}
//	public void setManagedConfigurationMap(Map<String, Object> managedConfigurationMap) {
//		this.managedConfigurationMap = managedConfigurationMap;
//	}

	public List<AppTrackInfoE> getAccessibleTrackIds() {
		return accessibleTrackIds;
	}

	public void setAccessibleTrackIds(List<AppTrackInfoE> accessibleTrackIds) {
		this.accessibleTrackIds = accessibleTrackIds;
	}

	//needs jdbc query to get the injection of the jdbc template
	//used for jdbc UI json so when viewed as a json result, it needs to access views from my db
	//when sending values from my UI's form to the java layer so it's saved, I need to use the MC template in the UI form
	//by using this getter method here
	public ManagedConfigurationTemplateE getManagedConfigurationTemplate() {
		return templateIdRepository.findByApplicationId(1L);
	}
	
	//handles the ui view so when it's going to the db and sending info from the forms, it works!
	public ManagedConfigurationTemplateE getManagedConfigurationView() {
		return this.managedConfigurationTemplate;
	}

	public void setManagedConfigurationTemplate(ManagedConfigurationTemplateE managedConfigurationTemplate) {
		this.managedConfigurationTemplate = managedConfigurationTemplate;
	}
	
	@Autowired
	public void setTemplateIdRepository(ManagedConfigurationTemplateRepository templateIdRepository) {
		ApplicationsPolicyE.templateIdRepository = templateIdRepository;
	}

}
