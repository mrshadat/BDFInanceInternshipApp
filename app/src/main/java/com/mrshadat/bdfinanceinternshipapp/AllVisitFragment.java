package com.mrshadat.bdfinanceinternshipapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.mrshadat.bdfinanceinternshipapp.adapters.CustomerAdapter;
import com.mrshadat.bdfinanceinternshipapp.adapters.DepositAdapter;
import com.mrshadat.bdfinanceinternshipapp.databinding.FragmentAllVisitBinding;
import com.mrshadat.bdfinanceinternshipapp.databinding.FragmentDepositProductBinding;
import com.mrshadat.bdfinanceinternshipapp.models.CustomerInfo;
import com.mrshadat.bdfinanceinternshipapp.models.Deposit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllVisitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllVisitFragment extends Fragment {

    FragmentAllVisitBinding allVisitBinding;
    CustomerAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllVisitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllVisitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllVisitFragment newInstance(String param1, String param2) {
        AllVisitFragment fragment = new AllVisitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        allVisitBinding = FragmentAllVisitBinding.inflate(LayoutInflater.from(getActivity()));
        return allVisitBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allVisitBinding.recyclerAllVisit.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<CustomerInfo> options =
                new FirebaseRecyclerOptions.Builder<CustomerInfo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("customer"), CustomerInfo.class)
                        .build();

        adapter = new CustomerAdapter(options);
        allVisitBinding.recyclerAllVisit.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}