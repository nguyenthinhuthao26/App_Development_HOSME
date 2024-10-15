package com.example.Chi.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import com.example.Giang.hosme.MainActivity;
import com.example.Trân.hosme.MainActivity3;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityPaymentResultBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentResult extends AppCompatActivity {
    ActivityPaymentResultBinding binding;
    public static final String DB_NAME = "database_db.sqlite";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "InvoiceData";
    public static final String INVOICEID = "InvoiceID";
    public static final String STATUS = "Status";
    public static final String PAYMENTDATETIME = "PaymentDateTime";
    public static final String PAYMENTMETHOD = "PaymentMethod";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadData();
        saveData();

    }

    private void saveData() {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String currentPaymentTime = dateFormat.format(new Date());

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String invoiceID = sharedPreferences.getString("invoiceID", "");
        String PaymentMethod = sharedPreferences.getString("PaymentMethod", "");

        String updateQuery = "UPDATE " + TBL_NAME + " SET " + STATUS + " = 'Đã thanh toán', " + PAYMENTDATETIME + " = '" + currentPaymentTime + "', " + PAYMENTMETHOD + " = '" + PaymentMethod + "' WHERE " + INVOICEID + " = '" + invoiceID + "'";
        db.execSQL(updateQuery);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PaymentResult.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String currentPaymentTime = dateFormat.format(new Date());

        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("PaymentDateTime", currentPaymentTime);
        editor.apply();

        binding.txtTimePayment.setText(currentPaymentTime);

        Intent intent = getIntent();
        binding.txtPrice.setText(intent.getStringExtra("price") + "đ");
        binding.txtNote.setText(intent.getStringExtra("note"));
    }

}