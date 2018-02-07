package com.tuacy.viewmove.module.overscroller.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

public class ScrollerDragLayout extends ViewGroup {

	private View         mViewContent;
	private View         mViewFollow;
	private View         mViewDelete;
	private OverScroller mScroller;
	private int          mTouchSlop;
	private boolean      mInControl;
	private int          mMaxDistance;
	private boolean      mIsOpen;

	public ScrollerDragLayout(Context context) {
		this(context, null);
	}

	public ScrollerDragLayout(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ScrollerDragLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		ViewConfiguration configuration = ViewConfiguration.get(getContext());
		mScroller = new OverScroller(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();
		mInControl = false;
		mIsOpen = false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		measureChild(mViewContent, widthMeasureSpec, heightMeasureSpec);
		mViewContent.getMeasuredHeight();
		measureChild(mViewFollow, widthMeasureSpec, MeasureSpec.makeMeasureSpec(mViewContent.getMeasuredHeight(), MeasureSpec.EXACTLY));
		measureChild(mViewDelete, widthMeasureSpec, MeasureSpec.makeMeasureSpec(mViewContent.getMeasuredHeight(), MeasureSpec.EXACTLY));
		setMeasuredDimension(mViewContent.getMeasuredWidth(), mViewContent.getMeasuredHeight());
		bringChildToFront(mViewDelete);
		bringChildToFront(mViewContent);
		mMaxDistance = mViewFollow.getMeasuredWidth() + mViewDelete.getMeasuredWidth();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() < 3) {
			throw new IllegalArgumentException("ScrollerDragLayout need 3 view");
		}
		mViewContent = getChildAt(0);
		mViewFollow = getChildAt(1);
		mViewDelete = getChildAt(2);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		mViewContent.layout(0, 0, mViewContent.getMeasuredWidth(), mViewContent.getMeasuredHeight());
		mViewFollow.layout(getMeasuredWidth(), 0, getMeasuredWidth() + mViewFollow.getMeasuredWidth(), mViewFollow.getMeasuredHeight());
		mViewDelete.layout(getMeasuredWidth(), 0, getMeasuredWidth() + mViewDelete.getMeasuredWidth(), mViewDelete.getMeasuredHeight());
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (!mScroller.computeScrollOffset()) {
			return;
		}
		dealDistance(mScroller.getCurrX() - mViewContent.getTranslationX());
	}

	@Override
	public boolean performClick() {
		return super.performLongClick();
	}

	private float mPreX;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float x = ev.getRawX();

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mPreX = x;
			case MotionEvent.ACTION_MOVE:
				if (Math.abs(x - mPreX) > mTouchSlop) {
					return true;
				}
				break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			performClick();
		}

		float x = event.getRawX();

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mPreX = x;
				mInControl = false;
				return true;
			case MotionEvent.ACTION_MOVE:
				if (mInControl || Math.abs(x - mPreX) > mTouchSlop) {
					dealDistance(x - mPreX);
					mPreX = x;
					mInControl = true;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mInControl = false;
				if (Math.abs(mViewContent.getTranslationX()) > mMaxDistance / 2.0f) {
					mScroller.startScroll((int) mViewContent.getTranslationX(), 0, (int) (-mMaxDistance - mViewContent.getTranslationX()),
										  0);
					mIsOpen = true;
				} else {
					mScroller.startScroll((int) mViewContent.getTranslationX(), 0, (int) -mViewContent.getTranslationX(), 0);
					mIsOpen = false;
				}
				invalidate();
				break;
		}
		return mInControl || super.onTouchEvent(event);
	}

	private void dealDistance(float diff) {
		if (diff == 0) {
			return;
		}
		if (mViewContent.getTranslationX() >= 0 && diff >= 0) {
			return;
		}
		if (mViewContent.getTranslationX() <= -mMaxDistance && diff <= 0) {
			return;
		}
		if (diff < 0) {
			if (mViewContent.getTranslationX() + diff < -mMaxDistance) {
				diff = -mMaxDistance - mViewContent.getTranslationX();
			}
		} else {
			if (mViewContent.getTranslationX() + diff > 0) {
				diff = mViewContent.getTranslationX();
			}
		}
		mViewContent.setTranslationX(mViewContent.getTranslationX() + diff);
		mViewFollow.setTranslationX(mViewFollow.getTranslationX() + diff);
		float deleteDiff = (mViewDelete.getMeasuredWidth() * mViewContent.getTranslationX() * 1.0f / mMaxDistance);
		mViewDelete.setTranslationX(deleteDiff);
		ViewCompat.postInvalidateOnAnimation(this);
	}

	public void toogle() {
		if (mIsOpen) {
			mIsOpen = false;
			mScroller.startScroll((int) mViewContent.getTranslationX(), 0, (int) -mViewContent.getTranslationX(), 0);
			invalidate();
		} else {
			mScroller.startScroll((int) mViewContent.getTranslationX(), 0, (int) (-mMaxDistance - mViewContent.getTranslationX()), 0);
			mIsOpen = true;
			invalidate();
		}
	}

	public void close() {
		if (mIsOpen) {
			mIsOpen = false;
			mScroller.startScroll((int) mViewContent.getTranslationX(), 0, (int) -mViewContent.getTranslationX(), 0);
			invalidate();
		}
	}
}
