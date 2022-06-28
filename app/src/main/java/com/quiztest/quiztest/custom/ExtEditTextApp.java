package com.quiztest.quiztest.custom;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Consumer;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.quiztest.quiztest.R;
import com.quiztest.quiztest.utils.Const;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtEditTextApp extends FrameLayout {

    private enum TYPE_ERROR {
        ERROR, DONE, NOT_CHANGE
    }

    public enum TYPE_VALIDATE {
        EMAIL, PASSWORD, NAME, COMFIRNPASSWORD
    }

    private TYPE_VALIDATE typeValidate;
    private TextInputEditText edtContent;
    private TextInputLayout etPasswordLayout;
    private ExtTextView txtError;
    private Consumer<Boolean> consumer;
    private String passWord;

    public ExtEditTextApp(@NonNull Context context) {
        super(context);
    }

    public ExtEditTextApp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.custome_edittext, null);
        edtContent = view.findViewById(R.id.edtContent);
        txtError = view.findViewById(R.id.txtError);
        etPasswordLayout = view.findViewById(R.id.etPasswordLayout);

        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                consumer.accept(false);
                if ("".equals(charSequence.toString())) {
                    addTextChange(TYPE_ERROR.NOT_CHANGE);
                } else {

                    if (typeValidate == TYPE_VALIDATE.EMAIL) {
                        consumer.accept(Patterns.EMAIL_ADDRESS.matcher(charSequence).matches());
                        addTextChange(Patterns.EMAIL_ADDRESS.matcher(charSequence).matches() ? TYPE_ERROR.DONE : TYPE_ERROR.ERROR);
                    }
                    if (typeValidate == TYPE_VALIDATE.PASSWORD) {
                        consumer.accept(charSequence.toString().length() >= 8);
                        addTextChange(charSequence.toString().length() >= 8 ? TYPE_ERROR.DONE : TYPE_ERROR.ERROR);
                    }
                    if (typeValidate == TYPE_VALIDATE.NAME) {
                        consumer.accept(charSequence.toString().length() <= 32);
                        addTextChange(charSequence.toString().length() <= 32 ? TYPE_ERROR.DONE : TYPE_ERROR.ERROR);
                    }
                    if (typeValidate == TYPE_VALIDATE.COMFIRNPASSWORD) {
                        boolean isSuccess = charSequence.toString().length() >= 8 && charSequence.toString().equals(passWord);
                        Log.e("=====>", "length:" + charSequence.toString().length());
                        Log.e("=====>", "passWord:" + charSequence +"  " +passWord);
                        consumer.accept(isSuccess);
                        addTextChange(isSuccess ? TYPE_ERROR.DONE : TYPE_ERROR.ERROR);
                        if (charSequence.toString().length() <= 8)
                            txtError.setText("Password cannot less 8 characters");
                        if (!charSequence.toString().equals(passWord)) {
                            txtError.setText("Re-entered password does not match");
                        }
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addTextChange(TYPE_ERROR.NOT_CHANGE);
        addView(view);
    }

    /**
     * typeValidate là điều kiện để validate
     * errpr là thông tin lỗi trả về khi sai
     * inputType loại type mặc định
     */

    public void initData(TYPE_VALIDATE typeValidate, String error, int inputType, Consumer<Boolean>consumer) {
        this.typeValidate = typeValidate;
        this.consumer = consumer;
        this.txtError.setText(error);
        this.edtContent.setInputType(inputType);
        if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            this.etPasswordLayout.setPasswordVisibilityToggleEnabled(true);
        }
    }

    public void validatePass(String passWord) {
        Log.e("=====>", "validatePass:"+"  " +passWord.toString());
        this.passWord = passWord;
    }

    public void setHintEditText(String hint) {
        this.edtContent.setHint(hint);
    }

    private void addTextChange(TYPE_ERROR typeError) {
        txtError.setVisibility(GONE);
        switch (typeError) {
            case DONE:
                edtContent.setBackgroundResource(R.drawable.bg_edt_done);
                break;
            case ERROR:
                txtError.setVisibility(VISIBLE);
                edtContent.setBackgroundResource(R.drawable.bg_edt_error);
                break;
            case NOT_CHANGE:
                edtContent.setBackgroundResource(R.drawable.edittext_background);
                break;
        }
    }


}
