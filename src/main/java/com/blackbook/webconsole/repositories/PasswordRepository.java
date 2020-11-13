package com.blackbook.webconsole.repositories;

import org.springframework.data.repository.CrudRepository;

import com.blackbook.webconsole.entities.PasswordRequirementsE;

public interface PasswordRepository extends CrudRepository <PasswordRequirementsE, Long>{
	
}
