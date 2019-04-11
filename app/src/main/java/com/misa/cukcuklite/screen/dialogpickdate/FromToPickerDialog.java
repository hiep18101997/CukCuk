package com.misa.cukcuklite.screen.dialogpickdate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.misa.cukcuklite.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static com.misa.cukcuklite.utils.AppConstant.DATE_FORMAT;

/**
 * - Mục đích Class :Dialog chọn từ ngày- đến ngày
 * - @created_by Hoàng Hiệp on 4/12/2019
 */
public class FromToPickerDialog extends DialogFragment implements View.OnClickListener {
    private TextView tvFromDateValue, tvToDateValue;
    private Date fromDate, toDate;
    private DateFormat dateFormat;
    private OnClickAcceptPickDate mOnClickAcceptPickDate;

    /**
     * Mục đích method: Set callback cho Dialog
     *
     * @param onClickAcceptPickDate
     * @created_by Hoàng Hiệp on 4/12/2019
     */
    public void setOnClickAcceptPickDate(OnClickAcceptPickDate onClickAcceptPickDate) {
        mOnClickAcceptPickDate = onClickAcceptPickDate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pick_date, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initView(view);
        initListener(view);
        initDefDate();
        return view;
    }

    /**
     * Mục đích method: Thay đổi kích cỡ dialog
     *
     * @created_by Hoàng Hiệp on 4/12/2019
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
     * Mục đích method: Khởi tạo giá trị mặc định
     *
     * @param
     * @return
     * @created_by Hoàng Hiệp on 4/12/2019
     */
    private void initDefDate() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            fromDate = calendar.getTime();
            tvFromDateValue.setText(formatDate(fromDate));
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.SECOND, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            toDate = calendar.getTime();
            tvToDateValue.setText(formatDate(toDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Format date về string
     *
     * @param date: ngày truyên vào
     * @return : dạng string format
     * @created_by Hoàng Hiệp on 4/12/2019
     */
    private String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Mục đích method: Bắt sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initListener(View view) {
        view.findViewById(R.id.lnFromDate).setOnClickListener(this);
        view.findViewById(R.id.lnToDate).setOnClickListener(this);
        view.findViewById(R.id.btnAcceptDialog).setOnClickListener(this);
        view.findViewById(R.id.btnCancelDialog).setOnClickListener(this);
    }

    /**
     * Mục đích method: Khởi tạo, ánh xạ View và đổ dữ liệu mặc định cho View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @SuppressLint("SimpleDateFormat")
    private void initView(View view) {
        try {
            tvToDateValue = view.findViewById(R.id.tvToDateValue);
            tvFromDateValue = view.findViewById(R.id.tvFromDateValue);
            dateFormat = new SimpleDateFormat(DATE_FORMAT);
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
    public void onClick(View v) {
        try {
            final Calendar calendar = Calendar.getInstance();
            switch (v.getId()) {
                case R.id.lnFromDate:
                    calendar.setTime(dateFormat.parse(tvFromDateValue.getText().toString()));
                    DatePickerDialog dialogFromDate = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            fromDate = calendar.getTime();
                            tvFromDateValue.setText(formatDate(fromDate));
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    dialogFromDate.show();
                    break;
                case R.id.lnToDate:
                    calendar.setTime(dateFormat.parse(tvToDateValue.getText().toString()));
                    DatePickerDialog dialogToDate = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            toDate = calendar.getTime();
                            tvToDateValue.setText(formatDate(toDate));
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    dialogToDate.show();
                    break;
                case R.id.btnAcceptDialog:
                    if (fromDate.compareTo(toDate) < 0) {
                        mOnClickAcceptPickDate.onPickDate(fromDate, toDate);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.msg_dialog_pick_date), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btnCancelDialog:
                    dismiss();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: interface bắt sự kiện
     *
     * @param
     * @return
     * @created_by Hoàng Hiệp on 4/12/2019
     */
    public interface OnClickAcceptPickDate {
        void onPickDate(Date fromDate, Date toDate);
    }

}
