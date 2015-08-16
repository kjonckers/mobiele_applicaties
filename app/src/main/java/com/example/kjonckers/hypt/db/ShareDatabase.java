package com.example.kjonckers.hypt.db;

import android.os.StrictMode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import domain.GeoLocation;
import domain.Share;
import domain.User;
import exceptions.DomainException;

/**
 * Created by kjonckers on 16/08/15.
 */
public class ShareDatabase implements Database<Share> {

    @Override
    public Share get(String id) {
        return null;
    }

    public void updateShare(Share share) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost("http://dealthis.be/hypt/db_insertShare.php");

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("id", share.getId()));
        params.add(new BasicNameValuePair("facebookid", share.getFacebookid()));
        params.add(new BasicNameValuePair("text", share.getText()));
        params.add(new BasicNameValuePair("latitude", Double.toString(share.getLocation().getLatitude())));
        params.add(new BasicNameValuePair("longitude", Double.toString(share.getLocation().getLongitude())));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        try {
            httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //@Override
    public List<Share> getAllShares() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httpget = new HttpGet("http://dealthis.be/hypt/db_getShares.php");
        httpget.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            result = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream != null)
                    inputStream.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }



        List<Share> shares = new ArrayList<Share>();
        try {
            JSONArray json = new JSONArray(result);
            for (int index = 0; index < json.length(); index++) {
                JSONObject obj = json.getJSONObject(index);
                GeoLocation geo = new GeoLocation(obj.getDouble("latitude"), obj.getDouble("longitude"));
                Share share = new Share(obj.getString("id"),obj.getString("facebookid"),obj.getString("text"),geo);

                shares.add(share);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (DomainException e) {
            e.printStackTrace();
        }

        return shares;
    }

}
