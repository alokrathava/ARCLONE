package com.razi.furnitar;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.data.User;
import com.network.Api;
import com.network.AppConfig;
import com.network.ServerResponse;
import com.razi.furnitar.databinding.ActivityLoginBinding;
import com.utils.Config;
import com.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private final Context context = this;
    private ActivityLoginBinding binding;
    private String Email, Password;
    private SharedPrefManager sharedPreferences;
    private User user;
    private String u_id, u_name, u_email, u_password, u_mob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sharedPreferences = new SharedPrefManager(context);
        init();
    }

    private void init() {
        binding.registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.this.startActivity(new Intent(context, signUp.class));
                Log.e(TAG, "onClick: Register Button");
            }
        });
        binding.signinbtn.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        Email = binding.email.getText().toString();
        Password = binding.password.getText().toString();
        if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password)) {
            binding.email.setError("All fields are required");
            binding.password.setError("All fields are required");
        } else {
            executeLogin(Email, Password);
        }
    }

    private void executeLogin(String email, String password) {
        Log.e(TAG, "executeLogin: " + email);
        Log.e(TAG, "executeLogin: " + password);

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.login(email, password);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body() != null) {
                    ServerResponse serverResponse = response.body();
                    if (!serverResponse.getError()) {
                        Config.showToast(context, serverResponse.getMessage());
                        sendUserData(serverResponse.getUser());

                    } else {
                        Config.showToast(context, serverResponse.getMessage());
                    }
                } else {
                    Config.showToast(context, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Config.showToast(context, t.getMessage());
            }
        });


    }

    /*----------------------------------------------- Save User Data -----------------------------------------------*/

    private void sendUserData(User user) {

        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        sharedPrefManager.setInt("id", user.getUId());
        sharedPrefManager.setString("name", user.getUName());

        sharedPref();
    }


    @Override
    protected void onStart() {
        super.onStart();

        sharedPref();

    }

    private void sharedPref() {
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        Config.user_id = sharedPrefManager.getInt("id");

        if (Config.user_id != -1) {

            openActivity(DashboardActivity.class);
            finish();
        }

    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }
}
