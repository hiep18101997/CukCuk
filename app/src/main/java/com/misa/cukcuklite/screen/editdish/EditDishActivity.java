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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.Dish;
import com.misa.cukcuklite.data.model.Unit;
import com.misa.cukcuklite.screen.calculator.CalculatorFragment;
import com.misa.cukcuklite.screen.chooseunit.ChooseUnitActivity;
import com.misa.cukcuklite.screen.dialogconfirm.ConfirmRemoveDialog;
import com.misa.cukcuklite.screen.dialogicon.IconPickerDialog;
import com.thebluealliance.spectrum.SpectrumDialog;

import java.text.NumberFormat;
import java.util.Locale;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.misa.cukcuklite.utils.AppConstant.ACTION_PICK_UNIT;
import static com.misa.cukcuklite.utils.AppConstant.ACTION_REMOVE_DISH;
import static com.misa.cukcuklite.utils.AppConstant.EXTRA_DISH;
import static com.misa.cukcuklite.utils.AppConstant.EXTRA_PICK_UNIT;

/**
 * ‐ Màn hình sừa món ăn
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class EditDishActivity extends AppCompatActivity implements IEditDishContract.IView, View.OnClickListener {
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
    private int unitId;

    /**
     * Mục đích method: Lấy intent
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
     * Mục đích method: Khởi tạo và đăng kí lắng nghe Broadcast
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initBroadcastReceiver() {
        try {
            IntentFilter filter = new IntentFilter(ACTION_PICK_UNIT);
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int unit = intent.getIntExtra(EXTRA_PICK_UNIT, -1);
                    unitId = unit;
                    mPresenter.getNameUnitFromId(unit);
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
            findViewById(R.id.ivBack).setOnClickListener(this);
            findViewById(R.id.tvDone).setOnClickListener(this);
            findViewById(R.id.tvRemove).setOnClickListener(this);
            findViewById(R.id.tvSave).setOnClickListener(this);
            findViewById(R.id.lnCost).setOnClickListener(this);
            findViewById(R.id.lnUnit).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComps() {
        try {
            currentDish = (Dish) getIntent().getSerializableExtra(EXTRA_DISH);
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
     * Mục đích method: Load dữ liệu có sẵn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void loadDataExist() {
        try {
            unitId = currentDish.getUnitId();
            mPresenter.getNameUnitFromId(unitId);
            edtName.setText(currentDish.getName());
            tvUnit.setText(currentDish.getUnitName());
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
     * Mục đích method: Khởi tạo hiện dialog để chọn màu
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
     * Mục đích method: Khởi tạo hiện dialog để chọn màu
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showDialogPickIcon() {
        try {
            FragmentManager fm = getSupportFragmentManager();
            IconPickerDialog tv = new IconPickerDialog(this);
            tv.show(fm, "icon");
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
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.rlIconContainer1:
                    showDialogPickColor();
                    break;
                case R.id.rlIconContainer2:
                    showDialogPickIcon();
                    break;
                case R.id.lnCost:
                    showCalDialog();
                    break;
                case R.id.lnUnit:
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
                    currentDish.setUnitId(unitId);
                    currentDish.setSell(!mCheckBox.isChecked());
                    mPresenter.editDish(currentDish);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Phương thức hiển thị hộp thoại bàn phím nhập giá sản phẩm
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public void showCalDialog() {
        try {
            FragmentManager fm = getSupportFragmentManager();
            CalculatorFragment calculatorFragment = CalculatorFragment.createInstance(tvCost.getText().toString(), new CalculatorFragment.IOnClickDone() {
                @Override
                public void onClickDone(long price, String priceShow) {
                    setPrice(price, priceShow);
                }
            });
            calculatorFragment.show(fm, getString(R.string.dialog_calculator));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Hiển thị và thêm giá trị trả về của máy tính
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public void setPrice(long price, String priceShow) {
        currentDish.setCost((long) price);
        tvCost.setText(priceShow);
    }

    /**
     * Mục đích method: Hiển thị dialog xác nhận
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showConfirmDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ConfirmRemoveDialog inputDialog = new ConfirmRemoveDialog(getString(R.string.confirm_remove_dish), new ConfirmRemoveDialog.OnClickAccept() {
            @Override
            public void onAccept() {
                removeDish();
            }
        });
        inputDialog.show(fragmentManager, getString(R.string.confirm_dialog));
    }

    /**
     * Mục đích method: Thay đổi icon sau khi chọn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public void setIcon(String icon) {
        Glide.with(this).load(mPresenter.getBitmapFromAssets(this, icon)).into(ivProductIcon);
        currentDish.setIcon(icon);
    }

    /**
     * Mục đích method: Xóa món ăn
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    public void removeDish() {
        mPresenter.removeDish(currentDish);
    }

    /**
     * Mục đích method: Gửi Broadcast đến Menu để cập nhật lại list khi xóa thành công món
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onRemoveDishDone() {
        try {
            Intent intent = new Intent(ACTION_REMOVE_DISH);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Gửi Broadcast đến Menu để cập nhật lại list khi sửa thành công món
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onEditDishDone() {
        try {
            Intent intent = new Intent(ACTION_REMOVE_DISH);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetNameDone(Unit unit) {
        tvUnit.setText(unit.getName());
    }

    @Override
    public void onRemoveUnitError() {
        Toast.makeText(this, getString(R.string.used_dish), Toast.LENGTH_SHORT).show();
    }
}
