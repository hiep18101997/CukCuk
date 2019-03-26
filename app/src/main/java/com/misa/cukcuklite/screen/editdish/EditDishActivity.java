package com.misa.cukcuklite.screen.editdish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maltaisn.calcdialog.CalcDialog;
import com.maltaisn.calcdialog.CalcNumpadLayout;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Dish;
import com.misa.cukcuklite.screen.chooseunit.ChooseUnitActivity;
import com.misa.cukcuklite.screen.dialogconfirm.ConfirmRemoveDishDialog;
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

import static com.misa.cukcuklite.AppConstant.ACTION_PICK_UNIT;
import static com.misa.cukcuklite.AppConstant.ACTION_REMOVE_DISH;
import static com.misa.cukcuklite.AppConstant.EXTRA_DISH;
import static com.misa.cukcuklite.AppConstant.EXTRA_PICK_UNIT;

/**
 * ‐ Màn hình sừa món ăn
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class EditDishActivity extends AppCompatActivity implements IEditDishContract.IView, View.OnClickListener, CalcDialog.CalcDialogCallback {
    private static final String TAG = EditDishActivity.class.getName();
    private IEditDishContract.IPresenter mPresenter;
    private RelativeLayout rlLayoutColor, rlLayoutIcon;
    private ImageView ivProductIcon;
    private TextView tvCost, tvUnit;
    private EditText edtName;
    private int selectedColor;
    private BroadcastReceiver mReceiver;
    private Dish currentDish;
    private CheckBox mCheckBox;

    /**
     * Mục dích method: Lấy intent
     *
     * @param context Context
     * @return Trả về intent trỏ tới EditDishActivity
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public static Intent getIntent(Context context, Dish dish) {
        Intent intent = new Intent(context, EditDishActivity.class);
        intent.putExtra(EXTRA_DISH, dish);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dish);
        mPresenter = new EditDishPresenter(this);
        initBroadcastReceiver();
        initComps();
        initListener();
    }

    /**
     * Mục dích method: Khởi tạo và đăng kí lắng nghe Broadcast
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(ACTION_PICK_UNIT);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String unit = intent.getStringExtra(EXTRA_PICK_UNIT);
                tvUnit.setText(unit);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
    }

    /**
     * Mục dích method: Bắt sự kiện
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
            findViewById(R.id.tvRemove).setOnClickListener(this);
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
            currentDish = getIntent().getParcelableExtra(EXTRA_DISH);
            tvCost = findViewById(R.id.tvCost);
            tvUnit = findViewById(R.id.tvUnit);
            mCheckBox = findViewById(R.id.cbState);
            edtName = findViewById(R.id.edtName);
            ivProductIcon = findViewById(R.id.ivProductIcon2);
            rlLayoutColor = findViewById(R.id.rlIconContainer1);
            rlLayoutIcon = findViewById(R.id.rlIconContainer2);
            loadDataExist();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method: Load dữ liệu có sẵn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void loadDataExist() {
        try {
            edtName.setText(currentDish.getName());
            tvUnit.setText(currentDish.getUnit());
            tvCost.setText(NumberFormat.getNumberInstance(Locale.US).format(currentDish.getCost()));
            Drawable drawableBg = getResources().getDrawable(R.drawable.bg_circle);
            drawableBg.setColorFilter(currentDish.getColor(), PorterDuff.Mode.SRC);
            rlLayoutColor.setBackground(drawableBg);
            rlLayoutIcon.setBackground(drawableBg);
            selectedColor = currentDish.getColor();
            Glide.with(this).load(mPresenter.getBitmapFromAssets(this, currentDish.getIcon())).into(ivProductIcon);
            mCheckBox.setChecked(!currentDish.isSell());
        } catch (Resources.NotFoundException e) {
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
                    .setSelectedColor(selectedColor)
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
                                currentDish.setColor(color);
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
     * Mục dích method: Khởi tạo và hiện dialog máy tính
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showDialogCalculator() {
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
        calcDialog.show(fm, "fragment_cal");
    }

    /**
     * Mục dích method: Khởi tạo hiện dialog để chọn màu
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showDialogPickIcon() {
        FragmentManager fm = getSupportFragmentManager();
        IconPickerDialog tv = new IconPickerDialog(this);
        tv.show(fm, "icon");
    }

    /**
     * Mục dích method: Xử lý sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(View view) {
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
            case R.id.tvRemove:
                showConfirmDialog();
                break;
            case R.id.tvDone:
            case R.id.tvSave:
                currentDish.setName(edtName.getText().toString());
                currentDish.setUnit(tvUnit.getText().toString());
                currentDish.setSell(!mCheckBox.isChecked());
                mPresenter.editDish(currentDish);
                break;
        }
    }

    /**
     * Mục dích method: Hiển thị dialog xác nhận
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showConfirmDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ConfirmRemoveDishDialog inputDialog = new ConfirmRemoveDishDialog();
        inputDialog.show(fragmentManager, "confirm_dialog");
    }

    @Override
    public void onValueEntered(int requestCode, @Nullable BigDecimal value) {
        currentDish.setCost(value.longValue());
        tvCost.setText(NumberFormat.getNumberInstance(Locale.US).format(value));
    }

    /**
     * Mục dích method: Thay đổi icon sau khi chọn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public void setIcon(String icon) {
        Glide.with(this).load(mPresenter.getBitmapFromAssets(this, icon)).into(ivProductIcon);
        currentDish.setIcon(icon);
    }

    /**
     * Mục dích method: Xóa món ăn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public void removeDish() {
        mPresenter.removeDish(currentDish);
    }

    /**
     * Mục dích method: Gửi Broadcast đến Menu để cập nhật lại list khi xóa thành công món
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onRemoveDishDone() {
        Intent intent = new Intent(ACTION_REMOVE_DISH);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }

    /**
     * Mục dích method: Gửi Broadcast đến Menu để cập nhật lại list khi sửa thành công món
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onEditDishDone() {
        Intent intent = new Intent(ACTION_REMOVE_DISH);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        finish();
    }
}
