package com.example.Giang.hosme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.Chi.hosme.ListProfileFrag;
import com.example.Chi.hosme.PaymentListBill;
import com.example.Chi.hosme.ProfileFrag;
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
import com.example.finalproject_hosme.databinding.FragmentHomeBinding;
import com.example.finalproject_hosme.databinding.FragmentUserBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentHomeBinding binding;
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

    public HomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initData();
        loadData();
        addEvents();

        initDataDoctor();
        loadDataDoctor();
        addEventsDoctor();

        customAndLoadData();

        addEventsOpenHome();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                    new AlertDialog.Builder(requireActivity())
                            .setIcon(R.drawable.icon_hosme)
                            .setTitle("Đăng xuất")
                            .setMessage("Bạn có muốn đăng xuất không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    logout();
                                }
                            })
                            .setNegativeButton("Không", null)
                            .show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    private void logout() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", requireActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(requireActivity(), nhapsdt.class);
        startActivity(intent);
        requireActivity().finishAffinity();
    }

    private void addEventsOpenHome() {
        binding.btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), DoctorList.class);
                startActivity(intent);
            }
        });

        binding.btnSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), SpecialistList.class);
                startActivity(intent);
            }
        });

        binding.btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), NewList.class);
                startActivity(intent);
            }
        });

    }

    private void customAndLoadData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvNew.setLayoutManager(layoutManager);
        binding.rvNew.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.rvNew.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.rvNew.addItemDecoration(dividerItemDecoration);
        binding.rvNew.setItemAnimator(new DefaultItemAnimator());

        news = new ArrayList<>();
        news.add(new New(R.drawable.new_1_1, "Đầm ấm Tết cổ truyền", "Lai Thị Bảo Trân", "11/04/2024"));
        news.add(new New(R.drawable.new_2_1, "Chung kết Miss Hypa 2024", "Nguyễn Ngọc Mai Chi", "09/01/2024"));
        news.add(new New(R.drawable.new_3_1, "Sinh hoạt cộng đồng", "Hoàng Thanh Giang", "07/01/2024"));
        news.add(new New(R.drawable.new_4_1, "Hội thi văn hóa 2023", "Nguyễn Huỳnh Mai Hân", "01/01/2024"));

        newAdapter = new NewAdapter(requireActivity().getApplicationContext(), news);
        binding.rvNew.setAdapter(newAdapter);
    }

    private void addEventsDoctor() {
        binding.gvDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doctor selectedSpecialist = (Doctor) doctorAdapter.getItem(position);
            }
        });
    }

    private void loadDataDoctor() {
        initDataDoctor();
        doctorAdapter = new DoctorAdapter(requireActivity(), R.layout.doctor, doctors);
        binding.gvDoctor.setAdapter(doctorAdapter);
    }

    private void initDataDoctor() {
        doctors = new ArrayList<>();
        doctors.add(new Doctor(R.drawable.saumot_trannhutthianhphuong, "Trần Nhựt Thị Ánh Phương"));
        doctors.add(new Doctor(R.drawable.saunam_phamkienhuu, "Phạm Kiên Hữu"));
        doctors.add(new Doctor(R.drawable.sausau_votranthanhnhan, "Võ Trần Thành Nhân"));
        doctors.add(new Doctor(R.drawable.sautam_nguyenthithuha, "Nguyễn Thị Thu Hà"));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDb();
    }

    private void loadDb() {
        db = requireActivity().openOrCreateDatabase(DB_NAME, requireActivity().MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TBL_NAME1, null);
        specialists = new ArrayList<>();

        int count = 0; // Biến đếm số dòng đã lấy

        while (cursor.moveToNext() && count < 10) {
            String departmentID = cursor.getString(0);
            String departmentName = cursor.getString(1);
            String description = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            if (departmentName.length() <= 18) {
                Specialists s = new Specialists(departmentID, departmentName, description, image);
                specialists.add(s);
                count++; // Tăng biến đếm sau khi lấy một dòng
            }
        }

        Comparator<Specialists> lengthComparator = new Comparator<Specialists>() {
            @Override
            public int compare(Specialists s1, Specialists s2) {
                return s1.getSpecialistName().length() - s2.getSpecialistName().length();
            }
        };

        Collections.sort(specialists, lengthComparator);

        cursor.close();
        AdapterSpecialist adapter = new AdapterSpecialist(requireActivity(), R.layout.specialist, specialists);
        binding.gvSpecialist.setAdapter(adapter);

        binding.gvSpecialist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Specialists selectedSpecialist = (Specialists) adapter.getItem(position);

                Intent intent = new Intent(requireActivity(), SpecialistInfor.class);
                intent.putExtra("DEPARTMENT_Image", selectedSpecialist.getSpecialistImage());
                intent.putExtra("DEPARTMENT_Name", selectedSpecialist.getSpecialistName());
                intent.putExtra("DEPARTMENT_Description", selectedSpecialist.getSpecialistDescription());
                startActivity(intent);
            }
        });
    }



    private void addEvents() {
        binding.lnNew1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainNew.class);
                startActivity(intent);
            }
        });

        binding.grMainFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Function selectedFunction = (Function) adapter.getItem(position);

                if (selectedFunction.getFunctionName().equals("Tư vấn")) {
                    Intent intent = new Intent(requireActivity(), com.example.Trân.hosme.MainActivity2.class);
                    startActivity(intent);
                } else if (selectedFunction.getFunctionName().equals("Phiếu khám")) {
                    Intent intent = new Intent(requireActivity(), com.example.Trân.hosme.MainActivity3.class);
                    startActivity(intent);
                } else if (selectedFunction.getFunctionName().equals("Thanh toán")) {
                    Intent intent = new Intent(requireActivity(), PaymentListBill.class);
                    startActivity(intent);
                } else if (selectedFunction.getFunctionName().equals("Đặt khám")) {
                    Intent intent = new Intent(requireActivity(), Chonhoso.class);
                    startActivity(intent);
                }else if (selectedFunction.getFunctionName().equals("Hồ sơ bệnh án")) {
                    Intent intent = new Intent(requireActivity(), ChooseProfile.class);
                    startActivity(intent);
                }else if (selectedFunction.getFunctionName().equals("Hướng dẫn đặt khám")) {
                    Intent intent = new Intent(requireActivity(), HuongdanDK.class);
                    startActivity(intent);
                }
            }
        });


        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", requireActivity().MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        binding.txtNameUser.setText(userName);

    }

    private void loadData() {
        initData();
        adapter = new FunctionAdapter(requireActivity(), R.layout.main_function, functions);
        binding.grMainFunction.setAdapter(adapter);
    }

    private void initData() {
        functions = new ArrayList<>();
        functions.add(new Function(R.drawable.icon_calendar, "Đặt khám"));
        functions.add(new Function(R.drawable.icon_document, "Hồ sơ bệnh án"));
        functions.add(new Function(R.drawable.icon_phone, "Thanh toán"));
        functions.add(new Function(R.drawable.icon_checktask, "Phiếu khám"));
        functions.add(new Function(R.drawable.icon_idcard, "Tư vấn"));
        functions.add(new Function(R.drawable.icon_findtask, "Hướng dẫn đặt khám"));
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart");
    }

    @Override
   public void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("MainActivity", "onPause");
    }
}