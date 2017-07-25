package com.avatarmind.camera.view;

import com.avatarmind.camera.util.CameraListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;


/**
 * 涓存椂ImageView锛屽湪鎵ц瀹屼竴涓姩鐢诲悗鑷姩闅愯棌
 */
public class TempImageView extends ImageView implements AnimationListener {

    public final static String TAG = "TempImageView";

    /**
     * 涓嶅瓨鍦ㄧ殑鍔ㄧ敾ID
     */
    public static final int NO_ID = -1;
    /**
     * 璁剧疆鐨勫姩鐢绘晥鏋淚D
     */
    private int mAnimationID = NO_ID;

    /** 鎷嶇収鍔ㄤ綔鐩戝惉鎺ュ彛 */
    private CameraListener mListener;

    private boolean mIsVideo;
    
    public static final int[] TempImageView = {
        0x7f010000
    };
    public static final int TempImageView_animat_id = 0;
    public TempImageView(Context context) {
        // TODO Auto-generated constructor stub
        super(context);
    }

    public TempImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                TempImageView);
        mAnimationID = a.getResourceId(TempImageView_animat_id,
                NO_ID);
        a.recycle();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub
        setVisibility(View.GONE);
        Drawable drawable = getDrawable();
        Bitmap bm = null;
        if (drawable != null && drawable instanceof BitmapDrawable)
            bm = ((BitmapDrawable) drawable).getBitmap();
        // 鐩告満鐩戝惉鎺ュ彛涓嶄负绌猴紝鍒欐墽琛屾媿鐓х粨鏉熸搷浣�
        if (mListener != null)
            mListener.onAnimationEnd(bm);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    /**
     * @Description: 寮�濮嬪姩鐢�
     * @param
     * @return void
     * @throws
     */
    public void startAnimation() {
        startAnimation(null);
    }

    /**
     * @Description: 寮�濮嬪姩鐢�
     * @param @param resourceID 鍔ㄧ敾璧勬簮鐨処D
     * @return void
     * @throws
     */
    public void startAnimation(int resourceID) {
        mAnimationID = resourceID;
        startAnimation();
    }

    public void startAnimation(Animation animation) {
        if (animation != null) {
            animation.setAnimationListener(this);
            super.startAnimation(animation);
            return;
        }
        if (mAnimationID != NO_ID) {
            animation = AnimationUtils
                    .loadAnimation(getContext(), mAnimationID);
            animation.setAnimationListener(this);
            super.startAnimation(animation);
        }
    }

    public void isVideo(boolean isVideo) {
        mIsVideo = isVideo;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        // TODO Auto-generated method stub
        super.setImageBitmap(bm);
    }

    public void setListener(CameraListener mListener) {
        this.mListener = mListener;
    }
}
