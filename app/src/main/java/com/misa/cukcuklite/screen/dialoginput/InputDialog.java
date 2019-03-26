package com.misa.cukcuklite.screen.dialoginput;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.chooseunit.ChooseUnitActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InputDialog extends DialogFragment implements View.OnClickListener {
    private TextView tvSave, tvCancel;
    private EditText edtIput;
    private String input;
    private boolean mIsEdit;
    private ImageView ivClose;

    public InputDialog() {
        mIsEdit = false;
    }

    @SuppressLint("ValidFragment")
    public InputDialog(String string, boolean isEdit) {
        input = string;
        mIsEdit = isEdit;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_input_unit, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initComponent(rootView);
        initListener();
        return rootView;
    }

    /**
     * Mục dích method: Bắt sự kiện
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initListener() {
        tvSave.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    /**
     * Mục dích method: Khởi tạo, ánh xạ View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComponent(View rootView) {
        try {
            tvSave = rootView.findViewById(R.id.tvOK);
            ivClose = rootView.findViewById(R.id.ivClose);
            tvCancel = rootView.findViewById(R.id.tvCancel);
            edtIput = rootView.findViewById(R.id.edtInput);
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
                case R.id.tvOK:
                    if (!validateInput()) {
                        ((ChooseUnitActivity) getActivity()).onEmptyInput();
                        return;
                    }
                    if (mIsEdit) {
                        ((ChooseUnitActivity) getActivity()).editUnit(edtIput.getText().toString());
                    } else {
                        ((ChooseUnitActivity) getActivity()).saveUnit(edtIput.getText().toString());
                    }
                    dismiss();
                    break;
                case R.id.tvCancel:
                    dismiss();
                    break;
                case R.id.ivClose:
                    dismiss();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục dích method thực hiện việc kiểm tra dữ liệu nhập vào
     *
     * @return trả về xem dữ liệu hợp lẹ hay không
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private boolean validateInput() {
        if (TextUtils.isEmpty(edtIput.getText())) {
            return false;
        }
        return true;
    }
}
