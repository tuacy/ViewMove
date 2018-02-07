package com.tuacy.viewmove.module.overscroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tuacy.viewmove.R;
import com.tuacy.viewmove.app.MobileBaseActivity;

public class OverScrollerFunctionActivity extends MobileBaseActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, OverScrollerFunctionActivity.class));
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_over_scroller_function);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
	}

	private void initEvent() {

	}

	private void initData() {

	}
}
