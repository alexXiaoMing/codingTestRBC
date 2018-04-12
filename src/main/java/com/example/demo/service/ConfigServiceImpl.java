package com.example.demo.service;

import com.example.demo.dao.ConfigRepository;
import com.example.demo.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "configServiceImpl")
public class ConfigServiceImpl implements ConfigService {

    private ConfigRepository configRepository;

    public ConfigServiceImpl(@Autowired ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public Config findByAppCodeAndVersion(String appCode, String version) {
        return configRepository.findByAppCodeAndVersion(appCode, version);
    }

    @Override
    public Config saveOrUpdate(Config config) {
        if(null == config)
            return null;
        if(null != config.getId())
            return configRepository.save(config);
        else{
            Config existConfig = configRepository.findByAppCodeAndVersion(config.getAppCode(),config.getVersion());
            if(null !=existConfig) config.setId(existConfig.getId());
            return configRepository.save(config);
        }

    }

    @Override
    public List<Config> findAllByAppCode(String appCode) {
        return configRepository.findAllByAppCodeOrderByModifiedDateDesc(appCode);
    }
}
