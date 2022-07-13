package com.quiztest.quiztest;

import static com.quiztest.quiztest.utils.Const.BEARER;
import static com.quiztest.quiztest.utils.Const.DATA;
import static com.quiztest.quiztest.utils.Const.TEST_ID;
import static com.quiztest.quiztest.utils.Const.TOKEN;
import static com.quiztest.quiztest.utils.Const.TYPE;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.testiq.MainIQActivity;
import com.quiztest.MyCustomOnboarder;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.callback.ActivityResultFragment;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.fragment.HomeFragment;
import com.quiztest.quiztest.fragment.ProfileFragment;
import com.quiztest.quiztest.fragment.RankingFragment;
import com.quiztest.quiztest.fragment.SettingFragment;
import com.quiztest.quiztest.model.TestItem;
import com.quiztest.quiztest.utils.Const;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.model.ChangeLanguageResponse;
import com.quiztest.quiztest.retrofit.RequestAPI;
import com.quiztest.quiztest.retrofit.RetrofitClient;
import com.quiztest.quiztest.utils.LanguageConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> fragmentStates = new ArrayList<>();
    private FragmentTransaction ft;
    private ExtTextView btnHome, btnRanking, btnProfile;
    private ConstraintLayout ctsBottomNavigation;
    private BaseFragment fragmentHome = new HomeFragment();
    private BaseFragment fragmentRanking;
    private BaseFragment fragmentProfile;
    private List<Fragment> fragments = new ArrayList<>();
    private List<ExtTextView> btns = new ArrayList<>();

    private boolean isKeyboardVisible;
    private ActivityResultFragment activityResultFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ScreenUtils.transparentStatusAndNavigation(this);
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
        setKeyboardVisibilityListener();
    }

    private void setKeyboardVisibilityListener() {
        final View parentView = ((ViewGroup) findViewById(R.id.cst_container)).getChildAt(0);
        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private boolean alreadyOpen;
            private final int defaultKeyboardHeightDP = 100;
            private final int EstimatedKeyboardDP = defaultKeyboardHeightDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect rect = new Rect();

            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, parentView.getResources().getDisplayMetrics());
                parentView.getWindowVisibleDisplayFrame(rect);
                int heightDiff = parentView.getRootView().getHeight() - (rect.bottom - rect.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;

                if (isShown == alreadyOpen) {
                    Log.i("Keyboard state", "Ignoring global layout change...");
                    return;
                }
                alreadyOpen = isShown;
                onKeyBoardChange(isShown);
            }
        });
    }

    private void onKeyBoardChange(boolean isShow) {
        if (isShow) {
//            hideOrShowBottomView(false);
            isKeyboardVisible = true;
        } else {
            isKeyboardVisible = false;
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    hideOrShowBottomView(true);
                }
            }, 100);
        }
    }

    public boolean isKeyBoardVisible() {
        return isKeyboardVisible;
    }

//    public static void hideKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//    }

    private void initOnboarding() {
        Intent intent = new Intent(this, MyCustomOnboarder.class);
        startActivity(intent);
        finish();
    }


    public void hideOrShowBottomView(boolean show) {
        if (ctsBottomNavigation != null) {
            if (show) {
                if (ctsBottomNavigation.getVisibility() != View.VISIBLE)
                    ctsBottomNavigation.setVisibility(View.VISIBLE);
            } else {
                ctsBottomNavigation.setVisibility(View.GONE);
            }
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
            if (fragmentRanking == null) {
                fragmentRanking = new RankingFragment();
            }
            if (!fragments.contains(fragmentRanking)) {
                fragments.add(fragmentRanking);
                addFragment(fragmentRanking, fragmentRanking.getClass().getSimpleName());
            }
            showFragment(fragmentRanking);
            enableButton(btnRanking);
        });
        btnProfile.setOnClickListener(v -> {
            if (fragmentProfile == null) {
                fragmentProfile = new ProfileFragment();
            }
            if (!fragments.contains(fragmentProfile)) {
                fragments.add(fragmentProfile);
                addFragment(fragmentProfile, fragmentProfile.getClass().getSimpleName());
            }
            showFragment(fragmentProfile);
            enableButton(btnProfile);

        });
