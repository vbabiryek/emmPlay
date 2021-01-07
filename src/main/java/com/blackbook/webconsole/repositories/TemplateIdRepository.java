package com.blackbook.webconsole.repositories;

import java.util.List;
import java.util.Optional;

import com.blackbook.webconsole.pojo.TemplatePolicy;

public interface TemplateIdRepository {
	int count();

    int save(TemplatePolicy templatePolicy);

    int update(TemplatePolicy templatePolicy);

    int deleteByTemplateId(Long templateId);
    
    int deleteAllConfigVariablesByTemplateId(Long templateId); // This because we need to delete the children first.
    
    List<TemplatePolicy> findAll();
    
    Optional<TemplatePolicy> findByTemplateId(Long templateId);// serves as the select by template id
    
}
