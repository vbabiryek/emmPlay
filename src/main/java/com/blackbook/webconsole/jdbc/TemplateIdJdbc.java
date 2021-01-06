package com.blackbook.webconsole.jdbc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.blackbook.webconsole.pojo.TemplatePolicy;
import com.blackbook.webconsole.repositories.TemplateIdRepository;

@Repository
public class TemplateIdJdbc implements TemplateIdRepository{
	
	private final static String INSERT_TEMPLATE_ID = "insert into template_policy (application_policy_id,template_id) values(?,?)";
	private final static String INSERT_CONFIGURATION_VARIABLES = "insert into configuration_variable (template_id, configuration_key, configuration_val) values(?,?,?)";
	private final static String DELETE_TEMPLATE_ID = "delete from template_policy where template_id = ?";
	private final static String DELETE_CONFIGURATION_VARIABLES = "delete from configuration_variable where template_id = ?";
	private final static String SELECT_CONFIGURATION_VARIABLES_BY_TEMPLATE_ID = "select * from configuration_variable cv where cv.template_id = (select tp.template_id from template_policy tp where tp.template_id = ?)";
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int save(TemplatePolicy templatePolicy) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(TemplatePolicy templatePolicy) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByTemplateId(Long templateId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAllConfigVariablesByTemplateId(Long templateId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TemplatePolicy> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TemplatePolicy> findByTemplateId(Long templateId) {
		// TODO Auto-generated method stub
		return null;
	}

}
