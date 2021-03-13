package com.blackbook.webconsole.repositories;

import java.util.List;
import java.util.Optional;

import com.blackbook.webconsole.pojo.ManagedConfigurationTemplateE;

public interface ManagedConfigurationTemplateRepository {
	Long count();

    Long save(ManagedConfigurationTemplateE templatePolicy);

    Long update(ManagedConfigurationTemplateE templatePolicy);

    void deleteByTemplateId(String templateId);
    
    Long deleteAllConfigVariablesByTemplateId(Long templateId); // This because we need to delete the children first.
    
    List<ManagedConfigurationTemplateE> findAll();
    
    Optional<ManagedConfigurationTemplateE> findByTemplateId(Long templateId);// serves as the select by template id
    
}
