package com.metacoders.hurrydriver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.metacoders.hurrydriver.Adapter.viewPager2_adapter;
import com.metacoders.hurrydriver.Adapter.viewPagerAdapter;
import com.metacoders.hurrydriver.Fragmnet.HomePageFragment;
import com.metacoders.hurrydriver.Fragmnet.RequestListFragment;
import com.metacoders.hurrydriver.Fragmnet.TripsList;
import com.metacoders.hurrydriver.R;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    //  ViewPager viewPager ;
    NavigationView navigationView;
    private NavController navController;

    private DrawerLayout drawerLayout;
    ImageView hamburger;
    ViewPager2 viewPager;
    FloatingActionButton emergencyFuel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//          //Navigation Component
//        BottomNavigationView navView = findViewById(R.id.navView);
//
//        navController = Navigation.findNavController(MainActivity.this , R.id.fragment_container_view_tag) ;
//
//        NavigationUI.setupWithNavController(navView , navController);


        // tab layout


//        tabLayout= (TabLayout)findViewById(R.id.tabLayout) ;
//        //  appBarLayout = findViewById(R.id.appbarId) ;
//        viewPager =findViewById(R.id.viewPager) ;
//
//
//        viewPagerAdapter adapter = new viewPagerAdapter(getSupportFragmentManager());
//
//        adapter.AddFragment(new HomePageFragment(), "Dash-Board");
//        adapter.AddFragment(new TripsList(), "Trip-List");
//        adapter.AddFragment(new RequestListFragment() , "Request-List");
//
//
//        viewPager.setAdapter(adapter) ;
//        tabLayout.setupWithViewPager(viewPager) ;


        // view pager 2

        final BottomNavigationView navigationBar = findViewById(R.id.bottom_navigation_);
        viewPager = findViewById(R.id.view_pager);

        viewPager.setUserInputEnabled(false);
        // navigationView = findViewById(R.id.navigation_view);
        navigationBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
       // navigationBar.setItemIconTintList(null);
        // getSupportFragmentManager().beginTransaction().replace(R.id.view_pager, new dashboardFragment()).commit();
        viewPager.setAdapter(new viewPager2_adapter(MainActivity.this));

// call back
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        navigationBar.getMenu().findItem(R.id.HomePageFragment).setChecked(true);
                        break;
                    case 1:
                        navigationBar.getMenu().findItem(R.id.TripsList).setChecked(true);
                        break;
                    case 2:
                        navigationBar.getMenu().findItem(R.id.RequestListFragment).setChecked(true);
                        break;

                }

                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                    switch (menuItem.getItemId()) {
                        case R.id.HomePageFragment:
                            viewPager.setCurrentItem(0, false);
                            break;

                        case R.id.TripsList:
                            viewPager.setCurrentItem(1, false);
                            break;

                        case R.id.RequestListFragment:
                            viewPager.setCurrentItem(2, false);
                            break;

                    }
                    return true;

                }
            };
}
