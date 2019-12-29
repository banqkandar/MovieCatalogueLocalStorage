package com.bangkandar.moviecatalogelocalstorage.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bangkandar.moviecatalogelocalstorage.R;
import com.bangkandar.moviecatalogelocalstorage.adapter.AdapterTvShow;
import com.bangkandar.moviecatalogelocalstorage.model.TvShow;
import com.bangkandar.moviecatalogelocalstorage.viewmodel.ViewModelTvshow;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {

    private AdapterTvShow adapterTvShow;
    private ProgressBar progressBar;
    private ViewModelTvshow viewModelTvshow;

    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapterTvShow = new AdapterTvShow();
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_tvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterTvShow);

        progressBar = view.findViewById(R.id.progressBar);
        viewModelTvshow = ViewModelProviders.of(this).get(ViewModelTvshow.class);
        viewModelTvshow.getTvShow().observe(this, getTvShow);
        viewModelTvshow.setTvShow("EXTRA_TV_SHOW");

        showLoading(true);
        return view;
    }

    private Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                adapterTvShow.setTvData(tvShows);
            }

            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
