package com.tuacy.viewmove.module.overscroller.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.OverScroller;


public class ScrollerFunctionLayout extends LinearLayout {

	private View            mViewMove;
	private VelocityTracker mVelocityTracker;
	private int             mMinimumVelocity;
	private int             mMaximumVelocity;
	private OverScroller    mScroller;
	private Rect            mRect;
	private Paint           mPaint;

	public ScrollerFunctionLayout(Context context) {
		this(context, null);
	}

	public ScrollerFunctionLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollerFunctionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
		mScroller = new OverScroller(context);
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setColor(Color.GRAY);
		mPaint.setStrokeWidth(5);
		mPaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mViewMove = getChildAt(0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mRect.set(getMeasuredWidth() / 2 - 500 / 2, getMeasuredHeight() / 2 - 500 / 2, getMeasuredWidth() / 2 + 500 / 2,
				  getMeasuredHeight() / 2 + 500 / 2);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		canvas.translate(getScrollX(), getScrollY());
		canvas.drawRect(mRect, mPaint);
		canvas.restore();
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	private float mPreX;
	private float mPreY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			performClick();
		}
		initVelocityTrackerIfNotExists();
		mVelocityTracker.addMovement(event);
		float x = event.getRawX();
		float y = event.getRawY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mPreX = x;
				mPreY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				float diffX = (x - mPreX);
				float diffY = (y - mPreY);
				mPreX = x;
				mPreY = y;
				offset(diffX, diffY);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
				final int velocityX = (int) mVelocityTracker.getXVelocity();
				final int velocityY = (int) mVelocityTracker.getYVelocity();
				//				mScroller.springBack((int) mViewMove.getTranslationX(), (int) mViewMove.getTranslationY(), mRect.left, mRect.right,
				//									 mRect.top, mRect.bottom);
				Log.d("tuacy",
					  "x start: " + mViewMove.getTranslationX() + " min: " + (mRect.left) + " max: " + (mRect.right) + " velocity: " +
					  velocityX);
				Log.d("tuacy",
					  "y start: " + mViewMove.getTranslationY() + " min: " + (mRect.top) + " max: " + (mRect.bottom) + " velocity: " +
					  velocityY);
				mScroller.fling((int) mViewMove.getTranslationX(), (int) mViewMove.getTranslationY(), velocityX, velocityY, mRect.left,
								mRect.right, mRect.top, mRect.bottom);

				//				mScroller.notifyHorizontalEdgeReached((int) mViewMove.getTranslationX(), mRect.right, 0);
				recycleVelocityTracker();
				invalidate();
				break;
		}
		return true;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (!mScroller.computeScrollOffset()) {
			return;
		}
		mViewMove.setTranslationX(mScroller.getCurrX());
		mViewMove.setTranslationY(mScroller.getCurrY());
		invalidate();
	}

	private void initOrResetVelocityTracker() {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		} else {
			mVelocityTracker.clear();
		}
	}

	private void initVelocityTrackerIfNotExists() {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
	}

	private void recycleVelocityTracker() {
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	private void offset(float diffX, float diffY) {
		mViewMove.setTranslationX(mViewMove.getTranslationX() + diffX);
		mViewMove.setTranslationY(mViewMove.getTranslationY() + diffY);
	}
}
