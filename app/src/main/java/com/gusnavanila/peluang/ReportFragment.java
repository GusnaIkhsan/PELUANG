package com.gusnavanila.peluang;

import android.net.Uri;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gusnavanila.peluang.FragmentReport.AllReportFragment;
import com.gusnavanila.peluang.FragmentReport.MonthFragment;
import com.gusnavanila.peluang.FragmentReport.WeekFragment;

public class ReportFragment extends Fragment {
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        this.tabLayout = rootView.findViewById(R.id.tabLayout);

        final ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        final MyViewPageAdapter adapter = new MyViewPageAdapter(getFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    public class MyViewPageAdapter extends FragmentStatePagerAdapter {
        int tabCounter;

        public MyViewPageAdapter(FragmentManager fm, int tabCounter) {
            super(fm);
            this.tabCounter = tabCounter;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    WeekFragment weekFragment = new WeekFragment();
                    return weekFragment;
                case 1:
                    MonthFragment monthFragment = new MonthFragment();
                    return monthFragment;
                case 2:
                    AllReportFragment allReportFragment = new AllReportFragment();
                    return allReportFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return this.tabCounter;
        }
    }
}
