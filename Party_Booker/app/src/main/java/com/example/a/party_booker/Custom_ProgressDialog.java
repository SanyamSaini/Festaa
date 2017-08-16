package com.example.a.party_booker;



import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.wang.avi.AVLoadingIndicatorView;

public class Custom_ProgressDialog extends ProgressDialog {
	Context context;
	Animation myRotation;
	String comment;
	ImageView la;
	AVLoadingIndicatorView indicatorView;

	public Custom_ProgressDialog(Context context, String comment) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.comment = comment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressdialog);
		getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

		//la = (ImageView) findViewById(R.id.img);
		indicatorView=(AVLoadingIndicatorView)findViewById(R.id.avi);


//		GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(la);
//		Glide.with(context).load(R.drawable.loader11).into(imageViewTarget);


	}

	@Override
	public void show() {
		super.show();
		//myRotation.setRepeatCount(Animation.INFINITE);
		//la.startAnimation(myRotation);

	}

	@Override
	public void dismiss() {
		super.dismiss();
		//myRotation.cancel();
	}
}
