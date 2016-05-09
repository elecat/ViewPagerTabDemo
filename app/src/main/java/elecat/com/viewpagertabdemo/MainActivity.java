package elecat.com.viewpagertabdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import elecat.com.viewpagertabdemo.R;
import elecat.com.viewpagertabdemo.fragment.OutterHomePageFragment;


public class MainActivity extends AppCompatActivity {
    ViewPager mainViewPager;
    List<Fragment> mainFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findById();
        mainViewPagerAddFragment();
    }

    private void mainViewPagerAddFragment() {
        mainFragments.add(new OutterHomePageFragment());
        mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mainViewPager.setCurrentItem(0);
    }

    private void findById() {
        mainViewPager = (ViewPager) findViewById(R.id.main_viewpager);
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {


        public MainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mainFragments.get(position);
        }

        @Override
        public int getCount() {
            return mainFragments.size();
        }
    }
}
