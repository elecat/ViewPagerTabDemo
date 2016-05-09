package elecat.com.viewpagertabdemo.fragment;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import elecat.com.viewpagertabdemo.R;

/**
 * Created by liangminhua on 16/5/7.
 */
public class OutterHomePageFragment extends Fragment {
    ViewPager innerViewPager;
    View view;
    List<Fragment> fragments;
    Fragment bookFragment, novelFragment, cartoomFragment;
    ImageView cursorImageView;
    //下标的偏移量
    int offset;
    int currIndex = 0;      // 当前页卡编号
    int ivWide;//下标宽度
    LinearLayout tagLinerLayout;
    int linerLayoutW;
    android.os.Handler handler = new android.os.Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage_outter, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findById();
        //在这里有可能是得不到控件的宽度的，因为这里还没有可以得到数据
        initImageView();
        viewPagerAddFragment();
        //让主线程代码延迟
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                innerViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
            }
        },300);
    }

    private void initImageView() {
        ViewTreeObserver vto= cursorImageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                cursorImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ivWide = cursorImageView.getWidth();
                Toast.makeText(getActivity(),"ivWide-->"+ivWide,Toast.LENGTH_SHORT).show();
                linerLayoutW =tagLinerLayout.getWidth();
                Toast.makeText(getActivity(),"linerLayoutW-->"+linerLayoutW,Toast.LENGTH_SHORT).show();
                offset = (linerLayoutW/3-ivWide)/2;// 获取图片偏移量
                Toast.makeText(getActivity(),"offset"+offset,Toast.LENGTH_SHORT).show();
                //设定imageView的初始位置

//                下面是网上写的设置初始位置的代码
//                Matrix matrix = new Matrix();
//                matrix.postTranslate(offset, 0);
//                cursorImageView.setImageMatrix(matrix);
            }
        });
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenW = dm.widthPixels; // 获取手机屏幕宽度分辨率
//        offset = (screenW / 3 - ivWide) / 2; // 获取图片偏移量
        // imageview设置平移，使下划线平移到初始位置（平移一个offset）
    }
    private void viewPagerAddFragment() {
        fragments = new ArrayList<Fragment>();
        bookFragment = new InnerBookFragment();
        novelFragment = new InnerNovelFragment();
        cartoomFragment = new InnerCartoonFragment();
        fragments.add(bookFragment);
        fragments.add(novelFragment);
        fragments.add(cartoomFragment);
        innerViewPager.setAdapter(new InnerPagerFragmentAdapter(getChildFragmentManager()));
        innerViewPager.setCurrentItem(0);
    }

    private void findById() {
        innerViewPager = (ViewPager) view.findViewById(R.id.inner_viewPager);
        cursorImageView = (ImageView) view.findViewById(R.id.cursor_iv);
        tagLinerLayout = (LinearLayout) view.findViewById(R.id.tag_ll);
    }

    public class InnerPagerFragmentAdapter extends FragmentPagerAdapter {

        public InnerPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    // 当页面滑动时，开始动画并跳出Toast
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private int one = offset * 2 + ivWide ; // 页面1到页面2的偏移量

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        // 即将要被显示的页卡的index
        @Override
        public void onPageSelected(int arg0) {
            // 初始化移动的动画（从当前位置，x平移到即将要到的位置）
            //这个动画设定了平移效果，从哪个位置移动到哪个位置，记录了上次的位置，计算出下次的位置
            Animation animation = new TranslateAnimation(currIndex * one, arg0
                    * one, 0, 0);
            currIndex = arg0;
            animation.setFillAfter(true); // 动画终止时停留在最后一帧，不然会回到没有执行前的状态
            animation.setDuration(200); // 动画持续时间，0.2秒
            cursorImageView.startAnimation(animation); // 是用imageview来显示动画
            int i = currIndex + 1;
        }
    }
}
