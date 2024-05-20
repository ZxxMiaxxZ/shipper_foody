package com.example.shipperapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {
    private MaterialButton btnRegister, btnGoToLogin;
    private TextInputLayout inputName, inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = findViewById(R.id.edit_name);
        inputEmail = findViewById(R.id.edit_email);
        inputPassword = findViewById(R.id.edit_password);
        btnRegister = findViewById(R.id.button_register);
        btnGoToLogin = findViewById(R.id.button_login);

        // Set the click listener for the "Register" button
        btnRegister.setOnClickListener(v -> {
            // You can add your registration logic here

            // After registration logic, navigate to the OTP verification screen
            Intent intent = new Intent(Register.this, VerifyOtpActivity.class);
            startActivity(intent);
        });

        // Set the click listener for the "Go To Login" button
        btnGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
        });
    }

}
