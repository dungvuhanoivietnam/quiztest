package com.quiztest.quiztest.fragment;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.quiztest.quiztest.R;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.custom.ExtTextView;

import java.io.IOException;
import java.io.InputStream;

public class PrivacyFragment extends BaseFragment {

    private ExtTextView txt_title;
    private TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_privacy;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initView(View v) {
        v.findViewById(R.id.txt_Left).setVisibility(View.GONE);
        v.findViewById(R.id.ic_right).setVisibility(View.GONE);
        v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        v.findViewById(R.id.iv_back).setOnClickListener(view -> backstackFragment());
        txt_title = v.findViewById(R.id.txt_title);
        tvContent = v.findViewById(R.id.tvContent);
        tvContent.setText(loadPrivacy("privacy_policy.txt"));

    }

    @Override
    protected void initData() {
        txt_title.setText(getString(R.string.privacy_policy));
        txt_title.setVisibility(View.VISIBLE);
    }

    public String loadPrivacy(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getContext().getAssets().open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }
}
