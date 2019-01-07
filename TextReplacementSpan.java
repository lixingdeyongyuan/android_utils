package com.example.administrator.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by haha on 2019/1/7.
 * 作用：
 * 用于把文字替换成图片+文字
 * 比如：把原来的“中”字替换成“图标”+“阿里巴巴”
 *         final TextView tv = (TextView) findViewById(R.id.sample_text);
 *         SpannableString spannableString = new SpannableString("中11111");
 *         ReplacementSpan replacementSpan = new TextReplacementSpan("阿里巴巴", Color.RED,50);
 *         spannableString.setSpan(replacementSpan,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
 *         tv.setText(spannableString);
 */
public class TextReplacementSpan extends ReplacementSpan {

    private CharSequence mRelaceText;
    private int mReplaceTextColor;
    private int mReplaceTextSize;
    private Bitmap mBitmap;

    public TextReplacementSpan(CharSequence mRelaceText) {
        this(mRelaceText, Color.BLACK, 30);
    }

    public TextReplacementSpan(CharSequence mRelaceText, int mReplaceTextColor, int mReplaceTextSize) {
        this.mRelaceText = mRelaceText;
        this.mReplaceTextColor = mReplaceTextColor;
        this.mReplaceTextSize = mReplaceTextSize;
        mBitmap = readBitmapFromResource(MainActivity.sContext,R.mipmap.ic_launcher);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setColor(mReplaceTextColor);
        paint.setTextSize(mReplaceTextSize);
        // Bitmap的大小
        int bitmapWidth = 0;
        if (mBitmap != null){
            bitmapWidth = mBitmap.getWidth();
        }
        // 测量字体的大小
        int width = (int) paint.measureText(mRelaceText, 0, mRelaceText.length()) + bitmapWidth;
        if (fm != null) {
            fm.ascent = (int) paint.ascent();
            fm.descent = (int) paint.descent();
            fm.top = fm.ascent;
            fm.bottom = fm.descent;
        }
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        canvas.drawBitmap(mBitmap,0,0,paint);
        paint.setTextSize(mReplaceTextSize);
        paint.setColor(mReplaceTextColor);
        canvas.drawText(mRelaceText, 0, mRelaceText.length(), mBitmap.getWidth()-10, y, paint);
    }

    public static Bitmap readBitmapFromResource(Context context, int resId) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inPreferredConfig = Bitmap.Config.ARGB_8888;
        op.inDither = false;
        op.inScaled = false;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, op);
    }


}
