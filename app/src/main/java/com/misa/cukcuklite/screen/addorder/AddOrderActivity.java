package com.misa.cukcuklite.screen.addorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.PendingOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddOrderActivity extends AppCompatActivity implements IAddOrderContract.IView, AddOrderAdapter.OnClickItem, View.OnClickListener {
    private static final String TAG = AddOrderActivity.class.getName();
    private IAddOrderContract.IPresenter mPresenter;
    private AddOrderAdapter mAdapter;
    private List<Map.Entry<Dish, Integer>> list;
    private TextView tvTotalMoney;
    private PendingOrder mOrder;

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

    public static Intent getIntent(Context context, PendingOrder order) {
        Intent intent = new Intent(context, AddOrderActivity.class);
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
    }

    private void initComponent() {
        mPresenter = new AddOrderPresenter(this, this);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        list = new ArrayList<>();
        mOrder = new PendingOrder.Builder().setListDish(list).build();
        mPresenter.getMenu();
        mAdapter = new AddOrderAdapter(this, list, this);
        RecyclerView recyclerView = findViewById(R.id.rcvInventoryItems);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getItemAnimator().setChangeDuration(0);
    }

    @Override
    public void onClick(List<Map.Entry<Dish, Integer>> mList) {
        long amount = 0;
        for (Map.Entry<Dish, Integer> entry : mList) {
            amount += entry.getKey().getCost() * entry.getValue();
        }
        tvTotalMoney.setText(String.valueOf(amount));
    }

    @Override
    public void onLoadListDishSuccess(List<Map.Entry<Dish, Integer>> list) {
        mAdapter.setData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
                case R.id.btnSave:
                mPresenter.savePendingOrder(mOrder);
                break;
            case R.id.btnTakeMoney:
            case R.id.btnActionTakeMoney:

                break;
        }
    }
}
