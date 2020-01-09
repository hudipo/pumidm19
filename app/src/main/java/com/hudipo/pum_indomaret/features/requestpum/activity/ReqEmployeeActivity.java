package com.hudipo.pum_indomaret.features.requestpum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.searchdept.SearchDeptActivity;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.departement.DepartmentItem;
import com.hudipo.pum_indomaret.utils.DatePickerFragment;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.PumDateFormat;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReqEmployeeActivity extends AppCompatActivity implements DatePickerFragment.DialogDataListener {

    @BindView(R.id.tvUseDate)
    TextView tvUseDate;
    @BindView(R.id.tvRespDate)
    TextView tvRespDate;
    @BindView(R.id.btnSearchDepartment)
    Button btnSearchDept;
    @BindView(R.id.tvEmployeeName)
    TextView tvEmployeeName;

    private HawkStorage hawkStorage;
    private RequestModel requestModel;
    private DatePickerFragment datePickerFragment = new DatePickerFragment();

    private String TAG_USE_DATE = "tag_use_date";
    private int REQUEST_CODE_SEARCH_DEPT = 100;

    /**
     * All function from
     * @see  AppCompatActivity
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_employee);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHawkStorage();
        initView();
        setDataView();
    }

    private void setDataView() {
        if (hawkStorage.getRequestModel() != null){
            if (hawkStorage.getRequestModel().getNameEmpDept() != null && !hawkStorage.getRequestModel().getNameEmpDept().isEmpty()){
                btnSearchDept.setText(hawkStorage.getRequestModel().getNameEmpDept());
            }

            if (hawkStorage.getRequestModel().getUseDate() != null && !hawkStorage.getRequestModel().getUseDate().isEmpty()){
                tvUseDate.setText(PumDateFormat.rawDateFormatView(hawkStorage.getRequestModel().getUseDate()));
            }

            if (hawkStorage.getRequestModel().getRespDate() != null && !hawkStorage.getRequestModel().getRespDate().isEmpty()){
                tvRespDate.setText(PumDateFormat.rawDateFormatView(hawkStorage.getRequestModel().getRespDate()));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SEARCH_DEPT){
            if (resultCode == SearchDeptActivity.RESULT_CODE){
                if (data != null){
                    DepartmentItem departmentItem = (DepartmentItem) data.getSerializableExtra(SearchDeptActivity.DATA_SELECTED_VALUE);
                    if (departmentItem != null){
                        btnSearchDept.setText(departmentItem.getDescription());
                        requestModel.setEmpDeptId(departmentItem.getDeptId());
                        requestModel.setNameEmpDept(departmentItem.getDescription());

                        hawkStorage.setRequestModel(requestModel);
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (hawkStorage.getRequestModel() != null){
           showDialogToHome();

       }else {
           super.onBackPressed();
           startActivity(new Intent(this, HomeActivity.class));
           finish();
       }

    }

    private void showDialogToHome() {
        new AlertDialog.Builder(this,R.style.CustomDialogTheme)
                .setTitle("Alert")
                .setMessage("Are you sure to leave Request menu ? \nAll data that you have entered will be deleted")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    hawkStorage.deleteRequestModel();
                    finish();
                    dialogInterface.dismiss();

                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * OnClick Listener
     * */

    @OnClick(R.id.btnBack)
    void btnBack(){
       onBackPressed();
    }

    @OnClick(R.id.btnRespDate)
    void btnRespDate(){
        String TAG_RESP_DATE = "tag_resp_date";
        datePickerFragment.show(getSupportFragmentManager(), TAG_RESP_DATE);
    }

    @OnClick(R.id.btnUseDate)
    void btnUseDate(){
        datePickerFragment.show(getSupportFragmentManager(), TAG_USE_DATE);
    }

    @OnClick(R.id.btnSearchDepartment)
    void setBtnSearchDept(){
        startActivityForResult(new Intent(this, SearchDeptActivity.class), REQUEST_CODE_SEARCH_DEPT);
    }

    @OnClick(R.id.btnNext)
    void btnNext(){
        int idEmp = hawkStorage.getUserData().getEmpId();
        int orgId = hawkStorage.getUserData().getOrgId();
        int userId = hawkStorage.getUserData().getUserId();
        String useDate = tvUseDate.getText().toString().trim();
        String respDate = tvRespDate.getText().toString().trim();
        String nameSearchDept = btnSearchDept.getText().toString().trim();

        if (checkValidateData(useDate, respDate, nameSearchDept)){
            Intent intent = new Intent(this, ReqDocumentActivity.class);

            requestModel.setNameEmpDept(nameSearchDept);
            requestModel.setEmpId(idEmp);
            requestModel.setOrgId(orgId);
            requestModel.setUserId(userId);
            requestModel.setUseDate(PumDateFormat.dateFormatServer(useDate));
            requestModel.setRespDate(PumDateFormat.dateFormatServer(respDate));

            hawkStorage.setRequestModel(requestModel);
            startActivity(intent);
            Animatoo.animateSlideLeft(this);
        }
    }

    /**
     * Function from
     * @see com.hudipo.pum_indomaret.utils.DatePickerFragment.DialogDataListener
     * */

    @Override
    public void onDialogSet(String tag, int year, int month, int dayOfMonth) {
        String time = PumDateFormat.dateFormatView(year, month, dayOfMonth);
        if (tag.equals(TAG_USE_DATE)){
            tvUseDate.setText(time);
            requestModel.setUseDate(PumDateFormat.dateFormatServer(time));
            hawkStorage.setRequestModel(requestModel);
        }else {
            tvRespDate.setText(time);
            requestModel.setRespDate(PumDateFormat.dateFormatServer(time));
            hawkStorage.setRequestModel(requestModel);
        }
    }


    /**
     *  All function from
     *  @see ReqEmployeeActivity
     *  */

    private Boolean checkValidateData(String useDate, String respDate, String searchDept) {

        if (searchDept.isEmpty()){
            Toast.makeText(this, getString(R.string.please_input_dept), Toast.LENGTH_SHORT).show();
            btnSearchDept.setError(getString(R.string.please_input_dept));
            return false;
        }else {
            btnSearchDept.setError(null);
        }



        if (useDate.isEmpty()){
            Toast.makeText(this, getString(R.string.please_input_date), Toast.LENGTH_SHORT).show();
            tvUseDate.setError(getString(R.string.please_input_date));
            return false;
        }else {
            tvUseDate.setError(null);
        }

        if (respDate.isEmpty()){
            tvRespDate.setError(getString(R.string.please_input_date));
            Toast.makeText(this, getString(R.string.please_input_date), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            tvRespDate.setError(null);
        }

        try {
            if (PumDateFormat.comparisionDateBefore(useDate, respDate)){
                tvRespDate.setError(null);
                tvUseDate.setError(null);
                return true;

            }else {
                Toast.makeText(this, R.string.use_date_greater_than_resp_date, Toast.LENGTH_SHORT).show();
                tvRespDate.setError(getString(R.string.use_date_greater_than_resp_date));
                tvUseDate.setError(getString(R.string.use_date_greater_than_resp_date));
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void initView() {
        tvEmployeeName.setText(hawkStorage.getUserData().getName());
    }

    private void initHawkStorage() {
        hawkStorage = new HawkStorage(this);
        if (hawkStorage.getRequestModel() != null){
            requestModel = hawkStorage.getRequestModel();
        }else {
            requestModel = new RequestModel();
        }
    }
}

