package com.hudipo.pum_indomaret.features.requestpum.activity;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.hudipo.pum_indomaret.R;
        import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
        import com.hudipo.pum_indomaret.features.requestpum.model.RequestInteractionImpl;
        import com.hudipo.pum_indomaret.features.requestpum.presenter.EmployeePresenter;
        import com.hudipo.pum_indomaret.features.searchdept.SearchDeptActivity;
        import com.hudipo.pum_indomaret.model.RequestModel;
        import com.hudipo.pum_indomaret.model.departement.DepartmentItem;
        import com.hudipo.pum_indomaret.utils.HawkStorage;
        import com.hudipo.pum_indomaret.utils.PumDateFormat;

        import java.util.ArrayList;
        import java.util.Objects;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import butterknife.OnClick;

public class EmployeeReqActivity extends AppCompatActivity implements RequestContract.EmployeeView {

    private int dateCode;

    @BindView(R.id.tvEmployeeNameEmp)
    TextView tvEmployeeName;
    @BindView(R.id.spnEmployeeDepartmentEmp)
    Spinner spnEmployeeDepartment;
    @BindView(R.id.etUseDateEmp)
    EditText etUseDateEmp;
    @BindView(R.id.etRespDateEmp)
    EditText etRespDate;
    @BindView(R.id.imgUseDateEmp)
    ImageView imgUseDate;
    @BindView(R.id.imgRespDateEmp)
    ImageView imgRespDate;

    RequestContract.EmployeePresenter presenter;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private HawkStorage hawkStorage;
    private Boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_employee);
        ButterKnife.bind(this);
        presenter = new EmployeePresenter(this, new RequestInteractionImpl(this));

        initHawkStorage();
        initView();
    }

    private void initHawkStorage() {
        hawkStorage = new HawkStorage(this);
    }

    private void initView() {
        presenter.getEmployeeName();
        presenter.getDepartmentList();
        mDateSetListener = (view, year, month, dayOfMonth) -> presenter.onDateSet(year, month, dayOfMonth,dateCode);
    }

    @OnClick(R.id.imgBack)
    void backPressed(){
        super.onBackPressed();
    }

    @OnClick(R.id.imgUseDateEmp)
    void onUseDateClick(){
        presenter.onDateClicked(0);
    }

    @OnClick(R.id.imgRespDateEmp)
    void onRespDateClick(){
        presenter.onDateClicked(1);
    }

    @OnClick(R.id.btnNextEmp)
    void next(){
        checkValidateData();
        goToNextDocumentActivity();
    }

    @OnClick(R.id.btnSearchDepartment)
    void searchDept(){
        startActivity(new Intent(this, SearchDeptActivity.class));
    }

    private void goToNextDocumentActivity() {
        if (validate){
            Intent intent = new Intent(EmployeeReqActivity.this, DocumentReqActivity.class);

            String employeeName = tvEmployeeName.getText().toString();
            int empId = hawkStorage.getUserData().getEmpId();
            String employeeDepartment = spnEmployeeDepartment.getSelectedItem().toString();
            String useDate = etUseDateEmp.getText().toString();
            String respDate = etRespDate.getText().toString();

            RequestModel requestModel = new RequestModel();

            for (DepartmentItem dept: hawkStorage.getDepartment().getDepartment()){
                if (dept.getDescription().equals(employeeDepartment)){
                    employeeDepartment = dept.getName();
                }
            }

            requestModel.setIdEmployee(empId);
            requestModel.setStringEmployeeName(employeeName);
            requestModel.setStringEmployeeDepartment(employeeDepartment);
            requestModel.setStringUseDate(PumDateFormat.rawToDateFormat(useDate));
            requestModel.setStringRespDate(PumDateFormat.rawToDateFormat(respDate));

            intent.putExtra(DocumentReqActivity.KEY_DATA_REQUEST_EMPLOYEE, requestModel);
            startActivity(intent);
        }
    }

    private void checkValidateData() {
        if (etUseDateEmp.getText().toString().isEmpty()){
            etUseDateEmp.setError("Please Input Date");
            Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
            validate = false;
        }else {
            etUseDateEmp.setError(null);
            validate = true;
        }

        if (etRespDate.getText().toString().isEmpty()){
            etRespDate.setError("Please input Resp Date");
            Toast.makeText(this, "Date cannot be empty", Toast.LENGTH_SHORT).show();
            validate = false;
        }else {
            etRespDate.setError(null);
            validate = true;
        }
    }

    @Override
    public void setEmployeeName(String employeeName) {
        tvEmployeeName.setText(employeeName);
    }

    @Override
    public void setDepartmentList(ArrayList<String> departmentList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, departmentList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnEmployeeDepartment.setAdapter(dataAdapter);
    }

    @Override
    public void displayDatePickerDialog(int year, int month, int day,int dateCode) {
        DatePickerDialog dialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,
                year, month, day);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        this.dateCode=dateCode;
    }


    @Override
    public void setDate(String date, int dateCode) {
        if (dateCode==0){
            etUseDateEmp.setText(date);
        }else etRespDate.setText(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
