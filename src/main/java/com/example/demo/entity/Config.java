package com.example.demo.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Config {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "APPCODE", nullable = false)
    private String appCode;

    @Column(name = "VERSION", nullable = false)
    private String version;

    @Column(name = "JSONDOC", nullable = false)
    @Lob @Basic(fetch = FetchType.LAZY)
    private String jsonDoc;

    @Column(nullable = false, updatable = false)
    private Date createDate;

    @Column(nullable = false)
    private Date modifiedDate;

    public Config() {
    }

    public Config(@NotNull String jsonDoc) {
        this.jsonDoc = jsonDoc;
    }

    public Config(@NotNull String appCode, @NotNull String version, @NotNull String jsonDoc) {
        this.appCode = appCode;
        this.version = version;
        this.jsonDoc = jsonDoc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJsonDoc() {
        return jsonDoc;
    }

    public void setJsonDoc(String jsonDoc) {
        this.jsonDoc = jsonDoc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", appCode='" + appCode + '\'' +
                ", version='" + version + '\'' +
                ", jsonDoc='" + jsonDoc + '\'' +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }

    @PrePersist
    public void onPrePersist() {
        this.setCreateDate(new Date());
        this.setModifiedDate(new Date());
    }

    @PreUpdate
    public void onPreUpdate() {
        this.setModifiedDate(new Date());
    }
}
