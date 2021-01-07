package com.blackbook.webconsole.jdbc;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.blackbook.webconsole.pojo.TemplatePolicy;
import com.blackbook.webconsole.repositories.TemplateIdRepository;

@Repository
public class TemplateIdJdbc implements TemplateIdRepository{
	
	//constants
	private final static String INSERT_TEMPLATE_ID = "insert into template_policy (application_policy_id,template_id) values(:application_policy_id,:template_id)";
	private final static String INSERT_CONFIGURATION_VARIABLES = "insert into configuration_variable (template_id, configuration_key, configuration_val) values(:template_id,:configuration_key,:configuration_val)";
	private final static String DELETE_TEMPLATE_ID = "delete from template_policy where template_id = :template_id";
	private final static String DELETE_CONFIGURATION_VARIABLES = "delete from configuration_variable where template_id = :template_id";
	private final static String SELECT_CONFIGURATION_VARIABLES_BY_TEMPLATE_ID = "select * from configuration_variable cv where cv.template_id = (select tp.template_id from template_policy tp where tp.template_id = :template_id)";
	private final static String SELECT_TEMPLATE_ID = "select * from template_policy where template_id = :template_id";
	
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
		KeyHolder keyholder = new GeneratedKeyHolder();
		
		namedParameterJdbcTemplate.update(INSERT_TEMPLATE_ID, new BeanPropertySqlParameterSource(templatePolicy), keyholder);
		for(Map.Entry<String, String> mapVal : templatePolicy.getConfigurationVariables().entrySet()) {
			namedParameterJdbcTemplate.update(INSERT_CONFIGURATION_VARIABLES, 
					new MapSqlParameterSource("template_id", templatePolicy.getTemplateId())
					.addValue("configuration_key", mapVal.getKey())
					.addValue("configuration_val", mapVal.getValue()));
		}
//				new MapSqlParameterSource("template_id", templatePolicy.getTemplateId())
//				.addValue("application_policy_id", templatePolicy.getApplicationPolicyId()));
		return (int) keyholder.getKey();
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
		Optional<TemplatePolicy> foundTemplatePolicy = namedParameterJdbcTemplate
				.queryForObject(SELECT_TEMPLATE_ID, new MapSqlParameterSource("template_id", templateId), (rs, rowNum) ->
                Optional.of(new TemplatePolicy(
                        rs.getLong("id"),
                        rs.getLong("application_policy_id"),
                        rs.getString("template_id")
                )));
		
		namedParameterJdbcTemplate.query(SELECT_CONFIGURATION_VARIABLES_BY_TEMPLATE_ID, (ResultSet resultSet) -> { //callback returns after execution of query
			Map<String, String> resultMap = new HashMap<>(); //Many rows
			while(resultSet.next()) {
				resultMap.put(resultSet.getString("configuration_key"), resultSet.getString("configuration_val"));
			}
		 foundTemplatePolicy.get().setConfigurationVariables(resultMap);
		});		
		return foundTemplatePolicy;
	}

}
