package com.tuacy.viewmove.module.overscroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tuacy.viewmove.R;
import com.tuacy.viewmove.app.MobileBaseActivity;

public class OverScrollerMainActivity extends MobileBaseActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, OverScrollerMainActivity.class));
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_over_scroller_main);
		findViewById(R.id.button_scroller_drag).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OverScrollerDragActivity.startUp(mContext);
			}
		});

		findViewById(R.id.button_scroller_function).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OverScrollerFunctionActivity.startUp(mContext);
			}
		});
	}

}
