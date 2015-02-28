package com.taku.kobayashi.chat_sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpRequestExecutor;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_view);
		final EditText inputText = (EditText) findViewById(R.id.InputText);
		SharedPreferences sp = Preferences.getCommonPreferences(this);
		inputText.setText(sp.getString("name", ""));
		inputText.setHint(R.string.login_hint_text);
		Button sendButton = (Button) findViewById(R.id.SendButton);
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomHttpRequest request = new CustomHttpRequest(Request.Method.POST, Config.ROOT_URL + "user/create", new HttpRequestBase.Listener<String>(){
					@Override
					public void onResponse(String res) {
						if(LoginActivity.this.isFinishing()) return;
						Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
						startActivity(intent);
						finish();
					}
					@Override
					public void onNetworkResponse(NetworkResponse response) {
						Log.d(getLocalClassName(), "code:" + response.statusCode);
						for(Map.Entry<String, String> e :response.headers.entrySet()){
							Log.d(getLocalClassName(), "key:" + e.getKey() + " value:" + e.getValue());
						}
					}
				}, new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(getLocalClassName(), error.getMessage());
					}
				});
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("name", inputText.getEditableText().toString());
				request.setParams(params);
				Preferences.saveCommonParam(LoginActivity.this, "name", inputText.getEditableText().toString());
				addRequestQueue(request);
			}
		});
	}
}
