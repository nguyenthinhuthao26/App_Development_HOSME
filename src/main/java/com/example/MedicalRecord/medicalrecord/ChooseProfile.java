package com.example.MedicalRecord.medicalrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.Giang.hosme.MainActivity;
import com.example.MedicalRecord.adapters.ProfileAdapter;
import com.example.MedicalRecord.models.MePro;
import com.example.Trân.hosme.MainActivity3;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ChooseProfileListBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class ChooseProfile extends AppCompatActivity {

    ChooseProfileListBinding binding;
    MePro selectedprofile = null;
    ProfileAdapter adapter;

    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "PatientProfile";
    public static final String COL_PATIENTID = "PatientID";
    public static final String COL_PATIENTNAME = "PatientName";
    public static final String COL_PHONENUMBER = "PhoneNumber";
    public static final String COL_DATE = "Date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChooseProfileListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        copyDB();
        initAdapter();
        addEvents();
    }

    private void initAdapter() {
        List<MePro> profile = loadData();

        if (profile.isEmpty()) {
            Intent intent = new Intent(ChooseProfile.this, NotProfile.class);
            startActivity(intent);
            finish();
        } else {
            adapter = new ProfileAdapter(this, R.layout.choose_profile, profile);
            binding.lvProfile.setAdapter(adapter);
        }
    }

    private List<MePro> loadData() {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        String userID = sharedPreferences.getString("userId", "");
        //Reset data
        List<MePro> profile = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT PatientProfile.* FROM PatientProfile INNER JOIN MedicalRecordData ON MedicalRecordData.PatienID = PatientProfile.PatientID WHERE  PatientProfile.UserID = ? ", new String[]{userID});

        while (cursor.moveToNext()) {
            String patientID = cursor.getString(1);
            String patientName = cursor.getString(2);
            String patientDOB = cursor.getString(4);
            String patientGender = cursor.getString(5);
            String patientAddress = cursor.getString(6);
            String phoneNumber = cursor.getString(7);

            MePro p = new MePro(Integer.parseInt(userID), patientID, patientName, patientDOB, patientGender, patientAddress, phoneNumber);
            profile.add(p);
        }
        cursor.close();
        return profile;
    }

    private void addEvents() {
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.lvProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedprofile = (MePro) adapter.getItem(position);
                Intent intent = new Intent(ChooseProfile.this, EnterDate.class);
//            Attach data
                if (selectedprofile != null){
                    intent.putExtra("data", selectedprofile);
                    startActivity(intent);
                }
            }
        });

    }

    private void copyDB() {
        File dbFile = getDatabasePath(DB_NAME);
        if(!dbFile.exists()){
            //Copy
            if (copyDbFromAssets())
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Fail!", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean copyDbFromAssets(){
        String dbPath = getApplicationInfo().dataDir + "/" + DB_FOLDER + "/" + DB_NAME;
        //data/data/packageName/product_db.db
        try {
            InputStream inputStream = getAssets().open(DB_NAME);
            File f = new File(getApplicationInfo().dataDir + "/" + DB_FOLDER + "/");
            if (!f.exists()) {
                f.mkdir();
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
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
