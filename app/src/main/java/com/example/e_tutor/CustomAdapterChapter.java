package com.example.e_tutor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapterChapter extends SimpleAdapter {
    private Context mContext;
    public LayoutInflater inflater;

    public CustomAdapterChapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        try {
            if(convertView == null)
                view = inflater.inflate(R.layout.chapter_info, null);
            HashMap<String, Object> data = (HashMap<String, Object>)getItem(position);
            TextView chapter_name = view.findViewById(R.id.textViewName);
            TextView chapter_description = view.findViewById(R.id.textViewDescription);
            CircleImageView chapter_image = view.findViewById(R.id.imageView);
            String name = (String)data.get("name");
            String description =(String)data.get("description");
            chapter_name.setText(name);
            chapter_description.setText(description);
            String image_url = "http://studentsumber.com/etutor/chapter_images/" + name.replaceAll(" ", "_").toLowerCase() + ".jpg";
            Picasso.with(mContext).load(image_url).resize(200, 200).into(chapter_image);
        }
        catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return view;
    }
}