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

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Order;
import com.misa.cukcuklite.screen.addorder.AddOrderActivity;
import com.misa.cukcuklite.screen.bill.BillActivity;
import com.misa.cukcuklite.screen.dialogconfirm.ConfirmRemoveDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.AppConstant.ACTION_ADD_ORDER;
import static com.misa.cukcuklite.AppConstant.ACTION_EDIT_ORDER;

public class SaleFragment extends Fragment implements ISaleContract.IView, SaleAdapter.OnClickItem {
    private static final String TAG = SaleFragment.class.getName();
    private ISaleContract.IPresenter mPresenter;
    private SaleAdapter mAdapter;
    private List<Order> mOrders;
    private BroadcastReceiver mReceiver;

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

    private void initComp() {
        try {
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

    @Override
    public void onClickItem(Order order) {
        Log.d(TAG, "onClickItem: " + order.toString());
        startActivity(AddOrderActivity.getIntent(getContext(), order));
    }

    @Override
    public void onClickCancel(final Order order) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ConfirmRemoveDialog inputDialog = new ConfirmRemoveDialog(getString(R.string.confirm_remove_order), new ConfirmRemoveDialog.OnClickAccept() {
            @Override
            public void onAccept() {
                mPresenter.removeOrder(order);
            }
        });
        inputDialog.show(fragmentManager, getString(R.string.confirm_dialog));
    }

    @Override
    public void onClickTakeMoney(Order order) {
<<<<<<< HEAD
        startActivity(BillActivity.getIntent(getContext(), order));
=======
        startActivity(BillActivity.getIntent(getContext(),order));
>>>>>>> 2f7d879e85c848c4f6f943621a1da7dc261ada73
    }

    @Override
    public void onLoadListOrderSuccess(List<Order> orders) {
        mAdapter.addData(orders);
    }

    @Override
    public void onRemoveOrderSuccess() {
        mPresenter.getAllOrder();
    }
}
