package com.hackathon.pemilu;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HackathonRESTClient {

	private static AsyncHttpClient httpClient = new AsyncHttpClient();
	private static final String DPT_URL = "http://data.kpu.go.id/dpt.php";
	private static final String API_URL = "http://api.pemiluapi.org";

	public static void getDptStatus(RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		httpClient.get(DPT_URL, params, responseHandler);
	}

	public static void get(String URL, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		httpClient.get(getAbsoluteURL(URL), params, responseHandler);
	}

	private static String getAbsoluteURL(String URL) {
		return API_URL + URL;
	}

}
