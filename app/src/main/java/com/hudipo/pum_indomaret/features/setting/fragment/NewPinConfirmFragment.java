package com.hudipo.pum_indomaret.features.setting.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hudipo.pum_indomaret.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hudipo.pum_indomaret.features.setting.activity.ChangePinActivity.NEW;
import static com.hudipo.pum_indomaret.features.setting.activity.ChangePinActivity.NEW_CONFIRM;

public class NewPinConfirmFragment extends Fragment {
    @BindView(R.id.etPin)
    EditText etPin;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private static final String TAG = "Current Pin";
    private FragmentCallback mCallback;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pin, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvTitle.setText(getString(R.string.confirm_new_pin));

        etPin.requestFocus();
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==6) {
                    mCallback.nextFragment(NEW_CONFIRM, editable.toString());
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback=(FragmentCallback) context;
        }catch (ClassCastException e)
        {
            Log.d(TAG, "onAttach: "+e.getMessage());
        }
    }
}
