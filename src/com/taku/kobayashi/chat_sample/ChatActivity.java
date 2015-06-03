package com.taku.kobayashi.chat_sample;

import java.net.URISyntaxException;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.provider.DocumentsContract.Root;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChatActivity extends BaseActivity {

	Socket mSocket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_view);
		try {
			mSocket = IO.socket(Config.ROOT_URL);
			mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
					Log.d(getLocalClassName(), "connect!!");
					for(Object o : arg0){
						Log.d(getLocalClassName(), "connect:" + o.toString());
					}
				}
			}).on("comment", new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
					Log.d(getLocalClassName(), "comment!!");
					for(Object o : arg0){
						Log.d(getLocalClassName(), "comment:" + o.toString());
					}
				}
			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
						Log.d(getLocalClassName(), "discomment!!");
						for(Object o : arg0){
							Log.d(getLocalClassName(), "discomment:" + o.toString());
						}
				}
			});
		} catch (URISyntaxException e) {
			Log.d(getLocalClassName(), "error:" + e.getMessage());
			e.printStackTrace();
		}
		
		EditText inputText = (EditText) findViewById(R.id.InputText);
		Button sendButton = (Button) findViewById(R.id.SendButton);
		
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				JSONObject obj = new JSONObject();
			}
		});
	}
}
