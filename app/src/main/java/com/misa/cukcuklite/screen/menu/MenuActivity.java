package com.misa.cukcuklite.screen.menu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.screen.adddish.AddDishActivity;
import com.misa.cukcuklite.screen.editdish.EditDishActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.misa.cukcuklite.AppConstant.ACTION_ADD_DISH;
import static com.misa.cukcuklite.AppConstant.ACTION_EDIT_DISH;
import static com.misa.cukcuklite.AppConstant.ACTION_REMOVE_DISH;

/**
 * ‐ Màn hình danh sách món ăn
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class MenuActivity extends AppCompatActivity implements IMenuContract.IView, MenuAdapter.OnItemClick {
    private static final String TAG = MenuActivity.class.getName();
    private IMenuContract.IPresenter mPresenter;
    private MenuAdapter mAdapter;
    private List<Dish> mDishes;
    private BroadcastReceiver mReceiver;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mPresenter = new MenuPresenter(this);
        mPresenter.getAllDish();
        setupToolbar();
        initComponent();
        initBroadcastReceiver();
    }

    /**
     * Mục dích method: Khởi tạo và đăng ki lắng nghe Broadcast
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initBroadcastReceiver() {
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
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
    }

    /**
     * Mục dích method: Khởi tạo và ánh xạ các View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComponent() {
        mDrawerLayout = findViewById(R.id.drawer);
        mDishes = new ArrayList<>();
        mPresenter = new MenuPresenter(this);
        mPresenter.getAllDish();
        RecyclerView recyclerView = findViewById(R.id.rvMenu);
        mAdapter = new MenuAdapter(this, mDishes, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Mục dích method: Khởi tạo và cài đặt toolbar
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    /**
     * Mục dích method: Chuyển vào màn Sửa món ăn khi click
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(Dish dish) {
        startActivity(EditDishActivity.getIntent(this, dish));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(AddDishActivity.getIntent(this));
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
                break;
        }
        return true;
    }

    /**
     * Mục dích method: Gọi adapter cập nhật lại data khi load thành công dữ liệu
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onLoadListDishSuccess(List<Dish> dishes) {
        mAdapter.addData(dishes);
    }

    /**
     * Mục dích method: Hủy đăng kí Broadcast
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
