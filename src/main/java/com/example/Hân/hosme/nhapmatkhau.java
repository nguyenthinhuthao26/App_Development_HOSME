package com.example.Hân.hosme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Giang.hosme.MainActivity;
import com.example.finalproject_hosme.R;
import com.example.finalproject_hosme.databinding.ActivityNhapmatkhauBinding;

import java.util.Locale;
import java.util.Random;

public class nhapmatkhau extends AppCompatActivity {

    ActivityNhapmatkhauBinding binding;
    TextView errorTextView2;
    EditText passwordEditText;
    Button continueButton;
    boolean isPasswordVisible = false;

    ImageView showPasswordImageView;

    public static final String CHANNEL_ID = "my_channel";

    private TextView forgotPasswordTextView;
    public static final String DB_NAME = "database_db.sqlite";
    public static final String DB_FOLDER = "/databases";
    public static SQLiteDatabase db = null;
    public static final String TBL_NAME = "UserData";
    public static final String COL_PHONENUMBER = "PhoneNumber";
    public static final String COL_NAME = "UserName";
    public static final String COL_PASSWORD = "Password";
    private CountDownTimer countDownTimer;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityNhapmatkhauBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        addEvents();
        getdata();
    }

    private void getdata() {
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phoneNumber");
        userId = intent.getStringExtra("userId");

        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        String[] projection = {COL_NAME, COL_PASSWORD};
        String selection = COL_PHONENUMBER + " = ?";
        String[] selectionArgs = {phoneNumber};
        Cursor cursor = db.query(TBL_NAME, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COL_NAME);
            int passwordIndex = cursor.getColumnIndex(COL_PASSWORD);
            String name = cursor.getString(nameIndex);
            String password = cursor.getString(passwordIndex);

            binding.txtUserName.setText(name);
            binding.txtPhoneNumber.setText(phoneNumber);
            binding.continueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enteredPassword = passwordEditText.getText().toString();

                    if (enteredPassword.length() < 8) {
                        errorTextView2.setVisibility(View.VISIBLE);
                        errorTextView2.setText("Nhập ít nhất 8 kí tự");
                        errorTextView2.setGravity(Gravity.CENTER);
                    } else {
                        if (enteredPassword.equalsIgnoreCase(password)) {
                            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName", name);
                            editor.putString("userId", userId);
                            editor.apply();

                            Intent homeIntent = new Intent(nhapmatkhau.this, MainActivity.class);
                            startActivity(homeIntent);
                        } else {
                            Toast.makeText(nhapmatkhau.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        cursor.close();
        db.close();

    }

    private void addEvents() {
        errorTextView2 = findViewById(R.id.errorTextView2);
        passwordEditText = findViewById(R.id.passwordEditText);
        continueButton = findViewById(R.id.continueButton);
        showPasswordImageView = findViewById(R.id.showPasswordImageView);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPassword = passwordEditText.getText().toString();

                if (enteredPassword.length() < 8) {
                    errorTextView2.setVisibility(View.VISIBLE);
                    errorTextView2.setText("Nhập ít nhất 8 kí tự");
                    errorTextView2.setGravity(Gravity.CENTER);
                } else if (!enteredPassword.equals("12345678")) {
                    errorTextView2.setVisibility(View.VISIBLE);
                    errorTextView2.setText("Mật khẩu không đúng. Vui lòng nhập lại!");
                } else {
                    Intent intent = new Intent(nhapmatkhau.this, datmkmoi.class);
                    startActivity(intent);
                }
            }
        });

        showPasswordImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        binding.forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void showNotification(int randomNumber) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(nhapmatkhau.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_hosme)
                .setContentTitle("Mã xác nhận")
                .setContentText(randomNumber + "là mã xác thực tài khoản trên ứng dụng HOSME của bạn.")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(nhapmatkhau.this);
        notificationManager.notify(1, builder.build());
    }

    public int otpNumber;
    private void showForgotPasswordDialog() {
        Dialog dialog = new Dialog(nhapmatkhau.this);
        dialog.setContentView(R.layout.dialog_forget_password);

        TextView txtTime = dialog.findViewById(R.id.txtTime);
        EditText edtOtp = dialog.findViewById(R.id.edtOtp);
        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                String timeString = String.format(Locale.getDefault(), "0:%02d", seconds);
                txtTime.setText(timeString);

                if (seconds == 55) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(900000) + 100000;
                    otpNumber = randomNumber;
                    showNotification(randomNumber);

                }
            }

            public void onFinish() {

            }

        }.start();


        Button btnResend = dialog.findViewById(R.id.btn_resend_otp);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });

        Button btnSend = dialog.findViewById(R.id.btn_send_otp);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOtp = edtOtp.getText().toString();

                if (Integer.parseInt(enteredOtp) == otpNumber) {
                    dialog.dismiss();
                    Intent intent = new Intent(nhapmatkhau.this, datmkmoi.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(nhapmatkhau.this, "Mã OTP không hợp lệ!", Toast.LENGTH_SHORT).show();
                }

                Log.d("OTP", "Entered OTP: " + enteredOtp);
                Log.d("OTP", "OTP Number: " + otpNumber);
            }
        });

        ImageButton btnClose = dialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            showPasswordImageView.setImageResource(R.drawable.eye);
        } else {
            passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPasswordImageView.setImageResource(R.drawable.eye);
        }
        isPasswordVisible = !isPasswordVisible;
        passwordEditText.setSelection(passwordEditText.getText().length());
    }
}