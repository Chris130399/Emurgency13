package com.example.emurgency13.Measures;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.emurgency13.Maps.MainActivity;
import com.example.emurgency13.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MeasureListActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private Measure1 measure1;
    private Measure2 measure2;
    private Measure3 measure3;
    private Measure4 measure4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_list);


        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        measure1 = new Measure1();
        measure2 = new Measure2();
        measure3 = new Measure3();
        measure4 = new Measure4();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(measure1, "Choking");
        viewPagerAdapter.addFragment(measure2, "Burns");
        viewPagerAdapter.addFragment(measure3, "Seizure");
        viewPagerAdapter.addFragment(measure4, "Drowning");
        viewPager.setAdapter(viewPagerAdapter);

        //FLOATING BUTTONS
        final FloatingActionButton fab1 = findViewById(R.id.action1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Calling 101");
                dialContactPhone("9326700947");

            }
        });

        final FloatingActionButton fab2 = findViewById(R.id.action2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Redirecting to Maps Page");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void showToast(String Message){
        Toast.makeText(this, Message,Toast.LENGTH_LONG).show();

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){

            fragments.add(fragment);
            fragmentTitles.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }
}
