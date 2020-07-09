package com.metacoders.hurrydriver.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.metacoders.hurrydriver.Fragmnet.HomePageFragment;
import com.metacoders.hurrydriver.Fragmnet.RequestListFragment;
import com.metacoders.hurrydriver.Fragmnet.TripsList;

public class viewPager2_adapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"DashBoard", "Products", "Cart" };

    public viewPager2_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new HomePageFragment();
            case 1:
                return new TripsList();

            case 2 :
                return  new RequestListFragment() ;

        }

        return new HomePageFragment();
    }



    @Override
    public int getItemCount() {
        return 3;
    }
}
