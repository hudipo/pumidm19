package com.hudipo.pum_indomaret.features.searchdept;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.SearchDeptAdapter;
import com.hudipo.pum_indomaret.model.departement.DepartmentItem;
import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.repository.Repository;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDeptActivity extends AppCompatActivity implements SearchDeptContract.SearchDeptView {

    private SearchDeptPresenter presenter;
    private Repository repository;

    @BindView(R.id.rvSearchDept)
    RecyclerView rvSearchDept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dept);
        ButterKnife.bind(this);

        onAttachView();
    }

    private void initRepository() {
        HawkStorage hawkStorage = new HawkStorage(this);
        repository = new Repository(hawkStorage);
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

    private void initPresenter() {
       presenter = new SearchDeptPresenter(repository);
       presenter.onAttach(this);
       presenter.loadDataDept();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDataDept(DepartmentResponse departmentResponse) {
        if (departmentResponse != null){
            setAdapter(departmentResponse);
        }
    }

    private void setAdapter(DepartmentResponse departmentResponse) {
        SearchDeptAdapter adapter = new SearchDeptAdapter(departmentItem -> {
            Toast.makeText(this, "item : " + departmentItem.getName(), Toast.LENGTH_SHORT).show();
        });
        adapter.setDepartmentResponse(departmentResponse);

        rvSearchDept.setLayoutManager(new LinearLayoutManager(SearchDeptActivity.this));
        rvSearchDept.setAdapter(adapter);
    }

    @Override
    public void setDataEmpty() {
        Toast.makeText(this, "Data kosong", Toast.LENGTH_SHORT).show();
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
