package com.example.Hân.hosme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.Chi.adapters.ProfileAdapter;
import com.example.Chi.hosme.ProfileFrag;
import com.example.Chi.models.Profile;
import com.example.Giang.hosme.HomeFrag;
import com.example.Hân.adapter.NoAdapter;
import com.example.Hân.model.Notifications;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.FragmentProfileBinding;
import com.example.finalproject_hosme.databinding.FragmentThongBaoBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongBaoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongBaoFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentThongBaoBinding binding;

    public static final String DB_NAME = "notificationdata.sqlite";
    public static final String DB_FOLDER = "databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "NoData";

    ArrayList<Notifications> notifications = new ArrayList<>();
    NoAdapter noadapter;

    public ThongBaoFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongBaoFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongBaoFrag newInstance(String param1, String param2) {
        ThongBaoFrag fragment = new ThongBaoFrag();
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
        binding = FragmentThongBaoBinding.inflate(inflater, container, false);
        copyData();
        loadDB();
        addEvent();
        return binding.getRoot();
    }

    private void loadDB() {
        db = requireActivity().openOrCreateDatabase(DB_NAME, requireActivity().MODE_PRIVATE, null);
        String query = "SELECT * FROM " + TBL_NAME;
        Cursor cursor = db.rawQuery(query, null);

//        List<Notifications> notifications = new ArrayList<>();
        Notifications n;
        while (cursor.moveToNext()) {
            n = new Notifications(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            notifications.add(n);
        }

        cursor.close();
        noadapter = new NoAdapter(requireActivity(), R.layout.item_tb, notifications);
        binding.lvNo.setAdapter(noadapter);
    }


    private void addEvent() {
        binding.lvNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoAdapter adapter = (NoAdapter) parent.getAdapter();
                Notifications selectedNo = (Notifications) adapter.getItem(position);
                if (selectedNo != null) {
                    Intent intent = new Intent(requireActivity(), DetailNotification.class);
                    intent.putExtra("data", selectedNo);
                    startActivity(intent);
                }
            }
        });

        binding.imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Tạo instance của Fragment Home
                HomeFrag fragment = new HomeFrag();

                transaction.replace(R.id.containerLayout, fragment);
                transaction.commit();
            }
        });
    }

    private void copyData() {
        File dbFile = requireActivity().getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            if (copyDbFromAssets())
                Toast.makeText(requireActivity(), "Success!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(requireActivity(), "Fail!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean copyDbFromAssets() {
        String dbPath = requireActivity().getApplicationInfo().dataDir + "/" + DB_FOLDER + "/" + DB_NAME;
        try {
            InputStream inputStream = requireActivity().getAssets().open(DB_NAME);
            File f = new File(requireActivity().getApplicationInfo().dataDir + "/" + DB_FOLDER + "/");
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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}