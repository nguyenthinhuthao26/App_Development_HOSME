package com.example.Chi.hosme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Thảo.hosme.Chonhoso;
import com.example.finalproject_hosme.databinding.ActivityForgetCodeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ForgetCodeDK extends AppCompatActivity {

    ActivityForgetCodeBinding binding;
    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "PatientProfile";
    public static final String COL_PATIENTNAME = "PatientName";
    public static final String COL_PHONENUMBER = "PhoneNumber";
    public static final String COL_DOB = "DateOfBirth";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgetCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEventBack();
        FindByInfo();
    }

    private void FindByInfo() {
        binding.btnFindByInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Find Info by Edt
                String Name = binding.edtPatientName.getText().toString();
                String DOB = binding.edtPatientDOB.getText().toString();
                String Phone = binding.edtPhoneNumber.getText().toString();
                // Kiểm tra xem các trường bắt buộc đã được nhập đúng chưa
                if (Name.isEmpty()) {
                    Toast.makeText(ForgetCodeDK.this, "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(DOB)) {
                    Toast.makeText(ForgetCodeDK.this, "Vui lòng nhập ngày sinh", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra định dạng ngày sinh
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                sdf.setLenient(false);

                try {
                    Date dob = sdf.parse(DOB);
                } catch (ParseException e) {
                    Toast.makeText(ForgetCodeDK.this, "Định dạng ngày sinh không đúng (dd/mm/yyyy)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Phone)) {
                    Toast.makeText(ForgetCodeDK.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!TextUtils.isDigitsOnly(Phone)) {
                    Toast.makeText(ForgetCodeDK.this, "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + COL_PHONENUMBER + " LIKE ?", new String[]{Phone});
                if (cursor.moveToFirst()) {
                    String patientID = cursor.getString(1);
                    String patientName = cursor.getString(2);

                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("patientID", patientID);
                    editor.putString("patientName", patientName);
                    editor.apply();

                    Intent intent = new Intent(ForgetCodeDK.this, Chonhoso.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgetCodeDK.this, "Không tìm thấy hồ sơ", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });
    }

    private void addEventBack() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back home screen
                finish();
            }
        });
    }
}