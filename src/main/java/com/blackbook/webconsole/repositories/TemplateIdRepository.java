package com.blackbook.webconsole.repositories;

import java.util.List;
import java.util.Optional;

import com.blackbook.webconsole.pojo.TemplatePolicy;

public interface TemplateIdRepository {
	Long count();

    Long save(TemplatePolicy templatePolicy);

    Long update(TemplatePolicy templatePolicy);

    Long deleteByTemplateId(Long templateId);
    
    Long deleteAllConfigVariablesByTemplateId(Long templateId); // This because we need to delete the children first.
    
    List<TemplatePolicy> findAll();
    
    Optional<TemplatePolicy> findByTemplateId(Long templateId);// serves as the select by template id
    
}
