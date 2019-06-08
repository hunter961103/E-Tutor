package com.example.e_tutor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView chapter;
    ArrayList<HashMap<String, String>> chapter_list;
    Spinner select_subject;
    String email, name, phone;
    Dialog dialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chapter = findViewById(R.id.listViewChapter);
        chapter_list = new ArrayList<>();
        select_subject = findViewById(R.id.spinner);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        email = bundle.getString("email");
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        loadSubject(select_subject.getSelectedItem().toString());
        chapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String cname = chapter_list.get(position).get("name");
                verifyChapter(cname, position);
            }
        });
        select_subject.setSelection(0, false);
        select_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadSubject(select_subject.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void verifyChapter(final String cname, final int position) {
        class VerifyChapter extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("chapter", cname);
                hashMap.put("phone", phone);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Verifychapter.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equalsIgnoreCase("unlocked")) {
                    Intent intent = new Intent(MainActivity.this, ChapterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", chapter_list.get(position).get("name"));
                    bundle.putString("description", chapter_list.get(position).get("description"));
                    bundle.putString("email", email);
                    bundle.putString("phone", phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    showChapterDetail(cname, position);
                }
            }
        }
        VerifyChapter verifyChapter = new VerifyChapter();
        verifyChapter.execute();
    }

    private void showChapterDetail(final String cname, final int position) {
        dialogBox = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        dialogBox.setContentView(R.layout.dialog_box);
        dialogBox.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView chapter_name, chapter_description, chapter_price;
        final ImageView chapter_image = dialogBox.findViewById(R.id.imageView);
        Button unlock_button = dialogBox.findViewById(R.id.button);
        chapter_name = dialogBox.findViewById(R.id.textViewName);
        chapter_description = dialogBox.findViewById(R.id.textViewDescription);
        chapter_price = dialogBox.findViewById(R.id.textViewPrice);
        chapter_name.setText(chapter_list.get(position).get("name"));
        chapter_description.setText(chapter_list.get(position).get("description"));
        chapter_price.setText("Price: RM" + chapter_list.get(position).get("price"));
        unlock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUnlock(cname, position);
            }
        });
        Picasso.with(this).load("http://studentsumber.com/etutor/chapter_images/" + cname.replaceAll(" ", "_").toLowerCase() + ".jpg").resize(400, 400).into(chapter_image);
        dialogBox.show();
    }

    private void dialogUnlock(final String cname, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Unlock \"" + cname + "\"");
        alertDialogBuilder.setMessage("Are you sure").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                unlockChapter(cname, position);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void unlockChapter(final String cname, final int position) {
        class UnlockChapter extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("chapter", cname);
                hashMap.put("phone", phone);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Unlockchapter.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equalsIgnoreCase("success")) {
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    dialogBox.dismiss();
                    Intent intent = new Intent(MainActivity.this, ChapterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", chapter_list.get(position).get("name"));
                    bundle.putString("description", chapter_list.get(position).get("description"));
                    bundle.putString("email", email);
                    bundle.putString("phone", phone);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        UnlockChapter unlockChapter = new UnlockChapter();
        unlockChapter.execute();
    }

    private void loadSubject(final String subject) {
        class LoadSubject extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("subject", subject);
                RequestHandler requestHandler = new RequestHandler();
                chapter_list = new ArrayList<>();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Loadchapter.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                chapter_list.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray chapterArray = jsonObject.getJSONArray("chapter");
                    for(int i = 0; i < chapterArray.length(); i++) {
                        JSONObject o = chapterArray.getJSONObject(i);
                        String chapter_name = o.getString("name");
                        String chapter_description = o.getString("description");
                        String price = o.getString("price");
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("name", chapter_name);
                        hashMap.put("description", chapter_description);
                        hashMap.put("price", price);
                        chapter_list.add(hashMap);
                    }
                }
                catch(final JSONException e) {
                    e.printStackTrace();
                }
                ListAdapter adapter = new CustomAdapterChapter(MainActivity.this, chapter_list, R.layout.chapter_info, new String[] {"name", "description"}, new int[] {R.id.textViewName, R.id.textViewDescription});
                chapter.setAdapter(adapter);
            }
        }
        LoadSubject loadSubject = new LoadSubject();
        loadSubject.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.itemMyProfile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("name", name);
                bundle.putString("phone", phone);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.itemAbout:
                return true;
            case R.id.itemLogOut:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
