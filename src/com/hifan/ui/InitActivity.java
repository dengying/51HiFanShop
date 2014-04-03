package com.hifan.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hifan.R;

public class InitActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.init_view);
		
		ImageView imgview=(ImageView) findViewById(R.id.imageView1);
		imgview.setVisibility(View.VISIBLE);
		imgview.startAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
			
				runOnUiThread(new Runnable() {
					public void run() {
						InitActivity.this.finish();
						Intent intent=new Intent(InitActivity.this, MainActivity.class);
						startActivity(intent);
					}
				});
				
			}
		}, 3000);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
