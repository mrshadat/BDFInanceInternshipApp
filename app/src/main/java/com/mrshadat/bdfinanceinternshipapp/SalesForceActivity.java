package com.mrshadat.bdfinanceinternshipapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mrshadat.bdfinanceinternshipapp.databinding.ActivitySalesForceBinding;

public class SalesForceActivity extends AppCompatActivity {

    ActivitySalesForceBinding salesForceBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        salesForceBinding = DataBindingUtil.setContentView(this, R.layout.activity_sales_force);
    }
}