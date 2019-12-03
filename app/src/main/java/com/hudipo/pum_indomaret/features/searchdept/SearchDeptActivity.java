package com.hudipo.pum_indomaret.features.searchdept;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.SearchDeptAdapter;
import com.hudipo.pum_indomaret.model.departement.DepartmentItem;
import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.repository.Repository;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDeptActivity extends AppCompatActivity implements SearchDeptContract.SearchDeptView {

    private SearchDeptAdapter adapter;
    private SearchDeptPresenter presenter;
    private Repository repository;
    static public int RESULT_CODE = 110;
    static public String DATA_SELECTED_VALUE = "selected_data_search_department";

    @BindView(R.id.rvSearchDept)
    RecyclerView rvSearchDept;
    @BindView(R.id.pbSearchDept)
    ProgressBar pbSearchDept;
    @BindView(R.id.etSearchDepart)
    EditText etSearchDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dept);
        ButterKnife.bind(this);

        onAttachView();
    }

    private void searchingDept() {
        etSearchDept.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initRepository() {
        HawkStorage hawkStorage = new HawkStorage(this);
        repository = new Repository();
        repository.setHawkStorage(hawkStorage);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clearComposite();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }

    private void initPresenter() {
       presenter = new SearchDeptPresenter(repository);
       presenter.onAttach(this);
       presenter.loadDataDept();
    }

    @Override
    public void showProgress() {
        rvSearchDept.setVisibility(View.INVISIBLE);
        pbSearchDept.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        rvSearchDept.setVisibility(View.VISIBLE);
        pbSearchDept.setVisibility(View.GONE);
    }

    @Override
    public void setDataDept(DepartmentResponse departmentResponse) {
        if (departmentResponse != null){
            setAdapter(departmentResponse);
        }
    }

    private void setAdapter(DepartmentResponse departmentResponse) {
        ArrayList<DepartmentItem> listDepartment = new ArrayList<>();
        listDepartment.clear();
        listDepartment.addAll(departmentResponse.getDepartment());
        adapter = new SearchDeptAdapter(departmentItem -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(DATA_SELECTED_VALUE, departmentItem);
            setResult(RESULT_CODE, resultIntent);
            finish();
        });
        adapter.setListDepartment(departmentResponse.getDepartment());

        rvSearchDept.setLayoutManager(new LinearLayoutManager(SearchDeptActivity.this));
        rvSearchDept.setHasFixedSize(true);
        rvSearchDept.setAdapter(adapter);

        searchingDept();
    }

    @Override
    public void setDataEmpty() {
        Toast.makeText(this, "DocData kosong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailed(String message) {
        Log.d("coba", "error : "+ message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttachView() {
        initRepository();
        initPresenter();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }
}
