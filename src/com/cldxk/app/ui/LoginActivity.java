package com.cldxk.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cldxk.app.base.EBaseActivity;
import com.cldxk.app.config.CldxkConfig;
import com.cldxk.farcardelegate.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends EBaseActivity implements OnClickListener{

	private TextView login;
	private EditText account, passwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initMylayout();
				
	}
	
	public void initMylayout(){
		
	login = (TextView) findViewById(R.id.wemall_login_button);
	account = (EditText) findViewById(R.id.wemall_login_account);
	passwd = (EditText) findViewById(R.id.wemall_login_passwd);

	login.setOnClickListener(this);
		
	
	}
	
	@Override
	public int setLayout() {
		// TODO Auto-generated method stub
		return R.layout.login_activity;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wemall_login_button:
			// 隐藏键盘
			this.HideKeyboard();
			if (((account.getText().toString().trim()).length() == 0)
					|| ((passwd.getText().toString().trim()).length() == 0)) {

				Toast.makeText(this, "帐号或密码为空", Toast.LENGTH_SHORT)
						.show();
			}

			else {
				login.setClickable(false);
								
				//检测用户状态
				if(checkOk()==true){					
					//获取用户信息
					this.getaccountinfo();
				}else{
					
					//查询用户审核状态
					checkUserStatus();
				}
				
			}
			break;
						
		default:
			break;
		}
	}
	
	public void getaccountinfo(){
		
		//切换到主界面
		Intent it = new Intent(LoginActivity.this, MainActivity.class);
		startActivity(it);
		
		finish();
		
	}
	
	public void HideKeyboard() {
		try {
			((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(this.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {

		}

	}
	
	private Boolean checkOk(){
		
		String msg = msharePreferenceUtil.loadStringSharedPreference("statusok", "");
		if(null == msg){
			return false;
		}else if(msg.equals("已开通")){
			return true;
		}else{
			return false;
		}
	}
	
	private void checkUserStatus(){
		 
		final ProgressDialog dialog = ProgressDialog.show(this, "用户认证状态", "正在查询中...");
		dialog.setCancelable(false);
		
		//发送Http请求
		
		RequestParams params = new RequestParams();
		//参数传递方式
		List<NameValuePair> values = new ArrayList<NameValuePair>(); 
		values.add(new BasicNameValuePair("account",account.getText().toString() ));
		values.add(new BasicNameValuePair("passwd", passwd.getText().toString()));
		
		params.addBodyParameter(values);
		
		httpClient.send(HttpMethod.POST, CldxkConfig.API_LOGIN, params, new RequestCallBack<String>(){

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "连接服务器异常", Toast.LENGTH_SHORT)
				.show();
				
				login.setClickable(true);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				login.setClickable(true);
				
				Log.i("tjc", "--->msg="+arg0.result);
				
				
				JSONObject resultjson = JSON.parseObject(arg0.result);
				int msgid = resultjson.getIntValue("code");
				Log.i("tjc", "--->code="+msgid+"");
				if(msgid == 200){
					JSONArray jsonarray = JSON.parseArray(resultjson.getString("data"));
					if(null != jsonarray){
						
						JSONObject jsonstatus = jsonarray.getJSONObject(0);
						String msgst = jsonstatus.getString("userstatus");
//						Log.i("tjc", jsonstatus.getString("userstatus"));
						
						if(msgst != null){
						if(msgst.equals("已开通")){
							
							msharePreferenceUtil.saveSharedPreferences("statusok", "已开通");
							Toast.makeText(getApplicationContext(), "已通过,可以直接登陆",
									Toast.LENGTH_SHORT).show();
							
						}else{
							Toast.makeText(getApplicationContext(), "请耐心等待审核...",
									Toast.LENGTH_SHORT).show();
						}
					}else{
						Toast.makeText(getApplicationContext(), "请耐心等待审核...",
								Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "请耐心等待审核...",
							Toast.LENGTH_SHORT).show();
				}
				}else{
					Toast.makeText(getApplicationContext(), "用户信息错误...",
							Toast.LENGTH_SHORT).show();
					
				}
			}
							
		});
		
	}


}
