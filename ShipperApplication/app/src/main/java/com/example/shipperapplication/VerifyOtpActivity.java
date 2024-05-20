//package com.example.shipperapplication;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.google.android.material.button.MaterialButton;
//import com.google.android.material.textfield.TextInputLayout;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class VerifyOtpActivity extends AppCompatActivity {
//
//    private Retrofit retrofit;
//    private RetrofitInterface retrofitInterface;
//    private static final String BASE_URL = "http://10.0.2.2:3302/";
//    private MaterialButton btnVerify;
//    private TextInputLayout txtVerify;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.verify_otp);
//        btnVerify = findViewById(R.id.button_verify_otp);
//        txtVerify = findViewById(R.id.edit_otp);
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        retrofitInterface = retrofit.create(RetrofitInterface.class);
//        btnVerify.setOnClickListener(v -> handleVerify());
//    }
//
//    private void handleVerify() {
//        String otp = txtVerify.getEditText().getText().toString().trim();
//
//        // Check if OTP field is empty
//        if (otp.isEmpty()) {
//            txtVerify.setError("Please enter OTP");
//            return;
//        }
//
//        // Call your Retrofit API interface method to verify OTP
//        Call<Void> call = retrofitInterface.verifyOtp(otp);
//
//        // Execute the network request asynchronously
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                // Handle successful response
//                if (response.isSuccessful()) {
//                    Driver result = response.body();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(VerifyOtpActivity.this);
//                    builder.setTitle("Welcome ")
//                            .setMessage("Successful);
//                    startActivity(new Intent(VerifyOtpActivity.this, NextActivity.class));
//                    finish(); // Finish this activity to prevent going back to OTP screen
//                } else {
//                    // OTP verification failed
//                    // Show appropriate error message
//                    txtVerify.setError("Incorrect OTP. Please try again.");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                // Handle network errors or request failure
//                // Show error message or retry request
//                Toast.makeText(VerifyOtpActivity.this, "Failed to verify OTP. Please try again later.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//}