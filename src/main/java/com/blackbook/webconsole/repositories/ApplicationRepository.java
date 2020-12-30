package com.blackbook.webconsole.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.blackbook.webconsole.entities.ApplicationsPolicyE;

public interface ApplicationRepository extends CrudRepository<ApplicationsPolicyE, Long>{

	ArrayList<ApplicationsPolicyE> findByPackageName(String packageName);
	ArrayList<ApplicationsPolicyE> findAll();

}
