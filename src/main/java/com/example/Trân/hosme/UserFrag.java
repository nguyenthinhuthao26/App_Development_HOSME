package com.example.Trân.hosme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Giang.hosme.HomeFrag;
import com.example.Hân.hosme.nhapsdt;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.FragmentListProfileBinding;
import com.example.finalproject_hosme.databinding.FragmentUserBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentUserBinding binding;

    public UserFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFrag newInstance(String param1, String param2) {
        UserFrag fragment = new UserFrag();
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
        binding = FragmentUserBinding.inflate(inflater, container, false);
        addEvents();
        return binding.getRoot();
    }
    private void addEvents() {

        binding.lnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity5.class);
                startActivity(intent);
            }
        });

        binding.lnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });

        binding.lnTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity6.class);
                startActivity(intent);
            }
        });

        binding.lnPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity7.class);
                startActivity(intent);
            }
        });

        binding.lnUsing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MainActivity8.class);
                startActivity(intent);
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
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", requireActivity().MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        String capitalizedUserName = userName.toUpperCase();
        binding.txtUserName.setText(capitalizedUserName);

        binding.btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogout();
            }

            private void performLogout() {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", requireActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Chuyển người dùng đến màn hình đăng nhập
                Intent intent = new Intent(requireActivity(), nhapsdt.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finishAffinity();
            }
        });


    }

}