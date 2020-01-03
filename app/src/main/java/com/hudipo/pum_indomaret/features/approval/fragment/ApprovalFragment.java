package com.hudipo.pum_indomaret.features.approval.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalSuccessActivity;
import com.hudipo.pum_indomaret.features.approval.adapter.ApprovalAdapter;
import com.hudipo.pum_indomaret.features.approval.presenter.ApprovalPresenter;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalContract;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.pin.PinActivity;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.utils.Extra;
import com.hudipo.pum_indomaret.utils.Global;

import org.w3c.dom.Text;

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
    @BindView(R.id.swipeRefreshApproval)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.search)
    SearchView searchView;

    private View view;
    private ApprovalAdapter approvalAdapter;
    private ApprovalPresenter presenter;
    private List<ApprovalListModel> approvalSelectedList = new ArrayList<>();
    private List<ApprovalListModel> approvalModelList = new ArrayList<>();
    private boolean isCheckedAll = false;
    private final int REQUEST_CODE_PIN = 100;
    private int requestType = -1; //0 reject; 1 aprove
    private String reasonValidation = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approval, container, false);
        ButterKnife.bind(this, view);
        presenter = new ApprovalPresenter(getActivity());

        setView();
        onAttachView();

        return view;
    }

    private void setView() {
        rvApproval.setLayoutManager(new LinearLayoutManager(getActivity()));
        cbApproval.setOnClickListener(view1 -> {
            if (isCheckedAll) {
                approvalSelectedList.clear();
                isCheckedAll = false;
            } else {
                approvalSelectedList.clear();
                approvalSelectedList.addAll(approvalModelList);
                isCheckedAll = true;
            }
            approvalAdapter.setAllChecked(isCheckedAll);
            initAction();
        });
        swipeRefreshLayout.setOnRefreshListener(() ->
                presenter.getData()
        );

        approvalAdapter = new ApprovalAdapter(new ArrayList<>(), (approvalModel, checked) -> {
            if (checked) {
                approvalSelectedList.add(approvalModel);
            } else {
                approvalSelectedList.remove(approvalModel);
            }
            initAction();
        });
        rvApproval.setAdapter(approvalAdapter);

        setSearch();
    }

    private void setSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                approvalAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @OnClick(R.id.btnApprove)
    void approve() {
        if (approvalSelectedList.size() > 0) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setTitle(getString(R.string.approve));
            if (approvalSelectedList.size() == 1) {
                alert.setMessage(getString(R.string.message_dialog_approve_single));
            } else {
                alert.setMessage(getString(R.string.message_dialog_approve));
            }
            alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
                Intent intent = new Intent(getActivity(), PinActivity.class);
                startActivityForResult(intent, REQUEST_CODE_PIN);
                requestType = 1;
            }));
            alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
            alert.show();
        } else {
            errorNotSelected("Not Selected");
        }
    }


    @OnClick(R.id.btnReject)
    void reject() {
        if (approvalSelectedList.size() > 0) {
            FrameLayout container = new FrameLayout(Objects.requireNonNull(getActivity()));

            EditText editText = generateEditText();
            container.addView(editText);

            final AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
            alert.setTitle(getString(R.string.reject));
            if (approvalSelectedList.size() == 1) {
                alert.setMessage(getString(R.string.message_dialog_reject_single));
            } else {
                alert.setMessage(getString(R.string.message_dialog_reject));
            }
            alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
                reasonValidation = editText.getText().toString();
                if (reasonValidation.isEmpty()) {
                    Toast.makeText(getActivity(), "Reason validation can't empty", Toast.LENGTH_SHORT).show();
                    reject();
                } else {
                    Intent intent = new Intent(getActivity(), PinActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PIN);
                    requestType = 0;
                }
            }));
            alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
            alert.setView(container);
            alert.show();

        } else {
            errorNotSelected("Not Selected");
        }
    }

    private EditText generateEditText() {
        int padding = getResources().getDimensionPixelSize(R.dimen.min_margin);
        int margin = getResources().getDimensionPixelSize(R.dimen.main_margin);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PIN) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    String pin = data.getStringExtra(PinActivity.EXTRA_PIN);
                    if (requestType == 0) { //reject
                        presenter.reject(approvalSelectedList, pin, reasonValidation);
                    } else {
                        presenter.approve(approvalSelectedList, pin);
                    }
                }
            }
        }
    }

    @Override
    public void showData(List<ApprovalListModel> approvalModels) {
        approvalModelList.clear();
        approvalModelList.addAll(approvalModels);
        approvalAdapter.updateListApproval(approvalModelList);
    }

    @Override
    public void errorNotSelected(String message) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Attention!");
        alert.setMessage(message);
        alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
            dialogInterface.dismiss();
        }));
        alert.show();
    }

    @Override
    public void error(String message) {
        if (isAdded())
//            Global.toast(getContext(), message);
        tvError.setText(R.string.data_is_empty);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void success(int requestType) {
        Intent intent = new Intent(getActivity(), ApprovalSuccessActivity.class);
        intent.putExtra(Extra.EXTRA_APPROVAL_HISTORY_TYPE, requestType);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void showAction() {
        if (llAction.getVisibility() == View.GONE) {
            Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_up);
            llAction.setVisibility(View.VISIBLE);
            llAction.startAnimation(slideUp);
        }
    }

    @Override
    public void closeAction() {
        if (llAction.getVisibility() == View.VISIBLE) {
            Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down_out);
            llAction.setVisibility(View.GONE);
            llAction.startAnimation(slideDown);
        }
    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);
        presenter.getData();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    private void initAction() {
        if (approvalSelectedList.size() > 0) {
            showAction();
        } else closeAction();
    }

    @OnClick(R.id.ivBack)
    void back() {
        Objects.requireNonNull(getActivity()).onBackPressed();

    }


}
