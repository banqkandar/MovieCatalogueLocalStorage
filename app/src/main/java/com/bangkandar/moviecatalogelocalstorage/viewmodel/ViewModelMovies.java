package com.bangkandar.moviecatalogelocalstorage.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bangkandar.moviecatalogelocalstorage.BuildConfig;
import com.bangkandar.moviecatalogelocalstorage.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ViewModelMovies extends ViewModel {
    String apiKey = BuildConfig.TMDB_API_KEY;
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();

    public void setMovies(final String EXTRA_MOVIE) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> arrayList = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=" + apiKey + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {

                        JSONObject mv = list.getJSONObject(i);
                        Movie movie = new Movie(mv);
                        arrayList.add(movie);
                    }
                    listMovies.postValue(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
                e.printStackTrace();
                Log.d("onFailure", e.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}
