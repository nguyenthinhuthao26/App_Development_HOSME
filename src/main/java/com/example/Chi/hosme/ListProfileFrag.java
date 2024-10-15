package com.example.Chi.hosme;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.Chi.adapters.ProfileAdapter;
import com.example.Chi.models.Profile;
import com.example.Giang.hosme.HomeFrag;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.FragmentListProfileBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListProfileFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListProfileFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentListProfileBinding binding;
    public static final String DB_NAME = "database_db.sqlite";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "PatientProfile";
    public static final String COL_USERID = "UserID";
    public static final String COL_PATIENTID = "PatientID";

    ArrayList<Profile> profiles = new ArrayList<>();
    ProfileAdapter adapter;

    public ListProfileFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ListProfileFrag newInstance(String param1, String param2) {
        ListProfileFrag fragment = new ListProfileFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentListProfileBinding.inflate(inflater, container, false);
        binding.linearAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate after choosing "addInfo"
                showaDialog();
            }
        });
        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                HomeFrag fragment = new HomeFrag();

                transaction.replace(R.id.containerLayout, fragment);
                transaction.commit();
            }
        });
        return binding.getRoot();
    }

    private void showaDialog() {
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.activity_dialog_add_profile);

        Button btnOldProfile, btnNewProfile;
        btnOldProfile = dialog.findViewById(R.id.btnOldProfile);
        btnOldProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate enter code
                Intent intent = new Intent(requireActivity(), EnterCode.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        btnNewProfile = dialog.findViewById(R.id.btnNewProfile);
        btnNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Nevigate to "ADD INFO by manual"
                Intent intent = new Intent(requireActivity(), AddProfile1.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //chinh kich thuoc dialog
        dialog.getWindow().setGravity(Gravity.BOTTOM); // dua dialog xuong duoi
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //loai bo bg trang
    }

    public void onResume() {
        super.onResume();
        loadDB();
    }

    private void loadDB() {
        db = requireActivity().openOrCreateDatabase(DB_NAME, requireActivity().MODE_PRIVATE, null);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", requireActivity().MODE_PRIVATE);
        String userID = sharedPreferences.getString("userId", "");
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + COL_USERID + " LIKE ?",
                new String[]{userID});

        profiles.clear();
        Profile profile;
        while (cursor.moveToNext()) {
            String patientID = cursor.getString(1);
            String patientName = cursor.getString(2);
            String patientIDCard = cursor.getString(3);
            String patientDOB = cursor.getString(4);
            String patientGender = cursor.getString(5);
            String patientAddress = cursor.getString(6);
            String phoneNumber = cursor.getString(7);
            String patientEmail = cursor.getString(9);

            profile = new Profile(patientID, patientName, phoneNumber, patientIDCard, patientDOB, patientGender, patientAddress, patientEmail);
            profiles.add(profile);
        }
        cursor.close();

        String selectedPatientID = sharedPreferences.getString("patientID", null);
        String selectedUserID = sharedPreferences.getString("userId", null);
        String selectedPatientName = sharedPreferences.getString("patientName", null);

        if (selectedPatientID != null && selectedPatientName != null) {
            Cursor cursor1 = db.rawQuery("SELECT * FROM " + TBL_NAME + " WHERE " + COL_PATIENTID + " LIKE ?",
                    new String[]{selectedPatientID});
            while (cursor1.moveToNext()) {
                String UserID = cursor1.getString(0);
                String patientID = cursor1.getString(1);
                String patientName = cursor1.getString(2);
                String patientIDCard = cursor1.getString(3);
                String patientDOB = cursor1.getString(4);
                String patientGender = cursor1.getString(5);
                String patientAddress = cursor1.getString(6);
                String phoneNumber = cursor1.getString(7);
                String patientEmail = cursor1.getString(9);


                profile = new Profile(patientID, patientName, phoneNumber, patientIDCard, patientDOB, patientGender, patientAddress, patientEmail);
                if (patientID.equals(selectedPatientID) && UserID.equals(selectedUserID) ) {
                    break;
                } else {
                    profiles.add(profile);
                }
            }
            cursor1.close();
        }

        adapter = new ProfileAdapter(requireActivity(), R.layout.profile_layout, profiles);
        binding.lvProfile.setAdapter(adapter);
        addEventClickProfile();
    }

    private void addEventClickProfile() {
        binding.lvProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profile selectedProfile = (Profile) adapter.getItem(position);

                Intent intent = new Intent(requireActivity(), ProfileDetail.class);

                intent.putExtra("patientID", selectedProfile.getPatientID());
                intent.putExtra("patientName", selectedProfile.getPatientName());
                intent.putExtra("phoneNumber", selectedProfile.getPhoneNumber());
                intent.putExtra("patientIDCard", selectedProfile.getPatientIDCard());
                intent.putExtra("patientDOB", selectedProfile.getPatientDOB());
                intent.putExtra("patientGender", selectedProfile.getPatientGender());
                intent.putExtra("patientAddress", selectedProfile.getPatientAddress());
                intent.putExtra("patientEmail", selectedProfile.getPatientEmail());
                startActivity(intent);
            }
        });
    }
}