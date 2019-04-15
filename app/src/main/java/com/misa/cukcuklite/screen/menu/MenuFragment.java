package com.misa.cukcuklite.screen.menu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.Dish;
import com.misa.cukcuklite.screen.adddish.AddDishActivity;
import com.misa.cukcuklite.screen.editdish.EditDishActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.utils.AppConstant.ACTION_ADD_DISH;
import static com.misa.cukcuklite.utils.AppConstant.ACTION_EDIT_DISH;
import static com.misa.cukcuklite.utils.AppConstant.ACTION_REMOVE_DISH;

/**
 * ‐ Màn hình danh sách món ăn
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class MenuFragment extends Fragment implements IMenuContract.IView, MenuAdapter.OnItemClick, View.OnClickListener {
    private static final String TAG = MenuFragment.class.getName();
    private IMenuContract.IPresenter mPresenter;
    private MenuAdapter mAdapter;
    private List<Dish> mDishes;
    private BroadcastReceiver mReceiver;
    private DrawerLayout mDrawerLayout;
    private ConstraintLayout clWaterMark;
    private TextView tvNotification2;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent();
        initBroadcastReceiver();
    }

    /**
     * Mục đích method: Khởi tạo và đăng ki lắng nghe Broadcast
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initBroadcastReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_ADD_DISH);
            filter.addAction(ACTION_REMOVE_DISH);
            filter.addAction(ACTION_EDIT_DISH);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    mPresenter.getAllDish();
                }
            };
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Khởi tạo và ánh xạ các View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComponent() {
        try {
            clWaterMark = getView().findViewById(R.id.clWaterMark);
            tvNotification2 = getView().findViewById(R.id.tvNotification2);
            tvNotification2.setOnClickListener(this);
            mDrawerLayout = getView().findViewById(R.id.drawer);
            mDishes = new ArrayList<>();
            mPresenter = new MenuPresenter(this, getContext());
            mPresenter.getAllDish();
            RecyclerView recyclerView = getView().findViewById(R.id.rvMenu);
            mAdapter = new MenuAdapter(getContext(), mDishes, this);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Chuyển vào màn Sửa món ăn khi click
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(Dish dish) {
        startActivity(EditDishActivity.getIntent(getContext(), dish));
    }

    /**
     * Mục đích method: Gọi adapter cập nhật lại data khi load thành công dữ liệu
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onLoadListDishSuccess(List<Dish> dishes) {
        if (dishes.size() > 0) {
            mAdapter.addData(dishes);
            clWaterMark.setVisibility(View.GONE);
        } else {
            clWaterMark.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Mục đích method: Hủy đăng kí Broadcast
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
    }

    /**
     * Mục đích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tvNotification2:
                    startActivity(AddDishActivity.getIntent(getContext()));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
