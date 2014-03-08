package com.hackathon.pemilu;

import java.util.Locale;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_search_nik)
public class SearchNIKActivity extends Activity {

	@ViewById(R.id.field_nik)
	EditText searchField;

	@ViewById(R.id.button_search)
	Button searchButton;

	private String province;
	protected int provinceId;
	HackathonApplication application;
	SessionManager session;

	@Click(R.id.button_search)
	void searchButtonClicked() {
		String nik = searchField.getText().toString();
		RequestParams params = new RequestParams();
		params.put("cmd", "cari");
		params.put("nik", nik);
		HackathonRESTClient.getDptStatus(params,
				new AsyncHttpResponseHandler() {
					@Override
					@Deprecated
					public void onSuccess(String content) {
						processHtml(content);
					}
				});
	}

	protected void processHtml(String content) {
		Document doc = Jsoup.parse(content);
		Elements group = doc.getElementsByClass("form");
		if (group.size() > 0) {
			for (int i = 0; i < group.size(); i++) {
				Elements child = group.eq(i);
				Elements label = child.select("span[class=label]");
				Elements field = child.select("span[class=field]");
				String labelText = label.text();
				labelText = labelText.replace(":", "");
				if (labelText.equals("Provinsi")) {
					province = field.text().toLowerCase(Locale.getDefault());
				}
			}
			getProvinceId(province);
		} else {
			Elements groups = doc.getElementsByClass("fboxbody");
			Elements errorBox = groups.select("div[class=errorbox]");
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(errorBox.text());
			builder.setMessage(groups.text());
			builder.setPositiveButton("Ok", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.cancel();
				}
			});
			builder.show();
		}
	}

	@SuppressLint("DefaultLocale")
	private void getProvinceId(final String province) {
		application = (HackathonApplication) getApplicationContext();
		session = application.getSession();
		RequestParams params = new RequestParams();
		params.put("apiKey", Constants.PEMILUAPI_KEY);
		HackathonRESTClient.get("/candidate/api/provinsi", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray provinceArray = new JSONArray();
						try {
							provinceArray = response.getJSONObject("data")
									.getJSONObject("results")
									.getJSONArray("provinsi");

							for (int i = 0; i < provinceArray.length(); i++) {
								String provinceName = provinceArray
										.getJSONObject(i).getString(
												"nama_lengkap");
								provinceName = provinceName.toLowerCase(Locale
										.getDefault());
								if (province.equals(provinceName)) {
									provinceId = provinceArray.getJSONObject(i)
											.getInt("id");
								}
							}
							session.setProvinceId(provinceId);
							Intent i = new Intent(SearchNIKActivity.this,
									SelectPartyActivity.class);
							startActivity(i);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

}
