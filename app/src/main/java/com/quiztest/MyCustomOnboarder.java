package com.quiztest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aemerse.onboard.OnboardAdvanced;
import com.aemerse.onboard.OnboardFragment;

import com.aemerse.onboard.ScreenUtils;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;

public class MyCustomOnboarder extends OnboardAdvanced {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(OnboardFragment.newInstance("Alarm with sound", "Alarm with gentle sound, more comfortable than other apps",
                R.drawable.img_onboard_example,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.color_22313F),
                getResources().getColor(R.color.color_22313F)));
        addSlide(OnboardFragment.newInstance("Alarm with sound", "Alarm with gentle sound, more comfortable than other apps",
                R.drawable.img_onboard_example,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.color_22313F),
                getResources().getColor(R.color.color_22313F)));
        addSlide(OnboardFragment.newInstance("Alarm with sound", "Alarm with gentle sound, more comfortable than other apps",
                R.drawable.img_onboard_example,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.color_22313F),
                getResources().getColor(R.color.color_22313F)));

    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        Bundle args = new Bundle();
        args.putBoolean("onboarding", true);
        intent.putExtras(args);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        Bundle args = new Bundle();
        args.putBoolean("onboarding", true);
        intent.putExtras(args);
        startActivity(intent);
        finish();
    }
}
