package com.quiztest.quiztest.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.SearchTopicAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.TestItem;
import com.quiztest.quiztest.model.TopicListResponse;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import java.util.ArrayList;

public class SearchFragment extends BaseFragment {
    private ImageView ivBack;
    private SearchView searchView;
    private LinearLayout llContainer;
    private RecyclerView rcvSearchResult;
    private RelativeLayout rlSearchResult;
    private LinearLayout llEmptyView;
    private ExtTextView tvSearchResult;

    private SearchTopicAdapter searchTopicAdapter;
    private ArrayList<TestItem> currentSearchResult;
    private UserViewModel userViewModel;
    final Handler handlerSearch = new Handler(Looper.getMainLooper());


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initView(View v) {
        ivBack = v.findViewById(R.id.iv_back);
        searchView = v.findViewById(R.id.search_view);
        llContainer = v.findViewById(R.id.ll_container);
        rcvSearchResult = v.findViewById(R.id.rcv_search_result);
        rlSearchResult = v.findViewById(R.id.rl_search_result);
        llEmptyView = v.findViewById(R.id.ll_empty_view);
        tvSearchResult = v.findViewById(R.id.tv_search_result);
    }

    @Override
    protected void initData() {
        ivBack.setOnClickListener(v -> backstackFragment());
        searchView.requestFocus();
        if (getContext() != null) {
            searchTopicAdapter = new SearchTopicAdapter(getContext());
            rcvSearchResult.setAdapter(searchTopicAdapter);
        }
        if (getActivity() != null) {
            userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }
        llContainer.setOnTouchListener((view, motionEvent) -> {
            if (((MainActivity) getActivity()).isKeyBoardVisible()) {
                ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getRootView().getWindowToken(), 0);
            }
            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                handlerSearch.removeCallbacksAndMessages(null);
                search(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                handlerSearch.removeCallbacksAndMessages(null);
                handlerSearch.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        search(s);
                    }
                }, 1500);
                return false;
            }
        });
    }

    private void search(String keyword) {
        if (userViewModel != null) {
            showLoading();
            userViewModel.searchTopics(requestAPI, keyword, o -> {
                if (o instanceof TopicListResponse) {
                    currentSearchResult = ((TopicListResponse) o).getData().getListTopicByType();
                    if (currentSearchResult != null && currentSearchResult.size() > 0) {
                        if (searchTopicAdapter != null) {
                            tvSearchResult.setText(getString(R.string.result_d, currentSearchResult.size()));
                            searchTopicAdapter.setListData(currentSearchResult);
                            searchTopicAdapter.notifyDataSetChanged();
                            showEmptyLayout(false);
                        }
                    } else {
                        showEmptyLayout(true);
                    }
                } else {
                    showEmptyLayout(true);
                }
                cancelLoading();
                if (((MainActivity) getActivity()).isKeyBoardVisible()) {
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getRootView().getWindowToken(), 0);
                }
            });
        }
    }

    private void showEmptyLayout(boolean b) {
        if (b) {
            rlSearchResult.setVisibility(View.GONE);
            llEmptyView.setVisibility(View.VISIBLE);
        } else {
            rlSearchResult.setVisibility(View.VISIBLE);
            llEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerSearch.removeCallbacksAndMessages(null);
    }
}
