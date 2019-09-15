package com.hudipo.pum_indomaret.features.approval.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.adapter.ApprovalAdapter;
import com.hudipo.pum_indomaret.features.approval.presenter.ApprovalPresenter;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalFragment extends Fragment implements ApprovalContract.ApprovalView {
    @BindView(R.id.rvApproval)
    RecyclerView rvApproval;
    @BindView(R.id.cbApproval)
    ImageView cbApproval;
    @BindView(R.id.llAction)
    LinearLayout llAction;

    private View view;
    private ApprovalAdapter approvalAdapter;
    private ApprovalPresenter presenter;
    private List<ApprovalModel> approvalSelectedList = new ArrayList<>();
    private List<ApprovalModel> approvalModelList = new ArrayList<>();
    private boolean isCheckedAll = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approval, container, false);
        ButterKnife.bind(this, view);

        setView();
        presenter.getData();

        return view;
    }

    private void setView() {
        rvApproval.setLayoutManager(new LinearLayoutManager(getActivity()));
        cbApproval.setOnClickListener(view1 -> {
            if(isCheckedAll){
                approvalSelectedList.clear();
                isCheckedAll = false;
            }else {
                approvalSelectedList.clear();
                approvalSelectedList.addAll(approvalModelList);
                isCheckedAll = true;
            }
            approvalAdapter.setAllChecked(isCheckedAll);
            initAction();
        });
    }

    @OnClick(R.id.btnApprove)
    void approve(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(getString(R.string.approve));
        alert.setMessage(getString(R.string.message_dialog_approve));
        alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
            // TODO: 15/09/19 to pin activity
            Toast.makeText(getActivity(), "To PIN Activity", Toast.LENGTH_LONG).show();
        }));
        alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
        alert.show();
    }


    @OnClick(R.id.btnReject)
    void reject(){
        FrameLayout container = new FrameLayout(Objects.requireNonNull(getActivity()));

        container.addView(generateEditText());

        final AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        alert.setTitle(getString(R.string.reject));
        alert.setMessage(getString(R.string.message_dialog_reject));
        alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
            // TODO: 15/09/19 to pin activity
            Toast.makeText(getActivity(), "To PIN Activity", Toast.LENGTH_LONG).show();
        }));
        alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
        alert.setView(container);
        alert.show();
    }

    private EditText generateEditText()
    {
        int padding = getResources().getDimensionPixelSize(R.dimen.min_margin);
        int margin = getResources().getDimensionPixelSize(R.dimen.main_margin);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = margin;
        params.rightMargin = margin;

        final EditText input = new EditText(getActivity());
        input.setLayoutParams(params);
        input.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.bg_rounded_border_black_solid_black));
        input.setHint(getString(R.string.hint_dialog_reject));
        input.setMinLines(3);
        input.setGravity(Gravity.TOP);
        input.setPadding(padding, padding, padding, padding);
        return input;
    }

    @Override
    public void showData(List<ApprovalModel> approvalModels) {
        approvalModelList.clear();
        approvalModelList.addAll(approvalModels);

        approvalAdapter = new ApprovalAdapter(approvalModels, (approvalModel, checked) -> {
            if(checked){
                approvalSelectedList.add(approvalModel);
            }else {
                approvalSelectedList.remove(approvalModel);
            }
            initAction();
        });
        rvApproval.setAdapter(approvalAdapter);
    }

    @Override
    public void errorNotSelected(String message) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void success(String message) {

    }

    @Override
    public void showAction() {
        if(llAction.getVisibility() == View.GONE){
            Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_up);
            llAction.setVisibility(View.VISIBLE);
            llAction.startAnimation(slideUp);
        }
    }

    @Override
    public void closeAction() {
        if(llAction.getVisibility() == View.VISIBLE){
            Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down_out);
            llAction.setVisibility(View.GONE);
            llAction.startAnimation(slideDown);
        }
    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = new ApprovalPresenter(getActivity());
        onAttachView();
    }

    private void initAction(){
        if(approvalSelectedList.size()>0){
            showAction();
        }else closeAction();
    }
}
