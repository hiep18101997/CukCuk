package com.misa.cukcuklite.screen.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.adddish.AddDishActivity;
import com.misa.cukcuklite.screen.addorder.AddOrderActivity;
import com.misa.cukcuklite.screen.menu.MenuFragment;
import com.misa.cukcuklite.screen.sale.SaleFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity implements IHomeContract.IView, View.OnClickListener {
    private static final String TAG = HomeActivity.class.getName();
    private static final int REQUEST_CODE = 914;
    private IHomeContract.IPresenter mPresenter;
    private DrawerLayout mDrawerLayout;
    private TextView tvTitle;
    private LinearLayout lnMenu, lnSale;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initComponent();
        initListener();
        setupToolbar();
        loadFragment(SaleFragment.newInstance());
    }

    private void initListener() {
        lnSale.setOnClickListener(this);
        lnMenu.setOnClickListener(this);
    }

    private void initComponent() {
        lnMenu = findViewById(R.id.lnMenu);
        lnSale = findViewById(R.id.lnSale);
        mDrawerLayout = findViewById(R.id.drawer);
        tvTitle = findViewById(R.id.titleToolbar);
        mPresenter = new HomePresenter(this);
    }

    /**
     * Mục dích method: Khởi tạo và cài đặt toolbar
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void setupToolbar() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionbar = getSupportActionBar();
            actionbar.setDisplayShowTitleEnabled(false);
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method: tạo button add
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    /**
     * Mục dích method: Bắt sự kiện click menu
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frHome);
                if (currentFragment instanceof MenuFragment) {
                    startActivity(AddDishActivity.getIntent(this));
                } else if (currentFragment instanceof SaleFragment) {
                    startActivity(AddOrderActivity.getIntent(this));
                }
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
                break;
        }
        return true;
    }

    private void loadFragment(Fragment fragment) {
        if (fragment instanceof MenuFragment) {
            tvTitle.setText(getString(R.string.menu));
        } else if (fragment instanceof SaleFragment) {
            tvTitle.setText(getString(R.string.sale));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.frHome, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnSale:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                loadFragment(SaleFragment.newInstance());
                break;
            case R.id.lnMenu:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                loadFragment(MenuFragment.newInstance());
                break;
        }
    }

}
