package com.example.shipperapplication.ShipperPanel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.shipperapplication.Driver;
import com.example.shipperapplication.DriverResponse;
import com.example.shipperapplication.R;
import com.example.shipperapplication.RetrofitInterface;
import com.example.shipperapplication.SharedPreferencesManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipperProfileFragment extends Fragment {
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://10.0.2.2:3001/";
    private String authToken;

    TextView txtDisplayName, txtUsername, txtEmail, txtPassword, txtPhone, txtLicenseNumber, txtVehicleType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shipper_profile, container, false);
        getActivity().setTitle("Profile");

        // Initialize TextViews
        txtDisplayName = v.findViewById(R.id.displayname);
        txtUsername = v.findViewById(R.id.username);
        txtEmail = v.findViewById(R.id.email);
        txtPassword = v.findViewById(R.id.password);
        txtPhone = v.findViewById(R.id.phone);
        txtLicenseNumber = v.findViewById(R.id.driver_licenseNumber);
        txtVehicleType = v.findViewById(R.id.vehicleType);

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
                    Driver driver = response.body().getDriver();
                    if (driver != null) {
                        // Display data in TextViews
                        txtDisplayName.setText(driver.getName());
                        txtUsername.setText(driver.getUsername());
                        txtEmail.setText(driver.getEmail());
                        txtPassword.setText(driver.getPassword());
                        txtPhone.setText(driver.getPhone());
                        txtLicenseNumber.setText(driver.getDriver_licenseNumber());
                        txtVehicleType.setText(driver.getVehicleType());
                    }
                } else {
                    Log.e("ShipperProfileFragment", "Response error: " + response.message());
                    // Handle the case where the response is not successful
                }
            }

            @Override
            public void onFailure(Call<DriverResponse> call, Throwable t) {
                Log.e("ShipperProfileFragment", "API call failed: ", t);
                // Handle failure
            }
        });

        return v;
    }
}
