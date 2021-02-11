package com.example.coronanews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonApiHolder {



    @GET("contacts")
    Call<String> getContacts();

    @GET("all")
    Call<String> getAllStats();

    @GET("countries")
    Call<String> getAllCountry();

    @GET("statewise")
    Call<String> getLatest();
    @GET("top-headlines")
    Call<String> getNews(@Query("apiKey") String api_key,
                         @Query("country") String in);

//    @GET("posts")
//    Call<List<Post>> getPosts();

//    @FormUrlEncoded
//    @POST("api.aspx")
//    Call<String> getComments(@Field("RequestType") String user);
//
//    @FormUrlEncoded
//    @POST("api.aspx")
//    Call<String> getDepartment(@Field("RequestType") String user);
//
//    @FormUrlEncoded
//    @POST("api.aspx")
//    Call<String> getCategory(@Field("RequestType") String user);
//
//
//    @FormUrlEncoded
//    @POST("api.aspx")
//    Call<String> addUser(@Field("RequestType") String requestType,
//                         @Field("FirstName") String FirstName,
//                         @Field("LastName") String LastName,
//                         @Field("Email") String Email,
//                         @Field("Password") String Password);
//
//
//    @FormUrlEncoded
//    @POST("api.aspx")
//    Call<String> addCategory(@Field("RequestType") String requestType,
//                             @Field("name") String Name,
//                             @Field("CrUser") int LastName);
//
//    @FormUrlEncoded
//    @POST("api.aspx")
//    Call<String> addDepartment(@Field("RequestType") String requestType,
//                               @Field("Deap") String Name,
//                               @Field("CrUser") int LastName);


}
