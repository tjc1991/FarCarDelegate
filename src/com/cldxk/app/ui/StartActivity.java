package com.cldxk.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cldxk.app.base.EBaseActivity;
import com.cldxk.farcardelegate.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class StartActivity extends EBaseActivity {
	private Handler mMainHandler = new Handler() {

		public void handleMessage(Message msg) {
			
			String userphone = msharePreferenceUtil.loadStringSharedPreference("userName", "");
			
			Log.i("tjc", "-->="+userphone);
			
			if(null != userphone && !TextUtils.isEmpty(userphone))
			{
				Intent it = new Intent(StartActivity.this, MainActivity.class);
				startActivity(it);
			}	
			else{
				Intent it = new Intent(StartActivity.this, LoginActivity.class);
				startActivity(it);	
			}
			finish();
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.wemall_splash);
		
		mMainHandler.sendEmptyMessageDelayed(0, 2000);
	}

	@Override
	public int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.start_activity;
	}

	
//	public void GetUserMsgCar(String ph , String pwd){
//		
//		//发送Http请求
//		
//		RequestParams params = new RequestParams();
//		//参数传递方式
//		List<NameValuePair> values = new ArrayList<NameValuePair>(); 
//		values.add(new BasicNameValuePair("account",ph));
//		values.add(new BasicNameValuePair("passwd", pwd));
//		
//		params.addBodyParameter(values);
//		
//		httpClient.send(HttpMethod.POST, CldxkConfig.API_CHECK_MSG, params, new RequestCallBack<String>(){
//
//			@Override
//			public void onFailure(HttpException arg0, String arg1) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), "连接服务器异常", Toast.LENGTH_SHORT)
//				.show();
//			}
//
//			@Override
//			public void onSuccess(ResponseInfo<String> arg0) {
//				// TODO Auto-generated method stub
//				
//				Log.i("tjc", "--->msg="+arg0.result);
//								
//				JSONObject resultjson = JSON.parseObject(arg0.result);
//				int msgid = resultjson.getIntValue("code");
//				Log.i("tjc", "--->code="+msgid+"");
//				if(msgid == 200){
//					
//					JSONArray jsarray = resultjson.getJSONArray("data");
//					
//					JSONObject xhobj = jsarray.getJSONObject(0);
//					
//					Log.i("tjc", "--->sda");
//					
//					String xhstr = xhobj.getString("bankcar");
//					
//					JSONObject carobj = jsarray.getJSONObject(0);
//					String carstr = carobj.getString("bxdlicene");
//					
//					
//					Log.i("tjc", "--->xhstr="+xhstr);
//					Log.i("tjc", "--->carstr="+carstr);
//					
//					//保存新密码
//					msharePreferenceUtil.saveSharedPreferences("userCar",xhstr);
//					
//					//清楚登录状态
//					msharePreferenceUtil.saveSharedPreferences("carxh", carstr);
//					
//					//跳转Activity
//					Intent it = new Intent(SplashActivity.this, MainActivity.class);
//					startActivity(it);										
//					finish();
//				}
//
//			}
//							
//		});
//	}

}
