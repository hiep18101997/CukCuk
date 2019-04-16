package com.misa.cukcuklite.screen.calculator;

import static com.misa.cukcuklite.utils.AppConstant.JSON_ASSETS;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.calculator.model.InputKeys;
import com.misa.cukcuklite.screen.calculator.model.Operators;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Màn hình bàn phím nhập đơn vị tính
 *
 * @created_by Hoàng Hiệp on 3/28/2019
 */
public class CalculatorFragment extends DialogFragment {

  private static final String NUMBER_COLUMN = "numberColumn";
  private static final String TEXT_INPUT = "textInput";
  private static final String DATA = "data";
  private static final String ID = "id";
  private static final String NAME = "name";
  private static IOnClickDone mIOnClickDone;
  RecyclerView rcvKeyboard;

  CalculatorAdapter mCalculatorAdapter;

  EditText etInputNumber;

  ArrayList<InputKeys> mDatasets;

  ArrayList<Operators> operators;

  ImageView ivButtonCloseKeyboard;

  private String textInput = "";


  public CalculatorFragment() {

  }

  /**
   * Mục đích method: Hàm khởi tạo Dialog bàn phím
   *
   * @param textInput: giá trị ban đầu
   * @param mIOnClickDone: Call back
   * @return calculatorFragment: đối tượng máy tính
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  public static CalculatorFragment createInstance(String textInput, IOnClickDone mIOnClickDone) {
    try {
      CalculatorFragment calculatorFragment = new CalculatorFragment();
      Bundle bundle = new Bundle();
      bundle.putInt(NUMBER_COLUMN, 4);
      bundle.putString(TEXT_INPUT, textInput);
      calculatorFragment.setArguments(bundle);
      calculatorFragment.setOnClickDone(mIOnClickDone);
      return calculatorFragment;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.dialog_fragment_keyboard, container);
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

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    try {
      createItems();
      mCalculatorAdapter = new CalculatorAdapter(mDatasets);
      setOnClickItem();
      rcvKeyboard = view.findViewById(R.id.rcvKeyboard);
      etInputNumber = view.findViewById(R.id.etInputNumber);
      ivButtonCloseKeyboard = view.findViewById(R.id.ivButtonCloseKeyboard);
      Bundle bundle = getArguments();
      int numberColumn = 4;
      if (bundle != null) {
        if (bundle.containsKey(NUMBER_COLUMN)) {
          numberColumn = bundle.getInt(NUMBER_COLUMN);
        }
        if (bundle.containsKey(TEXT_INPUT)) {
          textInput = bundle.getString(TEXT_INPUT);
          if (textInput != null && textInput.isEmpty()) {
            etInputNumber.setSelection(etInputNumber.getText().length());
            etInputNumber.selectAll();
            return;
          }
          etInputNumber.setText(textInput);
          etInputNumber.setSelection(etInputNumber.getText().length());
          etInputNumber.selectAll();
        }
      }
      GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), numberColumn);
      rcvKeyboard.setLayoutManager(gridLayoutManager);
      rcvKeyboard.setAdapter(mCalculatorAdapter);
      operators = new ArrayList<>();
      ivButtonCloseKeyboard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          dismiss();
        }
      });
      etInputNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
          if (b) {
            hideKeyboard();
          }
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Phương thức ẩn bàn phím
   *
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  private void hideKeyboard() {
    try {
      InputMethodManager imm = (InputMethodManager) getActivity()
          .getSystemService(Activity.INPUT_METHOD_SERVICE);
      View view = getActivity().getCurrentFocus();
      if (view == null) {
        view = new View(getActivity());
      }
      if (imm != null) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Khởi tạo giá trị cho danh sách bàn phím
   *
   * @created_by Hoàng Hiệp on 3/26/2019
   */
  private void createItems() {
    try {
      mDatasets = new ArrayList<>();
      String jsonString = loadJSONFromAsset(getContext(), JSON_ASSETS);
      if (jsonString != null) {
        try {
          JSONObject jsonObject = new JSONObject(jsonString);
          JSONArray datas = jsonObject.getJSONArray(DATA);
          for (int i = 0; i < datas.length(); i++) {
            JSONObject o = (JSONObject) datas.get(i);
            mDatasets.add(new InputKeys(o.getInt(ID), o.getString(NAME)));
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        for (int i = 0; i < 10; i++) {
          mDatasets.add(new InputKeys((i + 1), String.valueOf(i)));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Phương thức gán sự kiện nhập phím trên màn hình
   *
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  private void setOnClickItem() {
    try {
      mCalculatorAdapter.setOnClickListener(new CalculatorAdapter.OnclickInputKey() {
        @Override
        public void onClickItem(int id) {
          onChangeText(id);
        }


      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Phương thức xử lí sự kiện nhập giá
   *
   * @param id - mã bàn phím đã nhập
   */
  private void onChangeText(int id) {
    try {
      String text = textInput;
      String textString = etInputNumber.getText().toString();

      switch (id) {
        case 1: // Clear
          etInputNumber.setText("0");
          textInput = "";
          break;
        case 2: // Giảm
          if (getNumberInput(text) < 1) {
            return;
          }
          addValue(String.valueOf(getNumberInput(text) - 1));
          break;
        case 3: // Tăng
          addValue(String.valueOf(getNumberInput(text) + 1));
          break;
        case 4: // Back space
          if (textString.length() == 1) {
            etInputNumber.setText("0");
            textInput = "";
          } else {
            textString = textString.substring(0, textString.length() - 1);
            etInputNumber.setText(textString);
            etInputNumber.setSelection(etInputNumber.getText().length());
          }
          break;
        case 5: // 7
          text = text + "7";
          addValue(text);
          break;
        case 6: // 8
          text = text + "8";
          addValue(text);
          break;
        case 7: // 9
          text = text + "9";
          addValue(text);
          break;
        case 8: // -
          if (textString.charAt(textString.length() - 1) == '-') { // Đằng trước là 1 dấu -
            return;
          } else if (textString.charAt(textString.length() - 1) == '+') { // Đằng trước là 1 dấu +
            text = textString.substring(0, textString.length() - 1);
            text = text + "-";
            etInputNumber.setText(text);
            etInputNumber.setSelection(etInputNumber.getText().length());
            return;
          } else {
            operators.add(new Operators(1, getNumberInput(text)));
            textInput = "";
            text = textString + "-";
            etInputNumber.setText(text);
            etInputNumber.setSelection(etInputNumber.getText().length());

          }
          break;
        case 9: // 4
          text = text + "4";
          addValue(text);
          break;
        case 10: // 5
          text = text + "5";
          addValue(text);
          break;
        case 11: // 6
          text = text + "6";
          addValue(text);
          break;
        case 12: // +
          if (textString.charAt(textString.length() - 1) == '+') { // Đằng trước là 1 dấu +
            return;
          } else if (textString.charAt(textString.length() - 1) == '-') { // Đằng trước là 1 dấu -
            text = textString.substring(0, textString.length() - 1);
            text = text + "+";
            etInputNumber.setText(text);
            etInputNumber.setSelection(etInputNumber.getText().length());
            return;
          } else {
            operators.add(new Operators(2, getNumberInput(text)));
            textInput = "";
            text = textString + "+";
            etInputNumber.setText(text);
            etInputNumber.setSelection(etInputNumber.getText().length());
          }
          break;
        case 13: // 1
          text = text + "1";
          addValue(text);
          break;
        case 14: // 2
          text = text + "2";
          addValue(text);
          break;
        case 15: // 3
          text = text + "3";
          addValue(text);
          break;
        case 16: // +/-
          if (text.indexOf("-") == 0) {
            text = text.substring(1, text.length());
            etInputNumber.setText(text);
          } else {
            text = "-" + text;
            etInputNumber.setText(text);
          }
          break;
        case 17: // 0
          text = text + "0";
          addValue(text);
          break;
        case 18: // 000
          text = text + "000";
          addValue(text);
          break;
        case 19: // ,

          break;
        case 20: // Xong
          onCaculate();

          break;
        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Phương thức tính giá trị phép tính và kết thúc nhập
   *
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  private void onCaculate() {
    try {
      if (operators.size() > 0) {
        String txt = etInputNumber.getText().toString();
        txt = txt.replaceAll(",", "");
        Expression expression = new ExpressionBuilder(txt).build();
        try {
          // Calculate the result and display
          double result = expression.evaluate();
          textInput = formatAmount((long) result);
          etInputNumber.setText(textInput);
          operators.clear();

          mCalculatorAdapter.onChangelabel();
        } catch (Exception ex) {
          ex.fillInStackTrace();
        }
      } else {
        long price = getNumberInput(etInputNumber.getText().toString());
        if (price < 0) {
          Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_money),
              Toast.LENGTH_SHORT).show();
        } else {
          mIOnClickDone.onClickDone(price, etInputNumber.getText().toString());
          dismiss();
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * Phương thức thêm giá trị vào input
   *
   * @param text - Chuỗi đang nhập
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  private void addValue(String text) {
    try {

      textInput = formatAmount(getNumberInput(text));

      if (textInput.length() > 19) {
        return;
      }

      String values = getStringBefore() + textInput;
      etInputNumber.setText(values);
      etInputNumber.setSelection(etInputNumber.getText().length());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Phương thức giá trị số từ chuỗi đã nhập
   *
   * @param text - Chuỗi đã nhập
   * @return Giá trị số
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  private long getNumberInput(String text) {
    try {
      if (text.isEmpty()) {
        return 0;
      }
      text = text.replaceAll("\\,", "");
      return Long.parseLong(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Định dạng số thành 1 chuỗi có dấu phẩy ngăn cách
   *
   * @param num - giá trị số muốn đổi
   * @return Chuỗi string đã xử lý
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  private String formatAmount(long num) {
    try {
      DecimalFormat decimalFormat = new DecimalFormat();
      DecimalFormatSymbols decimalFormateSymbol = new DecimalFormatSymbols();
      decimalFormateSymbol.setGroupingSeparator(',');
      decimalFormat.setDecimalFormatSymbols(decimalFormateSymbol);
      return decimalFormat.format(num);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Chuỗi string đã lưu vào mảng trước toán tử gần nhất
   *
   * @return Chuỗi đã xử lý
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  public String getStringBefore() {
    if (operators == null || operators.size() == 0) {
      return "";
    } else {
      StringBuilder text = new StringBuilder();
      for (Operators o : operators) {
        text.append(formatAmount(o.getValue())).append(o.getId() == 1 ? "-" : "+");
      }
      return text.toString();
    }
  }

  /**
   * Phương thức thiết lập sự kiện callback khi người dùng chọn button "Xong"
   *
   * @param mIOnClickDone Sự kiện callback
   * @created_by Hoàng Hiệp on 3/28/2019
   */
  public void setOnClickDone(IOnClickDone mIOnClickDone) {
    CalculatorFragment.mIOnClickDone = mIOnClickDone;
  }

  /**
   * Phương thức dùng để đọc file Json từ thư mục assets
   *
   * @param context - Context
   * @param fileName - Tên file - @created_by Hoàng Hiệp on 3/13/2019 - @modified_by Hoàng Hiệp on
   * 3/13/2019 ‐ Cập nhật, bổ sung
   */
  public String loadJSONFromAsset(Context context, String fileName) {
    String json = null;
    try {
      InputStream inputStream = context.getAssets().open(fileName);

      int size = inputStream.available();

      byte[] buffer = new byte[size];

      inputStream.read(buffer);

      inputStream.close();

      json = new String(buffer, "UTF-8");

    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return json;

  }

  /**
   * Interface cho sự kiện callback
   */
  public interface IOnClickDone {

    void onClickDone(long price, String priceShow);
  }
}
