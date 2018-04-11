package com.demo.yetote.jboxloadinganimation;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.jbox2d.dynamics.Body;

/**
 * com.demo.yetote.jboxloadinganimation
 *
 * @author Swg
 * @date 2018/4/10 10:47
 */
public class LoadingView extends FrameLayout {
    private LoadingImpl loadingImpl;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();
    }



    private void init() {
        loadingImpl = new LoadingImpl(getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        loadingImpl.startWorld();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            Body body = (Body) view.getTag(R.id.body_tag);
            if (body!=null) {
                view.setX(loadingImpl.getX(view));
                view.setY(loadingImpl.getY(view));
                view.setRotation(loadingImpl.getViewRotate(view));
            }
        }

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadingImpl.createWorld();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            Body body = (Body) view.getTag(R.id.body_tag);
            if (body==null) {
                loadingImpl.createBody(view);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        loadingImpl.onSizeChange(w,h);
    }
}
