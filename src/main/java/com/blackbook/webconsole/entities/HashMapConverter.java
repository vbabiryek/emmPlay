package com.blackbook.webconsole.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
	private ObjectMapper objMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<String, Object> attribute) {//The value you're storing in the database - String value(s)
		//ObjectMapper
		String objectStrToJsonVal = null;
		try {
			objectStrToJsonVal = objMapper.writeValueAsString(attribute);//Takes the string and converts it to JSON object
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objectStrToJsonVal;//returns the JSON object
	}

	@Override
	public Map<String, Object> convertToEntityAttribute(String dbDataJSON) {//De-serializes the JSON value back to String
		Map<String, Object> jsonIntoMapObj = new HashMap<>();
		if("{}".equals(dbDataJSON) || dbDataJSON == null) {
			return jsonIntoMapObj;
		}
		try {
			jsonIntoMapObj = objMapper.readValue(dbDataJSON, Map.class);//It takes the obj and converts to a hashmap obj
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonIntoMapObj;
	}

}
