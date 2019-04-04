package com.misa.cukcuklite.screen.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maltaisn.calcdialog.CalcDialog;
import com.maltaisn.calcdialog.CalcNumpadLayout;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.DishOrder;
import com.misa.cukcuklite.data.db.model.Order;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.AppConstant.EXTRA_ORDER;

public class BillActivity extends AppCompatActivity implements IBillContract.IView,
        View.OnClickListener ,CalcDialog.CalcDialogCallback{
    private static final String TAG = BillActivity.class.getName();
    private IBillContract.IPresenter mPresenter;
    private BillAdapter mAdapter;
    private Order mOrder;
    private LinearLayout lnCustomerAmount;
    private TextView tvTotalAmount, tvTableName, tvRefNo, tvRefDate,tvCustomerAmount,tvReturnAmount;

    /**
     * Mục dích method: Lấy intent
     *
     * @param context Context
     * @return Trả về intent trỏ tới AddDishActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public static Intent getIntent(Context context, Order order) {
        Intent intent = new Intent(context, BillActivity.class);
        intent.putExtra(EXTRA_ORDER, order);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        initComponent();
        initListener();
        showBill();
    }

    private void showBill() {
        tvTotalAmount.setText(String.valueOf(getAmount(mOrder.getListDish())));
        tvTableName.setText(String.valueOf(mOrder.getNumberTable()));
    }

    private void initListener() {
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnDone).setOnClickListener(this);
        findViewById(R.id.btnDoneBelow).setOnClickListener(this);
        lnCustomerAmount.setOnClickListener(this);
    }

    private void initComponent() {
        mOrder = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
        mPresenter = new BillPresenter(this);
        lnCustomerAmount = findViewById(R.id.lnCustomerAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTableName = findViewById(R.id.tvTableName);
        tvReturnAmount = findViewById(R.id.tvReturnAmount);
        tvCustomerAmount = findViewById(R.id.tvCustomerAmount);
        tvRefNo = findViewById(R.id.tvRefNo);
        tvRefDate = findViewById(R.id.tvRefDate);
        mAdapter = new BillAdapter(this, mOrder.getListDish());
        RecyclerView rcvBill = findViewById(R.id.rcvBill);
        rcvBill.setAdapter(mAdapter);
        rcvBill.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnDone:
            case R.id.btnDoneBelow:
                break;
            case R.id.lnCustomerAmount:
                showDialogCal(Long.parseLong(tvTotalAmount.getText().toString()));
                break;
        }
    }

    private void showDialogCal(long amount) {
        try {
            CalcDialog calcDialog = new CalcDialog();
            calcDialog.getSettings()
                    .setRequestCode(0)
                    .setInitialValue(null)
                    .setNumberFormat(NumberFormat.getInstance())
                    .setNumpadLayout(CalcNumpadLayout.CALCULATOR)
                    .setExpressionShown(false)
                    .setExpressionEditable(false)
                    .setMinValue(new BigDecimal(amount))
                    .setZeroShownWhenNoValue(true)
                    .setAnswerBtnShown(false)
                    .setSignBtnShown(true)
                    .setShouldEvaluateOnOperation(true)
                    .setOrderOfOperationsApplied(true);
            FragmentManager fm = getSupportFragmentManager();
            calcDialog.setCancelable(false);
            calcDialog.show(fm, getString(R.string.fragment_cal));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getAmount(List<DishOrder> dishOrders) {
        try {
            long amount = 0;
            for (DishOrder entry : dishOrders) {
                amount += entry.getDish().getCost() * entry.getCount();
            }
            return amount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        tvCustomerAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(value));
        tvReturnAmount.setText(String.valueOf(value.longValue()-Long.parseLong(tvTotalAmount.getText().toString())));
    }
}
