package com.example.shipperapplication.ShipperPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shipperapplication.Item;
import com.example.shipperapplication.OrderDetails;
import com.example.shipperapplication.R;
import com.example.shipperapplication.RetrofitInterface;
import com.example.shipperapplication.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShipperPendingOrderFragment extends Fragment {
    private RetrofitInterface retrofitInterface;

    private static final String BASE_URL = "http://10.0.2.2:3001/";
    private TextView edit_username, edit_phone, edit_location_cus, edit_location_res, edit_note, edit_total;
    private Button btn_Accept, btn_Reject;
    private String authToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shipper_pendingorders, container, false);

        edit_username = v.findViewById(R.id.userNameTxt);
        edit_phone = v.findViewById(R.id.phoneTxt);
        edit_location_cus = v.findViewById(R.id.locationTxtCus);
        edit_location_res = v.findViewById(R.id.locationTxtRes);
        edit_note = v.findViewById(R.id.NoteTxt);
        edit_total = v.findViewById(R.id.totalPriceTxt);

        btn_Accept = v.findViewById(R.id.acceptBtn);
        btn_Reject = v.findViewById(R.id.rejectBtn);

        authToken = SharedPreferencesManager.getInstance(requireContext()).getAuthToken();

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Khởi tạo RetrofitInterface
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Gọi API để lấy danh sách đơn hàng
        Call<List<OrderDetails>> call = retrofitInterface.getOrders("Bearer " + authToken);
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderDetails> orderList = response.body();
                    if (!orderList.isEmpty()) {
                        OrderDetails orderDetails = orderList.get(0); // Lấy đơn hàng đầu tiên từ danh sách
                        Item item = orderDetails.getItem();

                        // Hiển thị thông tin đơn hàng trên giao diện
                        edit_username.setText(orderDetails.getReceiver_name());
                        // edit_phone.setText(orderDetails.getPhone());
                        edit_location_cus.setText(orderDetails.getReceiver_name());
                        edit_location_res.setText(orderDetails.getFrom_address());
                        // edit_note.setText(orderDetails.getNote());
                        edit_total.setText(orderDetails.getPrice()); // Hiển thị giá tổng cộng
                        // edit_total.setText(item.getPrice()); // Lấy giá từ đối tượng Item
                    }
                } else {
                    // Xử lý trường hợp không thành công
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {
                // Xử lý khi gọi API thất bại
            }
        });

        return v;
    }
}
