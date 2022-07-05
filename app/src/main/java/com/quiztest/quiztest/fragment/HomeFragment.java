package com.quiztest.quiztest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testiq.MainIQActivity;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.custom.ItemViewEarningTask;
import com.quiztest.quiztest.fragment.login.LoginFragment;
import com.quiztest.quiztest.retrofit.RequestAPI;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private ExtTextView extLogin, ivGetMoreStar, ivGetMoreMoney;
    private ImageView ivSearch, ivNotify;
    private ItemViewEarningTask itemIQ, itemMBI, itemEQ;
    private RequestAPI requestAPI;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void initView(View v) {
        extLogin = v.findViewById(R.id.ext_login);
        ivGetMoreStar = v.findViewById(R.id.iv_get_more_star);
        ivGetMoreMoney = v.findViewById(R.id.iv_get_more_money);
        ivSearch = v.findViewById(R.id.iv_search);
        ivNotify = v.findViewById(R.id.iv_notify);
        itemIQ = v.findViewById(R.id.item_test_iq);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        extLogin.setOnClickListener(this);
        ivGetMoreStar.setOnClickListener(this);
        ivGetMoreMoney.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivNotify.setOnClickListener(this);
        itemIQ.setListener(() -> {
            Intent intent = new Intent(getContext(), MainIQActivity.class);
            intent.putExtra("token", SharePrefrenceUtils.getInstance(requireActivity()).getUserAccessToken());
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ext_login) {
            replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
            if ((MainActivity) getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
//            Intent intent = new Intent(getActivity(), MainIQActivity.class);
//            startActivity(intent);
        }
//        if (view.getId() == R.id.search_view) {
//            replaceFragment(new SearchFragment(), SearchFragment.class.getSimpleName());
//            if (getActivity() != null) {
//                ((MainActivity) getActivity()).hideOrShowBottomView(false);
//            }
//        }
        if (view.getId() == R.id.iv_get_more_money) {
            replaceFragment(new GetMoreChancesFragment(GetMoreChancesFragment.TYPE_GET_MORE_MONEY), GetMoreChancesFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
        if (view.getId() == R.id.iv_get_more_star) {
            replaceFragment(new GetMoreChancesFragment(GetMoreChancesFragment.TYPE_GET_MORE_STAR), GetMoreChancesFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
        if (view.getId() == R.id.iv_search) {
            replaceFragment(new SearchFragment(), SearchFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
        if (view.getId() == R.id.iv_notify) {
            replaceFragment(new NotificationFragment(), NotificationFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).hideOrShowBottomView(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
