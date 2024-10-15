package com.example.Hân.hosme;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject_hosme.databinding.ActivityNhapsdtBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class nhapsdt extends AppCompatActivity {

    ActivityNhapsdtBinding binding;

    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "UserData";
    public static final String USER_ID = "UserID";
    public static final String USER_PHONE = "PhoneNumber";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityNhapsdtBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        addEvent();
        copyDB();
    }

    private void addEvent() {
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText phoneEditText = binding.phoneEditText;
                String phoneNumber = phoneEditText.getText().toString();

                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(nhapsdt.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    checkPhoneNumberAndNavigate(phoneNumber);
                }
            }

        });
        binding.txtDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.phoneEditText.getText().toString();

                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(nhapsdt.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(nhapsdt.this, taomatkhau.class);
                    intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);
                }
            }
        });
    }
    private void checkPhoneNumberAndNavigate(String phoneNumber) {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        String query = "SELECT * FROM " + TBL_NAME  + " WHERE " + USER_PHONE + " = ?";

        // Mảng đối số cho truy vấn
        String[] selectionArgs = { phoneNumber };

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            int userIdIndex = cursor.getColumnIndex(USER_ID);
            String userId = cursor.getString(userIdIndex);
            Intent intent = new Intent(nhapsdt.this, nhapmatkhau.class);
            intent.putExtra("phoneNumber", phoneNumber);
            intent.putExtra("userId", userId);

            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("phoneNumber", phoneNumber);
            editor.apply();

            startActivity(intent);
        } else {
            Toast.makeText(nhapsdt.this, "Tài khoản không tồn tại. Vui lòng đăng kí tài khoản.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(nhapsdt.this, taomatkhau.class);
            intent.putExtra("phoneNumber", binding.phoneEditText.getText().toString());
            startActivity(intent);
        }
        cursor.close();
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

