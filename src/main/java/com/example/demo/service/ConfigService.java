package com.example.demo.service;

import com.example.demo.entity.Config;

import java.util.List;

public interface ConfigService {

    Config findByAppCodeAndVersion(String appCode, String version);

    Config saveOrUpdate (Config config);

    List<Config> findAllByAppCode(String appCode);
}
