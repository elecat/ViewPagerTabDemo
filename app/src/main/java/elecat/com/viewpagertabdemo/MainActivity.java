package elecat.com.viewpagertabdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elecat.com.viewpagertabdemo.fragment.OutterHomePageFragment;


public class MainActivity extends AppCompatActivity {
    List<Fragment> mainFragments = new ArrayList<>();
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewPagerAddFragment();
    }

    private void mainViewPagerAddFragment() {
        mainFragments.add(new OutterHomePageFragment());
        mainViewpager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        mainViewpager.setCurrentItem(0);
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
