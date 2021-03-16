package com.blackbook.webconsole.jdbc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.blackbook.webconsole.pojo.ManagedConfigurationTemplateE;
import com.blackbook.webconsole.repositories.ManagedConfigurationTemplateRepository;
import com.mysql.cj.jdbc.exceptions.SQLError;

@Repository
public class ManagedConfigurationTemplateJdbc implements ManagedConfigurationTemplateRepository{
	
	// NOTE: When DB is dropped it is necessary to drop these tables manually using the SQL queries inside of MySQL workbench!
	
	//constants
	private final static String INSERT_TEMPLATE_ID = "insert into managedConfigurationTemplate (application_policy_id,template_id) values(:application_policy_id,:template_id)";
	private final static String INSERT_CONFIGURATION_VARIABLES = "insert into configuration_variables (template_id, configuration_key, configuration_val) values(:template_id,:configuration_key,:configuration_val)";
	private final static String DELETE_TEMPLATE_ID = "delete from managedConfigurationTemplate where template_id = :template_id";
	private final static String DELETE_CONFIGURATION_VARIABLES = "delete from configuration_variables where template_id = :template_id";
	private final static String SELECT_CONFIGURATION_VARIABLES_BY_TEMPLATE_ID = "select * from configuration_variables cv where cv.template_id = (select tp.template_id from managedConfigurationTemplate tp where tp.template_id = :template_id)";
	private final static String SELECT_TEMPLATE_ID = "select * from managedConfigurationTemplate where template_id = :template_id";
	private final static String SELECT_ALL_TEMPLATES = "select * from managedConfigurationTemplate";
	private static final Logger LOG = LoggerFactory.getLogger(ManagedConfigurationTemplateJdbc.class);
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return 1L;
	}

	@Override
	public Long save(ManagedConfigurationTemplateE managedConfigurationTemplatePolicy) {
		KeyHolder keyholder = new GeneratedKeyHolder();
		managedConfigurationTemplatePolicy.setApplicationPolicyId(1L);
		try {
			namedParameterJdbcTemplate.update(INSERT_TEMPLATE_ID, new MapSqlParameterSource("application_policy_id", managedConfigurationTemplatePolicy.getApplicationPolicyId())
					.addValue("template_id",  managedConfigurationTemplatePolicy.getTemplateId()), keyholder);
					for(Map.Entry<String, String> mapVal : managedConfigurationTemplatePolicy.getConfigurationVariables().entrySet()) {
						namedParameterJdbcTemplate.update(INSERT_CONFIGURATION_VARIABLES, 
								new MapSqlParameterSource("template_id", managedConfigurationTemplatePolicy.getTemplateId())
								.addValue("configuration_key", mapVal.getKey())
								.addValue("configuration_val", mapVal.getValue()));
					}
//							new MapSqlParameterSource("template_id", templatePolicy.getTemplateId())
//							.addValue("application_policy_id", templatePolicy.getApplicationPolicyId()));
					LOG.info("saving managedConfigurationTemplate");
					LOG.info("namedParameterJdbcTemplate.getJdbcTemplate is: " + namedParameterJdbcTemplate.getJdbcTemplate());
					LOG.info("keyholder.getKey().longValue() is: " + keyholder.getKey().longValue());
					return keyholder.getKey().longValue();
		}catch(Exception e){
			return null;
		}
	
	}

	@Override
	public Long update(ManagedConfigurationTemplateE managedConfigurationTemplate) {
		// TODO Auto-generated method stub
		return 1L;
	}

	@Override
	public void deleteByTemplateId(String templateId) {
		// TODO Auto-generated method stub
		if(templateId != null) {
			//dependency has to be deleted before you can delete the parent
			namedParameterJdbcTemplate.update(DELETE_CONFIGURATION_VARIABLES, new MapSqlParameterSource("template_id", templateId));
			namedParameterJdbcTemplate.update(DELETE_TEMPLATE_ID, new MapSqlParameterSource("template_id", templateId));
		}
	}

	@Override
	public Long deleteAllConfigVariablesByTemplateId(Long templateId) {
		// TODO Auto-generated method stub
		return 1L;
	}

	@Override
	public List<ManagedConfigurationTemplateE> findAll() {
		// Takes the data that it gets from each row and creates an object. 
		List<ManagedConfigurationTemplateE> templateList = new ArrayList<>();
				namedParameterJdbcTemplate
				.query(SELECT_ALL_TEMPLATES, (rs, rowNum) ->
               templateList.add(new ManagedConfigurationTemplateE(
                        rs.getLong("row_id"),
                        rs.getLong("application_policy_id"),
                        rs.getString("template_id")
                )));
				for(ManagedConfigurationTemplateE t : templateList) {
					setConfigVariablesForTemplatePolicy(t);//sets the config variables for this template
				}
				LOG.info("finding all templates");
		return templateList;
	}

	@Override
	public Optional<ManagedConfigurationTemplateE> findByTemplateId(Long templateId) {
		// TODO Auto-generated method stub
		Optional<ManagedConfigurationTemplateE> foundTemplatePolicy = namedParameterJdbcTemplate
				.queryForObject(SELECT_TEMPLATE_ID, new MapSqlParameterSource("template_id", templateId), (rs, rowNum) ->
                Optional.of(new ManagedConfigurationTemplateE(
                        rs.getLong("row_id"),
                        rs.getLong("application_policy_id"),
                        rs.getString("template_id")
                )));		
		setConfigVariablesForTemplatePolicy(foundTemplatePolicy.get());
		LOG.info("finding by template id");
		return foundTemplatePolicy;
	}
	
	private ManagedConfigurationTemplateE setConfigVariablesForTemplatePolicy(ManagedConfigurationTemplateE tp) {
		namedParameterJdbcTemplate.query(SELECT_CONFIGURATION_VARIABLES_BY_TEMPLATE_ID, 
				new MapSqlParameterSource("template_id", tp.getTemplateId()), (ResultSet resultSet) -> { //callback that returns after execution of query
			Map<String, String> resultMap = new HashMap<>(); //Many rows
			if(resultSet.first()) {
				resultMap.put(resultSet.getString("configuration_key"), resultSet.getString("configuration_val"));
			}
			while(resultSet.next()) {
				resultMap.put(resultSet.getString("configuration_key"), resultSet.getString("configuration_val"));
			}
//			resultSet.close();//We don't want a memory leak
		 tp.setConfigurationVariables(resultMap);
		 LOG.info("number of config variables found in db " + resultMap.size());
		});	
		LOG.info("setting config variables for this templatePolicy");
		return tp;
		
	}

	@Override
	public ManagedConfigurationTemplateE findByApplicationId(Long applicationPolicyId) {
		// TODO Auto-generated method stub
		if(findAll().size() == 0) { //in the event that the db is null it will return null else it will grab the first applicationPolicyId
			return null;
		}
		return findAll().get(0);
	}

}
