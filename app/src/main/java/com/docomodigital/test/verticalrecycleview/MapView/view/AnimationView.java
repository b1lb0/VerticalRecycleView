package com.docomodigital.test.verticalrecycleview.MapView.view;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.docomodigital.test.verticalrecycleview.MapView.model.Item;

public class AnimationView extends android.support.v7.widget.AppCompatImageView {
    private Item data = null;

    public interface IAnimationView {
        void onStickerAnimationEnd();
        void onItemAnimationStarted();
    }


    /**
     * @param context
     */
    public AnimationView(Context context) {
        super(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public AnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public Item getItem() {
        return this.data;
    }

    public void setItem(Item st) {
        this.data = st;
        setPosition(data.getFirstDrawable());
    }

    public boolean startAnimation() {
        return startAnimation(true, null);
    }

    public boolean startAnimation(boolean loop, IAnimationView callback) {
        AnimationDrawable animationDrawable = getItem().getAnimation();

        if (getItem().getAnimation() == null) {
            return false;
        }

        setPosition(animationDrawable);

        animationDrawable.setOneShot(!loop);
        animationDrawable.run();

        //animation is started, we send a signal to the callback
        if (callback != null) callback.onItemAnimationStarted();
        return true;
    }

    private void setPosition(Object object) {
        int actualWidth = getItem().getWidth();
        int actualHeight = getItem().getHeight();

        setScaleType(ScaleType.FIT_XY);

        if (object instanceof AnimationDrawable)
            setImageDrawable((AnimationDrawable) object);
        else if (object instanceof Drawable)
            setImageDrawable((Drawable) object);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = actualWidth;
            layoutParams.height = actualHeight;
            layoutParams.leftMargin = getItem().getX();
            layoutParams.topMargin = getItem().getY();

            Log.d("AnimationView:", "anim width: " + layoutParams.width + " height: " + layoutParams.height);
            setLayoutParams(layoutParams);
        }
    }

    public void startDalyedAnimation() {
        new Handler().postDelayed(() -> startAnimation(), 100);
    }

}
