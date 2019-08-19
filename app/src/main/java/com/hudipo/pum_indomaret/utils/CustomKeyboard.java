package com.hudipo.pum_indomaret.utils;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;

import com.hudipo.pum_indomaret.R;

public class CustomKeyboard extends FrameLayout implements View.OnClickListener {

    private EditText mPasswordField;
    private String realString="";

    public CustomKeyboard(Context context) {
        super(context);
        init();
    }

    public CustomKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_keyboard_view, this);
        initViews();
    }

    private void initViews() {
        mPasswordField = $(R.id.field);
        $(R.id.t9_key_0).setOnClickListener(this);
        $(R.id.t9_key_1).setOnClickListener(this);
        $(R.id.t9_key_2).setOnClickListener(this);
        $(R.id.t9_key_3).setOnClickListener(this);
        $(R.id.t9_key_4).setOnClickListener(this);
        $(R.id.t9_key_5).setOnClickListener(this);
        $(R.id.t9_key_6).setOnClickListener(this);
        $(R.id.t9_key_7).setOnClickListener(this);
        $(R.id.t9_key_8).setOnClickListener(this);
        $(R.id.t9_key_9).setOnClickListener(this);
        $(R.id.t9_key_backspace).setOnClickListener(this);
        $(R.id.t9_key_clear).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // handle number button click
        if (v.getTag() != null && "number_button".equals(v.getTag())&&realString.length()<6) {
            mPasswordField.append("*");
            realString = realString + ((TextView) v).getText();
        }
        switch (v.getId()) {
            case R.id.t9_key_clear: { // handle clear button
                mPasswordField.setText(null);
                realString = "";
            }
            break;
            case R.id.t9_key_backspace: { // handle backspace button
                // delete one character
                Editable editable = mPasswordField.getText();
                int charCount = editable.length();
                int realStringCount = realString.length()-1;
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                    realString = realString.substring(0,realStringCount);
                }
            }
            break;
        }
    }

    public String getInputText() {
        //return mPasswordField.getText().toString();
        return realString;
    }

    protected <T extends View> T $(@IdRes int id) {
        //noinspection unchecked
        return (T) super.findViewById(id);
    }
}
