package com.open.androidplugin.plugin1;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.open.androidplugin.plugin1.resource.ImageFactory;

public class ImagePluginView extends FrameLayout implements OnTouchListener,OnGestureListener{
	
	AnimationSet animation_right_in=new AnimationSet(true);
	AnimationSet animation_left_out=new AnimationSet(true);
	AnimationSet animation_left_in=new AnimationSet(true);
	AnimationSet animation_right_out=new AnimationSet(true);
	{
		TranslateAnimation translate_right_in=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1F, Animation.RELATIVE_TO_PARENT, 0.0F, 
				Animation.RELATIVE_TO_PARENT, 0.0F, Animation.RELATIVE_TO_PARENT, 0.0F);
		
		TranslateAnimation translate_left_out=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0F, Animation.RELATIVE_TO_PARENT, -1F, 
				Animation.RELATIVE_TO_PARENT, 0.0F, Animation.RELATIVE_TO_PARENT, 0.0F);
		
		TranslateAnimation translate_left_in=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1F, Animation.RELATIVE_TO_PARENT, 0F, 
				Animation.RELATIVE_TO_PARENT, 0.0F, Animation.RELATIVE_TO_PARENT, 0.0F);
		
		TranslateAnimation translate_right_out=new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0F, Animation.RELATIVE_TO_PARENT, 1F, 
				Animation.RELATIVE_TO_PARENT, 0.0F, Animation.RELATIVE_TO_PARENT, 0.0F);
		
		translate_right_in.setDuration(300);
		translate_left_out.setDuration(300);
		translate_left_in.setDuration(300);
		translate_right_out.setDuration(300);
		
		AlphaAnimation animation_alpha_in=new AlphaAnimation(0.2f,1.0f);
		AlphaAnimation animation_alpha_out=new AlphaAnimation(1f,0.2f);
		animation_alpha_in.setDuration(300);
		animation_alpha_out.setDuration(300);
		
		animation_right_in.addAnimation(translate_right_in);
		animation_right_in.addAnimation(animation_alpha_in);
		
		animation_left_out.addAnimation(translate_left_out);
		animation_left_out.addAnimation(animation_alpha_out);
		
		animation_left_in.addAnimation(translate_left_in);
		animation_left_in.addAnimation(animation_alpha_in);
		
		animation_right_out.addAnimation(translate_right_out);
		animation_right_out.addAnimation(animation_alpha_out);
	}
	
	
	private Context mContext;
	private ViewFlipper mViewFlipper;
	private GestureDetector mGestureDetector;//手势监听器
	private final int FLING_MIN_DISTANCE=100;
	private final int FLING_MIN_VELOCITY=50;

	public ImagePluginView(Context context) {
		super(context);
		this.mContext=context;
		
		this.setOnTouchListener(this);
		this.setLongClickable(true);
		mGestureDetector=new GestureDetector(this);
		mViewFlipper=new ViewFlipper(context);
		this.addView(mViewFlipper);
		
		init();
	}
	
	public void init()
	{
		for(int i=0;i<3;i++)
		{
			ImageView mImageView=new ImageView(this.mContext);
			mImageView.setImageBitmap(ImageFactory.getImage(i+".jpg"));
			this.mViewFlipper.addView(mImageView);
		}
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE&& Math.abs(velocityX) > FLING_MIN_VELOCITY)  // Fling left  
		{	 
//			this.mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext,com.open.androidplugin.R.anim.animation_right_in));
//			this.mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,com.open.androidplugin.R.anim.animation_left_out));
			
			this.mViewFlipper.setInAnimation(animation_right_in);
			this.mViewFlipper.setOutAnimation(animation_left_out);
			this.mViewFlipper.showNext();
			return true;
		} 
		else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE&& Math.abs(velocityX) > FLING_MIN_VELOCITY) // Fling right
		{ 
//			this.mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, com.open.androidplugin.R.anim.animation_left_in));
//			this.mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, com.open.androidplugin.R.anim.animation_right_out));
			
			this.mViewFlipper.setInAnimation(animation_left_in);
			this.mViewFlipper.setOutAnimation(animation_right_out);
			this.mViewFlipper.showPrevious();
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

}
