package com.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    String my_url = "Api.php?apicall=";

    @FormUrlEncoded
    @POST(my_url + "register")
    Call<ServerResponse> register(
            @Field("u_name") String name,
            @Field("u_email") String email,
            @Field("u_mob") String mobile,
            @Field("u_password") String password
    );

    @FormUrlEncoded
    @POST(my_url + "login")
    Call<ServerResponse> login(
            @Field("email") String email,
            @Field("pwd") String password
    );

}
