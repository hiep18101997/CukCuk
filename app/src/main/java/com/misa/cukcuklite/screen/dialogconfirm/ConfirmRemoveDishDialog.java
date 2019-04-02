package com.misa.cukcuklite.screen.dialogconfirm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.editdish.EditDishActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmRemoveDishDialog extends DialogFragment implements View.OnClickListener {
    private TextView tvOK, tvCancel;
    private ImageView ivClose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_confirm_remove_dish, container);
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
        tvOK.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        ivClose.setOnClickListener(this);
    }

    /**
     * Mục dích method: Khởi tạo, ánh xạ View
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void initComponent(View rootView) {
        tvOK = rootView.findViewById(R.id.tvOK);
        ivClose = rootView.findViewById(R.id.ivClose);
        tvCancel = rootView.findViewById(R.id.tvCancel);
    }

    /**
     * Mục dích method: Set kích cỡ cho dialog
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
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvOK:
                    ((EditDishActivity) getActivity()).removeDish();
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
}