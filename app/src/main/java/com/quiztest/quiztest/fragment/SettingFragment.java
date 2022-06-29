package com.quiztest.quiztest.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.adapter.LanguageAdapter;
import com.quiztest.quiztest.base.BaseFragment;
import com.quiztest.quiztest.callback.ActivityResultFragment;
import com.quiztest.quiztest.custom.ExtEditText;
import com.quiztest.quiztest.custom.ExtTextView;
import com.quiztest.quiztest.dialog.DialogChooseImage;
import com.quiztest.quiztest.model.BaseResponse;
import com.quiztest.quiztest.model.UserInfoResponse;
import com.quiztest.quiztest.utils.Const;
import com.quiztest.quiztest.utils.SharePrefrenceUtils;
import com.quiztest.quiztest.viewmodel.UserViewModel;

public class SettingFragment extends BaseFragment implements ActivityResultFragment {

    private ExtTextView txt_title, txt_content_create_account, txt_link_google, txt_link_facebook;
    private ImageView ivAvatar, ivUpload;
    private UserViewModel userViewModel;
    private UserInfoResponse.UserInfo userInfo;
    private LinearLayout ll_setting_content, ll_login, ll_logout, llSave;
    private ExtEditText edtName, edtGmail;
    private DialogChooseImage dialogChooseImage;
    private boolean isSave = false;
    private boolean isLogin = false;
    private Spinner sp_language;

    String[] countryNames = {"Tiếng Việt", "English"};
    String[] countryFlag = {"https://manager-apps.merryblue.llc/storage/flags/Vietnam.png", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ae/Flag_of_the_United_Kingdom.svg/1920px-Flag_of_the_United_Kingdom.svg.png"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View v) {
        v.findViewById(R.id.txt_Left).setVisibility(View.GONE);
        v.findViewById(R.id.ic_right).setVisibility(View.GONE);
        v.findViewById(R.id.iv_back).setVisibility(View.VISIBLE);
        v.findViewById(R.id.iv_back).setOnClickListener(view -> backstackFragment());
        txt_title = v.findViewById(R.id.txt_title);
        ivAvatar = v.findViewById(R.id.ivAvatar);
        ivUpload = v.findViewById(R.id.ivUpload);
        ll_setting_content = v.findViewById(R.id.ll_setting_content);
        edtName = v.findViewById(R.id.edtName);
        edtGmail = v.findViewById(R.id.edtGmail);
        txt_content_create_account = v.findViewById(R.id.txt_content_create_account);
        ll_login = v.findViewById(R.id.ll_login);
        txt_link_google = v.findViewById(R.id.txt_link_google);
        txt_link_facebook = v.findViewById(R.id.txt_link_facebook);
        ll_logout = v.findViewById(R.id.ll_logout);
        llSave = v.findViewById(R.id.llSave);

        dialogChooseImage = new DialogChooseImage(getContext(), R.style.MaterialDialogSheet, type_select -> {
            if (type_select == DialogChooseImage.TYPE_SELECT.CAMERA)
                openCamera();
            if (type_select == DialogChooseImage.TYPE_SELECT.ALBUM)
                openGallery();
        });

        v.findViewById(R.id.llSave).setOnClickListener(view -> {
            showLoading();
            userViewModel.updateProfile(requestAPI, edtName.getText().toString().trim(), edtGmail.getText().toString().trim(), o -> {
                cancelLoading();
                if (o instanceof BaseResponse) {
                    BaseResponse baseResponse = (BaseResponse) o;
                    if (baseResponse.getSuccess()) {
                        userViewModel.getUserInfoResponse().setEmail(edtGmail.getText().toString());
                        userViewModel.getUserInfoResponse().setName(edtName.getText().toString());
                        checkIsSave();
                    }
                }
            });
        });
        v.findViewById(R.id.ivUpload).setOnClickListener(view -> {

            if (!dialogChooseImage.isShowing())
                dialogChooseImage.show();
        });

        ll_login.setOnClickListener(view -> replaceFragment(new LoginFragment(), LoginFragment.class.getSimpleName()));
        ll_logout.setOnClickListener(view -> {
            showLoading();
            userViewModel.logout(requestAPI, o -> {
                cancelLoading();
                if (o instanceof BaseResponse) {
                    SharePrefrenceUtils.getInstance(mContext).saveAuth("");
                    userViewModel.clearViewModel();
                    backstackFragment();
                    if (getActivity() instanceof MainActivity) {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.actionLogout();
                    }
                }
            });

        });
        sp_language = v.findViewById(R.id.sp_language);
        sp_language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        LanguageAdapter customAdapter = new LanguageAdapter(getContext(), countryFlag, countryNames);
        sp_language.setAdapter(customAdapter);
    }

    private void checkIsSave() {
        String name = edtName.getText().toString().toLowerCase();
        String mName = userInfo.getName().toLowerCase();
        String gmail = edtGmail.getText().toString().toLowerCase();
        String mgmail = userInfo.getEmail().toLowerCase();
        isSave = !name.equals(mName) || !gmail.equals(mgmail);
        llSave.setBackgroundResource(isSave ? R.drawable.bg_button_blue_21 : R.drawable.bg_gray_b8);
    }

    @Override
    protected void initData() {
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
        txt_title.setText(getString(R.string.setting));
        txt_title.setVisibility(View.VISIBLE);
        userInfo = userViewModel.getUserInfoResponse();
        isLogin = userInfo.getEmail() != null;
        Glide.with(mContext).load(userInfo.getAvatar()).circleCrop().placeholder(R.drawable.ic_create_account_profile).into(ivAvatar);
        ivUpload.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        ll_setting_content.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        edtName.setText(userInfo.getName() != null ? userInfo.getName() : "");
        edtGmail.setText(userInfo.getEmail() != null ? userInfo.getEmail() : "");
        txt_content_create_account.setVisibility(isLogin ? View.GONE : View.VISIBLE);

        txt_link_google.setTextColor(getContext().getResources().getColor(userInfo.getGoogleProviderId() == null ? R.color.color_B8BDC2 : R.color.color_21C3FF));
        txt_link_facebook.setTextColor(getContext().getResources().getColor(userInfo.getFacebookProviderId() == null ? R.color.color_B8BDC2 : R.color.color_21C3FF));
        ll_login.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        ll_logout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkIsSave();
            }
        });
        edtGmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkIsSave();
            }
        });

    }

    private void openCamera() {
        try {
            if (Build.VERSION.SDK_INT >= 23 && mActivity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, Const.CAMERA_REQUEST_CODE);
                return;
            }
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mActivity.startActivityForResult(takePictureIntent, Const.CAMERA_REQUEST_CODE);
        } catch (Exception e) {
        }
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= 23 && mActivity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            mActivity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Const.ALBUM_REQUEST_CODE);
            return;
        }
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mActivity.startActivityForResult(i, Const.ALBUM_REQUEST_CODE);
    }


    @Override
    public void result(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Const.CAMERA_REQUEST_CODE) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Glide.with(mContext).load(photo).circleCrop().into(ivAvatar);
        }
        if (requestCode == Const.ALBUM_REQUEST_CODE) {
            Glide.with(mContext).load(data.getData()).circleCrop().into(ivAvatar);
        }
    }

    @Override
    public void result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Const.CAMERA_REQUEST_CODE)
            openCamera();
        if (requestCode == Const.ALBUM_REQUEST_CODE)
            openGallery();
    }
}
