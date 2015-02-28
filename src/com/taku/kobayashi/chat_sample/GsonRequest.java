package com.taku.kobayashi.chat_sample;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonRequest<T> extends HttpRequestBase<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;

    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        this(method, url, clazz, errorListener);
        this.listener = listener;
    }

    public GsonRequest(int method, String url, Class<T> clazz, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
    }

    @SuppressWarnings("rawtypes")
	@Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        if(this.listener != null & this.listener instanceof HttpRequestBase.Listener){
            ((HttpRequestBase.Listener) listener).onNetworkResponse(response);
        }
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}