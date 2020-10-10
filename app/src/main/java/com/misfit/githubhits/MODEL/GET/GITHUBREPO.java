package com.misfit.githubhits.MODEL.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.misfit.githubhits.MODEL.GET.GITREPO.REPOLIST;

import java.util.List;

public class GITHUBREPO {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<REPOLIST> REPOLISTS = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public List<REPOLIST> getREPOLISTS() {
        return REPOLISTS;
    }

    public void setREPOLISTS(List<REPOLIST> REPOLISTS) {
        this.REPOLISTS = REPOLISTS;
    }

    @Override
    public String toString() {
        return "GITHUBREPO{" +
                "totalCount=" + totalCount +
                ", incompleteResults=" + incompleteResults +
                ", REPOLISTS=" + REPOLISTS +
                '}';
    }
}