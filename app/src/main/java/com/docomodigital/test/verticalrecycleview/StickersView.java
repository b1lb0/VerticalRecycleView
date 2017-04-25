package com.docomodigital.test.verticalrecycleview;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.docomodigital.test.verticalrecycleview.model.Sticker;

public class StickersView extends android.support.v7.widget.AppCompatImageView {
    private Sticker data = null;
    private IStickerView iStickerView;
    private Handler mAnimationHandler;
    private Runnable mAnumationRunnable;

    /**
     * @param context
     */
    public StickersView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public StickersView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public StickersView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setIStickerView(IStickerView value) {
        iStickerView = value;
    }

    public Sticker getSticker() {
        return this.data;
    }


    public void setSticker(Sticker st) {
        this.data = st;
    }

    public AnimationDrawable getAnimationDrawable() {
        return this.getSticker().getAnimation();
    }

    public boolean startAnimation() {
        return startAnimation(true, null);
    }

    public boolean startAnimation(boolean loop, IStickerView callback) {
        try {
            if (callback!=null) iStickerView = callback;

            AnimationDrawable animationDrawable = getSticker().getAnimation();

            if (animationDrawable.isRunning()) {
                Log.w(getClass().getSimpleName(), "startAnimation: skipped because already running.");
            } else {
                int actualWidth = getSticker().getWidth();
                int actualHeight = getSticker().getHeight();

                setScaleType(ScaleType.FIT_XY);
                setImageDrawable(animationDrawable);

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
                layoutParams.width = actualWidth;
                layoutParams.height = actualHeight;
                layoutParams.leftMargin = getSticker().getX();
                layoutParams.topMargin = getSticker().getY();

                Log.d("StickerView:", "anim width: " + layoutParams.width + " height: " + layoutParams.height);
                setLayoutParams(layoutParams);
                //setTop(getSticker().getX());
                //setLeft(getSticker().getY());

                animationDrawable.setOneShot(!loop);
                animationDrawable.run();

                //animation is started, we send a signal to the callback
                if (iStickerView != null) iStickerView.onStickerAnimationStarted();

                if (animationDrawable.isOneShot()) {
                    mAnimationHandler = new Handler(Looper.getMainLooper());
                    mAnumationRunnable = new Runnable() {
                        public void run() {
                            //animation is ended, we send a signal to the callback
                            if (iStickerView != null) iStickerView.onStickerAnimationEnd();
                        }
                    };

                    mAnimationHandler.postDelayed(mAnumationRunnable, getDuration());
                }

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private int getDuration() {
        int iDuration = 0;

        for (int i = 0; i < getSticker().getAnimation().getNumberOfFrames(); i++) {
            iDuration += getSticker().getAnimation().getDuration(i);
        }

        return iDuration;
    }

    public interface IStickerView {
        void onStickerAnimationEnd();
        void onStickerAnimationStarted();
    }

}
