package com.example.nguoi_dung;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

public class AnimationUtil {
    private static final int ANIMATION_DURUTION = 1000;
    private static boolean isAnimationStart;

    public static void translateAnimation(final ImageView viewAnimation, @NotNull final View startView, View endView,
                                          final Animation.AnimationListener animationListener){
        startView.setDrawingCacheEnabled(true);
        Bitmap cache = startView.getDrawingCache();
        if (cache == null){
            return;
        }
        Bitmap bitmap = Bitmap.createBitmap(cache);
        startView.setDrawingCacheEnabled(false);

        viewAnimation.setImageBitmap(bitmap);

        float startViewWidthCenter = startView.getWidth() / 2;
        float startViewHeighCenter = startView.getHeight() / 2;

        float endViewWidthCenter = endView.getWidth() * 0.75f;
        float endtViewHeighCenter = endView.getHeight() / 2f;

        final int[] startPos = new int[2];
        startView.getLocationOnScreen(startPos);

        final int[] endPos = new int[2];
        endView.getLocationOnScreen(endPos);

        float fromX = startPos[0];
        float toX = endPos[0] + endViewWidthCenter - startViewWidthCenter;
        float fromY = startPos[1] - startViewHeighCenter;
        float toY = endPos[1] - endtViewHeighCenter + startViewHeighCenter;

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new AccelerateInterpolator());

        int animationDurution = 200;

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,startViewWidthCenter, startViewHeighCenter);
        scaleAnimation.setDuration(animationDurution);

        TranslateAnimation translateAnimation = new TranslateAnimation(fromX,toX, fromY,toY);
        translateAnimation.setStartOffset(animationDurution);
        translateAnimation.setDuration(ANIMATION_DURUTION);

        ScaleAnimation translatScaleAnimation = new ScaleAnimation(1.0f,0.0f,1.0f,0.0f,toX,toY);
        translatScaleAnimation.setStartOffset(animationDurution);
        translatScaleAnimation.setDuration(ANIMATION_DURUTION);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(translatScaleAnimation);

        if (isAnimationStart){
            viewAnimation.clearAnimation();
            if (animationListener != null){
                animationListener.onAnimationEnd(null);
            }
        }

        viewAnimation.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimationStart = true;

                viewAnimation.setVisibility(View.VISIBLE);
                startView.setVisibility(View.INVISIBLE);

                if (animationListener != null){
                    animationListener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewAnimation.setVisibility(View.GONE);
                startView.setVisibility(View.VISIBLE);

                if (animationListener != null){
                    animationListener.onAnimationEnd(animation);
                }

                isAnimationStart = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (animationListener != null){
                    animationListener.onAnimationRepeat(animation);
                }
            }
        });

    }
}
