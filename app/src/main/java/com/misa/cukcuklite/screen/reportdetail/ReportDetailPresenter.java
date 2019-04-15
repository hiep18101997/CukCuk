package com.misa.cukcuklite.screen.reportdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.misa.cukcuklite.data.db.DatabaseClient;
import com.misa.cukcuklite.data.model.Bill;
import com.misa.cukcuklite.data.model.Dish;
import com.misa.cukcuklite.data.model.DishOrder;
import com.misa.cukcuklite.data.model.ReportDetail;
import com.misa.cukcuklite.data.model.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * ‐ Presenter trong mô hình MVP cho màn hình Báo cáo chi tiết
 * ‐ @created_by Hoàng Hiệp on 4/15/2019
 */
public class ReportDetailPresenter implements IReportDetailContract.IPresenter {
    private static final String TAG = ReportDetailPresenter.class.getName();
    private Context mContext;
    private IReportDetailContract.IView mView;

    public ReportDetailPresenter(Context context, IReportDetailContract.IView view) {
        mView = view;
        mContext = context;
    }

    /**
     * Mục đích method: Laod dữ liệu từ 2 mốc thời gian
     *
     * @param dates: mảng 2 mốc thời gian
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void loadData(final Date[] dates) {
        try {
            new AsyncTask<Void, Void, List<ReportDetail>>() {
                @Override
                protected List<ReportDetail> doInBackground(Void... voids) {
                    List<ReportDetail> reportDetails = null;
                    try {
                        List<Bill> bills = DatabaseClient.getInstance(mContext).getAppDatabase().mBillDAO().getBillBetweenDate(dates[0], dates[1]);
                        reportDetails = new ArrayList<>();
                        for (Bill bill : bills) {
                            List<DishOrder> dishOrders = DatabaseClient.getInstance(mContext).getAppDatabase().mDishOrderDAO().getDishOrderByOrderId(bill.getOrderId());
                            for (DishOrder dishOrder : dishOrders) {
                                Dish dish = DatabaseClient.getInstance(mContext).getAppDatabase().mDishDAO().getDishById(dishOrder.getDishId());
                                Unit unit = DatabaseClient.getInstance(mContext).getAppDatabase().mUnitDAO().getUnitById(dish.getUnitId());
                                String name = dish.getName();
                                String unitName = unit.getName();
                                int quantity = dishOrder.getQuantity();
                                long amount = dishOrder.getCost();
                                ReportDetail reportDetail = new ReportDetail.Builder().setAmount(amount).setName(name).setQuantity(quantity).setUnit(unitName).build();
                                if (quantity!=0){
                                    reportDetails.add(reportDetail);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return makeListBeautiful(reportDetails);
                }

                @Override
                protected void onPostExecute(List<ReportDetail> reportDetails) {
                    super.onPostExecute(reportDetails);
                    mView.onLoadDataDone(reportDetails);
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mục đích method: Sắp xếp lại list
     *
     * @param reportDetails: mảng truyền vào
     * @created_by Hoàng Hiệp on 4/15/2019
     */
    private List<ReportDetail> makeListBeautiful(List<ReportDetail> reportDetails) {
        List<ReportDetail> list = new ArrayList<>();
        for (int i = 0; i < reportDetails.size(); i++) {
            ReportDetail reportDetail = reportDetails.get(i);
            if (i == 0) {
                list.add(reportDetail);
            } else {
                boolean isExist = false;
                for (ReportDetail detail : list) {
                    if (reportDetail.getName().equals(detail.getName())) {
                        isExist = true;
                        detail.addAmount(reportDetail.getAmount()*reportDetail.getQuantity());
                        detail.addQuantity(reportDetail.getQuantity());
                    }
                }
                if (!isExist) {
                    list.add(reportDetail);
                }
            }

        }
        Collections.sort(list, new Comparator<ReportDetail>() {
            @Override
            public int compare(ReportDetail o1, ReportDetail o2) {
                if (o1.getAmount()< o2.getAmount()) {
                    return 1;
                }
                if (o1.getAmount() > o2.getAmount()) {
                    return -1;
                }
                return 0;
            }
        });
        return list;
    }
}
