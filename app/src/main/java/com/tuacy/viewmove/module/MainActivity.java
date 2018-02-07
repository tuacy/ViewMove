package com.tuacy.viewmove.module;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.tuacy.viewmove.R;
import com.tuacy.viewmove.app.MobileBaseActivity;
import com.tuacy.viewmove.module.overscroller.OverScrollerMainActivity;
import com.tuacy.viewmove.module.viewdraghelper.ViewDragHelperMainActivity;

public class MainActivity extends MobileBaseActivity {

	private Toolbar              mToolbar;
	private MaterialRippleLayout mRippleScroller;
	private MaterialRippleLayout mRippleDragHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mToolbar = findViewById(R.id.tool_bar_main);
		mRippleScroller = findViewById(R.id.ripple_button_scroller);
		mRippleDragHelper = findViewById(R.id.ripple_button_drag_helper);
	}

	private void initEvent() {
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mRippleScroller.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OverScrollerMainActivity.startUp(mContext);
			}
		});

		mRippleDragHelper.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ViewDragHelperMainActivity.startUp(mContext);
			}
		});
	}

	private void initData() {

	}
}
