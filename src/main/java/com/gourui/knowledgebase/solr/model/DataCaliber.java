package com.gourui.knowledgebase.solr.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "new_core")
public class DataCaliber {
    @Id
    @Field
    private String id;

    @Field
    private String requirementDesc;

    @Field
    private String comments;

    private String snipplets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequirementDesc() {
        return requirementDesc;
    }

    public void setRequirementDesc(String requirementDesc) {
        this.requirementDesc = requirementDesc;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSnipplets() {
        return snipplets;
    }

    public void setSnipplets(String snipplets) {
        this.snipplets = snipplets;
    }
}
