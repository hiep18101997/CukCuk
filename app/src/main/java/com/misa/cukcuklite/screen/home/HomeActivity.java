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
import com.misa.cukcuklite.screen.report.ReportFragment;
import com.misa.cukcuklite.screen.sale.SaleFragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * - Mục đích Class : Màn hình chính của ưng dụng
 * - @created_by Hoàng Hiệp on 4/5/2019
 */
public class HomeActivity extends AppCompatActivity implements IHomeContract.IView, View.OnClickListener {
    private static final String TAG = HomeActivity.class.getName();
    private static final int REQUEST_CODE = 914;
    private IHomeContract.IPresenter mPresenter;
    private DrawerLayout mDrawerLayout;
    private TextView tvTitle;
    private LinearLayout lnMenu, lnSale, lnReport;
    private boolean isVisible;

    /**
     * Mục đích method: Lấy Intent
     *
     * @param context Context
     * @return intent Intent
     * @created_by Hoàng Hiệp on 4/5/2019
     */
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
        loadFragment(MenuFragment.newInstance());
    }

    /**
     * Mục đích method: Bắt sự kiện click
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void initListener() {
        lnSale.setOnClickListener(this);
        lnMenu.setOnClickListener(this);
        lnReport.setOnClickListener(this);
    }

    /**
     * Mục đích method: Ánh xạ và khai báo view
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void initComponent() {
        lnMenu = findViewById(R.id.lnMenu);
        lnSale = findViewById(R.id.lnSale);
        lnReport = findViewById(R.id.lnReport);
        mDrawerLayout = findViewById(R.id.drawer);
        tvTitle = findViewById(R.id.titleToolbar);
        mPresenter = new HomePresenter(this);
    }

    /**
     * Mục đích method: Khởi tạo và cài đặt toolbar
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
     * Mục đích method: tạo button add
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frHome);
            getMenuInflater().inflate(R.menu.menu_add, menu);
            if (isVisible) {
                menu.findItem(R.id.action_add).setVisible(true);
            }else {
                menu.findItem(R.id.action_add).setVisible(false);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mục đích method: Bắt sự kiện click menu
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Mục đích method: Replace Fragment
     *
     * @param fragment Fragment cần thay thế
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    private void loadFragment(Fragment fragment) {
        try {
            if (fragment instanceof MenuFragment) {
                isVisible=true;
                tvTitle.setText(getString(R.string.menu));
            } else if (fragment instanceof SaleFragment) {
                isVisible=true;
                tvTitle.setText(getString(R.string.sale));
            } else if (fragment instanceof ReportFragment) {
                isVisible=false;
                tvTitle.setText(getString(R.string.report));
            }
            invalidateOptionsMenu();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.replace(R.id.frHome, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Xử lý sự kiện onClick
     *
     * @created_by Hoàng Hiệp on 4/5/2019
     */
    @Override
    public void onClick(View v) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frHome);
        try {
            switch (v.getId()) {
                case R.id.lnSale:
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    if (!(currentFragment instanceof SaleFragment)) {
                        loadFragment(SaleFragment.newInstance());
                    }
                    break;
                case R.id.lnMenu:
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    if (!(currentFragment instanceof MenuFragment)) {
                        loadFragment(MenuFragment.newInstance());
                    }
                    break;
                case R.id.lnReport:
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    if (!(currentFragment instanceof ReportFragment)) {
                        loadFragment(ReportFragment.newInstance());
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
