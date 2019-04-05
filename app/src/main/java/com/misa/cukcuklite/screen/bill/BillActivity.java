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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.AppConstant.EXTRA_ORDER;

/**
 * - Mục đích Class : Activity của màn hóa đơn
 * - @created_by Hoàng Hiệp on 4/5/2019
 */
public class BillActivity extends AppCompatActivity implements IBillContract.IView,
        View.OnClickListener, CalcDialog.CalcDialogCallback {
    private static final String TAG = BillActivity.class.getName();
    private IBillContract.IPresenter mPresenter;
    private BillAdapter mAdapter;
    private Order mOrder;
    private LinearLayout lnCustomerAmount;
    private TextView tvTotalAmount, tvTableName, tvRefNo, tvRefDate, tvCustomerAmount, tvReturnAmount;

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

    /**
     * Mục đích method: Hiển thị hóa đơn từ đối tượng truyền sang
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void showBill() {
        try {
            tvTotalAmount.setText(String.valueOf(getAmount(mOrder.getListDish())));
            tvTableName.setText(String.valueOf(mOrder.getNumberTable()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: bắt sựu kiện click
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void initListener() {
        try {
            findViewById(R.id.btnBack).setOnClickListener(this);
            findViewById(R.id.btnDone).setOnClickListener(this);
            findViewById(R.id.btnDoneBelow).setOnClickListener(this);
            lnCustomerAmount.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Ánh xạ và khởi tạo các view
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void initComponent() {
        try {
            mOrder = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
            mPresenter = new BillPresenter(this);
            lnCustomerAmount = findViewById(R.id.lnCustomerAmount);
            tvTotalAmount = findViewById(R.id.tvTotalAmount);
            tvTableName = findViewById(R.id.tvTableName);
            tvReturnAmount = findViewById(R.id.tvReturnAmount);
            tvCustomerAmount = findViewById(R.id.tvCustomerAmount);
            tvRefNo = findViewById(R.id.tvRefNo);
            tvRefDate = findViewById(R.id.tvRefDate);
            mAdapter = new BillAdapter(this, getOderDish(mOrder.getListDish()));
            RecyclerView rcvBill = findViewById(R.id.rcvBill);
            rcvBill.setAdapter(mAdapter);
            rcvBill.setLayoutManager(new LinearLayoutManager(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<DishOrder> getOderDish(List<DishOrder> listDish) {
        List<DishOrder> dishOrders = new ArrayList<>();
        for (DishOrder dishOrder : listDish) {
            if (dishOrder.getQuantity() != 0) {
                dishOrders.add(dishOrder);
            }
        }
        return dishOrders;
    }

    /**
     * Mục đích method: Xử lý sự kiện click
     *
     * @param
     * @return
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onClick(View v) {
        try {
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: hiện thị dialog máy tính
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
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

    /**
     * Mục đích method: Tính tổng số tiền
     *
     * @param dishOrders danh sách món ăn đã đặt
     * @return amount tổng số tiền
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private long getAmount(List<DishOrder> dishOrders) {
        try {
            long amount = 0;
            for (DishOrder entry : dishOrders) {
                amount += entry.getDish().getCost() * entry.getQuantity();
            }
            return amount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Mục đích method: Tính toán tiền thừa khi bấm ok Dialog máy tính
     *
     * @param value       số trả về
     * @param requestCode code
     * @return amount tổng số tiền
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        try {
            tvCustomerAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(value));
            tvReturnAmount.setText(String.valueOf(value.longValue() - Long.parseLong(tvTotalAmount.getText().toString())));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

