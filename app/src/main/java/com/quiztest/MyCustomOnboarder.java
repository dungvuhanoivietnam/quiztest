package com.quiztest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aemerse.onboard.OnboardAdvanced;
import com.aemerse.onboard.OnboardFragment;

import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;

public class MyCustomOnboarder extends OnboardAdvanced {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(OnboardFragment.newInstance("Welcome...", "This is first slice",
                R.drawable.img_onboard_example,
                R.drawable.img_onboard_example,
                getResources().getColor(R.color.purple_200),
                getResources().getColor(R.color.teal_200)));
        addSlide(OnboardFragment.newInstance("Hmm...!", "This is the second slice",
                R.drawable.img_onboard_example,
                R.drawable.img_onboard_example,
                getResources().getColor(R.color.purple_200),
                getResources().getColor(R.color.teal_200)));
        addSlide(OnboardFragment.newInstance("Let's get started!", "This is the last slice",
                R.drawable.img_onboard_example,
                R.drawable.img_onboard_example,
                getResources().getColor(R.color.purple_200),
                getResources().getColor(R.color.teal_200)));

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
