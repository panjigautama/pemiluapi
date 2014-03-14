package com.hackathon.pemilu;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HackathonRESTClient {

	private static AsyncHttpClient httpClient = new AsyncHttpClient();

	public static void getDptStatus(RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		httpClient.get(Constants.DPT_URL, params, responseHandler);
	}

	public static void getLocation(RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		httpClient.get(Constants.GEOCODING_URL, params, responseHandler);
	}

	public static void get(String URL, RequestParams params,
			AsyncHttpResponseHandler responseHandler) {
		httpClient.get(getAbsoluteURL(URL), params, responseHandler);
	}

	private static String getAbsoluteURL(String URL) {
		return Constants.API_URL + URL;
	}

}
