package com.example.e_tutor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapterVideo extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater;

    public CustomAdapterVideo(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        try {
            if(convertView == null)
                view = inflater.inflate(R.layout.video_info, null);
            HashMap<String, Object> data = (HashMap<String, Object>)getItem(position);
            TextView video_name = view.findViewById(R.id.textViewName);
            TextView video_author = view.findViewById(R.id.textViewAuthor);
            TextView video_publisheddate = view.findViewById(R.id.textViewPublishedDate);
            String name = (String)data.get("name");
            String author =(String)data.get("author");
            String publisheddate =(String)data.get("publisheddate");
            video_name.setText(name);
            video_author.setText(author);
            video_publisheddate.setText("Published on " + publisheddate);
        }
        catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return view;
    }
}