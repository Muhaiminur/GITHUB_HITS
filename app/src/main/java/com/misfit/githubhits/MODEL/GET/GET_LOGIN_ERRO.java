package com.misfit.githubhits.MODEL.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GET_LOGIN_ERRO{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("documentation_url")
    @Expose
    private String documentationUrl;

    public GET_LOGIN_ERRO() {
    }

    public GET_LOGIN_ERRO(String message, String documentationUrl) {
        this.message = message;
        this.documentationUrl = documentationUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    @Override
    public String toString() {
        return "GET_LOGIN_ERRO{" +
                "message='" + message + '\'' +
                ", documentationUrl='" + documentationUrl + '\'' +
                '}';
    }
}