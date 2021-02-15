package com.mrshadat.bdfinanceinternshipapp.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mrshadat.bdfinanceinternshipapp.DepositProductFragment;
import com.mrshadat.bdfinanceinternshipapp.LoanProductFragment;

public class TabAdapter extends FragmentPagerAdapter {


    Context context;
    int totalTabs;
    public TabAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DepositProductFragment depositProductFragment = new DepositProductFragment();
                return depositProductFragment;
            case 1:
                LoanProductFragment loanProductFragment = new LoanProductFragment();
                return loanProductFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
