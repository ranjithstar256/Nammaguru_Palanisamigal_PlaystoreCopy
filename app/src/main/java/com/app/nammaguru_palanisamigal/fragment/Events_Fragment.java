package com.app.nammaguru_palanisamigal.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.nammaguru_palanisamigal.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class Events_Fragment extends Fragment {

    private View view;
    private ViewPager viewPagers;
    private TabLayout tabLayouts;
    private ViewPagerAdapter adapters;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.activity_events, container, false);


        initialView(view);

        return view;
    }

    private void initialView(View view) {


        viewPagers = (ViewPager) view.findViewById(R.id.pagers);
        tabLayouts = (TabLayout) view.findViewById(R.id.tabss);

        adapters = new ViewPagerAdapter(getChildFragmentManager());
        adapters.addFragment(new Present_Events_Fragment(), "EVENTS");
        adapters.addFragment(new Upcoming_Events_Fragment(), "UPCOMING");
        viewPagers.setAdapter(adapters);


        tabLayouts.setupWithViewPager(viewPagers);
        viewPagers.setSaveFromParentEnabled(false);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
