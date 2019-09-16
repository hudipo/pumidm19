package com.hudipo.pum_indomaret.features.requestpum.view;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Bundle;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;

        import com.hudipo.pum_indomaret.R;
        import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
        import com.hudipo.pum_indomaret.features.requestpum.model.RequestInteractorImpl;
        import com.hudipo.pum_indomaret.features.requestpum.presenter.EmployeePresenterImpl;
        import com.hudipo.pum_indomaret.model.RequestModel;

        import java.util.ArrayList;
        import java.util.Objects;

        import butterknife.BindView;
        import butterknife.ButterKnife;
        import butterknife.OnClick;

public class EmployeeReqActivity extends AppCompatActivity implements RequestContract.EmployeeView {

    public static final int USE_DATE_CODE=0;
    public static final int RESP_DATE_CODE=1;

    private int dateCode;

    @BindView(R.id.tvEmployeeNameEmp)
    TextView tvEmployeeName;
    @BindView(R.id.spnEmployeeDepartmentEmp)
    Spinner spnEmployeeDepartment;
    @BindView(R.id.tvUseDateEmp)
    TextView tvUseDate;
    @BindView(R.id.tvRespDateEmp)
    TextView tvRespDate;
    @BindView(R.id.imgUseDateEmp)
    ImageView imgUseDate;
    @BindView(R.id.imgRespDateEmp)
    ImageView imgRespDate;

    RequestContract.EmployeePresenter presenter;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_employee);
        ButterKnife.bind(this);
        presenter = new EmployeePresenterImpl(this, new RequestInteractorImpl());
        initView();


    }


    private void initView() {
        presenter.getEmployeeName();
        presenter.getDepartmentList();
        mDateSetListener = (view, year, month, dayOfMonth) -> presenter.onDateSet(year, month, dayOfMonth,dateCode);
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
        Intent intent = new Intent(EmployeeReqActivity.this, DocumentReqActivity.class);
        String employeeName = tvEmployeeName.getText().toString();
        String employeeDepartment = spnEmployeeDepartment.getSelectedItem().toString();
        String useDate = tvUseDate.getText().toString();
        String respDate = tvRespDate.getText().toString();
        RequestModel requestModel = new RequestModel();
        requestModel.setStringEmployeeName(employeeName);
        requestModel.setStringEmployeeDepartment(employeeDepartment);
        requestModel.setStringUseDate(useDate);
        requestModel.setStringRespDate(respDate);
        intent.putExtra("CURRENT_REQUEST", requestModel);
        startActivity(intent);
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
            tvUseDate.setText(date);
        }else tvRespDate.setText(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
