package com.example.finalprep;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import okhttp3.FormBody;

public class JSONFragment extends Fragment {
    /*
    Steps to make JSON work:
        1. Ask for permission in Android Manifest
        2. OkHttp library:
            a. add dependency in gradle: 'com.squareup.okhttp3:okhttp-bom:4.9.3'
            b. add GSON to dependency in gradle: 'com.google.code.gson:gson:2.8.6'
            c. add another dependency in gradle: 'com.squareup.okhttp3:okhttp'
        3. Instansiate client as field
        4. Import client
        5. Build a request
        6. Pass request to call
        7. Client calls cannot be in main thread
     */

    private final OkHttpClient client = new OkHttpClient();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    recycleViewAdapterJSON adapter;

    public JSONFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("JSON");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_j_s_o_n, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewJSON);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        /* Can do a url builder

        HttpUrl url = HttpUrl.parse("https://www.theappsdr.com/contacts/search").newBuilder()
                .addQueryParameter("name", "rh")
                .addQueryParameter("type", "HOME" )
                .build();

        And pass into the request
        Request request = new Request.Builder().url(url).build();

         */

        /*
            Post


        FormBody formBody = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("phone", phone)
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts?page=")
                .post(formBody)
                .build();
        */


        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/posts?page=" + 1)
                .addHeader("Authorization", "BEARER " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTEyNTg0NzQsImV4cCI6MTY4Mjc5NDQ3NCwianRpIjoiMW80aGxoODczbUU1c09qdTdPaFZxaCIsInVzZXIiOjIzfQ.wXGmp4g3AhKh5eJJzXT0HQ54a8GsG8CR1xa_0zop2ps")
                .build();


        //Get Request
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){

                    Gson gson = new Gson();
                    JSONClass listJason = gson.fromJson(response.body().charStream(), JSONClass.class);
                    adapter = new recycleViewAdapterJSON(listJason.posts);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            }
        });

        return view;
    }
}