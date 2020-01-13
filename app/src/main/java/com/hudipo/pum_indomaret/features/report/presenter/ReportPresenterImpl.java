package com.hudipo.pum_indomaret.features.report.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.hudipo.pum_indomaret.features.report.contract.ReportContract;
import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportPresenterImpl implements ReportContract.ReportPresenter {

    private ReportContract.ReportView reportView;
    private HawkStorage hawkStorage;
    private boolean writtenToDisk;
    private Uri fileUri;
    private HashMap<String, RequestBody> params = new HashMap<>();
    private ApiServices services;
    private Context context;

    public ReportPresenterImpl(Context context, ReportContract.ReportView reportView) {
        this.reportView = reportView;
        this.hawkStorage = new HawkStorage(context);
        this.services = new ApiServices();
        this.context = context;
    }

    @Override
    public void onDetach() {
        reportView = null;
    }

    @Override
    public void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        reportView.showDatePicker(day, month, year);
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        month = month + 1;
        String date = day + "/" + month + "/" + year;

        reportView.setDate(date);
    }

    @Override
    public void checkData(int reportType, String startDate, String endDate, String validStartDate, String validEndDate, String pumStatus, String respStatus, String groupBy) {
        reportView.showProgress();
        User user = hawkStorage.getUserData();
        RequestBody report_type = RequestBody.create(String.valueOf(reportType), MediaType.parse("text/plain"));
        RequestBody user_id = RequestBody.create(String.valueOf(user.getUserId()), MediaType.parse("text/plain"));
        RequestBody dept_id = RequestBody.create(String.valueOf(user.getDeptId()), MediaType.parse("text/plain"));
        RequestBody create_start_date = RequestBody.create(startDate, MediaType.parse("text/plain"));
        RequestBody create_end_date = RequestBody.create(endDate, MediaType.parse("text/plain"));
        RequestBody validate_start_date = RequestBody.create(validStartDate, MediaType.parse("text/plain"));
        RequestBody validate_end_date = RequestBody.create(validEndDate, MediaType.parse("text/plain"));
        RequestBody pum_status = RequestBody.create(pumStatus, MediaType.parse("text/plain"));
        RequestBody response_status = RequestBody.create(respStatus, MediaType.parse("text/plain"));
        RequestBody org_id = RequestBody.create(String.valueOf(user.getOrgId()), MediaType.parse("text/plain"));
        RequestBody group_by = RequestBody.create(groupBy, MediaType.parse("text/plain"));

        //reportView.toast(reportType+startDate+user.getUserId()+endDate+user.getOrgId()+validStartDate+user.getDeptId()+validEndDate+pumStatus+respStatus+groupBy);

//        RequestBody report_type = RequestBody.create(String.valueOf(1), MediaType.parse("text/plain"));
//        RequestBody user_id = RequestBody.create(String.valueOf(1719), MediaType.parse("text/plain"));
//        RequestBody dept_id = RequestBody.create(String.valueOf(1000), MediaType.parse("text/plain"));
//        RequestBody create_start_date= RequestBody.create("2019-09-01", MediaType.parse("text/plain"));
//        RequestBody create_end_date = RequestBody.create("2019-11-21", MediaType.parse("text/plain"));
//        RequestBody validate_start_date = RequestBody.create("2019-10-01", MediaType.parse("text/plain"));
//        RequestBody validate_end_date = RequestBody.create("2019-11-20", MediaType.parse("text/plain"));
//        RequestBody pum_status = RequestBody.create("ALL", MediaType.parse("text/plain"));
//        RequestBody response_status = RequestBody.create("ALL", MediaType.parse("text/plain"));
//        RequestBody org_id = RequestBody.create(String.valueOf(143013), MediaType.parse("text/plain"));
//        RequestBody group_by = RequestBody.create("-", MediaType.parse("text/plain"));

        params.put("report_type", report_type);
        params.put("user_id", user_id);
        params.put("dept_id", dept_id);
        params.put("create_start_date", create_start_date);
        params.put("create_end_date", create_end_date);
        params.put("validate_start_date", validate_start_date);
        params.put("validate_end_date", validate_end_date);
        params.put("pum_status", pum_status);
        params.put("response_status", response_status);
        params.put("org_id", org_id);
        params.put("group_by", group_by);


        Call<ResponseBody> call = services.getApiPumServices().getReportFile(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    reportView.hideProgress();
                    reportView.askToDownload();
                } else {
                    reportView.hideProgress();
                    reportView.toast("No data available!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                reportView.hideProgress();
                reportView.toast(t.getMessage());
            }
        });

    }

    @Override
    public void downloadData() {
        reportView.showProgress();
        Call<ResponseBody> call = services.getApiPumServices().getReportFile(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String header = response.headers().get("Content-Disposition");
                        assert header != null;
                        String fileName = header.replaceAll("attachment; filename=", "");
                        fileName = fileName.replace("\"", "");
                        writtenToDisk = writeResponseBodyToDisk(response.body(), fileName);
                        if (writtenToDisk) {
                            reportView.hideProgress();
                            reportView.showPdf(fileUri);
                        } else {
                            reportView.toast("Failed to save into memory!");
                        }
                    } else {
                        reportView.toast("Null Response Body!");
                    }
                } else reportView.toast("No data available!");
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                reportView.toast(t.getMessage());
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String fileName) {
        try {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;


            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("REPORT PRESENTER", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                //fileUri = Uri.fromFile(file);
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

}
