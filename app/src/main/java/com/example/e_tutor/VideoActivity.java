package com.example.e_tutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity {
    private YouTubePlayerView video;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    TextView video_name, video_author, video_publisheddate;
    String name, author, publisheddate, url, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        video = findViewById(R.id.webView);
        video_name = findViewById(R.id.textViewName);
        video_author = findViewById(R.id.textViewAuthor);
        video_publisheddate = findViewById(R.id.textViewPublishedDate);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        author = bundle.getString("author");
        publisheddate = bundle.getString("publisheddate");
        url = bundle.getString("url");
        email = bundle.getString("email");
        phone = bundle.getString("phone");
        video_name.setText(name);
        video_author.setText(author);
        video_publisheddate.setText(publisheddate);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(url);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.initialize("AIzaSyDOWjgiY5nA1TBKDC1pc2DH14QpUCP3T-M", onInitializedListener);
            }
        });
    }
}
