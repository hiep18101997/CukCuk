package com.misa.cukcuklite.screen.dialogconfirm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.misa.cukcuklite.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * - Mục đích Class :Dialog xác nhận
 * - @created_by Hoàng Hiệp on 4/5/2019
 */
@SuppressLint("ValidFragment")
public class ConfirmRemoveDialog extends DialogFragment implements View.OnClickListener {
    private String mMess;
    private TextView tvMess;
    private OnClickAccept mOnClickAccept;

    @SuppressLint("ValidFragment")
    public ConfirmRemoveDialog(String mess, OnClickAccept onClickAccept) {
        mMess = mess;
        mOnClickAccept = onClickAccept;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_confirm_remove, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        tvMess = rootView.findViewById(R.id.tvMess);
        tvMess.setText(mMess);
        rootView.findViewById(R.id.btnClose).setOnClickListener(this);
        rootView.findViewById(R.id.btnAcceptDialog).setOnClickListener(this);
        rootView.findViewById(R.id.btnCancelDialog).setOnClickListener(this);
        return rootView;
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
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnAcceptDialog:
                    mOnClickAccept.onAccept();
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

    public interface OnClickAccept {
        void onAccept();
    }
}
