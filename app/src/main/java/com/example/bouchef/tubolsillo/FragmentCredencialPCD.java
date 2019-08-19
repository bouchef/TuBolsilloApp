package com.example.bouchef.tubolsillo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentCredencialPCD extends Fragment {

    public FragmentCredencialPCD() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_fragment1, container, false);
        return inflater.inflate(R.layout.activity_credencial_pcd, container, false);
    }
}
