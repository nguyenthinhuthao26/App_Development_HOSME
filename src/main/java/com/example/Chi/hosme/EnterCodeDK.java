package com.example.Chi.hosme;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Thảo.hosme.Chonhoso;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityEnterCodeBinding;

public class EnterCodeDK extends AppCompatActivity {

    ActivityEnterCodeBinding binding;
    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "PatientProfile";
    public static final String COL_PATIENTID = "PatientID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvent();
        FindProfile();
        forgetCode();
    }

    private void forgetCode() {
        binding.txtForgetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterCodeDK.this, ForgetCodeDK.class);
                startActivity(intent);
            }
        });
    }

    private void FindProfile() {
        binding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiem tra key MSBN để get Profile theo MSBN
                String id = binding.edtPatientID.getText().toString();
                db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + COL_PATIENTID + " LIKE ?", new String[]{"%" + id + "%"});

                if (cursor.moveToFirst()) {
                    String patientID = cursor.getString(1);
                    String patientName = cursor.getString(2);
                    String patientIDCard = cursor.getString(3);
                    String patientDOB = cursor.getString(4);
                    String patientGender = cursor.getString(5);
                    String patientAddress = cursor.getString(6);
                    String phoneNumber = cursor.getString(7);
                    String patientEmail = cursor.getString(9);

                    SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("patientID", patientID);
                    editor.putString("patientName", patientName);
                    editor.putString("phone", phoneNumber);
                    editor.putString("patientDOB", phoneNumber);
                    editor.putString("patientGender", phoneNumber);
                    editor.apply();

                    // Tạo Intent để chuyển dữ liệu sang MainProfile
                    Intent intent = new Intent(EnterCodeDK.this, Chonhoso.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EnterCodeDK.this, "Không tìm thấy hồ sơ", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });
    }

    private void addEvent() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back home screen
                finish();
            }
        });

        binding.linearAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate after choosing "addInfo"
                showaDialog();
            }
        });
    }

    private void showaDialog() {
        Dialog dialog = new Dialog(EnterCodeDK.this);
        dialog.setContentView(R.layout.activity_dialog_add_profile);

        Button btnOldProfile, btnNewProfile;
        btnOldProfile = dialog.findViewById(R.id.btnOldProfile);
        btnOldProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate enter code
                Intent intent = new Intent(EnterCodeDK.this, EnterCode.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        btnNewProfile = dialog.findViewById(R.id.btnNewProfile);
        btnNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate to "ADD INFO by manual"
                Intent intent = new Intent(EnterCodeDK.this, AddProfile1.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //chinh kich thuoc dialog
        dialog.getWindow().setGravity(Gravity.BOTTOM); // dua dialog xuong duoi
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //loai bo bg trang
    }
}
