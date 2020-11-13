package com.blackbook.webconsole.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.blackbook.webconsole.entities.ApplicationsPolicyE;

public interface ApplicationRepository extends CrudRepository<ApplicationsPolicyE, Long>{

	Optional<ApplicationsPolicyE> findByPackageName(String packageName);

}
