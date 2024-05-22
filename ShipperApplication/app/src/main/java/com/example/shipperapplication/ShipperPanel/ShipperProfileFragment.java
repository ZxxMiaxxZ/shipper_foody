package com.example.shipperapplication.ShipperPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.shipperapplication.Driver;
import com.example.shipperapplication.DriverResponse;
import com.example.shipperapplication.R;
import com.example.shipperapplication.RetrofitInterface;
import com.example.shipperapplication.SharedPreferencesManager;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipperProfileFragment extends Fragment {
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://10.0.2.2:3001/";
    private String authToken;

    TextInputEditText txtDisplayName, txtUsername, txtEmail, txtPhone, txtLicenseNumber, txtVehicleType, txtCicardNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shipper_profile, container, false);

        // Initialize TextInputEditTexts
        txtDisplayName = v.findViewById(R.id.displayname_edit);
        txtUsername = v.findViewById(R.id.username_edit);
        txtEmail = v.findViewById(R.id.email_edit);
        txtPhone = v.findViewById(R.id.phone_edit);
        txtLicenseNumber = v.findViewById(R.id.driver_licenseNumber_edit);
        txtVehicleType = v.findViewById(R.id.vehicleType_edit);
        txtCicardNumber = v.findViewById(R.id.cicard_number_edit);

        // Get authToken from SharedPreferences
        authToken = SharedPreferencesManager.getInstance(requireContext()).getAuthToken();
        Log.d("ShipperProfileFragment", "AuthToken: " + authToken);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call API to get user data
        Call<DriverResponse> call = retrofitInterface.getDriverProfile("Bearer " + authToken);
        call.enqueue(new Callback<DriverResponse>() {
            @Override
            public void onResponse(Call<DriverResponse> call, Response<DriverResponse> response) {
                if (response.isSuccessful()) {
                    DriverResponse driverResponse = response.body();
                    if (driverResponse != null) {
                        Driver driver = driverResponse.getDriver();
                        if (driver != null) {
                            Log.d("ShipperProfileFragment", "Driver Name: " + driver.getName());
                            // Display data in TextInputEditTexts
                            txtDisplayName.setText(driver.getName());
                            txtUsername.setText(driver.getUsername());
                            txtEmail.setText(driver.getEmail());
                            txtPhone.setText(driver.getPhone());
                            txtLicenseNumber.setText(driver.getDriver_licenseNumber());
                            txtVehicleType.setText(driver.getVehicleType());
                            txtCicardNumber.setText(driver.getCicardNumber());
                        } else {
                            Log.w("ShipperProfileFragment", "Driver data is null");
                        }
                    } else {
                        Log.w("ShipperProfileFragment", "DriverResponse is null");
                    }
                } else {
                    Log.e("ShipperProfileFragment", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverResponse> call, Throwable t) {
                Log.e("ShipperProfileFragment", "API call failed: ", t);
            }
        });
        return v;
    }
}
