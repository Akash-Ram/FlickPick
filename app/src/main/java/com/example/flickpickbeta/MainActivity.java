package com.example.flickpickbeta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    String json_url= "https://api.themoviedb.org/3/movie/popular?api_key=fd9172749401d293f45b9082d2878e67";

    List<MovieModelClass> movieList;
    RecyclerView recyclerView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList= new ArrayList<>();
        recyclerView= findViewById(R.id.recyclerview);
        textView= findViewById(R.id.textView);


        

        GetData getdata= new GetData();
        getdata.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {




        getMenuInflater().inflate(R.menu.main_menu, menu);



        //Search Bar
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search_view));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                textView.setText("Result");



                json_url="https://api.themoviedb.org/3/search/movie?api_key=fd9172749401d293f45b9082d2878e67&query="+query;
                movieList.removeAll(movieList);
                GetData getdata= new GetData();
                getdata.execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String current= "";

            try{
                URL url;
                HttpURLConnection urlConnection= null;

                try{
                    url= new URL(json_url);
                    urlConnection= (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr= new InputStreamReader(is);

                    int data= isr.read();
                    while(data != -1){
                        current += (char)data;
                        data=isr.read();
                    }

                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                JSONObject obj= new JSONObject(s);
                JSONArray jsonArray= obj.getJSONArray("results");

                for(int i=0;i< jsonArray.length(); i++){

                    JSONObject movieObject= jsonArray.getJSONObject(i);

                    MovieModelClass model= new MovieModelClass();
                    model.setTitle(movieObject.getString("title"));
                    model.setRating(movieObject.getString("vote_average"));
                    model.setInfo(movieObject.getString("overview"));
                    model.setImg(movieObject.getString("poster_path"));

                    movieList.add(model);



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(movieList);
        }


    }
    private void PutDataIntoRecyclerView(List<MovieModelClass> movieList){

        MovieAdapter adaptery= new MovieAdapter(this, movieList);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(adaptery);


    }

}

