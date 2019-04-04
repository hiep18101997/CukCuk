package com.misa.cukcuklite.screen.chooseunit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.misa.cukcuklite.R;
import com.misa.cukcuklite.data.db.model.Unit;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ‐ Adapter RecyclerView cho màn hình chọn đơn vị
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/23/2019
 */
public class ChooseUnitAdapter extends RecyclerView.Adapter<ChooseUnitAdapter.ViewHolder> {
    private List<Unit> mUnits;
    private Context mContext;
    private LayoutInflater mInflater;
    private int indexSelect;
    private OnClickUnit mOnClick;
    private String mUnit;

    public ChooseUnitAdapter(List<Unit> units, Context context, OnClickUnit onClick, String unit) {
        mUnits = units;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mOnClick = onClick;
        mUnit = unit;
    }

    /**
     * Mục đích method cập nhất vị trí item đang được tích
     *
     * @param unit Đơn vị tính được chọn
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void setIndexUnit(String unit) {
        if (unit == null) {
            indexSelect = 0;
            return;
        } else {
            for (int i = 0; i < mUnits.size(); i++) {
                if (mUnits.get(i).getName().equals(unit)) {
                    indexSelect = i;
                    return;
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_unit, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bindView(mUnits.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indexSelect = position;
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                indexSelect = position;
                showPopUp(view);
                return false;
            }
        });
    }

    /**
     * Mục đích method hiển thị popup dưới item view khi long click
     *
     * @param view itemview
     * @created_by Hoàng Hiệp on 3/27/2019
     */
    private void showPopUp(View view) {
        MenuBuilder menuBuilder = new MenuBuilder(mContext);
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.menu_popup_unit, menuBuilder);
        MenuPopupHelper optionsMenu = new MenuPopupHelper(mContext, menuBuilder, view);
        optionsMenu.setForceShowIcon(true);
        menuBuilder.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                mOnClick.onClickRemove(mUnits.get(indexSelect));
                return true;
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });
        optionsMenu.show();
    }

    @Override
    public int getItemCount() {
        return mUnits != null ? mUnits.size() : 0;
    }

    /**
     * Mục đích method lấy đơn vị đang được chọn
     *
     * @return trả về 1 đơn vị tính
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    public Unit getUnit() {
        return mUnits.get(indexSelect);
    }

    /**
     * Mục đích method set giá trị cho biến index
     *
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    public void setIndexSelect(int indexSelect) {
        this.indexSelect = indexSelect;
    }

    /**
     * Mục đích cập nhật lại khi dữ liểu đổ vào
     *
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    public void addUnit(List<Unit> units) {
        if (units == null) {
            return;
        }
        mUnits.clear();
        mUnits.addAll(units);
        setIndexUnit(mUnit);
        notifyDataSetChanged();
    }

    /**
     * Mục đích method tạo ra interface bắt onclick
     *
     * @created_by Hoàng Hiệp on 3/28/2019
     */
    interface OnClickUnit {
        void onClickEdit(Unit unit);

        void onClickRemove(Unit unit);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCheck, ivEdit;
        public TextView tvUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCheck = itemView.findViewById(R.id.ivCheck);
            tvUnit = itemView.findViewById(R.id.tvUnit);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }

        public void bindView(final Unit unit, final int position) {
            tvUnit.setText(unit.getName());
            if (position == indexSelect) {
                ivCheck.setVisibility(View.VISIBLE);
            } else {
                ivCheck.setVisibility(View.INVISIBLE);
            }
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    indexSelect = position;
                    mOnClick.onClickEdit(unit);
                }
            });
        }
    }
}
