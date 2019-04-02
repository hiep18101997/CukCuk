package com.misa.cukcuklite.screen.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.data.db.model.PendingOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SaleFragment extends Fragment implements ISaleContract.IView, SaleAdapter.OnClickItem {
    private static final String TAG = SaleFragment.class.getName();
    private ISaleContract.IPresenter mPresenter;
    private SaleAdapter mAdapter;
    private List<PendingOrder> mPendingOrders;

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
    }

    private void initComp() {
        mPendingOrders = new ArrayList<>();
        Dish dish=new Dish.Builder().setName("Thịt chó").build();
        HashMap<Dish,Integer> hashMap=new HashMap<>();
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        hashMap.put(dish,6);
        mPendingOrders.add(new PendingOrder(8,8,hashMap));
        mPendingOrders.add(new PendingOrder(8,8,hashMap));
        mPendingOrders.add(new PendingOrder(8,8,hashMap));
        mPendingOrders.add(new PendingOrder(8,8,hashMap));
        mPendingOrders.add(new PendingOrder(8,8,hashMap));
        mPendingOrders.add(new PendingOrder(8,8,hashMap));
        mPresenter = new SalePresenter(this);
        mAdapter = new SaleAdapter(getContext(), mPendingOrders, this);
        RecyclerView rvSale = getView().findViewById(R.id.rvSale);
        rvSale.setAdapter(mAdapter);
        rvSale.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onClickItem(PendingOrder order) {

    }
}
