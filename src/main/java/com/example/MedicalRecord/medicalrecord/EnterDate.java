package com.example.MedicalRecord.medicalrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Giang.hosme.MainActivity;
import com.example.MedicalRecord.models.MePro;
import com.example.Trân.hosme.MainActivity3;
import com.example.finalproject_hosme.databinding.EnterDateBinding;

public class EnterDate extends AppCompatActivity {

    EnterDateBinding binding;
    MePro p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EnterDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getData();
        addEvents();
    }

    private void getData() {
        Intent intent = getIntent();
        p = (MePro) intent.getSerializableExtra("data");
        assert p != null;
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText dateEditText = binding.edtDate;
                String date = dateEditText.getText().toString();

                if (TextUtils.isEmpty(date)) {
                    Toast.makeText(EnterDate.this, "Vui lòng nhập ngày để xem hồ sơ", Toast.LENGTH_SHORT).show();
                } else {
                    checkDate(date);
                }
            }
        });

        binding.imvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterDate.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkDate(String date) {
        ChooseProfile.db = openOrCreateDatabase(ChooseProfile.DB_NAME, MODE_PRIVATE, null);

        String query = "SELECT PatientProfile.*, MedicalRecordData.Date FROM PatientProfile INNER JOIN MedicalRecordData ON MedicalRecordData.PatienID = PatientProfile.PatientID WHERE MedicalRecordData.Date = ?";

        // Mảng đối số cho truy vấn
        String[] selectionArgs = { date };

        // Thực hiện truy vấn
        Cursor cursor = ChooseProfile.db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            int patientIdIndex = cursor.getColumnIndex(ChooseProfile.COL_PATIENTID);
            String patientId = cursor.getString(patientIdIndex);
            Intent intent = new Intent(EnterDate.this, KetQuaKham.class);
            intent.putExtra("date", date);
            intent.putExtra("patientId", patientId);
            startActivity(intent);
        } else {
            Toast.makeText(EnterDate.this, "Kết quả không đúng. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}
