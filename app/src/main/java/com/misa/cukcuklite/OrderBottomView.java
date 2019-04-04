package com.misa.cukcuklite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class OrderBottomView extends View {
    public OrderBottomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        @SuppressLint("DrawAllocation")
        Paint paint = new PainBill(this);
        @SuppressLint("DrawAllocation")
        Path path = new Path();
        int i = (width / height) + 1;
        path.moveTo(0.0f, 0.0f);
        path.lineTo(0.0f, (float) height);
        path.lineTo((float) height, 0.0f);
        width = 2;
        while (width < i) {
            path.lineTo((float) (width * height), (float) height);
            width++;
            path.lineTo((float) (width * height), 0.0f);
            width++;
        }
        canvas.drawPath(path, paint);
    }

    class PainBill extends Paint {
        final OrderBottomView mBottomView;

        PainBill(OrderBottomView orderBottomView) {
            this.mBottomView = orderBottomView;
            setStyle(Style.FILL);
            setColor(this.mBottomView.getContext().getResources().getColor(R.color.color_white));
        }
    }
}
