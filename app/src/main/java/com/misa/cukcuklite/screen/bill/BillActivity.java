package com.misa.cukcuklite.screen.bill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.Order;
import com.misa.cukcuklite.screen.addorder.AddOrderActivity;
import com.misa.cukcuklite.screen.calculator.InputNumberFragment;
import com.misa.cukcuklite.screen.dialogbillcal.BillCalculatorDialog;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.utils.AppConstant.ACTION_EDIT_ORDER;
import static com.misa.cukcuklite.utils.AppConstant.DATE_FORMAT;
import static com.misa.cukcuklite.utils.AppConstant.EXTRA_ORDER;

/**
 * - Mục đích Class : Activity của màn hóa đơn
 * - @created_by Hoàng Hiệp on 4/5/2019
 */
public class BillActivity extends AppCompatActivity implements IBillContract.IView,
        View.OnClickListener {
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

    /**
     * Mục dích method: Hàm khởi tạo
     *
     * @return Trả về intent trỏ tới AddDishActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
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
            tvTotalAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(getAmount(mOrder.getOrders())));
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
            mPresenter = new BillPresenter(this, this);
            lnCustomerAmount = findViewById(R.id.lnCustomerAmount);
            tvTotalAmount = findViewById(R.id.tvTotalAmount);
            tvTableName = findViewById(R.id.tvTableName);
            tvReturnAmount = findViewById(R.id.tvReturnAmount);
            tvCustomerAmount = findViewById(R.id.tvCustomerAmount);
            tvRefNo = findViewById(R.id.tvRefNo);
            tvRefDate = findViewById(R.id.tvRefDate);
            mAdapter = new BillAdapter(this, getOderDish(mOrder.getOrders()));
            RecyclerView rcvBill = findViewById(R.id.rcvBill);
            rcvBill.setAdapter(mAdapter);
            rcvBill.setLayoutManager(new LinearLayoutManager(this));
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            tvRefDate.setText(dateFormat.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Lấy danh danh sách món có số lượng !=0
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
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
                    mPresenter.saveBill(mOrder, (long) NumberFormat.getNumberInstance(Locale.US).parse(tvTotalAmount.getText().toString()));
                    break;
                case R.id.lnCustomerAmount:
                    showDialogCal((long) NumberFormat.getNumberInstance(Locale.US).parse(tvTotalAmount.getText().toString()));
                    break;
            }
        } catch (Exception e) {
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
            BillCalculatorDialog billCalculatorDialog = new BillCalculatorDialog(new InputNumberFragment.DialogCallBack() {
                @Override
                public void setAmount(String amount) {
                    tvCustomerAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(amount)));
                    try {
                        tvReturnAmount.setText(NumberFormat.getNumberInstance(Locale.US).format(Long.parseLong(amount) - (long) NumberFormat.getNumberInstance(Locale.US).parse(tvTotalAmount.getText().toString())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            FragmentManager fm = getSupportFragmentManager();
            billCalculatorDialog.setCancelable(false);
            billCalculatorDialog.show(fm, getString(R.string.fragment_cal));
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
     * Mục đích method: Trả về khi lưu hóa đơn thành công
     *
     * @created_by Hoàng Hiệp on 4/12/2019
     */
    @Override
    public void onSaveBillDone() {
        try {
            finish();
            Intent intent1 = new Intent(ACTION_EDIT_ORDER);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
            Intent intent = new Intent(this, AddOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

