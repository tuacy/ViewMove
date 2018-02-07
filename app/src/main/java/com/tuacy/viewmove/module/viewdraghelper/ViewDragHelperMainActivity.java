package com.tuacy.viewmove.module.viewdraghelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tuacy.viewmove.R;
import com.tuacy.viewmove.app.MobileBaseActivity;

public class ViewDragHelperMainActivity extends MobileBaseActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, ViewDragHelperMainActivity.class));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_drag_helper_main);
	}
}
