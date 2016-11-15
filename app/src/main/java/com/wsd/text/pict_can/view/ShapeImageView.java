package com.wsd.text.pict_can.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.wsd.text.pict_can.R;
import com.wsd.text.pict_can.utils.ConvertUtil;


/**
 * Created by Libery on 2016/9/20.
 * Email:libery.szq@qq.com
 */

public class ShapeImageView extends SmartImageView {

    protected int borderColor;
    protected int borderWidth;
    protected int roundRadius;
    protected int leftTopRadius;
    protected int rightTopRadius;
    protected int rightBottomRadius;
    protected int leftBottomRadius;
    protected boolean onlyDrawBorder;
    private Paint shaderPaint;
    private Paint mPaint;
    protected int mShape;

    public ShapeImageView(final Context context) {
        super(context);
    }

    public ShapeImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        sharedConstructor(context, attrs);
    }

    private void sharedConstructor(Context context, AttributeSet attrs) {
        shaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomShapeImageView);
        borderColor = a.getColor(R.styleable.CustomShapeImageView_borderColor, Color.TRANSPARENT);
        borderWidth = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_imageBorderWidth, 0);
        roundRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_roundRadius, 0);
        leftTopRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_leftTopRadius, -1);
        if (leftTopRadius == -1) {
            leftTopRadius = roundRadius;
        }
        rightTopRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_rightTopRadius, -1);
        if (rightTopRadius == -1) {
            rightTopRadius = roundRadius;
        }
        rightBottomRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_rightBottomRadius, -1);
        if (rightBottomRadius == -1) {
            rightBottomRadius = roundRadius;
        }
        leftBottomRadius = a.getDimensionPixelSize(R.styleable.CustomShapeImageView_leftBottomRadius, -1);
        if (leftBottomRadius == -1) {
            leftBottomRadius = roundRadius;
        }
        mShape = a.getInt(R.styleable.CustomShapeImageView_shape, Shape.CIRCLE);
        onlyDrawBorder = a.getBoolean(R.styleable.CustomShapeImageView_onlyDrawBorder, true);
        a.recycle();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (onlyDrawBorder) {
            super.onDraw(canvas);
        } else {
            Bitmap bmp = ConvertUtil.drawableToBitmap(getDrawable());
            if (bmp != null) {
                float bmpW = bmp.getWidth();
                float bmpH = bmp.getHeight();
                float h = getHeight();
                float w = getWidth();
                drawShader(canvas, bmp, bmpW, bmpH, w, h);
                drawShape(canvas, w, h);
            }
        }
        drawBorder(canvas);
    }

    private void drawShader(Canvas canvas, Bitmap bmp, float bmpW, float bmpH, float w, float h) {
        final Shader shader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.setScale(w / bmpW, h / bmpH);
        shader.setLocalMatrix(matrix);
        shaderPaint.setColor(Color.TRANSPARENT);
        shaderPaint.setShader(shader);
        canvas.drawPaint(shaderPaint);
    }

    private void drawShape(Canvas canvas, float w, float h) {
        RectF rectF = new RectF();
        shaderPaint.setColor(Color.WHITE);
        shaderPaint.setAntiAlias(true);
        shaderPaint.setStyle(Paint.Style.FILL);
        switch (mShape) {
            case Shape.CIRCLE:
                float min = Math.min(w, h);
                rectF.left = (w - min) / 2 + borderWidth / 2;
                rectF.top = (h - min) / 2 + borderWidth / 2;
                rectF.right = w - (w - min) / 2 - borderWidth / 2;
                rectF.bottom = h - (h - min) / 2 - borderWidth / 2;
                canvas.drawArc(rectF, 0, 360, true, shaderPaint);
                break;
            case Shape.RECTANGLE:
                Path path = new Path();
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = w - borderWidth / 2;
                rectF.bottom = h - borderWidth / 2;
                float[] rad = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius,
                        rightBottomRadius, leftBottomRadius, leftBottomRadius};
                path.addRoundRect(rectF, rad, Path.Direction.CW);
                canvas.drawPath(path, shaderPaint);
                break;
            case Shape.ARC:
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = w - borderWidth / 2;
                rectF.bottom = h - borderWidth / 2;
                canvas.drawArc(rectF, 0, 360, true, shaderPaint);
                break;
        }

    }


    private void drawBorder(Canvas canvas) {
        if (borderWidth == 0 || borderColor == Color.TRANSPARENT) {
            return;
        }
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(borderColor);
        switch (mShape) {
            case Shape.CIRCLE:
                float radius = (Math.min(getWidth(), getHeight()) - borderWidth) / 2;
                float cx = getWidth() / 2f;
                float cy = getHeight() / 2f;
                canvas.drawCircle(cx, cy, radius, mPaint);
                break;
            case Shape.RECTANGLE:
                Path path = new Path();
                RectF rectF = new RectF();
                rectF.left = borderWidth / 2;
                rectF.top = borderWidth / 2;
                rectF.right = getWidth() - borderWidth / 2;
                rectF.bottom = getHeight() - borderWidth / 2;

                float[] rad = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius,
                        rightBottomRadius, leftBottomRadius, leftBottomRadius};
                path.addRoundRect(rectF, rad, Path.Direction.CW);
                canvas.drawPath(path, mPaint);
                return;
            case Shape.ARC:
                RectF rectF2 = new RectF();
                rectF2.left = borderWidth / 2;
                rectF2.top = borderWidth / 2;
                rectF2.right = getWidth() - borderWidth / 2;
                rectF2.bottom = getHeight() - borderWidth / 2;
                canvas.drawArc(rectF2, 0, 360, false, mPaint);
                break;
        }
    }

    public void setBorderColor(int color) {
        borderColor = color;
    }

    public void setBorderWidth(int width) {
        this.borderWidth = width;
    }

    public static class Shape {
        public static final int CIRCLE = 1;
        public static final int RECTANGLE = 2;
        public static final int ARC = 3;
    }

}
