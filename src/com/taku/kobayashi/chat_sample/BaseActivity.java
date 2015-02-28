package com.taku.kobayashi.chat_sample;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public abstract class BaseActivity extends Activity{

  private RequestQueue _queue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    _queue = Volley.newRequestQueue(this);
  }

  protected void setHttpStack(HttpStack stack) {
    _queue = Volley.newRequestQueue(this, stack);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected void addRequestQueue(HttpRequestBase request) {
    _queue.add(request);
  }

  // Volleyでは400番代、500番代のステータスコードを受け取るとエラーになるので、そのときの共通処理をここに書いておく
  protected ErrorListener commonRequestErrorHanlingListener(){
    ErrorListener errorListener = new ErrorListener(){
      @Override
      public void onErrorResponse(VolleyError error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        switch(error.networkResponse.statusCode){
        //メンテナンス中
        case HttpStatus.SC_SERVICE_UNAVAILABLE:
          builder.setMessage("メンテナンス中です");
          break;
        //バージョンが古い
        case 501:
          builder.setMessage("バージョンが古いですアップデートしてください");
          break;
        //認証エラー
        case HttpStatus.SC_UNAUTHORIZED:
          builder.setMessage("認証エラーです");
          break;
        default:
          builder.setMessage(error.getMessage());
          break;
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            finish();
          }
        });
        builder.show();
      }
    };
    return errorListener;
  }
}
