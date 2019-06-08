package com.example.e_tutor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    TextView register, forgot_password;
    EditText input_email, input_password;
    Button login_button;
    SharedPreferences sharedPreferences;
    CheckBox remember_me;
    Dialog forgotPasswordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_email = findViewById(R.id.editTextEmail);
        input_password = findViewById(R.id.editTextPassword);
        login_button = findViewById(R.id.button);
        register = findViewById(R.id.textViewRegister);
        forgot_password = findViewById(R.id.textViewForgotPassword);
        remember_me = findViewById(R.id.checkBox);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();
                loginUser(email, password);
            }
        });
        remember_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remember_me.isChecked()) {
                    String email = input_email.getText().toString();
                    String password = input_password.getText().toString();
                    savePref(email, password);
                }
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog();
            }
        });
        loadPref();
    }

    private void savePref(String email, String password) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
        Toast.makeText(this, "Preferences has been saved", Toast.LENGTH_SHORT).show();
    }

    private void loadPref() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pref_email = sharedPreferences.getString("email", "");
        String pref_password = sharedPreferences.getString("password", "");
        if(pref_email.length() > 0) {
            remember_me.setChecked(true);
            input_email.setText(pref_email);
            input_password.setText(pref_password);
        }
    }

    private void loginUser(final String email, final String password) {
        class LoginUser extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Login user", "...", false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("email", email);
                hashMap.put("password", password);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Login.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("failed")) {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
                else {
                    String[] val = s.split(",");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("name", val[0]);
                    bundle.putString("phone", val[1]);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }
        LoginUser loginUser = new LoginUser();
        loginUser.execute();
    }

    private void forgotPasswordDialog(){
        forgotPasswordDialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        forgotPasswordDialog.setContentView(R.layout.forgot_password);
        forgotPasswordDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final EditText edit_email = forgotPasswordDialog.findViewById(R.id.editTextEmail);
        Button send_email = forgotPasswordDialog.findViewById(R.id.button);
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forgot_email = edit_email.getText().toString();
                sendPassword(forgot_email);
            }
        });
        forgotPasswordDialog.show();
    }

    private void sendPassword(final String forgot_email) {
        class SendPassword extends AsyncTask<Void, String, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap();
                hashMap.put("email", forgot_email);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Verifyemail.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equalsIgnoreCase("success")) {
                    Toast.makeText(LoginActivity.this, "Success. Check your email", Toast.LENGTH_LONG).show();
                    forgotPasswordDialog.dismiss();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        SendPassword sendPassword = new SendPassword();
        sendPassword.execute();
    }
}