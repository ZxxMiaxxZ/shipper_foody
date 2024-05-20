package com.example.shipperapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://10.0.2.2:3302/";
    private MaterialButton btnLogin, btnLinkToRegister, btnForgotPass;
    private TextInputEditText inputUsername, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = findViewById(R.id.username);
        inputPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.button_login);
        btnLinkToRegister = findViewById(R.id.button_register);
        btnForgotPass = findViewById(R.id.button_reset);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        btnLogin.setOnClickListener(v -> handleLogin());

        btnLinkToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });

        btnForgotPass.setOnClickListener(v -> {
            handleForgotPassword();
        });
    }

    private void handleLogin() {
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill in both fields", Toast.LENGTH_LONG).show();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        Call<Driver> call = retrofitInterface.executeLogin(map);

        call.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Driver result = response.body();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Welcome " + result.getUsername())
                            .setMessage("Email: " + result.getEmail())
                            .setPositiveButton("OK", (dialog, which) -> {
                                // Redirect to ShipperPanelBottomNavigationActivity
                                Intent intent = new Intent(MainActivity.this, ShipperPanelBottomNavigationActivity.class);
                                startActivity(intent);
                                finish();  // Optional: finish the current activity
                            })
                            .show();
                    // Save user session here if needed
                } else if (response.code() == 401) {
                    Toast.makeText(MainActivity.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Login failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleForgotPassword() {
        // Implement your forgot password functionality here
        Toast.makeText(MainActivity.this, "Forgot password feature coming soon!", Toast.LENGTH_LONG).show();
    }
}
