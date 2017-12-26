package com.nilin.etherealmuisc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nilin.etherealmuisc.R;

/**
 * Created by liangd on 2017/11/9.
 */

public class ShaderView extends View {

    /**
     * 绘制扫描圈的笔
     */
    private Paint mSweepPaint;
    /**
     * 绘制背景bitmap的笔
     */
    private Paint mBitmapPaint;
    /**
     * 这个自定义View的宽度，就是你在xml布局里面设置的宽度（目前不支持）
     */
    private int mWidth;
    /**
     * 背景图片
     */
    private Bitmap mBitmap;
    /**
     * 雷达扫描旋转角度
     */
    private int degrees = 0;
    /**
     * 用于控制扫描圈的矩阵
     */
    Matrix mSweepMatrix = new Matrix();
    /**
     * 用于控制背景Bitmap的矩阵
     */
    Matrix mBitmapMatrix = new Matrix();
    /**
     * 着色器---生成扫描圈
     */
    private SweepGradient mSweepGradient;
    /**
     * 图片着色器
     */
    private BitmapShader mBitmapShader;
    private float mScale;

    public ShaderView(Context context) {
        super(context);
        init();
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 属性动画，必须有setXxx方法，才可以针对这个属性实现动画
     *
     * @param degrees
     */
    public void setDegrees(int degrees) {
        this.degrees = degrees;
        postInvalidate();//在主线程里执行OnDraw
    }

    private void init() {
//    1.准备好画笔
        mSweepPaint = new Paint();
        mBitmapPaint = new Paint();
//    2.图片着色器
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.scan_music);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//    3.将图片着色器设置给画笔
        mBitmapPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    获取这个自定义view的宽高，注意在onMeasure里获取，在构造函数里得到的是0
        mWidth = getMeasuredWidth();
//    根据你所设置的view的尺寸和bitmap的尺寸计算一个缩放比例，否则的话，得到的图片是一个局部，而不是一整张图片
        mScale = (float) mWidth / (float) mBitmap.getWidth();
//    4.梯度扫描着色器
        mSweepGradient = new SweepGradient(mWidth / 2, mWidth / 2, new int[]{Color.argb(50, 0, 0, 100), Color.argb(10, 30, 0, 0)}, null);
//    5.将梯度扫描着色器设置给另外一支画笔
        mSweepPaint.setShader(mSweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//    迫不得已的时候，才在onDraw方法写代码，能提前准备的要在之前去准备，
//    不要写在onDraw里面，因为onDraw会不停地刷新绘制，写的代码越多，越影响效率


//    将图片缩放至你指定的自定义View的宽高
        mBitmapMatrix.setScale(mScale, mScale);
        mBitmapShader.setLocalMatrix(mBitmapMatrix);

//   设置扫描圈旋转角度
        mSweepMatrix.setRotate(degrees, mWidth / 2, mWidth / 2);
        mSweepGradient.setLocalMatrix(mSweepMatrix);

//    5. 使用设置好图片着色器的画笔，画圆，先画出下层的背景图片，在画出上层的扫描图片
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mBitmapPaint);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mSweepPaint);
    }
}