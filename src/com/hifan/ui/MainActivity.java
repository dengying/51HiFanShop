package com.hifan.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.hifan.R;

public class MainActivity extends Activity {
	
	private final static String url="http://www.51hifan.com/?/mobile/mobile";
	private WebView webui;
	private ProgressDialog loadingDialog=null;
	private LinearLayout msg_view=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_view);
		
		msg_view = (LinearLayout) findViewById(R.id.msg_view);
		msg_view.setVisibility(View.GONE);
		msg_view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(webui!=null){
					webui.reload();
				}
				
			}
		});
		webui = (WebView) findViewById(R.id.web_view);
		webui.getSettings().setJavaScriptEnabled(true);
		webui.getSettings().setBuiltInZoomControls(false);//设置使支持缩放  
		webui.loadUrl(url);
		webui.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				if(loadingDialog!=null && loadingDialog.isShowing()){
					loadingDialog.dismiss();
				}
				loadingDialog=ProgressDialog.show(MainActivity.this, "", getString(R.string.loading), true, true);
				msg_view.setVisibility(View.GONE);
				view.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				if(loadingDialog!=null && loadingDialog.isShowing()){
					loadingDialog.dismiss();
				}
//				msg_view.setVisibility(View.GONE);
			}
			
            @Override  
            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
                // TODO Auto-generated method stub  
                view.loadUrl(url);// 使用当前WebView处理跳转  
                return true;//true表示此事件在此处被处理，不需要再广播  
            }  
            @Override   //转向错误时的处理  
            public void onReceivedError(WebView view, int errorCode,  
                    String description, String failingUrl) {  
                // TODO Auto-generated method stub  
            	if(loadingDialog!=null && loadingDialog.isShowing()){
					loadingDialog.dismiss();
				}
            	view.setVisibility(View.GONE);
            	msg_view.setVisibility(View.VISIBLE);
//                Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();  
            }  
        });  
	}
	
	@Override   //默认点回退键，会退出Activity，需监听按键操作，使回退在WebView内发生  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        // TODO Auto-generated method stub  
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	if(webui.canGoBack()){
        		webui.goBack();
        	}else{
        		AlertDialog.Builder builder=new Builder(this);
        		builder.setTitle(R.string.exit);
        		builder.setMessage(R.string.exit_msg);
        		builder.setNegativeButton(getString(R.string.exit_btn_yes), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
						finish();
						
					}
				});
        		builder.setPositiveButton(getString(R.string.exit_btn_no), new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						
						dialog.dismiss();
						
					}
				});
        		builder.show();
        	}
            return true;  
        }  
        return super.onKeyDown(keyCode, event);  
    }  
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
