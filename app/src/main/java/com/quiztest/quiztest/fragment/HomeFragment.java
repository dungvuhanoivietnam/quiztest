package com.quiztest.quiztest.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.testiq.MainIQActivity;
import com.bumptech.glide.Glide;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.EarningTasksAdapter;
import com.quiztest.quiztest.adapter.GetMoreStarsAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.fragment.login.LoginFragment;
import com.quiztest.quiztest.model.HomeDataResponse;
import com.quiztest.quiztest.model.TestItem;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.retrofit.RequestAPI;

import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private ExtTextView extLogin, ivGetMoreStar, ivGetMoreMoney, extName, extStarCount, extMoneyCount;
    private ImageView ivSearch, ivNotify, ivAvatar;
    private RecyclerView rcvGetMoreStar;
    private RecyclerView rcvEarningTask;

    private UserViewModel userViewModel;
    private ArrayList<TestItem> currentListEarningTasks;
    private ArrayList<TestItem> currentListGetMoreStars;
    private GetMoreStarsAdapter getMoreStarsAdapter;
    private EarningTasksAdapter earningTasksAdapter;
    private UserInfoResponse currentUser;


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
        ivAvatar = v.findViewById(R.id.iv_avatar);
        ivNotify = v.findViewById(R.id.iv_notify);
        extName = v.findViewById(R.id.ext_name);
        extStarCount = v.findViewById(R.id.ext_star_count);
        extMoneyCount = v.findViewById(R.id.ext_money_count);

        rcvGetMoreStar = v.findViewById(R.id.rcv_get_more_star);
        rcvEarningTask = v.findViewById(R.id.rcv_earning_task);
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
        getMoreStarsAdapter = new GetMoreStarsAdapter(mContext);
        earningTasksAdapter = new EarningTasksAdapter(mContext);
        if (getActivity() != null) {
            userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }
        initDataUser();
        if (userViewModel != null) {
            showLoading();
            userViewModel.getDataForHome(requestAPI, o -> {
                if (o instanceof HomeDataResponse) {
                    currentListEarningTasks = ((HomeDataResponse) o).getData().getListEarningTasks();
                    currentListGetMoreStars = ((HomeDataResponse) o).getData().getListGetMoreStars();
                    getMoreStarsAdapter.setListData(currentListGetMoreStars);
                    rcvGetMoreStar.setAdapter(getMoreStarsAdapter);

                    earningTasksAdapter.setListData(currentListEarningTasks);
                    rcvEarningTask.setAdapter(earningTasksAdapter);
                }
                cancelLoading();
            });
        }
    }

    private void initDataUser() {
        if (UserInfoResponse.getInstance() != null) {
            currentUser = UserInfoResponse.getInstance();
            setDataUserInfo(currentUser);
        } else if (!TextUtils.isEmpty(SharePrefrenceUtils.getInstance(mContext).getAuth())) {
            if (userViewModel != null) {
                showLoading();
                userViewModel.getUserInfo(requestAPI, o -> {
                    if (o instanceof UserInfoResponse) {
                        UserInfoResponse.setCurrentUser((UserInfoResponse) o);
                        currentUser = UserInfoResponse.getInstance();
                        setDataUserInfo(currentUser);
                    }
                });
            }
        } else {
            if (userViewModel != null) {
                showLoading();
                userViewModel.getGuestUserInfo(requestAPI, o -> {
                    if (o instanceof UserInfoResponse) {
                        UserInfoResponse.setCurrentUser((UserInfoResponse) o);
                        currentUser = UserInfoResponse.getInstance();
                        setDataUserInfo(currentUser);
                    }
                });
            }
        }
        cancelLoading();
    }

    private void setDataUserInfo(UserInfoResponse currentUser) {
        String avatar = currentUser.getData().getUserInfo().getAvatar();
        String name = currentUser.getData().getUserInfo().getName();
        int total_money = currentUser.getData().getUserInfo().getTotalMoney();
        int total_star = currentUser.getData().getUserInfo().getTotalStar();
        if (!TextUtils.isEmpty(avatar)) {
            Glide.with(mContext).load(avatar).circleCrop().into(ivAvatar);
        }
        extMoneyCount.setText(getString(R.string.value_, total_money));
        extStarCount.setText(getString(R.string.value_, total_star));
        extName.setText(name);
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
            addFragment(new GetMoreChancesFragment(GetMoreChancesFragment.TYPE_GET_MORE_MONEY, currentUser == null ?
                    0 : currentUser.getData().getUserInfo().getTotalMoney()), GetMoreChancesFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
        if (view.getId() == R.id.iv_get_more_star) {
            addFragment(new GetMoreChancesFragment(GetMoreChancesFragment.TYPE_GET_MORE_STAR), GetMoreChancesFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
        if (view.getId() == R.id.iv_search) {
            addFragment(new SearchFragment(), SearchFragment.class.getSimpleName());
            if (getActivity() != null) {
                ((MainActivity) getActivity()).hideOrShowBottomView(false);
            }
        }
        if (view.getId() == R.id.iv_notify) {
            addFragment(new NotificationFragment(), NotificationFragment.class.getSimpleName());
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
