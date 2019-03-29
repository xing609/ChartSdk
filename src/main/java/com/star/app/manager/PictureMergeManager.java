package com.star.app.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.star.app.ui.R;
import com.star.kchart.utils.DensityUtil;


/**
 * Description：
 * Author: chenshanshan
 * Email: 1175558532@qq.com
 * Date: 2018-9-13  13:53
 */
public class PictureMergeManager {
    private Activity context;
    private static PictureMergeManager pictureMergeManager = null;

    public PictureMergeManager() {
    }

    public static synchronized PictureMergeManager getPictureMergeManager() {
        if (pictureMergeManager == null) {
            pictureMergeManager = new PictureMergeManager();
        }
        return pictureMergeManager;
    }

    //得到合并的图片
    public Bitmap getBitmap(Activity mcontext, Bitmap bitmap, boolean isBaseMax) {
        this.context = mcontext;
//        return mergeBitmap_TB(captureWebView1(webView), getPictureBottom(), isBaseMax);
        return mergeBitmap_TB(bitmap, getPictureBottom(mcontext), isBaseMax);
    }

    /**
     * 对WebView进行截图
     * 屏幕里面
     *
     * @param webView
     * @return
     */
    public Bitmap getWebViewShotToBitmap(WebView webView) {
        Picture snapShot = webView.capturePicture();
        if (snapShot == null && snapShot.getWidth() == 0 && snapShot.getHeight() == 0) {
            return null;
        }
        Bitmap bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
        snapShot.draw(canvas);
        return bmp;
    }

    //获取webview内容的截图
    public Bitmap getWebviewContentToBitmap(final WebView view) {
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);
        if (view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0) {
            return null;
        }
        Bitmap bm;
        try {
            bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_4444);
        } catch (OutOfMemoryError e) {
            System.gc();
            bm = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_4444);
        }
        Canvas bigCanvas = new Canvas(bm);
        Paint paint = new Paint();
        int iHeight = bm.getHeight();
        bigCanvas.drawBitmap(bm, 0, iHeight, paint);
        view.draw(bigCanvas);
        return bm;
    }

    //获取任意view里面的内容转图片
    public Bitmap getViewToBitmap(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE); /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }

    // 需要截取屏幕内view转成图片
    public Bitmap getScreenBitmap(Activity mcontext, View view) throws Exception {
        View screenView = mcontext.getWindow().getDecorView();
        screenView.setDrawingCacheEnabled(true);
        screenView.buildDrawingCache();
        //获取屏幕整张图片
        Bitmap bitmap = screenView.getDrawingCache();
        if (bitmap != null) {
            //需要截取的长和宽
            int outWidth = view.getWidth();
            int outHeight = view.getHeight();

            //获取需要截图部分的在屏幕上的坐标(view的左上角坐标）
            int[] viewLocationArray = new int[2];
            view.getLocationOnScreen(viewLocationArray);
            //从屏幕整张图片中截取指定区域
            bitmap = Bitmap.createBitmap(bitmap, viewLocationArray[0], viewLocationArray[1], outWidth, outHeight);
        }
        return bitmap;
    }

    //获取scrollview的图片
    public static Bitmap getScrollViewToBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#ffffff"));
        scrollView.draw(canvas);
        Bitmap bb = Bitmap.createScaledBitmap(bitmap, scrollView.getWidth(), h, false);
        return bb;
    }

    //画文字
    public Bitmap getCanvasTextBitmap(Activity context, String value, int height) {
        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        Bitmap b = Bitmap.createBitmap(width, dp2px(context, height), Bitmap.Config.ARGB_8888);
        Canvas cvs = new Canvas(b); //然后在cvs上的操作也都会在bitmap上进行记录
        cvs.drawColor(context.getResources().getColor(R.color.c2A2D4F));
        //设置画笔
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(R.color.cFFFFFF));
        paint.setTextSize(dp2px(context, 16));//设置字体大小
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        //设置字体的高度
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        float textBaseY = dp2px(context, height) - (dp2px(context, height) - fontHeight) / 2 - fontMetrics.bottom;
        cvs.drawText(value, width / 2, textBaseY, paint);
        return b;
    }

    //添加底部二维码图片
    private Bitmap getPictureBottom(Activity context) {
        int widthed = context.getWindowManager().getDefaultDisplay().getWidth();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.iv_share_logo);
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(), bitmap.getHeight(), false);
//        Bitmap b = Bitmap.createBitmap(bitmap.getWidth()+dp2px(context,40), bitmap.getHeight()+dp2px(context,20), Bitmap.Config.ARGB_8888);
        Bitmap b = Bitmap.createBitmap(widthed, bitmap.getHeight() + dp2px(context, 20), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        canvas.drawColor(context.getResources().getColor(R.color.c2A2D4F));
//        Rect rect=new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        int aa = (widthed - bitmap.getWidth()) / 2;
        canvas.drawBitmap(bitmap, aa, dp2px(context, 10), null);
//        canvas.drawBitmap(bitmap, dp2px(context, 20), dp2px(context, 10), null);
        return b;
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     * isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     *
     * @return
     */
    public Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {
        if (topBitmap == null || topBitmap.isRecycled() || bottomBitmap == null || bottomBitmap.isRecycled()) {
            Log.i("错误", "topBitmap=" + topBitmap + ";bottomBitmap=" + bottomBitmap);
            return null;
        }
        int width = 0;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;
        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int) (topBitmap.getHeight() * 1f / topBitmap.getWidth() * width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int) (bottomBitmap.getHeight() * 1f / bottomBitmap.getWidth() * width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }


    public int dp2px(Activity context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    public void setTextDraw(TextView textViewshare) {
//        int mWebViewTotalHeight = (int) (mwebView.getContentHeight());
        int mWebViewTotalHeight = textViewshare.getMeasuredHeight();
        Log.i("4444444444444", mWebViewTotalHeight + "#########" + textViewshare.getWidth());
        Bitmap longImage = Bitmap.createBitmap(textViewshare.getWidth(), mWebViewTotalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(longImage);  // 画布的宽高和 WebView 的网页保持一致
        canvas.drawColor(context.getResources().getColor(R.color.cFFFFFF));
//                Paint paint = new Paint();
//                paint.setColor(context.getResources().getColor(R.color.cFFFFFF));
//                paint.setTextSize(60);//设置字体大小
//                paint.setAntiAlias(true);
//                paint.setTextAlign(Paint.Align.LEFT);
//                canvas.drawText(getContext().getResources().getString(R.string.txt_texttext),0,10,paint);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(context.getResources().getColor(R.color.c000000));
        textPaint.setTextSize(DensityUtil.dp2px(16));
//                textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.LEFT);
        //画字体换行
        StaticLayout sl = new StaticLayout(context.getResources().getString(R.string.txt_chart_more), textPaint
                , textViewshare.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
//                canvas.translate(6, 40);
        sl.draw(canvas);
//                canvas.drawBitmap(longImage, 0, mWebViewTotalHeight, paint);
//                mwebView.draw(canvas);
    }


    //    public Bitmap getNewBitMap(String text) {
//        Bitmap newBitmap = Bitmap.createBitmap(120,150, Config.ARGB_4444);
//        Canvas canvas = new Canvas(newBitmap);
//        canvas.drawBitmap(bmp, 0, 0, null);
//        TextPaint textPaint = new TextPaint();
//        textPaint.setAntiAlias(true);
//        textPaint.setTextSize(16.0F);
//        StaticLayout sl= new StaticLayout(text, textPaint, newBitmap.getWidth()-8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
//        canvas.translate(6, 40);
//        sl.draw(canvas);
//        return newBitmap;
//    }

}
