package com.tuacy.viewmove.module.overscroller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuacy.viewmove.R;
import com.tuacy.viewmove.app.MobileBaseActivity;
import com.tuacy.viewmove.module.overscroller.widget.ScrollerDragLayout;

public class OverScrollerMainActivity extends MobileBaseActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, OverScrollerMainActivity.class));
	}

	private ScrollerDragLayout mDragLayout0;
	private TextView mTextContent0;
	private ScrollerDragLayout mDragLayout1;
	private ImageView          mImageDelete;
	private ImageView          mImageFollow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_over_scroller_main);
		initView();
		initEvent();
		initData();
	}

	private void initView() {
		mDragLayout0 = findViewById(R.id.scroller_drag_0);
		mTextContent0 = findViewById(R.id.text_content_0);
		mImageDelete = findViewById(R.id.image_delete);
		mImageFollow = findViewById(R.id.image_follow);
		mDragLayout1 = findViewById(R.id.scroller_drag_1);
	}

	private void initEvent() {

		mTextContent0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDragLayout0.close();
				showSnackBar(R.string.content);
			}
		});

		mImageDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDragLayout0.close();
				showSnackBar(R.string.delete);
			}
		});

		mImageFollow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDragLayout0.close();
				showSnackBar(R.string.follow);
			}
		});

		mDragLayout0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDragLayout0.close();
				showSnackBar(R.string.delete);
			}
		});
	}

	private void initData() {

	}
}
