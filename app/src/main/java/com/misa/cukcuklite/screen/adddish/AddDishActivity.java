package com.misa.cukcuklite.screen.adddish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maltaisn.calcdialog.CalcDialog;
import com.maltaisn.calcdialog.CalcNumpadLayout;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.screen.chooseunit.ChooseUnitActivity;
import com.misa.cukcuklite.screen.dialogicon.IconPickerDialog;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.misa.cukcuklite.AppConstant.ACTION_ADD_DISH;
import static com.misa.cukcuklite.AppConstant.ACTION_PICK_UNIT;
import static com.misa.cukcuklite.AppConstant.EXTRA_PICK_UNIT;

/**
 * - Mục đích Class : Thêm món ăn
 * <p>
 * - @created_by Hoàng Hiệp on 3/27/2019
 */
public class AddDishActivity extends AppCompatActivity implements IAddDishContract.IView,
        View.OnClickListener, CalcDialog.CalcDialogCallback {
    private static final String TAG = AddDishActivity.class.getName();
    private static final int COLOR_DEF = -14235942;
    private static final String ICON_DEF = "ic_def";
    private RelativeLayout rlLayoutColor, rlLayoutIcon;
    private ImageView ivProductIcon;
    private TextView tvCost, tvUnit;
    private EditText edtName;
    private IAddDishContract.IPresenter mPresenter;
    private int selectedColor;
    private BroadcastReceiver mReceiver;
    private Dish.Builder mBuilder;

    /**
     * Mục dích method: Lấy intent
     *
     * @param context Context
     * @return Trả về intent trỏ tới AddDishActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AddDishActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        mPresenter = new AddDishPresenter(this, this);
        initComps();
        initListener();
        initBroadcastReceiver();
    }

    /**
     * Mục dích method: Khởi tạo và đăng kí lắng nghe Broadcast
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initBroadcastReceiver() {
        try {
            IntentFilter filter = new IntentFilter(ACTION_PICK_UNIT);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String unit = intent.getStringExtra(EXTRA_PICK_UNIT);
                    mBuilder.setUnit(unit);
                    tvUnit.setText(unit);
                }
            };
            LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Bắt sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initListener() {
        try {
            rlLayoutColor.setOnClickListener(this);
            rlLayoutIcon.setOnClickListener(this);
            tvCost.setOnClickListener(this);
            tvUnit.setOnClickListener(this);
            findViewById(R.id.ivBack).setOnClickListener(this);
            findViewById(R.id.tvDone).setOnClickListener(this);
            findViewById(R.id.tvSave).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComps() {
        try {
            mBuilder = new Dish.Builder();
            mBuilder.setColor(COLOR_DEF);
            mBuilder.setIcon(ICON_DEF);
            mBuilder.setSell(true);
            tvCost = findViewById(R.id.tvCost);
            tvUnit = findViewById(R.id.tvUnit);
            edtName = findViewById(R.id.edtName);
            ivProductIcon = findViewById(R.id.ivProductIcon2);
            rlLayoutColor = findViewById(R.id.rlIconContainer1);
            rlLayoutIcon = findViewById(R.id.rlIconContainer2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method: Khởi tạo hiện dialog để chọn màu
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    void showDialogPickColor() {
        try {
            SpectrumDialog dialog = new SpectrumDialog.Builder(this)
                    .setTitle(getString(R.string.pick_color))
                    .setSelectedColor(selectedColor != 0 ? selectedColor : COLOR_DEF)
                    .setPositiveButtonText(getString(R.string.ok))
                    .setNegativeButtonText(getString(R.string.cancel))
                    .setColors(R.array.arr_colors)
                    .setDismissOnColorSelected(false)
                    .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                            if (positiveResult) {
                                Drawable drawableBg = getResources().getDrawable(R.drawable.bg_circle);
                                drawableBg.setColorFilter(color, PorterDuff.Mode.SRC);
                                rlLayoutColor.setBackground(drawableBg);
                                rlLayoutIcon.setBackground(drawableBg);
                                selectedColor = color;
                                mBuilder.setColor(color);
                            } else {
                            }
                        }
                    }).build();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), getString(R.string.fragment_picker));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.rlIconContainer1:
                    showDialogPickColor();
                    break;
                case R.id.rlIconContainer2:
                    showDialogPickIcon();
                    break;
                case R.id.tvCost:
                    showDialogCalculator();
                    break;
                case R.id.tvUnit:
                    startActivity(ChooseUnitActivity.getIntent(this, tvUnit.getText().toString()));
                    break;
                case R.id.ivBack:
                    onBackPressed();
                    break;
                case R.id.tvDone:
                case R.id.tvSave:
                    mBuilder.setName(edtName.getText().toString());
                    Dish dish = mBuilder.build();
                    mPresenter.addDish(dish);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method: Khởi tạo và hiện dialog máy tính
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showDialogCalculator() {
        try {
            CalcDialog calcDialog = new CalcDialog();
            calcDialog.getSettings()
                    .setRequestCode(0)
                    .setInitialValue(null)
                    .setNumberFormat(NumberFormat.getInstance())
                    .setNumpadLayout(CalcNumpadLayout.CALCULATOR)
                    .setExpressionShown(false)
                    .setExpressionEditable(false)
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
     * Mục dích method: Khởi tạo và hiện dialog chọn icon
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showDialogPickIcon() {
        try {
            FragmentManager fm = getSupportFragmentManager();
            IconPickerDialog tv = new IconPickerDialog(this);
            tv.show(fm, getString(R.string.icon_fragment));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method: Thay đổi icon sau khi chọn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public void setIcon(String icon) {
        try {
            Glide.with(this).load(mPresenter.getBitmapFromAssets(this, icon)).into(ivProductIcon);
            mBuilder.setIcon(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        try {
            mBuilder.setCost(value.longValue());
            tvCost.setText(NumberFormat.getNumberInstance(Locale.US).format(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    /**
     * Mục dích method: Báo lỗi khi không nhập tên món
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onEmptyName() {
        Toast.makeText(this, getString(R.string.error_empty_name), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục dích method: Báo lỗi vì không nhập đơn vị
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onEmptyUnit() {
        Toast.makeText(this, getString(R.string.error_empty_unit), Toast.LENGTH_SHORT).show();
    }

    /**
     * Mục dích method: Lắng nghe khi thêm món thành công
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onAddDishDone() {
        try {
            Intent intent = new Intent(ACTION_ADD_DISH);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
