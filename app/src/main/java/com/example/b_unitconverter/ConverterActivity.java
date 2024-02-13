package com.example.b_unitconverter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class ConverterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_converter);



                ViewPager viewPager = findViewById(R.id.viewPager);

                PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(pagerAdapter);


                TabLayout tabLayout = findViewById(R.id.tabLayout);


                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

                tabLayout.setupWithViewPager(viewPager);
            }
        }




