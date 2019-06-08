package com.example.e_tutor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RequestHandler {

    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            outputStream.close();
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();
                String response;
                while((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String sendGetRequest(String requestURL) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response;
            while((response = bufferedReader.readLine()) != null) {
                stringBuilder.append(response + "\n");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public String sendGetRequestParam(String requestURL, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL + id);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response;
            while((response = bufferedReader.readLine()) != null) {
                stringBuilder.append(response + "\n");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()) {
            if(first)
                first = false;
            else
                stringBuilder.append("&");
            stringBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return stringBuilder.toString();
    }
}