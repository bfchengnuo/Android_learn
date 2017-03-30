package com.bfchengnuo.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 冰封承諾Andy on 2017/3/20 0020.
 * 自定义View相关
 * 偷懒没有适应wrap_content的测量模式
 */

public class MyView extends View {
    private int mWidth, mHeight;
    private static final int FONTSIZE = 40;
    private String[] yTexts;
    private String[] xTexts;
    private int[] points;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wMode == MeasureSpec.AT_MOST || hMode == MeasureSpec.AT_MOST) {
            throw new IllegalArgumentException("模式错误");
        }
        mHeight = hSize;
        mWidth = wSize;
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (xTexts.length != 0 && yTexts.length != 0) {
            Paint paint = new Paint();
            paint.setTextSize(FONTSIZE);
            paint.setColor(Color.parseColor("#000000"));

            // 如果y轴文字无,在屏幕中间绘制无数据
            if (yTexts == null || yTexts.length == 0) {
                int textLenght = (int) paint.measureText("无数据");
                canvas.drawText("无数据", mWidth / 2 - textLenght, mHeight / 2, paint);
            } else {
                // 画Y轴
                // 保存Y轴文字的坐标
                int[] yPoints = new int[yTexts.length];
                // 测量Y轴文字高度，确定间距
                int yInterval = (mHeight - FONTSIZE) / yTexts.length;
                for (int i = 0; i < yTexts.length; i++) {
                    canvas.drawText(yTexts[i], 0, FONTSIZE + i * yInterval, paint);
                    yPoints[i] = FONTSIZE + i * yInterval;
                }

                // 画X轴
                float[] xPoints = new float[xTexts.length];
                int xInter = (mWidth - FONTSIZE) / xTexts.length;
                for (int x = 0; x < xTexts.length; x++) {
                    canvas.drawText(xTexts[x], x * xInter, mHeight - 50, paint);
                    xPoints[x] = x * xInter;
                }

                // 画点
                paint.setColor(Color.parseColor("#66ccff"));
                paint.setStyle(Paint.Style.FILL);
                Paint lineP = new Paint();
                lineP.setColor(Color.parseColor("#ff0000"));
                lineP.setAntiAlias(true);
                lineP.setStrokeWidth(8.0f);
                for (int y = 1; y < points.length; y++) {
                    canvas.drawCircle(xPoints[y], yPoints[points[y]] - 10, 10, paint);
                    // 画线
                    if (y > 1) {
                        canvas.drawLine(xPoints[y - 1], yPoints[points[y - 1]] - 10, xPoints[y], yPoints[points[y]] - 10, lineP);
                    }
                }

                lineP.setColor(Color.parseColor("#000000"));
                canvas.drawLine(20,yPoints[0],20,mHeight - 50,lineP);
                canvas.drawLine(20,mHeight - 50, xPoints[xPoints.length-1] + 20,mHeight - 50,lineP);
            }
        }
    }

    public void setyTexts(String[] strings) {
        yTexts = strings;
    }

    public void setxTexts(String[] strings) {
        xTexts = strings;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }
}
