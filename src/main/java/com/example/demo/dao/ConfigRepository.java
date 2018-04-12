package com.example.demo.dao;

import com.example.demo.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config findByAppCodeAndVersion(String appCode, String version);

    Config save(Config config);

    List<Config> findAllByAppCodeOrderByModifiedDateDesc(String appCode);
}
