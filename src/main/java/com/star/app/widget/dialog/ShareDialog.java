package com.star.app.widget.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gjmetal.star.kit.KnifeKit;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

import com.star.app.manager.PictureMergeManager;
import com.star.app.model.share.ShareContent;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.util.FileUtils;
import com.star.app.util.QrCodeUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class ShareDialog extends Dialog {
    @BindView(R2.id.ivShare)
    ImageView ivShare;
    @BindView(R2.id.tvTitle)
    TextView tvTitle;
    @BindView(R2.id.scrollview)
    ScrollView scrollview;
    @BindView(R2.id.llShare)
    LinearLayout llShare;
    @BindView(R2.id.ivEwm)
    ImageView ivEwm;
    @BindView(R2.id.rlShareEWM)
    RelativeLayout rlShareEWM;
    @BindView(R2.id.tvEWMContent)
    TextView tvEWMContent;

    @BindView(R2.id.rlKline)
    RelativeLayout rlKline;
    @BindView(R2.id.tvTxjs)
    TextView tvTxjs;
    @BindView(R2.id.ivFlashImage)
    ImageView ivFlashImage;

    @BindView(R2.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R2.id.wexinfriend)
    LinearLayout wexinfriend;
    @BindView(R2.id.wexinfriends)
    LinearLayout wexinfriends;
    @BindView(R2.id.qqfriend)
    LinearLayout qqfriend;
    @BindView(R2.id.sinwebo)
    LinearLayout sinwebo;
    @BindView(R2.id.cancel)
    TextView cancel;

    private ShareContent shareContent;//行情 计算的内容
    private Context context;
    private int Flag = 0;//0是行情 1 是快讯 2 计算分享
    private String mUrl;//生成二维码的链接地址
    private Bitmap longImage = null;//生成图片
    private Bitmap erWmbitmap = null;//二维码图片
    private Bitmap bitmap = null;

    public ShareDialog(@NonNull Context context) {
        super(context);
    }

    public ShareDialog(int Flag, Context context, int themeResId, ShareContent shareContent) {
        super(context, themeResId);
        this.context = context;
        this.Flag = Flag;
        this.shareContent = shareContent;
    }

//    public ShareDialog(int Flag, Context context, int themeResId, WebViewBean shareBean) {
//        super(context, themeResId);
//        this.context = context;
//        this.Flag = Flag;
//        this.shareBean = shareBean;
//    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_pictureshare, null);
        setContentView(view);
        KnifeKit.bind(this, view);
//        if (shareBean == null) {
//            shareBean = new WebViewBean();
//        }
        if (Flag == 0) {//K线
            rlKline.setVisibility(View.VISIBLE);
            ivFlashImage.setVisibility(View.GONE);
            rlShareEWM.setBackgroundResource(R.color.c2A2D4F);
            tvTxjs.setTextColor(getContext().getResources().getColor(R.color.cFFFFFF));
            tvTitle.setText(shareContent.getTitle());
            mUrl = shareContent.getUrl();
            bitmap = shareContent.getBitmap();
            if (bitmap != null) {
                ivShare.setImageBitmap(bitmap);
            }
            //设置二维码
            erWmbitmap = QrCodeUtil.addLogo(context, QrCodeUtil.createQRCode(mUrl), BitmapFactory.decodeResource(context.getResources(), R.mipmap.iv_new_erweima_logo));
            if (erWmbitmap != null) {
                ivEwm.setImageBitmap(erWmbitmap);
            }

        } else if (Flag == 1) {//快选
            rlKline.setVisibility(View.GONE);
            ivFlashImage.setVisibility(View.VISIBLE);
            rlShareEWM.setVisibility(View.GONE);
            tvEWMContent.setTextColor(getContext().getResources().getColor(R.color.c7A7E82));
//            if (shareBean.getBitmap() != null) {
//                ivFlashImage.setImageBitmap(shareBean.getBitmap());
//            }
        } else if (Flag == 2) {
            tvTitle.setVisibility(View.GONE);
            rlKline.setVisibility(View.VISIBLE);
            ivFlashImage.setVisibility(View.GONE);
            rlShareEWM.setVisibility(View.GONE);
            rlShareEWM.setBackgroundResource(R.color.c2A2D4F);
            tvTxjs.setTextColor(getContext().getResources().getColor(R.color.cFFFFFF));
            bitmap = shareContent.getBitmap();
            if (bitmap != null) {
                ivShare.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        if (isShowing())
            dismiss();
        super.onDetachedFromWindow();
    }


    @Override
    public void show() {
        super.show();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth(); //设置dialog的宽度为当前手机屏幕的宽度
        getWindow().setAttributes(p);
    }

    @OnClick({R2.id.wexinfriend, R2.id.wexinfriends, R2.id.qqfriend, R2.id.sinwebo, R2.id.cancel})
    public void onViewClicked(View view) {
        if (Flag == 1) {
            // longImage = shareBean.getBitmap();
        } else if (Flag == 2) {
            longImage = bitmap;
        } else {
            try {
                longImage = PictureMergeManager.getPictureMergeManager().getViewToBitmap(llShare);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (longImage == null) {
            return;
        }
        int id = view.getId();
//            case R2.id.wexinfriend:
//                shareBean.setBitmap(longImage);
//                ShareUtils.shareBitmapTo(getContext(), Constant.ShareType.WECHAT, shareBean);
//                this.dismiss();
//                break;
//            case R2.id.wexinfriends:
//                shareBean.setBitmap(longImage);
//                ShareUtils.shareBitmapTo(getContext(), Constant.ShareType.WECHAT_FRIENDS, shareBean);
//                this.dismiss();
//                break;
//            case R2.id.qqfriend:
//                setPression(longImage);
//                break;
//            case R2.id.sinwebo:
//                if (!ShareUtils.isWeiboInstalled(context)) {
//                    ToastUtil.showToast(R.string.please_install_sina_share);
//                    return;
//                }
//                shareBean.setBitmap(longImage);
//                ShareUtils.shareBitmapTo(getContext(), Constant.ShareType.SINA, shareBean);
//                this.dismiss();
//                break;
        if (id == R.id.cancel) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (erWmbitmap != null && !erWmbitmap.isRecycled()) {
                erWmbitmap.recycle();
            }
            if (longImage != null && !longImage.isRecycled()) {
                longImage.recycle();
            }
            this.dismiss();
        }

    }


    //打开权限
    private void setPression(final Bitmap bitmapImage) {
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        String shareImage = FileUtils.saveBitmap(context, bitmapImage);
//                        if (shareImage != null && shareImage.length() > 0) {
//                            shareBean.setImgUrl(shareImage);
//                            ShareUtils.shareBitmapTo(getContext(), Constant.ShareType.QQ, shareBean);
//                            dismiss();
//                        } else {
//                            ToastUtil.showToast("获取生成图片失败");
//                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, permissions.toString() + "图片权限拒绝将无法分享", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
