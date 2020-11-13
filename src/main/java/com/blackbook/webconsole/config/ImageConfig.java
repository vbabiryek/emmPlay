package com.blackbook.webconsole.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;

@Configuration
public class ImageConfig{

	private List<MediaType> getSupportedMediaTypes() {
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.APPLICATION_OCTET_STREAM);
		list.add(MediaType.IMAGE_JPEG);
		return list;
	}
	
	@Bean
	public ByteArrayHttpMessageConverter byteArrayMessageHttpConverter() {
		ByteArrayHttpMessageConverter byteArray = new ByteArrayHttpMessageConverter();
		byteArray.setSupportedMediaTypes(getSupportedMediaTypes());
		return byteArray;
	}
	
}
