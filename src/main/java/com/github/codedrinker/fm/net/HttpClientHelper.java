package com.github.codedrinker.fm.net;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author wangwei on 2018/9/6.
 * wangwei@jiandaola.com
 */
public class HttpClientHelper {

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static OkHttpClient.Builder createClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * Sync execute a Get request ,and format response to string
     */
    public static String getSync(String url) throws IOException {
        return getSync(null, url);
    }

    /**
     * Sync execute a Get request ,and format response to string
     */
    public static String getSync(OkHttpClient client, String url) throws IOException {
        if (client == null) {
            client = createClient().build();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }
        throw new EmptyResponseException();
    }

    /**
     * ASync execute a Get request
     */
    public static void getASync(String url, final Callback callback) {
        getASync(null, url, callback);
    }

    /**
     * ASync execute a Get request
     */
    public static void getASync(OkHttpClient client, String url, final Callback callback) {
        if (client == null) {
            client = createClient().build();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        //noinspection Duplicates
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    callback.onResponse(call, response);
                }
            }
        });
    }

    /**
     * Sync execute a post request ,and format response to string
     *
     * @throws IOException IOException,EmptyResponseException
     */
    public static String postSync(String url, String json) throws IOException {
        return postSync(null, url, json);
    }

    /**
     * Sync execute a post request ,and format response to string
     *
     * @throws IOException IOException,EmptyResponseException
     */
    public static String postSync(OkHttpClient client, String url, String json) throws IOException {
        if (client == null) {
            client = createClient().build();
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }
        throw new EmptyResponseException();
    }

    /**
     * Async execute a post request
     */
    public static void postASync(String url, String json, final Callback callback) {
        postASync(null, url, json, callback);
    }

    /**
     * Async execute a post request
     */
    public static void postASync(OkHttpClient client, String url, String json, final Callback callback) {
        if (client == null) {
            client = createClient().build();
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        //noinspection Duplicates
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFailure(call, e);
                }
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null) {
                    callback.onResponse(call, response);
                }
            }
        });
    }
}
