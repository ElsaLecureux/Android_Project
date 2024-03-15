package com.example.webview;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.util.Log;
import android.webkit.WebView;

import android.os.Bundle;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private String API_KEY= "86acef608d834720b5c4fb539c6bff17";

    private TextView textView;

    private Article article;

    private ArrayList<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getNewsArticles();
    }

    public void getNewsArticles() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("articles");
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                article = new Article();
                                JSONObject jSonArticle = jsonArray.getJSONObject(i);
                                String titleJson =  jSonArticle.getString("title");
                                article.setTitle(titleJson);
                                String authorJson =  jSonArticle.getString("author");
                                article.setTitle(authorJson);
                                String descriptionJson =  jSonArticle.getString("description");
                                article.setTitle(descriptionJson);
                                String urlJson =  jSonArticle.getString("url");
                                article.setTitle(urlJson);
                                String urlImageJson =  jSonArticle.getString("urlImage");
                                article.setTitle(urlImageJson);
                                String publicationJson =  jSonArticle.getString("publication");
                                article.setTitle(publicationJson);
                                articles.add(article);
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR", "onErrorResponse: error");
                    }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-agent", "mozilla/5.0");
                return headers;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

