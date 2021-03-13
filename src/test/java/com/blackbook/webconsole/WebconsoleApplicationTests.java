package com.blackbook.webconsole;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

import com.blackbook.webconsole.controllers.urls.Urls;
import com.blackbook.webconsole.services.EnterpriseI;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(Parameterized.class)
class WebconsoleApplicationTests {

	@Autowired
	public EnterpriseI enterpriseImpl;
	
	@Autowired
	public MockMvc mockMvc;
	
	@Test
	void visitHomepage() throws Exception {
		mockMvc.perform(get(Urls.GET_COSU_POLICY)).andDo(print()).andExpect(status().isOk());
	}
	
	
	@Test
	void urlTesting() {
		assertThat(enterpriseImpl).isNotNull();
	}
	
	//Mock MVC - inject dependencies through REST (Autowires) - visit the URLs

}
