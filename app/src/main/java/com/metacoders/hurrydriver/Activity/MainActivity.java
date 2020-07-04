package com.metacoders.hurrydriver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.metacoders.hurrydriver.Adapter.viewPagerAdapter;
import com.metacoders.hurrydriver.Fragmnet.HomePageFragment;
import com.metacoders.hurrydriver.Fragmnet.RequestListFragment;
import com.metacoders.hurrydriver.Fragmnet.TripsList;
import com.metacoders.hurrydriver.R;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout  ;
    AppBarLayout appBarLayout ;
    ViewPager viewPager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout= (TabLayout)findViewById(R.id.tabLayout) ;
        //  appBarLayout = findViewById(R.id.appbarId) ;
        viewPager =findViewById(R.id.viewPager) ;


        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new HomePageFragment(), "Dash-Board");
        adapter.AddFragment(new TripsList(), "Trip-List");
        adapter.AddFragment(new RequestListFragment() , "Request-List");


        viewPager.setAdapter(adapter) ;
        tabLayout.setupWithViewPager(viewPager) ;


    }
}
