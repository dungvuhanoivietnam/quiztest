package com.quiztest.quiztest.fragment;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.testiq.MainIQActivity;
import com.example.testiq.model.Event;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testiq.MainIQActivity;
import com.example.testiq.model.Event;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.EarningTasksAdapter;
import com.quiztest.quiztest.adapter.GetMoreStarsAdapter;
import com.quiztest.quiztest.adapter.RankingAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.custom.ItemViewEarningTask;
import com.quiztest.quiztest.fragment.login.LoginFragment;
import com.quiztest.quiztest.model.HomeDataResponse;
import com.quiztest.quiztest.model.TestItem;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.quiztest.quiztest.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends BaseFragment implements View.OnClickListener, RankingAdapter.ItemClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private ExtTextView extLogin, ivGetMoreStar, ivGetMoreMoney, extName, extStarCount, extMoneyCount;
    private ImageView ivSearch, ivNotify, ivAvatar;
    private RecyclerView rcvGetMoreStar;
    private RecyclerView rcvEarningTask;
    private ItemViewEarningTask itemIQ, itemMBI, itemEQ, itemOT;

    private UserViewModel userViewModel;
    private ArrayList<TestItem> currentListEarningTasks;
    private ArrayList<TestItem> currentListGetMoreStars;
    private GetMoreStarsAdapter getMoreStarsAdapter;
    private EarningTasksAdapter earningTasksAdapter;
    private UserInfoResponse currentUser;


    @Override
    protected void initView(View v) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
        itemIQ = v.findViewById(R.id.item_test_iq);
        itemEQ = v.findViewById(R.id.item_test_eq);
        itemMBI = v.findViewById(R.id.item_test_mbi);
        itemOT = v.findViewById(R.id.item_test_other);
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
        initDataTest();

    }

    private void initDataTest() {
        if (userViewModel != null) {
            showLoading();
            userViewModel.getDataForHome(requestAPI, o -> {
                if (o instanceof HomeDataResponse) {
                    currentListEarningTasks = ((HomeDataResponse) o).getData().getListEarningTasks();
                    currentListGetMoreStars = ((HomeDataResponse) o).getData().getListGetMoreStars();
                    getMoreStarsAdapter.setListData(currentListGetMoreStars);
                    rcvGetMoreStar.setAdapter(getMoreStarsAdapter);

                    earningTasksAdapter.setListData(currentListEarningTasks);
                    earningTasksAdapter.setItemClickListener(this);
                    rcvEarningTask.setAdapter(earningTasksAdapter);
                }
                cancelLoading();
            });
        }
    }

    private void startActTestIQ(String type, TestItem testItem){
        Intent intent = new Intent(getContext(), MainIQActivity.class);
        intent.putExtra("token", SharePrefrenceUtils.getInstance(requireActivity()).getUserAccessToken());
        intent.putExtra("type", type);
        intent.putExtra("data", testItem.getMoneyBonus() + "," + testItem.getFeeStar());
        startActivity(intent);
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
                    cancelLoading();
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
                    cancelLoading();
                });
            }
        }
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event != null) {
            new Handler().postDelayed(() -> {
                replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName());
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).hideOrShowBottomView(false);
                }
            }, 500);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClickListener(int position) {
        startActTestIQ("",currentListEarningTasks.get(position));
    }

    @Override
    public void onItemLongClickListener(int position) {

    }
    public void resetData(){
        initDataUser();
        initDataTest();
    }

}
