package elecat.com.viewpagertabdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import elecat.com.viewpagertabdemo.R;

/**
 * Created by liangminhua on 16/5/8.
 */
public class InnerCartoonFragment extends Fragment {
    @BindView(R.id.cartoon_tv)
    TextView cartoonTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartoon_inner, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cartoonTv.setText("漫画");
    }
}
