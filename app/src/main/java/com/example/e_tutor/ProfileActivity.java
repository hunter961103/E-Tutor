package com.example.e_tutor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView profile_picture;
    TextView display_phone;
    EditText input_name, input_email, input_oldpassword, input_newpassword;
    Button update_button;
    String phone, name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profile_picture = findViewById(R.id.imageView);
        display_phone = findViewById(R.id.displayPhone);
        input_name = findViewById(R.id.editTextName);
        input_email = findViewById(R.id.editTextEmail);
        input_oldpassword = findViewById(R.id.editTextOldPassword);
        input_newpassword = findViewById(R.id.editTextNewPassword);
        update_button = findViewById(R.id.button);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        phone = bundle.getString("phone");
        name = bundle.getString("name");
        email = bundle.getString("email");
        display_phone.setText(phone);
        String image_url = "https://grouping.000webhostapp.com/etutor/profile_images/" + phone + ".jpg";
        Picasso.with(this).load(image_url).resize(400, 400).into(profile_picture);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadUserProfile();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = input_name.getText().toString();
                String newemail = input_email.getText().toString();
                String oldpassword = input_oldpassword.getText().toString();
                String newpassword = input_newpassword.getText().toString();
                dialogUpdate(newname, newemail, oldpassword, newpassword);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("phone", phone);
                bundle.putString("name", name);
                bundle.putString("email", email);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void loadUserProfile() {
        class LoadUserProfile extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("phone", phone);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Loaduser.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray userArray = jsonObject.getJSONArray("user");
                    JSONObject o = userArray.getJSONObject(0);
                    name = o.getString("name");
                    email = o.getString("email");
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                input_name.setText(name);
                input_email.setText(email);
            }
        }
        LoadUserProfile loadUserProfile = new LoadUserProfile();
        loadUserProfile.execute();
    }

    private void dialogUpdate(final String newname, final String newemail, final String oldpassword, final String newpassword) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Profile");
        alertDialogBuilder.setMessage("Update this profile").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                updateProfile(newname, newemail, oldpassword, newpassword);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    void updateProfile(final String newname, final String newemail, final String oldpassword, final String newpassword) {
        class UpdateProfile extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("phone", phone);
                hashMap.put("name", newname);
                hashMap.put("email", newemail);
                hashMap.put("oldpassword", oldpassword);
                hashMap.put("newpassword", newpassword);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Updateprofile.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equalsIgnoreCase("success")) {
                    Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phone);
                    bundle.putString("name", name);
                    bundle.putString("email", email);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        UpdateProfile updateProfile = new UpdateProfile();
        updateProfile.execute();
    }
}