//        SharePrefrenceUtils.getInstance(this).saveAuth("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Njk5ZjAxMS1iYmY4LTRlNjUtOWMyOS1kNTQ0NWM3MWRmMjYiLCJqdGkiOiI2NTdmMjE3NmI0YmY5OWQyNzBmM2NkNzQxNzNlNDhlNWVmNGE1ZTNkOTdmMGQ5OTJkNTBhYmE5ZDlhMDk3ODRhMTFjOTM4OTk0MjViYWI5MiIsImlhdCI6MTY1NzE5Mjc5NS40NjU3MDEsIm5iZiI6MTY1NzE5Mjc5NS40NjU3MDQsImV4cCI6MTgxNDk1OTE5NS40NjAyODgsInN1YiI6IjEiLCJzY29wZXMiOltdfQ.m1oqyNO1p3eLFFJPe3ehyZOh1z8XbWNLwdYUNmwlA5A4T9HtrkV9p4huYvJQ524SOplOAn3bNDzCIvem9OPDAPPYKzYesdXflxOfuKFWbk3gWjNebKRYt0sCeIGVWIdDqfN211Eiiz_P8mKwTH73uPcUD9ub_-34XqDkQGizincWYYdnLlyrzAQ4rRhu6puwoSWG52fe6Srkbb54nT0IHFLN9i6x-MYV6yEL98kdk14iHxRxNme22kDG9Y92WwTQ_s6rAzyBjJs-xczHcn0uuHBpYDyEqmXYtWVzbqIbXuJJpZcTmhj3aAYewSYwd0RdTx5mg87-EsS59CGuseO8ZjmS6SzGV4CQCj1QB5gfCyFN5nqa8vnZdAzUAI2V6XAQjcHV7aM17e5BNNBGZCgMwhIPpFmaa48WOmRTVRR7pXNRgRHy00U8F5j-CmtibgPcodj0uEWusDo47y7HdB0nzeUWYJmZwjI2-B_9X8BdePXICWUSkUSL9Imy3gcub9rcaYerz6GatG3-hcHTmvdACG18T1I6eDqGdCs3rsdycT5LUCIXt3CdQyxCaxvPLgjL7USvv6W2sh249qnT2Wg-JDx8fReyAwTAWzaXxoVfVJGn9lcANi0TYcklE5DZVmbjUMI0CIVwSUg0DIqdYnjUT43viCnjt6W96oYKHe1OWVI");
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
//        fragments.add(fragmentRanking);
//        fragments.add(fragmentProfile);

//        fragmentStates.add(HomeFragment.class.getSimpleName());
//        ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.frContent, new HomeFragment());
//        ft.addToBackStack(null);
//        ft.commit();

        addFragment(fragmentHome, fragmentHome.getClass().getSimpleName());
//        addFragment(fragmentRanking);
//        addFragment(fragmentProfile);

        btnHome = findViewById(R.id.btn_home);
        btns.add(btnHome);
        btnRanking = findViewById(R.id.btn_ranking);
        btns.add(btnRanking);
        btnProfile = findViewById(R.id.btn_profile);
        btns.add(btnProfile);
        ctsBottomNavigation = findViewById(R.id.cts_bottomNavigation);
    }

    public void actionLogout() {
        showFragment(fragmentHome);
        enableButton(btnHome);
        ((HomeFragment) fragmentHome).resetData();
    }

    private void addFragment(BaseFragment fmAdd, String tag) {
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
        hideOrShowBottomView(tag.contains(HomeFragment.class.getSimpleName()) || tag.contains(ProfileFragment.class.getSimpleName()) || tag.contains(RankingFragment.class.getSimpleName()));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        if (tag.contains(SettingFragment.class.getSimpleName())) {
            activityResultFragment = (SettingFragment) fragment;
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frContent, fragment);
        if (!fragmentStates.contains(tag))
            fragmentStates.add(tag);
        ft.addToBackStack(tag);
        ft.commit();
        hideOrShowBottomView(tag.contains(HomeFragment.class.getSimpleName()) || tag.contains(ProfileFragment.class.getSimpleName()) || tag.contains(RankingFragment.class.getSimpleName()));
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
            String tagg = fragmentStates.get(fragmentStates.size() - 1);
            hideOrShowBottomView(tagg.contains(HomeFragment.class.getSimpleName()) || tagg.contains(ProfileFragment.class.getSimpleName()) || tagg.contains(RankingFragment.class.getSimpleName()));
            return;
        } else {
//            setResult(MainActivity.REQUEST_CODE_BACK_LOGIN);
            finish();
        }

    }

    public void startActTestIQ(String type, TestItem testItem) {
        Intent intent = new Intent(this, MainIQActivity.class);
        intent.putExtra(TOKEN, BEARER + SharePrefrenceUtils.getInstance(this).getAuth());
        intent.putExtra(TYPE, type);
        intent.putExtra(TEST_ID, testItem.getId());
        intent.putExtra(Const.TEST_TYPE, testItem.getType().toString());
        intent.putExtra(DATA, testItem.getMoneyBonus() + "," + testItem.getFeeStar());
        startActivity(intent);
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
        updateLanguage();
    }

    /**
     * Post current language of device to server
     */
    public void updateLanguage() {
        String language = LanguageConfig.INSTANCE.getCurrentLanguage();
        RetrofitClient.getInstance().create(RequestAPI.class).changeLanguage(language).enqueue(new Callback<ChangeLanguageResponse>() {
            @Override
            public void onResponse(Call<ChangeLanguageResponse> call, Response<ChangeLanguageResponse> response) {

            }

            @Override
            public void onFailure(Call<ChangeLanguageResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (activityResultFragment != null) {
            activityResultFragment.result(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        activityResultFragment.result(requestCode, permissions, grantResults);
    }

    public void addFragment(Fragment fragment, String tag) {
        if (tag.contains(SettingFragment.class.getSimpleName())) {
            activityResultFragment = (SettingFragment) fragment;
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frContent, fragment);
        if (!fragmentStates.contains(tag))
            fragmentStates.add(tag);
        ft.addToBackStack(tag);
        ft.commit();
        hideOrShowBottomView(tag.contains(HomeFragment.class.getSimpleName()) || tag.contains(ProfileFragment.class.getSimpleName()) || tag.contains(RankingFragment.class.getSimpleName()));
    }
}
