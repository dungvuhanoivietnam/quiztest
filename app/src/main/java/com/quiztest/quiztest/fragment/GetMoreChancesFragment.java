package com.quiztest.quiztest.fragment;

import static com.quiztest.quiztest.utils.Const.DATA;
import static com.quiztest.quiztest.utils.Const.LANGUAGE;
import static com.quiztest.quiztest.utils.Const.TOKEN;
import static com.quiztest.quiztest.utils.Const.TYPE;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testiq.MainIQActivity;
import com.example.testiq.ui.DialogResultCallApi;
import com.example.testiq.utils.Status;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.GetMoreStarsAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.dialog.DialogWithdrawMoney;
import com.quiztest.quiztest.model.AuthResponse;
import com.quiztest.quiztest.model.TestItem;
import com.quiztest.quiztest.model.TopicListResponse;
import com.quiztest.quiztest.utils.LanguageConfig;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.model.WithDrawResponse;
import com.quiztest.quiztest.viewmodel.UserViewModel;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class GetMoreChancesFragment extends BaseFragment implements View.OnClickListener, DialogWithdrawMoney.OnWithDrawClickLisenter, GetMoreStarsAdapter.ItemClickListener {
    public static final int TYPE_GET_MORE_STAR = 1;
    public static final int TYPE_GET_MORE_MONEY = 2;

    private ExtTextView extTitleActionBar, extWithdrawMoney, extMoneyCount;
    private RelativeLayout rlWithdrawMoney;
    private RecyclerView rcvGetMoreChance;
    private GetMoreStarsAdapter getMoreStarsAdapter;
    private ImageView ivBack;

    private UserViewModel userViewModel;
    private ArrayList<TestItem> currentListTopic;
    private int current_type;
    private int totalMoney;
    private String currentLanguage;

    public GetMoreChancesFragment(int type_get_more) {
        current_type = type_get_more;
    }

    public GetMoreChancesFragment(int type_get_more, int totalMoney) {
        current_type = type_get_more;
        this.totalMoney = totalMoney;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_get_more_chances;
    }

    @Override
    protected void initView(View v) {
        extTitleActionBar = v.findViewById(R.id.ext_title_action_bar);
        rlWithdrawMoney = v.findViewById(R.id.rl_withdraw_money);
        extWithdrawMoney = v.findViewById(R.id.ext_withdraw_money);
        extMoneyCount = v.findViewById(R.id.ext_money_count);

        rcvGetMoreChance = v.findViewById(R.id.rcv_get_more_chance);
        ivBack = v.findViewById(R.id.iv_back);
    }

    @Override
    protected void initData() {

        currentLanguage = LanguageConfig.INSTANCE.getCurrentLanguage();

        ivBack.setOnClickListener(v -> {
            backstackFragment();
        });
        extWithdrawMoney.setOnClickListener(this);
        setLayoutType(current_type);
        if (getActivity() != null) {
            userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        }
        if (userViewModel != null) {
            getTopicByType(current_type);
        }

        extMoneyCount.setText(getString(R.string.value_, totalMoney));
    }

    private void getTopicByType(int current_type) {
        showLoading();
        getMoreStarsAdapter = new GetMoreStarsAdapter(mContext);
        userViewModel.getTopicListByType(requestAPI, current_type, o -> {
            if (o instanceof TopicListResponse) {
                currentListTopic = ((TopicListResponse) o).getData().getListTopicByType();
                getMoreStarsAdapter.setListData(currentListTopic);
                getMoreStarsAdapter.setItemClickListener(this);
                rcvGetMoreChance.setAdapter(getMoreStarsAdapter);
            }
            cancelLoading();
        });
    }

    private void setLayoutType(int current_type) {
        if (current_type == TYPE_GET_MORE_STAR) {
            extTitleActionBar.setText(R.string.get_more_star_chances);
            rlWithdrawMoney.setVisibility(View.GONE);
        } else if (current_type == TYPE_GET_MORE_MONEY) {
            extTitleActionBar.setText(R.string.get_more_money_chances);
            rlWithdrawMoney.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ext_withdraw_money) {
            DialogWithdrawMoney dialogWithdrawMoney = new DialogWithdrawMoney(getContext(), R.style.MaterialDialogSheet, this);
            dialogWithdrawMoney.setTotalMoney(totalMoney);
            dialogWithdrawMoney.show();
        }
    }

    @Override
    public void onWidthDrawClick(String email, int money) {
        if (userViewModel != null) {
            showLoading();
            userViewModel.requestWithDraw(requestAPI, email, money, o -> {
                if (o instanceof WithDrawResponse) {
                    if (getContext() != null) {
                        if (Boolean.TRUE.equals(((WithDrawResponse) o).getSuccess())) {
                            new DialogResultCallApi(getContext(), Status.SUCCESS, ((WithDrawResponse) o).getMessage(), () -> null).show();
                        } else {
                            DialogResultCallApi dialogResultCallApi = new DialogResultCallApi(getContext(), Status.ERROR,
                                    ((WithDrawResponse) o).getMessage(), () -> null);
                            dialogResultCallApi.setTextAndIcon(getString(R.string.minimum_money), R.drawable.ic_error);
                        }
                    }
                } else {
                    if (getContext() != null) {
                        DialogResultCallApi dialogResultCallApi = new DialogResultCallApi(getContext(), Status.ERROR,
                                getString(R.string.error_message_default), () -> null);
                        dialogResultCallApi.setTextAndIcon(getString(R.string.error), R.drawable.ic_error);
                        dialogResultCallApi.show();
                    }
                }
                cancelLoading();
            });
        }
    }

    @Override
    public void onItemClickListener(TestItem item) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).startActTestIQ("", item);
        }
    }

    @Override
    public void onItemLongClickListener(int position) {

    }
}
