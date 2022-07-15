package com.quiztest.quiztest.fragment;

import static com.quiztest.quiztest.utils.Const.DATA;
import static com.quiztest.quiztest.utils.Const.LANGUAGE;
import static com.quiztest.quiztest.utils.Const.TOKEN;
import static com.quiztest.quiztest.utils.Const.TYPE;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testiq.MainIQActivity;
import com.example.testiq.model.Event;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.EarningTasksAdapter;
import com.quiztest.quiztest.adapter.GetMoreStarsAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.custom.ItemViewEarningTask;
import com.quiztest.quiztest.fragment.login.LoginFragment;
import com.quiztest.quiztest.model.TestItem;
import com.quiztest.quiztest.model.TopicListResponse;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.retrofit.RequestAPI;
import com.quiztest.quiztest.retrofit.RetrofitClient;
import com.quiztest.quiztest.utils.Const;
import com.quiztest.quiztest.utils.LanguageConfig;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Retrofit;


public class HomeFragment extends BaseFragment implements View.OnClickListener, EarningTasksAdapter.ItemClickListener,
        GetMoreStarsAdapter.ItemClickListener {

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
    private String currentLanguage;
    private ArrayList<TestItem> moreStarTopic;
    private ArrayList<TestItem> earningTaskTopic;
    private ExtTextView tvNoDataMoreStar, tvNoDataEarning, tvMore;


    @Override
    protected void initView(View v) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        extLogin = v.findViewById(R.id.ext_login);
        tvNoDataMoreStar = v.findViewById(R.id.tvNoDataMoreStar);
        tvNoDataEarning = v.findViewById(R.id.tvNoDataEarning);
        ivGetMoreStar = v.findViewById(R.id.iv_get_more_star);
        ivGetMoreMoney = v.findViewById(R.id.iv_get_more_money);
        ivSearch = v.findViewById(R.id.iv_search);
        ivAvatar = v.findViewById(R.id.iv_avatar);
        ivNotify = v.findViewById(R.id.iv_notify);
        extName = v.findViewById(R.id.ext_name);
        extStarCount = v.findViewById(R.id.ext_star_count);
        extMoneyCount = v.findViewById(R.id.ext_money_count);
        tvMore = v.findViewById(R.id.tvMore);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        extLogin.setOnClickListener(this);
        ivGetMoreStar.setOnClickListener(this);
        tvMore.setOnClickListener(this);
        ivGetMoreMoney.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivNotify.setOnClickListener(this);
        currentLanguage = LanguageConfig.INSTANCE.getCurrentLanguage();

        getMoreStarsAdapter = new GetMoreStarsAdapter(mContext);
        earningTasksAdapter = new EarningTasksAdapter(mContext);
        if (getActivity() != null) {
            userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }
//        getMoreStarsAdapter.setListener(item -> {
//            startActTestIQ("", item);
//        });
        initDataUser();
        initDataTest();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDataTest() {
        if (userViewModel != null) {
            showLoading();
            getTopicByType(GetMoreChancesFragment.TYPE_GET_MORE_MONEY);
            getTopicByType(GetMoreChancesFragment.TYPE_GET_MORE_STAR);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getTopicByType(int current_type) {
        getMoreStarsAdapter = new GetMoreStarsAdapter(mContext);
        earningTasksAdapter = new EarningTasksAdapter(mContext);
        getMoreStarsAdapter.setItemClickListener(this);
        userViewModel.getTopicListByType(requestAPI, current_type, o -> {
            if (o instanceof TopicListResponse) {
                moreStarTopic = ((TopicListResponse) o).getData().getListTopicByType();
                earningTaskTopic = ((TopicListResponse) o).getData().getListTopicByType();
                if (current_type == GetMoreChancesFragment.TYPE_GET_MORE_STAR) {
                    if (moreStarTopic.isEmpty()) {
                        rcvGetMoreStar.setVisibility(View.GONE);
                        tvNoDataMoreStar.setVisibility(View.VISIBLE);
                    } else {
                        List<TestItem> listSorted = moreStarTopic.stream().limit(4).sorted(Comparator.comparing(TestItem::getTitle)).
                                collect(Collectors.toList());
                        rcvGetMoreStar.setVisibility(View.VISIBLE);
                        tvNoDataMoreStar.setVisibility(View.GONE);

                        if (moreStarTopic.size() > 4) {
                            tvMore.setVisibility(View.VISIBLE);
                        } else {
                            tvMore.setVisibility(View.GONE);
                        }

                        getMoreStarsAdapter.setListData((ArrayList<TestItem>) listSorted);
                        rcvGetMoreStar.setAdapter(getMoreStarsAdapter);
                    }

                } else {
                    if (earningTaskTopic.isEmpty()) {
                        rcvEarningTask.setVisibility(View.GONE);
                        tvNoDataEarning.setVisibility(View.VISIBLE);
                    } else {
                        rcvEarningTask.setVisibility(View.VISIBLE);
                        tvNoDataEarning.setVisibility(View.GONE);

                        List<TestItem> listSorted = earningTaskTopic.stream().sorted(Comparator.comparing(TestItem::getTitle)).
                                collect(Collectors.toList());

                        earningTasksAdapter.setListData((ArrayList<TestItem>) listSorted);
                        earningTasksAdapter.setItemClickListener(this);
                        rcvEarningTask.setAdapter(earningTasksAdapter);
                    }

                }

            }
            cancelLoading();
        });
    }

    private void startActTestIQ(String type, TestItem testItem) {
        Intent intent = new Intent(getContext(), MainIQActivity.class);
        intent.putExtra(TOKEN, SharePrefrenceUtils.getInstance(mContext).getAuth());
        intent.putExtra(TYPE, type);
        intent.putExtra(LANGUAGE, currentLanguage);
        intent.putExtra(Const.TEST_ID, testItem.getId());
        intent.putExtra(Const.TEST_TYPE, testItem.getType().toString());
        intent.putExtra(DATA, testItem.getMoneyBonus() + "," + testItem.getFeeStar());
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
            Glide.with(mContext).load(avatar)
                    .circleCrop()
                    .placeholder(R.drawable.ic_create_account_profile)
                    .error(R.drawable.ic_create_account_profile)
                    .into(ivAvatar);
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
        if (view.getId() == R.id.iv_get_more_star || view.getId() == R.id.tvMore) {
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
    public void onItemClickListener(TestItem item) {
        startActTestIQ("", item);
    }

    @Override
    public void onItemLongClickListener(int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void resetData() {
        Retrofit retrofit = RetrofitClient.getInstance();
        requestAPI = retrofit.create(RequestAPI.class);
        initDataUser();
        initDataTest();
    }

}
