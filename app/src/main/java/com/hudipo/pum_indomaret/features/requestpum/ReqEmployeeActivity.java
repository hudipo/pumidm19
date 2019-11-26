package com.hudipo.pum_indomaret.features.requestpum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.searchdept.SearchDeptActivity;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.departement.DepartmentItem;
import com.hudipo.pum_indomaret.utils.DatePickerFragment;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import com.hudipo.pum_indomaret.utils.PumDateFormat;

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
    private DatePickerFragment datePickerFragment = new DatePickerFragment();
    private RequestModel requestModel = new RequestModel();

    private String TAG_USE_DATE = "tag_use_date";
    private int REQUEST_CODE_SEARCH_DEPT = 100;
    private boolean isValid = false;

    /**
     * All function from
     * @see  AppCompatActivity
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_employee);
        ButterKnife.bind(this);

        initHawkStorage();
        initView();
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
                    }
                }
            }
        }
    }

    /**
     * OnClick Listener
     * */

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
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
        String useDate = tvUseDate.getText().toString().trim();
        String respDate = tvRespDate.getText().toString().trim();
        String nameSearchDept = btnSearchDept.getText().toString().trim();

        checkValidateData(useDate, respDate, nameSearchDept);
        if (isValid){
            Intent intent = new Intent(this, ReqDocumentActivity.class);

            requestModel.setNameEmpDept(nameSearchDept);
            requestModel.setEmpId(idEmp);
            requestModel.setUseDate(PumDateFormat.dateFormatServer(useDate));
            requestModel.setRespDate(PumDateFormat.dateFormatServer(respDate));

            intent.putExtra(ReqDocumentActivity.KEY_DATA_REQUEST_EMPLOYEE, requestModel);
            startActivity(intent);
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
        }else {
            tvRespDate.setText(time);
        }
    }


    /**
     *  All function from
     *  @see ReqEmployeeActivity
     *  */

    private void checkValidateData(String useDate, String respDate, String searchDept) {
        if (useDate.isEmpty()){
            tvUseDate.setError(getString(R.string.please_input_date));
            Toast.makeText(this, getString(R.string.please_input_date), Toast.LENGTH_SHORT).show();
            isValid = false;
        }else {
            tvUseDate.setError(null);
            isValid = true;
        }

        if (respDate.isEmpty()){
            tvRespDate.setError(getString(R.string.please_input_date));
            Toast.makeText(this, getString(R.string.please_input_date), Toast.LENGTH_SHORT).show();
            isValid = false;
        }else {
            tvRespDate.setError(null);
            isValid = true;
        }

        if (searchDept.isEmpty()){
            Toast.makeText(this, getString(R.string.please_input_dept), Toast.LENGTH_SHORT).show();
            btnSearchDept.setError(getString(R.string.please_input_dept));
            isValid = false;
        }else {
            btnSearchDept.setError(null);
            isValid = true;
        }
    }

    private void initView() {
        tvEmployeeName.setText(hawkStorage.getUserData().getName());
    }

    private void initHawkStorage() {
        hawkStorage = new HawkStorage(this);
    }
}

