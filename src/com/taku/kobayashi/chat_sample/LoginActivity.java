package com.taku.kobayashi.chat_sample;

import java.util.HashMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

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
		Button sendButton = (Button) findViewById(R.id.SendButton);
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomHttpRequest request = new CustomHttpRequest(Request.Method.POST, Config.ROOT_URL + "user/create", new Response.Listener<String>(){
					@Override
					public void onResponse(String res) {
						Log.d(getLocalClassName(), res);
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
				addRequestQueue(request);
			}
		});
	}
}
