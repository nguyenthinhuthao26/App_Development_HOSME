package com.example.MedicalRecord.medicalrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.Giang.hosme.MainActivity;
import com.example.MedicalRecord.adapters.DetailResultAdapter;
import com.example.MedicalRecord.models.DetailResult;
import com.example.MedicalRecord.models.ResultMedicalRecord;
import com.example.Trân.hosme.MainActivity3;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityKetQuaKhamBinding;

import java.util.ArrayList;


public class KetQuaKham extends AppCompatActivity {
    ActivityKetQuaKhamBinding binding;
    ResultMedicalRecord rs;

    DetailResultAdapter adapter;
    ArrayList<DetailResult> results;
    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "MedicalRecordData";
    public static final String COL_MEDICALID = "MedicalID";
    public static final String COL_PATIENTID = "PatientID";
    public static final String COL_PATIENTNAME = "PatientName";
    public static final String COL_DEPARTMENTNAME = "DepartmentName";
    public static final String COL_DATEIN = "Date";
    public static final String COL_DOCTOR = "EmployeeName";
    public static final String COL_DATEOUT = "ResultDate";
    public static final String COL_PREDICT = "Predict";
    public static final String COL_PRESCRIPTION = "Prescription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKetQuaKhamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDb();
        addEvents();
    }

    private void addEvents() {
        binding.btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KetQuaKham.this, ChiTietKham.class);
                intent.putExtra("patientId", patientId);
                startActivity(intent);
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.imvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KetQuaKham.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    String patientId;
    private void getDb() {
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        patientId = intent.getStringExtra("patientId");

        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        String[] projection = null;
        String selection = COL_DATEIN + " = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.query(TBL_NAME, projection, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            rs = new ResultMedicalRecord(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8));

            String patientId = cursor.getString(1);
            String patientName = cursor.getString(2);
            String specialist = cursor.getString(3);
            String datein = cursor.getString(4);
            String dateout = cursor.getString(6);
            String predict = cursor.getString(7);
            String pres = cursor.getString(8);
            String doctor = cursor.getString(5);

            binding.txtPatientCodeDetail.setText(patientId);
            binding.txtPatientNameDetail.setText(patientName);
            binding.txtMainExamDetail.setText(specialist);
            binding.txtDateTimeInDetail.setText(datein);
            binding.txtDateTimeOutDetail.setText(dateout);
            binding.txtPredict.setText(predict);
            binding.txtPrescription.setText(pres);
            binding.txtDoctorDetail.setText(doctor);

        }

        cursor.close();
        db.close();

    }
}