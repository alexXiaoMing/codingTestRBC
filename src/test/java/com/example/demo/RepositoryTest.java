package com.example.demo;

import com.example.demo.dao.ConfigRepository;
import com.example.demo.entity.Config;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private ConfigRepository configRepository;

    @Test
    public void test() throws Exception{
        this.configRepository.save(new Config("appCode1", "V1", "port:8080"));
        this.configRepository.save(new Config("appCode1", "V2", "port:8081"));
        this.configRepository.save(new Config("appCode2", "V1", "port:8280"));
        this.configRepository.save(new Config("appCode2", "V2", "port:8281"));
        this.configRepository.save(new Config("appCode1", "V3", "port:8082"));
        // test save
        Assert.assertEquals(5,configRepository.count());

        // test findBy appCode and version
        Config config = configRepository.findByAppCodeAndVersion("appCode1", "V2");
        Assert.assertThat(config.getJsonDoc(),endsWith("8081"));

        configRepository.findAllByAppCodeOrderByModifiedDateDesc("appCode1")
                .stream().forEach(System.out::println);

        Thread.sleep(1000);
        config.setJsonDoc("port:9090");
        // test update
        this.configRepository.save(config);

        // test order
        configRepository.findAllByAppCodeOrderByModifiedDateDesc("appCode1")
                .stream().forEach(System.out::println);
    }
}
