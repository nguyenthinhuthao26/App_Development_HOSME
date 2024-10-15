package com.example.Chi.hosme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.Chi.adapters.Payment;
import com.example.Giang.adapter.NewAdapter;
import com.example.Giang.model.New;
import com.example.Thảo.hosme.Xacnhanthongtin;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityConfirmMoMoBinding;

import java.util.ArrayList;
import java.util.List;

public class ConfirmMoMo extends AppCompatActivity {
    ActivityConfirmMoMoBinding binding;
    Payment payment;
    ArrayList<com.example.Chi.models.Payment> payments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmMoMoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();
        getData();
        addEvent();
    }

    @SuppressLint("SetTextI18n")
    private void getData() {
        Intent intent = getIntent();
        binding.txtPrice.setText(intent.getStringExtra("price"));
        binding.txtNote.setText(intent.getStringExtra("note"));
        binding.txtPriceTotal.setText(intent.getStringExtra("price") + "đ");
    }

    private void addEvent() {
        binding.lnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(ConfirmMoMo.this, PaymentResult.class);
            intent.putExtra("price", binding.txtPrice.getText().toString());
            intent.putExtra("note", binding.txtNote.getText().toString());
            startActivity(intent);
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ConfirmMoMo.this, LinearLayoutManager.HORIZONTAL, false);
        binding.rvpt.setLayoutManager(layoutManager);
        binding.rvpt.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvpt.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.rvpt.addItemDecoration(dividerItemDecoration);
        binding.rvpt.setItemAnimator(new DefaultItemAnimator());

        List<com.example.Chi.models.Payment> payments = new ArrayList<>();
        payments.add(new com.example.Chi.models.Payment(R.drawable.icon_momo, "Ví MoMo"));
        payments.add(new com.example.Chi.models.Payment(R.drawable.trasau, "Ví Trả Sau"));
        payments.add(new com.example.Chi.models.Payment(R.drawable.tui, "Túi Thần Tài"));
        payments.add(new com.example.Chi.models.Payment(R.drawable.logo_vietcombank, "VCB"));
        payments.add(new com.example.Chi.models.Payment(R.drawable.adds, "Liên kết ngân hàng"));
        payments.add(new com.example.Chi.models.Payment(R.drawable.card, "Liên kết thẻ quốc tế"));

        payment = new Payment(ConfirmMoMo.this, R.layout.item_tt, payments);
        binding.rvpt.setAdapter(payment);
    }

}