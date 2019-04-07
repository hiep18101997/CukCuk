package com.misa.cukcuklite.screen.dialoginput;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.misa.cukcuklite.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * - Mục đích Class :Tạo dialog input
 * - @created_by Hoàng Hiệp on 4/5/2019
 */
public class InputDialog extends DialogFragment implements View.OnClickListener {
    private Button btnAcceptDialog, btnCancelDialog;
    private EditText edtIput;
    private String input;
    private boolean mIsEdit;
    private ImageView ivClose;
    private OnClickAccept mClickAccept;

    public InputDialog() {
    }

    @SuppressLint("ValidFragment")
    public InputDialog(OnClickAccept onClickAccept) {
        mClickAccept = onClickAccept;
    }

    @SuppressLint("ValidFragment")
    public InputDialog(String string, OnClickAccept onClickAccept) {
        input = string;
        mClickAccept = onClickAccept;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add_edit_unit, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initComponent(rootView);
        initListener();
        return rootView;
    }

    /**
     * Mục đích method: Bắt sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initListener() {
        btnAcceptDialog.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btnCancelDialog.setOnClickListener(this);
    }

    /**
     * Mục đích method: Khởi tạo, ánh xạ View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComponent(View rootView) {
        try {
            btnAcceptDialog = rootView.findViewById(R.id.btnAcceptDialog);
            ivClose = rootView.findViewById(R.id.btnClose);
            btnCancelDialog = rootView.findViewById(R.id.btnCancelDialog);
            edtIput = rootView.findViewById(R.id.etUnitName);
            edtIput.requestFocus();
            if (!TextUtils.isEmpty(input)) {
                edtIput.setText(input);
                edtIput.setSelection(edtIput.getText().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnAcceptDialog:
                    if (!validateInput()) {
                        mClickAccept.onEmptyInput();
                        return;
                    } else {
                        mClickAccept.onSuccess(edtIput.getText().toString());
                    }
                    dismiss();
                    break;
                case R.id.btnCancelDialog:
                    dismiss();
                    break;
                case R.id.btnClose:
                    dismiss();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method thực hiện việc kiểm tra dữ liệu nhập vào
     *
     * @return trả về xem dữ liệu hợp lẹ hay không
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private boolean validateInput() {
        try {
            if (TextUtils.isEmpty(edtIput.getText())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public interface OnClickAccept {
        void onEmptyInput();

        void onSuccess(String s);
    }
}
