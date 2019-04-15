package com.misa.cukcuklite.screen.sale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.Order;
import com.misa.cukcuklite.screen.addorder.AddOrderActivity;
import com.misa.cukcuklite.screen.bill.BillActivity;
import com.misa.cukcuklite.screen.dialogconfirm.ConfirmRemoveDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.utils.AppConstant.ACTION_ADD_ORDER;
import static com.misa.cukcuklite.utils.AppConstant.ACTION_EDIT_ORDER;

/**
 * - Mục đích Class :Màn hình bán hàng
 * - @created_by Hoàng Hiệp on 4/15/2019
 */
public class SaleFragment extends Fragment implements ISaleContract.IView, SaleAdapter.OnClickItem, View.OnClickListener {
    private static final String TAG = SaleFragment.class.getName();
    private ISaleContract.IPresenter mPresenter;
    private SaleAdapter mAdapter;
    private List<Order> mOrders;
    private BroadcastReceiver mReceiver;
    private ConstraintLayout clWaterMark;
    private TextView tvNotification2;

    public static SaleFragment newInstance() {
        return new SaleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComp();
        initBroadcastReceiver();
    }

    /**
     * Mục đích method: Khỏi tạo và ánh xạ các view
     *
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    private void initComp() {
        try {
            clWaterMark = getView().findViewById(R.id.clWaterMark);
            tvNotification2 = getView().findViewById(R.id.tvNotification2);
            tvNotification2.setOnClickListener(this);
            mOrders = new ArrayList<>();
            mPresenter = new SalePresenter(this, getContext());
            mPresenter.getAllOrder();
            mAdapter = new SaleAdapter(getContext(), mOrders, this);
            RecyclerView rvSale = getView().findViewById(R.id.rvSale);
            rvSale.setAdapter(mAdapter);
            rvSale.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Đăng kí lắng nghe BroadcastReceiver
     *
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    private void initBroadcastReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_ADD_ORDER);
            filter.addAction(ACTION_EDIT_ORDER);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    mPresenter.getAllOrder();
                }
            };
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, filter);
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
    public void onClickItem(Order order) {
        Log.d(TAG, "onClickItem: " + order.toString());
        startActivity(AddOrderActivity.getIntent(getContext(), order));
    }

    /**
     * Mục đích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClickCancel(final Order order) {
        try {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            ConfirmRemoveDialog inputDialog = new ConfirmRemoveDialog(getString(R.string.confirm_remove_order), new ConfirmRemoveDialog.OnClickAccept() {
                @Override
                public void onAccept() {
                    mPresenter.removeOrder(order);
                }
            });
            inputDialog.show(fragmentManager, getString(R.string.confirm_dialog));
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
    public void onClickTakeMoney(Order order) {
        startActivity(BillActivity.getIntent(getContext(), order));
    }

    /**
     * Mục đích method: Xử lý sự kiện khi load danh sách Order xong
     *
     * @param orders: danh sách order
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @Override
    public void onLoadListOrderSuccess(List<Order> orders) {
        if (orders.size() > 0) {
            mAdapter.addData(orders);
            clWaterMark.setVisibility(View.GONE);
        } else {
            clWaterMark.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Mục đích method: Load loai data khi xóa thành công 1 Order
     *
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @Override
    public void onRemoveOrderSuccess() {
        mPresenter.getAllOrder();
    }

    /**
     * Mục đích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNotification2:
                startActivity(AddOrderActivity.getIntent(getContext()));
                break;
        }
    }
}
