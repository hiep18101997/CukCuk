package com.misa.cukcuklite.screen.dialogicon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.adddish.AddDishActivity;
import com.misa.cukcuklite.screen.editdish.EditDishActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
/**
 * - Mục đích Class :Tạo dialog chon icon
 * - @created_by Hoàng Hiệp on 4/5/2019
 */
@SuppressLint("ValidFragment")
public class IconPickerDialog extends DialogFragment implements IconPickerAdapter.OnItemClick, View.OnClickListener {
    private RecyclerView rvIcon;
    private IconPickerAdapter adapter;
    private TextView tvCancel;
    private Activity mActivity;

    public IconPickerDialog(AddDishActivity addDishActivity) {
        mActivity = addDishActivity;
    }

    public IconPickerDialog(EditDishActivity editDishActivity) {
        mActivity = editDishActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_icon_fragment, container);
        getDialog().setTitle("Chọn icon");
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        tvCancel = rootView.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        try {
            String[] images = getContext().getAssets().list("images");
            List<String> listImages = new ArrayList<String>(Arrays.asList(images));
            rvIcon = rootView.findViewById(R.id.rvIcon);
            adapter = new IconPickerAdapter(getContext(), listImages, this);
            rvIcon.setAdapter(adapter);
            rvIcon.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    /**
     * Mục đích method: Bắt sự kiện click icon ở Dialog thì sẽ set icon cho Activity
     *
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    @Override
    public void onClick(String icon) {
        try {
            if (mActivity instanceof AddDishActivity) {
                ((AddDishActivity) mActivity).setIcon(icon);
            } else if (mActivity instanceof EditDishActivity) {
                ((EditDishActivity) mActivity).setIcon(icon);
            }
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }
}
