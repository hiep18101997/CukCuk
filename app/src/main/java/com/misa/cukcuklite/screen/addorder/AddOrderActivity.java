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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.AppConstant.ACTION_ADD_ORDER;
import static com.misa.cukcuklite.AppConstant.ACTION_EDIT_ORDER;
import static com.misa.cukcuklite.AppConstant.EXTRA_ORDER;

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
     * Mục dích method: Lấy intent
     *
     * @param context Context
     * @return Trả về intent trỏ tới AddDishActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddOrderActivity.class);
        return intent;
    }

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

    private void initComponent() {
        mPresenter = new AddOrderPresenter(this, this);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tvTable = findViewById(R.id.tvTable);
        tvPerson = findViewById(R.id.tvPerson);
        currentOrder = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
        if (currentOrder != null) {
            showCurrentOrder();
            isEdit = true;
            list = new ArrayList<>();
            list.addAll(currentOrder.getListDish());
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
    }

    private void showCurrentOrder() {
        tvPerson.setText(String.valueOf(currentOrder.getNumberPerson()));
        tvTable.setText(String.valueOf(currentOrder.getNumberTable()));
        tvTotalMoney.setText(String.valueOf(getAmount(currentOrder.getListDish())));
    }

    @Override
    public void onClick(List<DishOrder> mList) {
        tvTotalMoney.setText(String.valueOf(getAmount(mList)));
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
    public void onLoadListDishSuccess(List<DishOrder> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onZeroPerson() {
        Toast.makeText(this, getString(R.string.msg_person_empty), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onZeroTable() {
        Toast.makeText(this, getString(R.string.msg_table_empty), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onZeroDish() {
        Toast.makeText(this, getString(R.string.msg_dish_empty), Toast.LENGTH_SHORT).show();
    }

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
    public void onClick(View v) {
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
                break;
            case R.id.tvTable:
                showDialogCalculator(FLAG_TABLE);
                break;
            case R.id.tvPerson:
                showDialogCalculator(FLAG_PERSON);
                break;

        }
    }

    /**
     * Mục dích method: Khởi tạo và hiện dialog máy tính
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
