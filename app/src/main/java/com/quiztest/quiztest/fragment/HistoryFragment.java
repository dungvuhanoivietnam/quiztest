package com.quiztest.quiztest.fragment;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.HistoryAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.viewmodel.UserViewModel;

public class HistoryFragment extends BaseFragment {

    private HistoryAdapter historyAdapter;
    private ExtTextView txt_title;
    private RecyclerView rcv_history;
    private UserViewModel userViewModel;

    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View v) {
        v.findViewById(R.id.txt_Left).setVisibility(View.GONE);
        v.findViewById(R.id.ic_right).setVisibility(View.GONE);
        v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        v.findViewById(R.id.iv_back).setOnClickListener(view -> backstackFragment());
        txt_title = v.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.VISIBLE);
        rcv_history = v.findViewById(R.id.rcv_history);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcv_history.setLayoutManager(mLayoutManager);
        historyAdapter = new HistoryAdapter(mContext, true);
        rcv_history.setAdapter(historyAdapter);
        rcv_history.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        userViewModel.getHistory(requestAPI, userViewModel.getPageHistory() + 1, o -> {
                            historyAdapter.setData(userViewModel.getHistories());
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        if (userViewModel.getHistories().size() > 0)
            historyAdapter.setData(userViewModel.getHistories());
        txt_title.setText(getString(R.string.history_result));
    }
}
