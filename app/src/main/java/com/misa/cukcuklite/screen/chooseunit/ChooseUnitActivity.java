package com.misa.cukcuklite.screen.chooseunit;

import static com.misa.cukcuklite.utils.AppConstant.ACTION_PICK_UNIT;
import static com.misa.cukcuklite.utils.AppConstant.EXTRA_PICK_UNIT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.model.Unit;
import com.misa.cukcuklite.screen.dialogconfirm.ConfirmRemoveDialog;
import com.misa.cukcuklite.screen.dialoginput.InputDialog;
import java.util.ArrayList;
import java.util.List;

/**
 * ‐ Màn hình chọn đơn vị
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/27/2019
 */
public class ChooseUnitActivity extends AppCompatActivity implements IChooseUnitContract.IView,
    ChooseUnitAdapter.OnClickUnit, View.OnClickListener {

  public static final String EXTRA_UNIT = "com.misa.cukcuklite.extra.unit";
  private static final String TAG = ChooseUnitActivity.class.getName();
  private IChooseUnitContract.IPresenter mPresenter;
  private ChooseUnitAdapter mAdapter;
  private String mStringUnit;
  private ArrayList<Unit> mUnits;
  private Unit mUnit;

  /**
   * Mục đích method: Tạo ra intent trỏ đến ChooseUnitActivity
   *
   * @param unit: đơn vị
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  public static Intent getIntent(Context context, String unit) {
    Intent intent = new Intent(context, ChooseUnitActivity.class);
    intent.putExtra(EXTRA_UNIT, unit);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_choose_unit);
    mPresenter = new ChooseUnitPresenter(this, this);
    initComponent();
    initListener();
  }

  /**
   * Mục đích method: bắt sự kiện click
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initListener() {
    findViewById(R.id.ivBack).setOnClickListener(this);
    findViewById(R.id.ivAdd).setOnClickListener(this);
    findViewById(R.id.tvDone).setOnClickListener(this);
  }

  /**
   * Mục đích method: Khởi tạo và ánh xạ các View
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initComponent() {
    try {
      mUnits = new ArrayList<>();
      mPresenter.getListUnit();
      mStringUnit = getIntent().getStringExtra(EXTRA_UNIT);
      mAdapter = new ChooseUnitAdapter(mUnits, this, this, mStringUnit);
      RecyclerView recyclerView = findViewById(R.id.rvUnit);
      recyclerView.setAdapter(mAdapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Xử lí khi View click Edit, hiển thị input Dialog
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onClickEdit(Unit unit) {
    try {
      FragmentManager fragmentManager = getSupportFragmentManager();
      InputDialog inputDialog = new InputDialog(unit.getName(), new InputDialog.OnClickAccept() {
        @Override
        public void onEmptyInput() {
          onEmptyInput();
        }

        @Override
        public void onSuccess(String s) {
          editUnit(s);
        }
      });
      inputDialog.show(fragmentManager, getString(R.string.input_dialogl));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Xử lí khi View click Remove, hiển thị dialog confirm
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onClickRemove(final Unit unit) {
    try {
      FragmentManager fragmentManager = getSupportFragmentManager();
      ConfirmRemoveDialog inputDialog = new ConfirmRemoveDialog(
          getString(R.string.confirm_remove_unit), new ConfirmRemoveDialog.OnClickAccept() {
        @Override
        public void onAccept() {
          removeUnit(unit);
        }
      });
      inputDialog.show(fragmentManager, getString(R.string.confirm_dialog));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Xử lí khi click
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onClick(View view) {
    try {
      switch (view.getId()) {
        case R.id.ivBack:
          onBackPressed();
          break;
        case R.id.ivAdd:
          FragmentManager fragmentManager = getSupportFragmentManager();
          InputDialog inputDialog = new InputDialog(new InputDialog.OnClickAccept() {
            @Override
            public void onEmptyInput() {
              onEmptyInput();
            }

            @Override
            public void onSuccess(String s) {
              saveUnit(s);
            }
          });
          inputDialog.show(fragmentManager, getString(R.string.input_dialogl));
          break;
        case R.id.tvDone:
          int unitId = mAdapter.getUnit().getId();
          Intent intent = new Intent(ACTION_PICK_UNIT);
          intent.putExtra(EXTRA_PICK_UNIT, unitId);
          LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
          finish();
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method load lại list khi lấy được dữ liệu
   *
   * @param units Danh sách các đơn vị tính
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onGetUnitSuccess(List<Unit> units) {
    mAdapter.addUnit(units);
  }

  /**
   * Mục đích method gửi Broadcast để load lại list sau khi thêm đơn vị tính thành công
   *
   * @param unitId Danh sách các đơn vị tính
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onInsertUnitSuccess(int unitId) {
    try {
      Intent intent = new Intent(ACTION_PICK_UNIT);
      intent.putExtra(EXTRA_PICK_UNIT, unitId);
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
      finish();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Hiện lỗi khi thêm đơn vị thất bại
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onInsertUnitError() {
    Toast.makeText(this, getString(R.string.error_unit_exist), Toast.LENGTH_SHORT).show();
  }

  /**
   * Mục đích method gửi Broadcast để load lại list sau khi sửa đơn vị tính thành công
   *
   * @param unitId Đơn vị tính được thêm
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onEditUnitDone(int unitId) {
    try {
      Intent intent = new Intent(ACTION_PICK_UNIT);
      intent.putExtra(EXTRA_PICK_UNIT, unitId);
      LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
      finish();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Cập nhật lại list khi xóa đơn vị tính thành công
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onRemoveUnitSuccess() {
    try {
      mAdapter.setIndexSelect(0);
      mPresenter.getListUnit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Hiện lỗi khi xóa đơn vị tính thất bại
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onRemoveUnitError() {
    Toast.makeText(this, "Đơn vị đang được sử dụng", Toast.LENGTH_SHORT).show();
  }

  /**
   * Mục đích method: Hàm gọi từ dialog để lưu đơn vị tính
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  public void saveUnit(String text) {
    try {
      mPresenter.saveUnit(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Hiện lỗi khi bỏ trống đơn vị tính
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  public void onEmptyInput() {
    Toast.makeText(this, getString(R.string.error_input_unit_empty), Toast.LENGTH_SHORT).show();
  }

  /**
   * Mục đích method gọi từ Dialog đẻ sửa đơn vị tính
   *
   * @param unit Đơn vị tính được sửa
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  public void editUnit(String unit) {
    try {
      Unit unitEdit = mAdapter.getUnit();
      unitEdit.setName(unit);
      mPresenter.editUnit(unitEdit);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method gọi từ Dialog đẻ xóa đơn vị tính
   *
   * @param unit Đơn vị tính bị xóa
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  public void removeUnit(Unit unit) {
    try {
      mPresenter.removeUnit(unit);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
