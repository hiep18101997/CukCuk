package com.misa.cukcuklite.screen.calculator;

import static com.misa.cukcuklite.screen.addorder.AddOrderActivity.FLAG_PERSON;
import static com.misa.cukcuklite.screen.addorder.AddOrderActivity.FLAG_TABLE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.misa.cukcuklite.R;

public class InputNumberFragment extends DialogFragment implements View.OnClickListener {

  private TextView tvTitle;
  private EditText etInputNumber;
  private String textInput;
  private int flag;
  private DialogCallBack mCallBack;

  /**
   * Mục đích method: Hàm khởi tạo Dialog bàn phím
   *
   * @param flag: Cờ phân loại nhập số người hay số bàn
   * @param callBack: Call back
   * @param input: giá trị đầu
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  @SuppressLint("ValidFragment")
  public InputNumberFragment(int flag, DialogCallBack callBack, CharSequence input) {
    this.flag = flag;
    mCallBack = callBack;
    if (TextUtils.isEmpty(input)) {
      textInput = "";
    } else {
      textInput = String.valueOf(input);
    }
  }

  public InputNumberFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.dialog_keyboard_number_table_person, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    getDialog().setCancelable(false);
    getDialog().setCanceledOnTouchOutside(false);
    initView(rootView);
    initListener(rootView);
    return rootView;
  }

  /**
   * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initView(View rootView) {
    try {
      etInputNumber = rootView.findViewById(R.id.edtScreen);
      tvTitle = rootView.findViewById(R.id.tvTitle);
      switch (flag) {
        case FLAG_TABLE:
          tvTitle.setText(getString(R.string.title_table));
          break;
        case FLAG_PERSON:
          tvTitle.setText(getString(R.string.title_person));
          break;
      }
      showResult();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Bắt sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initListener(View rootView) {
    rootView.findViewById(R.id.btnClose).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey0).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey1).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey2).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey3).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey4).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey5).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey6).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey7).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey8).setOnClickListener(this);
    rootView.findViewById(R.id.btnKey9).setOnClickListener(this);
    rootView.findViewById(R.id.btnKeyBack).setOnClickListener(this);
    rootView.findViewById(R.id.btnKeyAccept).setOnClickListener(this);
    rootView.findViewById(R.id.btnKeyMinus).setOnClickListener(this);
    rootView.findViewById(R.id.btnKeyPlus).setOnClickListener(this);
    rootView.findViewById(R.id.btnKeyClear).setOnClickListener(this);
  }

  /**
   * Mục đích method: Set kích cỡ cho dialog
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onResume() {
    super.onResume();
    try {
      ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
      params.width = ViewGroup.LayoutParams.MATCH_PARENT;
      params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
      getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Xử lý sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @SuppressLint("SetTextI18n")
  @Override
  public void onClick(View v) {
    try {
      int number;
      switch (v.getId()) {
        case R.id.btnKey0:
          showResult(getString(R.string.key0));
          break;
        case R.id.btnKey1:
          showResult(getString(R.string.key1));
          break;
        case R.id.btnKey2:
          showResult(getString(R.string.key2));
          break;
        case R.id.btnKey3:
          showResult(getString(R.string.key3));
          break;
        case R.id.btnKey4:
          showResult(getString(R.string.key4));
          break;
        case R.id.btnKey5:
          showResult(getString(R.string.key5));
          break;
        case R.id.btnKey6:
          showResult(getString(R.string.key6));
          break;
        case R.id.btnKey7:
          showResult(getString(R.string.key7));
          break;
        case R.id.btnKey8:
          showResult(getString(R.string.key8));
          break;
        case R.id.btnKey9:
          showResult(getString(R.string.key9));
          break;
        case R.id.btnKeyBack:
          if (textInput.length() > 0) {
            textInput = textInput.substring(0, textInput.length() - 1);
            showResult();
          }
          break;
        case R.id.btnKeyAccept:
          mCallBack.setAmount(textInput);
          dismiss();
          break;
        case R.id.btnKeyMinus:
          if (TextUtils.isEmpty(textInput)) {
            textInput = "0";
          } else {
            number = Integer.parseInt(textInput);
            if (number > 0) {
              number--;
              textInput = number + "";
            }
          }
          showResult();
          break;
        case R.id.btnKeyPlus:
          if (TextUtils.isEmpty(textInput)) {
            textInput = "1";
          } else {
            number = Integer.parseInt(textInput);
            number++;
            textInput = number + "";
          }
          showResult();
          break;
        case R.id.btnKeyClear:
          textInput = "";
          showResult();
          break;
        case R.id.btnClose:
          dismiss();
          break;
      }
      etInputNumber.setText(textInput);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create by Hoàng Hiệp on 4/4/2019 Hiển thị số lượng
   */
  private void showResult() {
    try {
      etInputNumber.setText(textInput);
      etInputNumber.setSelection(textInput.length());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Create by Hoàng Hiệp on 4/4/2019 Hiển thị số lượng
   *
   * @param s thông tin số lượng
   */
  private void showResult(String s) {
    try {
      if (!TextUtils.isEmpty(s)) {
        textInput = textInput.concat(s);
        etInputNumber.setText(textInput);
        etInputNumber.setSelection(textInput.length());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Interface Call back
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  public interface DialogCallBack {

    void setAmount(String amount);
  }
}
