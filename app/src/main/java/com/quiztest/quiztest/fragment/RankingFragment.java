package com.quiztest.quiztest.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.RankingAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.model.RankerResponse;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.model.UserRankingResponse;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import java.util.ArrayList;

public class RankingFragment extends BaseFragment implements View.OnClickListener, RankingAdapter.ItemClickListener {

    public static final String TAG = RankingFragment.class.getSimpleName();

    private RankingAdapter rankingAdapter;
    private RecyclerView recyclerViewRanking;
    private ExtTextView extWeeklyRanking, extOverallRating, extName, extStars, extRanking;
    private ImageView ivAvatar;
    private LinearLayout llEmptyView;

    private UserViewModel userViewModel;
    private UserInfoResponse currentUser;
    private ArrayList<UserRankingResponse.UserRanking> listUserWeeklyRanking = new ArrayList<>();
    private ArrayList<UserRankingResponse.UserRanking> listUserAllTimeRanking = new ArrayList<>();

    private final int ButtonWeeklyRanking = 1;
    private final int ButtonOverallRating = 2;

    private final String AllTimeRanking = "all";
    private final String WeekRanking = "week";
    private int currentWeeklyRanking = 0;
    private int currentAllTimeRanking = 0;
    private String currentType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    protected void initView(View v) {
        extWeeklyRanking = v.findViewById(R.id.ext_weekly_ranking);
        extOverallRating = v.findViewById(R.id.ext_overall_rating);
        recyclerViewRanking = v.findViewById(R.id.rcv_ranking);
        extName = v.findViewById(R.id.ext_name);
        extStars = v.findViewById(R.id.ext_stars);
        ivAvatar = v.findViewById(R.id.iv_avatar);
        extRanking = v.findViewById(R.id.ext_ranking);
        llEmptyView = v.findViewById(R.id.ll_empty_view);
    }

    @Override
    protected void initData() {
        if (getActivity() != null) {
            userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }
        extWeeklyRanking.setOnClickListener(this);
        extOverallRating.setOnClickListener(this);
        rankingAdapter = new RankingAdapter(getContext());
        rankingAdapter.setItemClickListener(this);
        setDataUser();
        setDataRanking();
    }

    private void setDataUser() {
        if (UserInfoResponse.getInstance() != null && getContext() != null) {
            currentUser = UserInfoResponse.getInstance();
            UserInfoResponse.UserInfo userInfo = currentUser.getData().getUserInfo();
            if (userInfo != null) {
                extName.setText(userInfo.getName());
                extStars.setText(getString(R.string.stars, userInfo.getTotalStar()));
                String avatar = userInfo.getAvatar();
                if (!TextUtils.isEmpty(avatar)) {
                    Glide.with(getContext()).load(avatar)
                            .placeholder(R.drawable.ic_create_account_profile)
                            .error(R.drawable.ic_create_account_profile)
                            .circleCrop().into(ivAvatar);
                }
                if (userInfo.getId() == 0) {
                    showEmptyView(true);
                }else {
                    showEmptyView(false);
                }
            }
        } else {
            showEmptyView(true);
        }
    }

    private void showEmptyView(boolean show) {
        if (show) {
            llEmptyView.setVisibility(View.VISIBLE);
            recyclerViewRanking.setVisibility(View.GONE);
        } else {
            llEmptyView.setVisibility(View.GONE);
            recyclerViewRanking.setVisibility(View.VISIBLE);
        }
    }

    private void setDataRanking() {
        if (userViewModel != null) {
            showLoading();
            userViewModel.getUserRankingByType(requestAPI, WeekRanking, o -> {
                if (o instanceof UserRankingResponse) {
                    listUserWeeklyRanking = ((UserRankingResponse) o).getData();
                    rankingAdapter.setListData(listUserWeeklyRanking);
                    if (UserInfoResponse.getInstance() != null) {
                        for (int i = 0; i < listUserWeeklyRanking.size(); i++) {
                            if (listUserWeeklyRanking.get(i).getId() == UserInfoResponse.getInstance().getData().getUserInfo().getId()) {
                                currentWeeklyRanking = i + 1;
                                break;
                            }
                        }
                    }
                    setRanking(currentWeeklyRanking);
                    rankingAdapter.notifyDataSetChanged();
                }
                cancelLoading();
            });

            userViewModel.getUserRankingByType(requestAPI, AllTimeRanking, o -> {
                if (o instanceof UserRankingResponse) {
                    listUserAllTimeRanking = ((UserRankingResponse) o).getData();
                    if (UserInfoResponse.getInstance() != null) {
                        for (int i = 0; i < listUserAllTimeRanking.size(); i++) {
                            if (listUserAllTimeRanking.get(i).getId() == UserInfoResponse.getInstance().getData().getUserInfo().getId()) {
                                currentAllTimeRanking = i + 1;
                                break;
                            }
                        }
                    }
                }
                cancelLoading();
            });
        }
        recyclerViewRanking.setAdapter(rankingAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ext_weekly_ranking) {
            if (rankingAdapter != null) {
                setRanking(currentWeeklyRanking);
                rankingAdapter.setListData(listUserWeeklyRanking);
                rankingAdapter.notifyDataSetChanged();
            }
            setButtonOptionEnable(ButtonWeeklyRanking);
        }
        if (view.getId() == R.id.ext_overall_rating) {
            if (rankingAdapter != null) {
                setRanking(currentAllTimeRanking);
                rankingAdapter.setListData(listUserAllTimeRanking);
                rankingAdapter.notifyDataSetChanged();
            }
            setButtonOptionEnable(ButtonOverallRating);
        }
    }

    private void setRanking(int currentWeeklyRanking) {
        if (currentWeeklyRanking > 0) {
            extRanking.setText(getString(R.string.top_d, currentWeeklyRanking));
        } else {
            extRanking.setText(getString(R.string.not_update));
        }
    }

    private void setButtonOptionEnable(int btnEnable) {
        if (btnEnable == ButtonWeeklyRanking) {
            setButtonUI(extWeeklyRanking, true);
            setButtonUI(extOverallRating, false);
        } else if (btnEnable == ButtonOverallRating) {
            setButtonUI(extWeeklyRanking, false);
            setButtonUI(extOverallRating, true);
        }
    }

    private void setButtonUI(ExtTextView extTextView, boolean enable) {
        if (getActivity() != null) {
            if (enable) {
                extTextView.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.color_21C3FF));
                extTextView.setElevation(1f);
                extTextView.setTextColor(getResources().getColor(R.color.white));
            } else {
                extTextView.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                extTextView.setElevation(0f);
                extTextView.setTextColor(getResources().getColor(R.color.color_8D959D));
            }
        }
    }

    @Override
    public void onItemClickListener(int position) {
        Toast.makeText(getContext(), position + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClickListener(int position) {

    }
}
