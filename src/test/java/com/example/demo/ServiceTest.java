package com.example.demo;

import com.example.demo.entity.Config;
import com.example.demo.service.ConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.endsWith;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private ConfigService configServiceImpl;

    @Test
    public void test() throws Exception{
        this.configServiceImpl.saveOrUpdate(new Config("appCode1", "V1", "port:8080"));
        this.configServiceImpl.saveOrUpdate(new Config("appCode1", "V2", "port:8081"));
        this.configServiceImpl.saveOrUpdate(new Config("appCode2", "V1", "port:8280"));
        this.configServiceImpl.saveOrUpdate(new Config("appCode2", "V2", "port:8281"));
        this.configServiceImpl.saveOrUpdate(new Config("appCode1", "V3", "port:8082"));
        //test query
        Config config = configServiceImpl.findByAppCodeAndVersion("appCode1", "V2");
        Assert.assertThat(config.getJsonDoc(),endsWith("8081"));

        configServiceImpl.findAllByAppCode("appCode1")
                .stream().forEach(System.out::println);
        Thread.sleep(1000);
        // test update
        config.setJsonDoc("port:9090");
        this.configServiceImpl.saveOrUpdate(config);

        // test order
        this.configServiceImpl.saveOrUpdate(new Config("appCode1", "V1", "port:9001"));
        configServiceImpl.findAllByAppCode("appCode1")
                .stream().forEach(System.out::println);
    }
}
