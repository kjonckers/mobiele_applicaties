package com.example.kjonckers.hypt.db;

import android.os.StrictMode;
import android.util.Log;

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
import java.util.Set;
import java.util.TreeSet;

import exceptions.DomainException;
import domain.Category;
import domain.Experience;
import domain.GeoLocation;
import domain.Interest;
import domain.User;
import domain.UserInterest;

/**
 * Created by kjonckers on 16/08/15.
 */
public class UserDatabase implements Database<User>{


    public void updateUser(User user) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost("http://dealthis.be/hypt/db_insert.php");

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("id", user.getId()));
        params.add(new BasicNameValuePair("firstname", user.getFirstName()));
        params.add(new BasicNameValuePair("latitude", Double.toString(user.getLocation().getLatitude())));
        params.add(new BasicNameValuePair("longitude", Double.toString(user.getLocation().getLongitude())));
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

    @Override
    public User get(String id) {
        return null;
    }
}
