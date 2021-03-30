package com.razi.furnitar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.network.Api;
import com.network.AppConfig;
import com.network.ServerResponse;
import com.razi.furnitar.databinding.ActivitySignUpBinding;
import com.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class signUp extends AppCompatActivity {

    private static final String TAG = "signUp";
    private final Context context = this;
    private ActivitySignUpBinding binding;
    private String Email, Password, Name, Mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.signupbtn.setOnClickListener(v -> {
            doRegister();
        });
        binding.signinbtn.setOnClickListener(v -> startActivity(new Intent(context, Login.class)));
    }

    private void doRegister() {
        Name = binding.name.getText().toString();
        Email = binding.email.getText().toString();
        Password = binding.password.getText().toString();
        Mobile = binding.mobile.getText().toString();

        if (TextUtils.isEmpty(Name) && TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password) && TextUtils.isEmpty(Mobile)) {
            binding.name.setError("All Fields are Required");
            binding.email.setError("All Fields are Required");
            binding.password.setError("All Fields are Required");
            binding.mobile.setError("All Fields are Required");
        } else {
            ExecuteRegister(Name, Email, Password, Mobile);
        }
    }

    private void ExecuteRegister(String name, String email, String password, String mobile) {
        Log.e(TAG, "ExecuteRegister: " + name);
        Log.e(TAG, "ExecuteRegister: " + email);
        Log.e(TAG, "ExecuteRegister: " + password);
        Log.e(TAG, "ExecuteRegister: " + mobile);

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.register(name, email, password, mobile);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body() != null) {
                    ServerResponse serverResponse = response.body();
                    if (!serverResponse.getError()) {
                        Config.showToast(context, serverResponse.getMessage());
                        MoveActivity();
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

    private void MoveActivity() {
        startActivity(new Intent(context, Login.class));
    }


}
