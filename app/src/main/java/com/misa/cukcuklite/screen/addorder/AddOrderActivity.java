package com.misa.cukcuklite.screen.addorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.maltaisn.calcdialog.CalcDialog;
import com.maltaisn.calcdialog.CalcNumpadLayout;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.Order;
import com.misa.cukcuklite.screen.bill.BillActivity;
import com.misa.cukcuklite.screen.calculator.InputNumberFragment;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.utils.AppConstant.ACTION_ADD_ORDER;
import static com.misa.cukcuklite.utils.AppConstant.ACTION_EDIT_ORDER;
import static com.misa.cukcuklite.utils.AppConstant.EXTRA_ORDER;

public class AddOrderActivity extends AppCompatActivity implements IAddOrderContract.IView,
        AddOrderAdapter.OnClickItem, View.OnClickListener, CalcDialog.CalcDialogCallback {
    public static final int RESULT_OK = 1997;
    private static final String TAG = AddOrderActivity.class.getName();
    private static final int FLAG_TABLE = 0;
    private static final int FLAG_PERSON = 1;
    private IAddOrderContract.IPresenter mPresenter;
    private AddOrderAdapter mAdapter;
    private List<DishOrder> list;
    private TextView tvTotalMoney, tvPerson, tvTable;
    private boolean isEdit;
    private Order currentOrder;

    /**
     * Mục đích method: Lấy intent
     *
     * @param context Context
     * @return Trả về intent trỏ tới AddDishActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddOrderActivity.class);
        return intent;
    }

    /**
     * Mục đích method: Lấy intent
     *
     * @param context Context
     * @param order   Đối tượng bán hàng
     * @return Trả về intent trỏ tới AddDishActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public static Intent getIntent(Context context, Order order) {
        Intent intent = new Intent(context, AddOrderActivity.class);
        intent.putExtra(EXTRA_ORDER, order);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        initComponent();
        initListener();
    }

    /**
     * Mục đích method: Bắt sự kiện click
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void initListener() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.tvPerson).setOnClickListener(this);
        findViewById(R.id.tvTable).setOnClickListener(this);
        findViewById(R.id.btnTakeMoney).setOnClickListener(this);
        findViewById(R.id.btnActionTakeMoney).setOnClickListener(this);
        tvPerson.setOnClickListener(this);
        tvTable.setOnClickListener(this);
    }

    /**
     * Mục đích method: Ánh xạ khởi tạo các view
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void initComponent() {
        try {
            mPresenter = new AddOrderPresenter(this, this);
            tvTotalMoney = findViewById(R.id.tvTotalMoney);
            tvTable = findViewById(R.id.tvTable);
            tvPerson = findViewById(R.id.tvPerson);
            currentOrder = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
            if (currentOrder != null) {
                showCurrentOrder();
                isEdit = true;
                list = new ArrayList<>();
                list.addAll(currentOrder.getOrders());
            } else {
                isEdit = false;
                list = new ArrayList<>();
                mPresenter.getMenu();
            }
            mAdapter = new AddOrderAdapter(this, list, this);
            RecyclerView recyclerView = findViewById(R.id.rcvInventoryItems);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.getItemAnimator().setChangeDuration(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method:Hiển thị thông tin bán hàng sẵn có
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void showCurrentOrder() {
        tvPerson.setText(String.valueOf(currentOrder.getNumberPerson()));
        tvTable.setText(String.valueOf(currentOrder.getNumberTable()));
        tvTotalMoney.setText(String.valueOf(getAmount(list)));
    }

    /**
     * Mục đích method:Xử ;ý sự kiện onclick
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onClick(List<DishOrder> mList) {
        tvTotalMoney.setText(String.valueOf(getAmount(mList)));
    }

    /**
     * Mục đích method:Xử lý sự kiện onclick
     *
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
     * Mục đích method:Callback trả về khi lấy lish món ăn thành công
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onLoadListDishSuccess(List<DishOrder> list) {
        mAdapter.setData(list);
    }

    /**
     * Mục đích method:Call back trả về khi người dùng không nhập số người
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onZeroPerson() {
        Toast.makeText(this, getString(R.string.msg_person_empty), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method:Call back trả về khi người dùng không nhập số bàn
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onZeroTable() {
        Toast.makeText(this, getString(R.string.msg_table_empty), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method:Call back trả về khi người dùng không chọn món
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onZeroDish() {
        Toast.makeText(this, getString(R.string.msg_dish_empty), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục đích method:Call back trả về khi lưu đối tượng bán hàng thành công
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onSaveOrderDone() {
        try {
            Intent intent = new Intent(ACTION_ADD_ORDER);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method:Call back trả về khi sửa đối tượng bán hàng thành công
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onEditOrderDone() {
        try {
            Intent intent = new Intent(ACTION_EDIT_ORDER);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void navigateBillActivity(Order mOrder) {
        startActivity(BillActivity.getIntent(this, mOrder));
    }

    /**
     * Mục đích method:Sử ly sự kiện khi click
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.imgBack:
                    onBackPressed();
                    break;
                case R.id.btnSave:
                    if (isEdit) {
                        mPresenter.editOrder(currentOrder.getId(), tvTable.getText().toString(), tvPerson.getText().toString(), mAdapter.getList());
                    } else {
                        mPresenter.saveOrder(tvTable.getText().toString(), tvPerson.getText().toString(), mAdapter.getList());
                    }
                    break;
                case R.id.btnTakeMoney:
                case R.id.btnActionTakeMoney:
                    if (currentOrder != null) {
                        startActivity(BillActivity.getIntent(this, currentOrder));
                    } else {
                        mPresenter.takeMoney(tvTable.getText().toString(), tvPerson.getText().toString(), mAdapter.getList());
                    }
                    break;
                case R.id.tvTable:
                    InputNumberFragment inputNumberFragment=new InputNumberFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    inputNumberFragment.show(fm, getString(R.string.fragment_cal));
                    break;
                case R.id.tvPerson:
                    showDialogCalculator(FLAG_PERSON);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Khởi tạo và hiện dialog máy tính
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showDialogCalculator(int flag) {
        try {
            CalcDialog calcDialog = new CalcDialog();
            calcDialog.getSettings()
                    .setRequestCode(flag)
                    .setInitialValue(null)
                    .setNumberFormat(NumberFormat.getInstance())
                    .setNumpadLayout(CalcNumpadLayout.CALCULATOR)
                    .setExpressionShown(false)
                    .setExpressionEditable(false)
                    .setMinValue(new BigDecimal(0))
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
     * Mục đích method:Call back trả về khi người dùng ấn Ok dialog máy tính
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        try {
            switch (requestCode) {
                case FLAG_PERSON:
                    tvPerson.setText(NumberFormat.getNumberInstance(Locale.US).format(value));
                    break;
                case FLAG_TABLE:
                    tvTable.setText(NumberFormat.getNumberInstance(Locale.US).format(value));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
