package com.example.e_tutor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChapterActivity extends AppCompatActivity {
    TextView chapter_name, chapter_description;
    CircleImageView chapter_image;
    String name, description, email, phone;
    ListView video, quiz;
    ArrayList<HashMap<String, String>> video_list, quiz_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        chapter_name = findViewById(R.id.textViewName);
        chapter_description = findViewById(R.id.textViewDescription);
        chapter_image = findViewById(R.id.imageView);
        video = findViewById(R.id.listViewVideo);
        video_list = new ArrayList<>();
        quiz = findViewById(R.id.listViewQuiz);
        quiz_list = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        description = bundle.getString("description");
        email = bundle.getString("email");
        phone = bundle.getString("phone");
        chapter_name.setText(name);
        chapter_description.setText(description);
        String image_url = "http://studentsumber.com/etutor/chapter_images/" + name.replaceAll(" ", "_").toLowerCase() + ".jpg";
        Picasso.with(this).load(image_url).resize(200, 200).into(chapter_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadVideos(name);
        video.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChapterActivity.this, VideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", video_list.get(position).get("name"));
                bundle.putString("author", video_list.get(position).get("author"));
                bundle.putString("publisheddate", video_list.get(position).get("publisheddate"));
                bundle.putString("url", video_list.get(position).get("url"));
                bundle.putString("email", email);
                bundle.putString("phone", phone);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ChapterActivity.this, MainActivity.class);
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

    private void loadVideos(final String name) {
        class LoadVideo extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("chapter", name);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://grouping.000webhostapp.com/etutor/Loadvideo.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                video_list.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray videoArray = jsonObject.getJSONArray("video");
                    for(int i = 0; i < videoArray.length(); i++) {
                        JSONObject o = videoArray.getJSONObject(i);
                        String video_name = o.getString("name");
                        String video_author = o.getString("author");
                        String video_publisheddate = o.getString("publisheddate");
                        String video_url = o.getString("url");
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("name", video_name);
                        hashMap.put("author", video_author);
                        hashMap.put("publisheddate", video_publisheddate);
                        hashMap.put("url", video_url);
                        video_list.add(hashMap);
                    }
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }
                ListAdapter adapter = new CustomAdapterVideo(ChapterActivity.this, video_list, R.layout.video_info, new String[] {"name", "author", "publisheddate"}, new int[] {R.id.textViewName, R.id.textViewAuthor, R.id.textViewPublishedDate});
                video.setAdapter(adapter);
            }
        }
        LoadVideo loadVideo = new LoadVideo();
        loadVideo.execute();
    }
}
