package com.misfit.githubhits.HTTP;

import com.misfit.githubhits.MODEL.GET.GETBATTLEUSER;
import com.misfit.githubhits.MODEL.GET.GET_LOGIN;
import com.misfit.githubhits.MODEL.GET.GET_OWNREPO;
import com.misfit.githubhits.MODEL.GET.GITHUBDEVO;
import com.misfit.githubhits.MODEL.GET.GITHUBREPO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //1 github hits repository
    @GET("search/repositories")
    Call<GITHUBREPO> GIT_HITS_REPO(@Query("q") String que, @Query("sort") String sort, @Query("order") String order, @Query("per_page") int page_count);

    //2 github hits Developers
    @GET("search/users")
    Call<GITHUBDEVO> GIT_HITS_DEVELOPERS(@Query("q") String que, @Query("followers") int follow, @Query("sort") String sort, @Query("order") String order);

    //3 Login Verification
    @POST("user")
    Call<GET_LOGIN> SIGN_IN(@Header("Content-Type") String ct, @Header("Accept") String acc, @Header("Authorization") String token);

    //4 github own repository
    @GET("/users/{own_url}/repos")
    Call<List<GET_OWNREPO>> OWN_REPO(@Path("own_url") String url);

    //5 github user search
    @GET("/users/{username}")
    Call<GETBATTLEUSER> SEARCH_USER(@Path("username") String name);

}
