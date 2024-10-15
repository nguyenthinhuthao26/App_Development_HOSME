package com.example.Giang.hosme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Giang.adapter.AdapterSpecialist;
import com.example.Giang.model.InforDoctor;
import com.example.Giang.model.Specialists;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivitySpecialistInforBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SpecialistInfor extends AppCompatActivity {

    ActivitySpecialistInforBinding binding;
    AdapterSpecialist adapter;
    ArrayList<Specialists> specialists;
    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "Department";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpecialistInforBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        copyData();
        addEvents();
        getData();
    }

    private void copyData() {
        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            if (copyDbFromAssets())
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean copyDbFromAssets() {
        String dbPath = getApplicationInfo().dataDir + "/" + DB_FOLDER + "/" + DB_NAME;
        try {
            InputStream inputStream = getAssets().open(DB_NAME);
            File file = new File(getApplicationInfo().dataDir + "/" + DB_FOLDER + "/");
            if (!file.exists()) {
                file.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent!=null){

            byte[] departmentImages = intent.getByteArrayExtra("DEPARTMENT_Image");
            String departmentName = intent.getStringExtra("DEPARTMENT_Name");
            String departmentDescription = intent.getStringExtra("DEPARTMENT_Description");

            binding.txtSpecialistNameInfor.setText(departmentName);
            binding.txtSpecialistDescriptionInfor.setText(departmentDescription);

            Bitmap bitmap = BitmapFactory.decodeByteArray(departmentImages, 0, departmentImages.length);
            binding.imvSpecialistInfor.setImageBitmap(bitmap);

        }
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}