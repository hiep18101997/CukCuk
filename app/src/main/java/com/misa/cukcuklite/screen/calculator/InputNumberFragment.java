package com.misa.cukcuklite.screen.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.misa.cukcuklite.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class InputNumberFragment extends DialogFragment implements View.OnClickListener {
    private EditText edtScreen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_keyboard_number_table_person, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initView(rootView);
        initListener(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        edtScreen = rootView.findViewById(R.id.edtScreen);
    }

    private void initListener(View rootView) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnKey0:

                break;
            case R.id.btnKey1:
                break;
            case R.id.btnKey2:
                break;
            case R.id.btnKey3:
                break;
            case R.id.btnKey4:
                break;
            case R.id.btnKey5:
                break;
            case R.id.btnKey6:
                break;
            case R.id.btnKey7:
                break;
            case R.id.btnKey8:
                break;
            case R.id.btnKey9:
                break;
            case R.id.btnKeyBack:
                break;
            case R.id.btnKeyAccept:
                break;
            case R.id.btnKeyMinus:
                break;
            case R.id.btnKeyPlus:
                break;
            case R.id.btnKeyClear:
                break;
        }
    }
}
