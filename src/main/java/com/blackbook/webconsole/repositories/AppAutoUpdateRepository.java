package com.blackbook.webconsole.repositories;

import org.springframework.data.repository.CrudRepository;
import com.blackbook.webconsole.entities.AppAutoUpdatePolicyE;

public interface AppAutoUpdateRepository extends CrudRepository<AppAutoUpdatePolicyE, Long>{

}
