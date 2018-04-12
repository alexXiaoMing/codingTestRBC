package com.example.demo.controller;

import com.example.demo.entity.Config;
import com.example.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ConfigController {

    private ConfigService configService;

    public ConfigController(@Autowired ConfigService configServiceImpl){
        this.configService = configServiceImpl;
    }

    @GetMapping("{appCode}/config/{version}")
    public Config getConfigByAppCodeAndVersion(@PathVariable String appCode, @PathVariable String version){
        return configService.findByAppCodeAndVersion(appCode,version);
    }

    @PostMapping("{appCode}/config/{version}")
    public Config mergeConfig(@RequestBody Config config,
                              @PathVariable String appCode, @PathVariable String version){
        config.setAppCode(appCode);
        config.setVersion(version);
        return configService.saveOrUpdate(config);
    }

    @GetMapping("{appCode}/config")
    public List<Config> findAllVersionByAppCode(@PathVariable String appCode){
        return configService.findAllByAppCode(appCode);
    }
}
