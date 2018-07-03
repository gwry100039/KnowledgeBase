package com.gourui.knowledgebase.domain;


import java.io.Serializable;

public class DataCaliber implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String requirementId;

    private String requirementName;

    private String workerName;

    private String extractorName;

    private String departmentName;

    private String comment;

    private String sql;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getExtractorName() {
        return extractorName;
    }

    public void setExtractorName(String extractorName) {
        this.extractorName = extractorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
