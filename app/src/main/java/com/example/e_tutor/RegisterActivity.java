package com.example.e_tutor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    ImageView profile_picture;
    EditText input_name, input_password, input_email, input_phone;
    Button register_button;
    TextView login;
    User user;
    boolean has_image = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        profile_picture = findViewById(R.id.imageView);
        input_name = findViewById(R.id.editTextName);
        input_password = findViewById(R.id.editTextPassword);
        input_email = findViewById(R.id.editTextEmail);
        input_phone = findViewById(R.id.editTextPhone);
        register_button = findViewById(R.id.button);
        login = findViewById(R.id.textViewAlreadyRegister);
        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTakePicture();
            }
        });
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserInput();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void dialogTakePicture() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(this.getResources().getString(R.string.dialogtakepicture1));
        alertDialogBuilder.setMessage(this.getResources().getString(R.string.dialogtakepicture2)).setCancelable(false).setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }
        }).setNegativeButton(this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap)extras.get("data");
            imageBitmap = ThumbnailUtils.extractThumbnail(imageBitmap, 400, 500);
            profile_picture.setImageBitmap(imageBitmap);
            profile_picture.buildDrawingCache();
            ContextWrapper contextWrapper = new ContextWrapper(this);
            File pictureFileDir = contextWrapper.getDir("basic", Context.MODE_PRIVATE);
            if(!pictureFileDir.exists()) {
                pictureFileDir.mkdir();
            }
            if(!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                return;
            }
            FileOutputStream outStream = null;
            String photoFile = "profile.jpg";
            File outFile = new File(pictureFileDir, photoFile);
            try {
                outStream = new FileOutputStream(outFile);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                has_image = true;
            }
            catch(FileNotFoundException e) {
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerUserInput() {
        String name, password, email, phone;
        name = input_name.getText().toString();
        password = input_password.getText().toString();
        email = input_email.getText().toString();
        phone = input_phone.getText().toString();
        user = new User(name, password, email, phone);
        registerUserDialog();
    }

    private void registerUserDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(this.getResources().getString(R.string.registerfor) + " " + user.name + "?");
        alertDialogBuilder.setMessage(this.getResources().getString(R.string.registernewaccount)).setCancelable(false).setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(has_image) {
                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.registrationinprogress), Toast.LENGTH_SHORT).show();
                    new Encode_image().execute(getDir(), user.phone + ".jpg");
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please upload picture", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton(this.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public String getDir() {
        ContextWrapper contextWrapper = new ContextWrapper(this);
        File pictureFileDir = contextWrapper.getDir("basic", Context.MODE_PRIVATE);
        if(!pictureFileDir.exists()) {
            pictureFileDir.mkdir();
        }
        return pictureFileDir.getAbsolutePath() + "/profile.jpg";
    }

    public class Encode_image extends AsyncTask<String, String, Void> {
        private String encoded_string, image_name;
        Bitmap bitmap;

        @Override
        protected Void doInBackground(String... args) {
            String filename = args[0];
            image_name = args[1];
            bitmap = BitmapFactory.decodeFile(filename);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            makeRequest(encoded_string, image_name);
        }

        private void makeRequest(final String encoded_string, final String image_name) {
            class UploadAll extends AsyncTask<Void, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(Void... params) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", user.name);
                    hashMap.put("password", user.password);
                    hashMap.put("email", user.email);
                    hashMap.put("phone", user.phone);
                    hashMap.put("encoded_string", encoded_string);
                    hashMap.put("image_name", image_name);
                    RequestHandler requestHandler = new RequestHandler();
                    String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Register.php", hashMap);
                    return s;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if(s.equalsIgnoreCase("success")) {
                        Toast.makeText(RegisterActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.finish();
                        startActivity(intent);
                    }
                    else if(s.equalsIgnoreCase("nodata")) {
                        Toast.makeText(RegisterActivity.this, "Please fill in data first", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            UploadAll uploadall = new UploadAll();
            uploadall.execute();
        }
    }
}