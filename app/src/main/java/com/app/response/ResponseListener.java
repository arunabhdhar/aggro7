package com.app.response;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.AppConstant;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ericbasendra on 27/08/15.
 */
public class ResponseListener {

    public ResponseListener() {
    }

    public static void postNewRequest(Context context,String url ,final HashMap<String, String> hm,final PostCommentResponseListener mPostCommentResponse){
        mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST, url,createMyReqSuccessListener(mPostCommentResponse),
                createMyReqErrorListener(mPostCommentResponse)){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return hm;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                AppConstant.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(sr);
    }


    private static Response.Listener<String>createMyReqSuccessListener(final PostCommentResponseListener mPostCommentResponse){

        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mPostCommentResponse.requestCompleted(response);
            }
        };
    }

    private static Response.ErrorListener createMyReqErrorListener(final PostCommentResponseListener mPostCommentResponse){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
                mPostCommentResponse.requestEndedWithError(error);
            }
        };
    }


    public interface PostCommentResponseListener {
        public void requestStarted();
        public void requestCompleted(String response);
        public void requestEndedWithError(VolleyError error);
    }
}
