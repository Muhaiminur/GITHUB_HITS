package com.misfit.githubhits.MODEL.GET;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.misfit.githubhits.MODEL.GET.GITREPO.DEVOLIST;
import com.misfit.githubhits.MODEL.GET.GITREPO.REPOLIST;

import java.util.List;

public class GITHUBDEVO {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<DEVOLIST> devolists = null;

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

    public List<DEVOLIST> getREPOLISTS() {
        return devolists;
    }

    public void setREPOLISTS(List<DEVOLIST> REPOLISTS) {
        this.devolists = REPOLISTS;
    }

    @Override
    public String toString() {
        return "GITHUBREPO{" +
                "totalCount=" + totalCount +
                ", incompleteResults=" + incompleteResults +
                ", REPOLISTS=" + devolists +
                '}';
    }
}