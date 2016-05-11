package elecat.com.viewpagertabdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import elecat.com.viewpagertabdemo.R;

/**
 * Created by liangminhua on 16/5/7.
 */
public class OutterHomePageFragment extends Fragment implements View.OnClickListener {
    View view;
    List<Fragment> fragments;
    Fragment bookFragment, novelFragment, cartoomFragment;
    int offset; //下标的偏移量
    int currIndex = 0;// 当前页卡编号
    int ivWide;//下标宽度
    int linerLayoutW;
    Handler handler = new Handler();
    @BindView(R.id.booksheft_tv)
    TextView booksheftTv;
    @BindView(R.id.novel_tv)
    TextView novelTv;
    @BindView(R.id.cartoon_tv)
    TextView cartoonTv;
    @BindView(R.id.tag_ll)
    LinearLayout tagLL;
    @BindView(R.id.cursor_iv)
    ImageView cursorIv;
    @BindView(R.id.inner_viewPager)
    ViewPager innerViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage_outter, null);
        ButterKnife.bind(this, view);
        setTitle();
        initImageView();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        viewPagerAddFragment();
        //让主线程代码延迟s
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                innerViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
            }
        }, 100);
        setListener();
    }

    private void setTitle() {
        booksheftTv.setText("书籍");
        cartoonTv.setText("漫画");
        novelTv.setText("小说");
    }

    private void setListener() {
        booksheftTv.setOnClickListener(this);
        novelTv.setOnClickListener(this);
        cartoonTv.setOnClickListener(this);
    }

    private void initImageView() {
        ViewTreeObserver vto = cursorIv.getViewTreeObserver();
        //这个回调接口使view的计算推迟到view被加载完毕

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                cursorIv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ivWide = cursorIv.getWidth();
                linerLayoutW = tagLL.getWidth();
                offset = (linerLayoutW / 3 - ivWide) / 2;// 获取图片偏移量

                /*cursor的初始位置设定：
                思路 屏幕总宽减去LinerLayout宽度再除以2在加上offset就得到初始位置
                 */
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                int screenW = dm.widthPixels; // 获取手机屏幕宽度分辨率
                int initX = (screenW - linerLayoutW) / 2 + offset;
                //计算出初始位置后，设置控件的位置
                LinearLayout.MarginLayoutParams margin = new LinearLayout.MarginLayoutParams(cursorIv.getLayoutParams());
                margin.setMargins(initX, margin.topMargin, margin.rightMargin, margin.bottomMargin);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(margin);
                cursorIv.setLayoutParams(params);
            }
        });

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
        textViewChangeColor(0);
    }

    private void textViewChangeColor(int i) {
        switch (i) {
            case 0:
                booksheftTv.setTextColor(getResources().getColor(R.color.colorAccent));
                novelTv.setTextColor(getResources().getColor(R.color.defaultTextColor));
                cartoonTv.setTextColor(getResources().getColor(R.color.defaultTextColor));
                break;
            case 1:
                booksheftTv.setTextColor(getResources().getColor(R.color.defaultTextColor));
                novelTv.setTextColor(getResources().getColor(R.color.colorAccent));
                cartoonTv.setTextColor(getResources().getColor(R.color.defaultTextColor));
                break;
            case 2:
                booksheftTv.setTextColor(getResources().getColor(R.color.defaultTextColor));
                novelTv.setTextColor(getResources().getColor(R.color.defaultTextColor));
                cartoonTv.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.booksheft_tv:
                innerViewPager.setCurrentItem(0);
                break;
            case R.id.novel_tv:
                innerViewPager.setCurrentItem(1);
                break;
            case R.id.cartoon_tv:
                innerViewPager.setCurrentItem(2);
                break;
        }

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

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /*  计算出标签的偏移量
             标签偏移量大小为offset * 2 + ivWide
         */
        private int one = offset * 2 + ivWide;

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
            cursorIv.startAnimation(animation); // 是用imageview来显示动画
            int i = currIndex + 1;
            //改变textView的颜色
            textViewChangeColor(arg0);
        }
    }
}
