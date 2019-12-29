package com.bangkandar.moviecatalogelocalstorage.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bangkandar.moviecatalogelocalstorage.R;
import com.bangkandar.moviecatalogelocalstorage.pager.FavoritePager;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        FavoritePager favoritePager = new FavoritePager(getActivity(), getFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.fav_view_pager);
        viewPager.setAdapter(favoritePager);
        TabLayout tabs = view.findViewById(R.id.fav_tabs);
        tabs.setupWithViewPager(viewPager);
        return view;
    }

}
