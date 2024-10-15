package com.example.Giang.hosme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.Chi.hosme.ListProfileFrag;
import com.example.Chi.hosme.PaymentListBill;
import com.example.Chi.hosme.ProfileFrag;
import com.example.Chi.models.Profile;
import com.example.Giang.adapter.AdapterSpecialist;
import com.example.Giang.adapter.DoctorAdapter;
import com.example.Giang.adapter.FunctionAdapter;
import com.example.Giang.adapter.NewAdapter;
import com.example.Giang.model.Doctor;
import com.example.Giang.model.Function;
import com.example.Giang.model.New;
import com.example.Giang.model.Specialists;
import com.example.Hân.hosme.ThongBaoFrag;
import com.example.Hân.hosme.nhapsdt;
import com.example.MedicalRecord.medicalrecord.ChooseProfile;
import com.example.Thảo.hosme.Chonhoso;
import com.example.Trân.hosme.UserFrag;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMain2Binding binding;
    FunctionAdapter adapter;
    ArrayList<Function> functions;
    AdapterSpecialist adapterSpecialist;
    ArrayList<Specialists> specialists;
    DoctorAdapter doctorAdapter;
    ArrayList<Doctor> doctors;
    NewAdapter newAdapter;
    ArrayList<New> news;

    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "PatientProfile";
    public static final String USER_ID = "UserID";
    public static final String PATIENT_ID = "PatientID";
    public static final String TBL_NAME1 = "Department";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        HomeFrag homeFragment = new HomeFrag();
        transaction.replace(R.id.containerLayout, homeFragment);
        transaction.commit();
        loadData();
    }
    private void loadData() {
        binding.lnHome.setOnClickListener(clickListener);
        binding.lnPersonalInfo.setOnClickListener(clickListener);
        binding.lnRing.setOnClickListener(clickListener);
        binding.lnUser.setOnClickListener(clickListener);

        // Kiểm tra Intent xem có yêu cầu mở Fragment ListProfileFrag hay không
        String openFragment = getIntent().getStringExtra("openFragment");
        if (openFragment != null && openFragment.equals("listProfile")) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerLayout, new ListProfileFrag());
            fragmentTransaction.commit();
        }
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            Fragment fragment = null;
            if (view.equals(binding.lnPersonalInfo)) {
                SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                String selectedPatientID = sharedPreferences.getString("patientID", null);
                String selectedPatientName = sharedPreferences.getString("patientName", null);
                String selectedPhoneNumber = sharedPreferences.getString("phone", null);

                if (selectedPatientID != null && selectedPatientName != null && selectedPhoneNumber != null) {
                    fragment = new ListProfileFrag();
                } else {
                    db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

                    String userID = sharedPreferences.getString("userId", "");

                    Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + USER_ID + " = ?", new String[]{userID});

                    if (cursor.moveToFirst()) {
                        int userIdIndex = cursor.getColumnIndex(USER_ID);
                        String userIdFromDatabase = cursor.getString(userIdIndex);

                        if (userID.equals(userIdFromDatabase)) {
                            fragment = new ListProfileFrag();
                        } else {
                            fragment = new ProfileFrag();
                        }
                    } else {
                        fragment = new ProfileFrag();
                    }

                    cursor.close();
                    db.close();
                }
            } else if (view.equals(binding.lnRing)) {
                fragment = new ThongBaoFrag();
            } else if (view.equals(binding.lnUser)) {
                fragment = new UserFrag();
            } else if (view.equals(binding.lnHome)) {
                fragment = new HomeFrag();
            }

            assert fragment != null;

            transaction.replace(R.id.containerLayout, fragment);

            transaction.commit();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity", "onRestart");
    }

}
