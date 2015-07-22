package com.davidllorca.apptest.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Call web service class.
 *
 * Created by David Llorca <davidllorcabaron@gmail.com> on 7/17/15.
 */
public class Request {

    public String GET(String url){
        HttpGet get = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();

        HttpResponse response = null;
        try {
            response = client.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            return "IO error";
        }
    }
}
