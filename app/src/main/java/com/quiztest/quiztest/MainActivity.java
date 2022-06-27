package com.quiztest.quiztest;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aemerse.onboard.OnboardAdvanced;
import com.quiztest.MyCustomOnboarder;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.fragment.HomeFragment;
import com.quiztest.quiztest.fragment.ProfileFragment;
import com.quiztest.quiztest.fragment.RankingFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.aemerse.onboard.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends OnboardAdvanced {

    private ArrayList<String> fragmentStates = new ArrayList<>();
    private FragmentTransaction ft;
    private ExtTextView btnHome, btnRanking, btnProfile;
    private ConstraintLayout ctsBottomNavigation;
    private BaseFragment fragmentHome = new HomeFragment();
    private BaseFragment fragmentRanking = new RankingFragment();
    private BaseFragment fragmentProfile = new ProfileFragment();
    private List<Fragment> fragments = new ArrayList<>();
    private List<ExtTextView> btns = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.transparentStatusAndNavigation(this);
        boolean onboarding = false;
        if (getIntent() != null && getIntent().getExtras() != null) {
            onboarding = getIntent().getExtras().getBoolean("onboarding", false);
        }
        if (!onboarding) {
            initOnboarding();
        }
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initOnboarding() {
        Intent intent = new Intent(this, MyCustomOnboarder.class);
        startActivity(intent);
        finish();
    }


    public void hideOrShowBottomView(boolean show) {
        if (show) {
            ctsBottomNavigation.setVisibility(View.VISIBLE);
        } else {
            ctsBottomNavigation.setVisibility(View.GONE);
        }
    }

    private void initData() {
        showFragment(fragmentHome);
        enableButton(btnHome);
        btnHome.setOnClickListener(v -> {
            if (fragmentHome.isAdded()) {
                showFragment(fragmentHome);
                enableButton(btnHome);
            }
        });
        btnRanking.setOnClickListener(v -> {
            if (fragmentRanking.isAdded()) {
                showFragment(fragmentRanking);
                enableButton(btnRanking);
            }
        });
        btnProfile.setOnClickListener(v -> {
            if (fragmentProfile.isAdded()) {
                showFragment(fragmentProfile);
                enableButton(btnProfile);
            }
        });
    }

    private void enableButton(ExtTextView btnEnable) {
        setTextViewDrawableColor(btnEnable, R.color.color_21C3FF);
        btnEnable.setTextColor(getResources().getColor(R.color.color_21C3FF));
        for (int i = 0; i < btns.size(); i++) {
            if (btns.get(i).getId() != btnEnable.getId()) {
                setTextViewDrawableColor(btns.get(i), R.color.color_8D959D);
                btns.get(i).setTextColor(getResources().getColor(R.color.color_8D959D));
            }
        }
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(textView.getContext(), color), PorterDuff.Mode.SRC_IN));
            }
        }
    }


    private void initView() {
        fragments.add(fragmentHome);
        fragments.add(fragmentRanking);
        fragments.add(fragmentProfile);

//        fragmentStates.add(HomeFragment.class.getSimpleName());
//        ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frContent, new HomeFragment());
//        ft.addToBackStack(null);
//        ft.commit();

        addFragment(fragmentHome);
        addFragment(fragmentRanking);
        addFragment(fragmentProfile);

        btnHome = findViewById(R.id.btn_home);
        btns.add(btnHome);
        btnRanking = findViewById(R.id.btn_ranking);
        btns.add(btnRanking);
        btnProfile = findViewById(R.id.btn_profile);
        btns.add(btnProfile);
        ctsBottomNavigation = findViewById(R.id.cts_bottomNavigation);
    }

    private void addFragment(BaseFragment fmAdd) {
        getFragmentManager().executePendingTransactions();
        if (!fmAdd.isAdded()) {
            if (fmAdd instanceof HomeFragment) {
                fragmentStates.add(HomeFragment.TAG);
            } else if (fmAdd instanceof RankingFragment) {
                fragmentStates.add(RankingFragment.TAG);
            } else if (fmAdd instanceof ProfileFragment) {
                fragmentStates.add(ProfileFragment.TAG);
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frContent, fmAdd);
            if (fmAdd instanceof HomeFragment) {
//                transaction.addToBackStack("Add Fragment");
            }
            transaction.commit();
        }
    }

    public void replaceFragment(Fragment fragment, String tag) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frContent, fragment);
        if (!fragmentStates.contains(tag))
            fragmentStates.add(tag);
        ft.addToBackStack(tag);
        ft.commit();
    }

    public boolean hasFragmentState(String tag) {
        return fragmentStates.contains(tag);
    }

    @Override
    public void onBackPressed() {
//        if (fragmentStates.contains(ChangePassSuccessFragment.TAG)) {
//            onBackStackChangePassSuccess();
//            return;
//        }
//        if (fragmentStates.contains(WelcomeFragment.TAG)) {
//            fragmentStates = new ArrayList<>();
//            replaceFragment(new SplashLoginFragment(), SplashLoginFragment.TAG);
//            return;
//        }
        if (fragmentStates.size() > 1) {
            fragmentStates.remove(fragmentStates.size() - 1);
            getSupportFragmentManager().popBackStack();
            return;
        } else {
//            setResult(MainActivity.REQUEST_CODE_BACK_LOGIN);
            finish();
        }

    }

    private void onBackStackChangePassSuccess() {
//        fragmentStates.remove(ChangePassSuccessFragment.TAG);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            if (fragment instanceof InputOtpFragment) {
//                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//                fragmentStates.remove(InputOtpFragment.TAG);
//                getSupportFragmentManager().popBackStack();
//            }
        }
    }

    private void showFragment(Fragment fmShow) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(fmShow);
        if (fmShow instanceof HomeFragment) {
//            transaction.addToBackStack("Show fragment");
        }
        getFragmentManager().executePendingTransactions();
        for (int i = 0; i < fragments.size(); i++) {
            // chỗ này đang k hiểu tại sao check isAdded false
//            if (fragments.get(i).isAdded()) {
            hideFragment(fragments.get(i));
//            }
        }
        transaction.commit();
    }

    private void hideFragment(Fragment fmHide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fmHide);
        if (fmHide instanceof HomeFragment) {
//            transaction.addToBackStack("Hide Fragment");
        }
        transaction.commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LOGGGGG", "LOGGGGG onDestroy NativeLogin");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LOGGGGG", "LOGGGGG onResume NativeLogin");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LOGGGGG", "LOGGGGG onStop NativeLogin");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOGGGGG", "LOGGGGG onPause NativeLogin");
    }
}
