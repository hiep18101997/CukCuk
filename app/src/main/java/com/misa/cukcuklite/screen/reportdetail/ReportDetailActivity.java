package com.misa.cukcuklite.screen.reportdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.ReportTotal;
import com.misa.cukcuklite.screen.addorder.AddOrderActivity;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.misa.cukcuklite.utils.AppConstant.EXTRA_REPORT_TOTAL;

public class ReportDetailActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Mục đích method: Lấy intent
     *
     * @param context     Context
     * @param reportTotal Đối tượng báo cáo
     * @return Trả về intent trỏ tới ReportDetailActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public static Intent getIntent(Context context, ReportTotal reportTotal) {
        Intent intent = new Intent(context, ReportDetailActivity.class);
        intent.putExtra(EXTRA_REPORT_TOTAL, reportTotal);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        initView();
        initListener();

    }
    /**
     * Mục đích method: Bắt sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initListener() {
        findViewById(R.id.ivBack).setOnClickListener(this);
    }
    /**
     * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initView() {
        ReportTotal reportTotal = (ReportTotal) getIntent().getSerializableExtra(EXTRA_REPORT_TOTAL);
        Date[] dates=new Date[2];
        dates[0]=reportTotal.getFromDate();
        dates[1]=reportTotal.getToDate();
        loadFragment(ReportDetailFragment.newInstance(dates));
    }

    /**
     * Mục đích method: Replace Fragment
     *
     * @param fragment Fragment cần thay thế
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void loadFragment(Fragment fragment) {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.rlContent, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Mục đích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
               finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
