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
import com.github.nkzawa.engineio.client.transports.WebSocket;

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

	private Socket mSocket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_view);
		ConnectionSocketIO();

		final EditText inputText = (EditText) findViewById(R.id.InputText);
		Button sendButton = (Button) findViewById(R.id.SendButton);
		
		sendButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSocket.emit("message", inputText.getEditableText().toString());
			}
		});
	}

	private void ConnectionSocketIO(){
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
			});
			mSocket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
					Log.d(getLocalClassName(), "error!!");
					for(Object o : arg0){
						Log.d(getLocalClassName(), "error:" + o.toString());
					}
				}
			});
			mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
					Log.d("ChatActivity", "timeout!!");
					for(Object o : arg0){
						Log.d(getLocalClassName(), "timeout:" + o.toString());
					}
				}
			});
			mSocket.on("message", new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
					Log.d("ChatActivity", "message!!");
					for(Object o : arg0){
						Log.d(getLocalClassName(), "message:" + o.toString());
					}
				}
			});
			mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... arg0) {
						Log.d("ChatActivity", "discomment!!");
						for(Object o : arg0){
							Log.d(getLocalClassName(), "discomment:" + o.toString());
						}
				}
			});
			mSocket.connect();
		} catch (URISyntaxException e) {
			Log.d("ChatActivity", "error:" + e.getMessage());
			e.printStackTrace();
		}
	}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
    }
}
