package com.example.demo;

import com.example.demo.controller.ConfigController;
import com.example.demo.entity.Config;
import com.example.demo.service.ConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.List;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testExample() throws Exception {
        restTemplate.postForEntity("/api/app1/config/V1",new Config("'port':8081"),Config.class);
        restTemplate.postForEntity("/api/app1/config/V2",new Config("'port':8082"),Config.class);
        restTemplate.postForEntity("/api/app1/config/V3",new Config("'port':8083"),Config.class);
        restTemplate.postForEntity("/api/app2/config/V1",new Config("'port':8281"),Config.class);
        restTemplate.postForEntity("/api/app2/config/V2",new Config("'port':8282"),Config.class);
        restTemplate.postForEntity("/api/app2/config/V3",new Config("'port':8283"),Config.class);

        restTemplate.getForEntity("/api/app1/config",List.class)
                .getBody().stream().forEach(System.out::println);
        // test find specified config
        ResponseEntity<Config> res = restTemplate.getForEntity("/api/app1/config/V2",Config.class);
        Assert.assertThat(res.getBody().getJsonDoc(),endsWith("8082"));

        // test update
        restTemplate.postForEntity("/api/app1/config/V2",new Config("'port':8092"),Config.class);

        // test desc order
        restTemplate.getForEntity("/api/app1/config",List.class)
                .getBody().stream().forEach(System.out::println);

    }


    @Before
    public void setup() {
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
//        // MockMvc standalone approach
//        mvc = MockMvcBuilders.standaloneSetup(superHeroController)
//                .setControllerAdvice(new SuperHeroExceptionHandler())
//                .addFilters(new SuperHeroFilter())
//                .build();
    }
}
