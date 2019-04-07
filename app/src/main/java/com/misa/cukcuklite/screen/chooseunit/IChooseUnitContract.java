package com.misa.cukcuklite.screen.chooseunit;

import com.misa.cukcuklite.data.model.Unit;

import java.util.List;

/**
 * ‐ Contract  trong mô hình MVP cho màn hình Chọn đơn vị
 * <p>
 * ‐ @created_by Hoàng Hiệp on 3/23/2019
 */
interface IChooseUnitContract {
    interface IView {
        void onGetUnitSuccess(List<Unit> units);

        void onInsertUnitSuccess(int unit);

        void onInsertUnitError();

        void onEditUnitDone(int id);

        void onRemoveUnitSuccess();

        void onRemoveUnitError();
    }

    interface IPresenter {
        void getListUnit();

        void saveUnit(String text);

        void editUnit(Unit unitEdit);

        void removeUnit(Unit unit);
    }
}
