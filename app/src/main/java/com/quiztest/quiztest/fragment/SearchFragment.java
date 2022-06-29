package com.quiztest.quiztest.fragment;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;

public class SearchFragment extends BaseFragment {
    private ImageView ivBack;
    private SearchView searchView;
    private LinearLayout llContainer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View v) {
        ivBack = v.findViewById(R.id.iv_back);
        searchView = v.findViewById(R.id.search_view);
        llContainer = v.findViewById(R.id.ll_container);
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(v -> backstackFragment());
        searchView.requestFocus();

        llContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (((MainActivity)getActivity()).isKeyBoardVisible()){
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getRootView().getWindowToken(), 0);
                }
                return false;
            }
        });
    }
}
