package com.example.Giang.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.Giang.adapter.AdapterSpecialist;
import com.example.Giang.adapter.DoctorInforAdapter;
import com.example.Giang.model.InforDoctor;
import com.example.Giang.model.Specialists;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivitySpecialListBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SpecialistList extends AppCompatActivity {
    
    ActivitySpecialListBinding binding;

    AdapterSpecialist adapter;
    ArrayList<Specialists> specialists;

    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "Department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpecialListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME, null);
        specialists = new ArrayList<>();

        while (cursor.moveToNext()) {
            String departmentID = cursor.getString(0);
            String departmentName = cursor.getString(1);
            String description = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            Specialists s = new Specialists(departmentID, departmentName, description, image);
            specialists.add(s);
        }

        cursor.close();
        adapter = new AdapterSpecialist(SpecialistList.this, R.layout.item_specialist, specialists);
        binding.lvSpecialist.setAdapter(adapter);
    }



    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.lvSpecialist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Specialists selectedSpecialist = (Specialists) adapter.getItem(position);

                Intent intent = new Intent(SpecialistList.this, SpecialistInfor.class);
                intent.putExtra("DEPARTMENT_Image", selectedSpecialist.getSpecialistImage());
                intent.putExtra("DEPARTMENT_Name", selectedSpecialist.getSpecialistName());
                intent.putExtra("DEPARTMENT_Description", selectedSpecialist.getSpecialistDescription());
                startActivity(intent);
            }
        });
    }
}