package com.quiztest.quiztest.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.fragment.app.Fragment;

import com.quiztest.quiztest.MainActivity;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.callback.ActivityResultFragment;
import com.quiztest.quiztest.dialog.DialogProgressLoading;
import com.quiztest.quiztest.retrofit.RequestAPI;
import com.quiztest.quiztest.retrofit.RetrofitClient;
import com.quiztest.quiztest.utils.NetworkUtils;

import retrofit2.Retrofit;

public abstract class BaseFragment extends Fragment {

    //    protected AlertDialog progressDialog;
    private Thread thread;
    protected Activity mActivity;
    protected Context mContext;
    //    protected ShowErrorDialog errorDialog;
    private DialogProgressLoading dialogProgressLoading;
    protected RequestAPI requestAPI;

    // cái này cần set mới có nhé
    protected ActivityResultFragment activityResultFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(
                getLayoutId(),
                container,
                false
        );
        initView(v);
        initData();
        return v;
    }

    protected abstract int getLayoutId();

    protected void replaceFragment(Fragment fragment, String tag) {
        if (getActivity() instanceof MainActivity) {
            MainActivity nativeLoginActivity = (MainActivity) getActivity();
            nativeLoginActivity.replaceFragment(fragment, tag);
        }
    }

    protected void addFragment(Fragment fragment, String tag) {
        if (getActivity() instanceof MainActivity) {
            MainActivity nativeLoginActivity = (MainActivity) getActivity();
            nativeLoginActivity.addFragment(fragment, tag);
        }
    }

    protected void backstackFragment() {
        if (getActivity() instanceof MainActivity) {
            MainActivity nativeLoginActivity = (MainActivity) getActivity();
            nativeLoginActivity.onBackPressed();
        }
    }

    protected boolean hasFragmentState(String tag) {
        if (getActivity() instanceof MainActivity) {
            MainActivity nativeLoginActivity = (MainActivity) getActivity();
            nativeLoginActivity.hasFragmentState(tag);
        }
        return false;
    }

    protected void startThread(Consumer consumer) {
        thread = new Thread(() -> consumer.accept(new Object()));
        thread.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();
        Retrofit retrofit = RetrofitClient.getInstance();
        requestAPI = retrofit.create(RequestAPI.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract void initView(View v);

    protected abstract void initData();

//    public Dialog initDialogConfirm(String title, String message, String textPositiveButton, String textNegativeButton, Consumer consumer) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//        builder.setMessage(message);
//        builder.setTitle(title);
//        builder.setPositiveButton(TextUtils.isEmpty(textPositiveButton) ? LocaleController.getString("OK", vn.viva.messenger.R.string.OK) : textPositiveButton, (dialogInterface, i) -> {
//            consumer.accept(new Object());
//        });
//        builder.setNegativeButton(TextUtils.isEmpty(textNegativeButton) ? LocaleController.getString("Cancel", vn.viva.messenger.R.string.Cancel) : textNegativeButton, null);
//        return builder.create();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }

//    protected void showDialogError(Consumer<Boolean> consumerAccept) {
//        errorDialog = new ShowErrorDialog(mContext, R.style.MaterialDialogSheet);
//        errorDialog.onClickAccept(consumerAccept);
//        errorDialog.show();
//    }
//
//    protected void showDialogInformation(String content, String accept, Consumer<Boolean> consumerAccept) {
//        errorDialog = new ShowErrorDialog(mContext, R.style.MaterialDialogSheet);
//        errorDialog.onClickAccept(consumerAccept);
//        errorDialog.setCanceledOnTouchOutside(false);
//        errorDialog.show();
//        errorDialog.initData(content, accept, "");
//        errorDialog.showInformation();
//    }
//
//    protected void showDialogError(String content, String accept, String cancel, Consumer<Boolean> consumerAccept) {
//        errorDialog = new ShowErrorDialog(mContext, R.style.MaterialDialogSheet);
//        errorDialog.onClickAccept(consumerAccept);
//        errorDialog.show();
//        errorDialog.initData(content, accept, cancel);
//    }


//    protected boolean showLoading() {
//        if (progressDialog != null && progressDialog.isShowing())
//            return true;
//        progressDialog = new AlertDialog(mContext, 3);
//        progressDialog.setOnCancelListener(dialog -> {
//            if (thread != null && thread.isAlive()) {
//                thread.interrupt();
//            }
//            if (progressDialog == null)
//                return;
//            if (progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//        });
//        progressDialog.show();
//        return false;
//    }

//    protected void dismissDialog() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//    }

    protected boolean isNoneNetWork() {
        if (!NetworkUtils.isNetworkAvailable(mActivity)) {
//            showDialogError(aBoolean -> {
//            });
            return true;
        }
        return false;
    }

    protected void showLoading() {
        dialogProgressLoading = new DialogProgressLoading(getContext(), R.style.MaterialDialogSheet);
        if (!dialogProgressLoading.isShowing()) {
            dialogProgressLoading.show();
        }
    }

    protected void cancelLoading() {
        if (dialogProgressLoading != null) {
            dialogProgressLoading.dismiss();
        }
    }

    public void setActivityResultFragment(ActivityResultFragment activityResultFragment) {
        this.activityResultFragment = activityResultFragment;
    }
}