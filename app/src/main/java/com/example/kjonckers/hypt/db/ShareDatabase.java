package com.example.kjonckers.hypt.db;

import android.os.StrictMode;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import domain.Share;
import domain.User;

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
}
