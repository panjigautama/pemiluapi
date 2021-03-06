package com.hackathon.pemilu;

import java.util.ArrayList;
import java.util.Locale;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
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
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

@EActivity(R.layout.activity_search_nik)
public class SearchNIKActivity extends Activity {

	@ViewById(R.id.field_nik)
	EditText searchField;

	@ViewById(R.id.button_search)
	Button searchButton;

	@ViewById(R.id.buttonCandidateSearch)
	Button searchCandidateButton;

	@ViewById(R.id.buttonReport)
	Button report;

	@ViewById
	TextView nik, nama, kelamin, kelurahan, kecamatan, kabupaten, provinsi,
			reportText;

	@ViewById
	LinearLayout dataView;

	private String province, district;
	private int provinceId;
	private ArrayList<Area> areaList;
	HackathonApplication application;
	SessionManager session;
	ProgressDialog progDialog;

	@UiThread
	void showDialog(boolean state) {
		if ((state == true)
				&& (progDialog == null || progDialog.isShowing() == false)) {
			progDialog = ProgressDialog.show(this, "", "Sedang mencari...");
			progDialog.show();
		} else {
			progDialog.dismiss();
		}
	}

	@AfterViews
	void setDataViewInvisible() {
		dataView.setVisibility(View.GONE);
		searchCandidateButton.setVisibility(View.GONE);
		report.setVisibility(View.GONE);
		reportText.setVisibility(View.GONE);
	}

	@Click(R.id.button_search)
	void searchButtonClicked() {
		showDialog(true);
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

	@Click(R.id.buttonCandidateSearch)
	void candidateSearchButtonClicked() {
		Intent i = new Intent(SearchNIKActivity.this, SelectPartyActivity.class);
		startActivity(i);
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
					provinsi.setText(field.text());
				}
				if (labelText.equals("Nama")) {
					nama.setText(field.text());
				}
				if (labelText.equals("NIK")) {
					nik.setText(field.text());
				}
				if (labelText.equals("Jenis kelamin")) {
					kelamin.setText(field.text());
				}
				if (labelText.equals("Kelurahan")) {
					kelurahan.setText(field.text());
				}
				if (labelText.equals("Kecamatan")) {
					kecamatan.setText(field.text());
					district = field.text().toLowerCase();
					session.setDistrict(field.text());
				}
				if (labelText.equals("Kabupaten/Kota")) {
					kabupaten.setText(field.text());
				}

			}
			getDapilData();
			System.out.println(province);
			// getProvinceId();
		} else {
			showDialog(false);
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

	private void getDapilData() {
		RequestParams params = new RequestParams();
		params.put("key", Constants.GOOGLEAPI_KEY);
		params.put("address", district);
		params.put("sensor", true);
		HackathonRESTClient.getLocation(params, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				double latitude, longitude;
				try {
					if (response.getString("status").equals("OK")) {
						JSONObject location = response.getJSONArray("results")
								.getJSONObject(0).getJSONObject("geometry")
								.getJSONObject("location");
						latitude = location.getDouble("lat");
						longitude = location.getDouble("lng");
						getDapilByLocation(latitude, longitude);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void getDapilByLocation(double latitude, double longitude) {
		areaList = new ArrayList<Area>();
		RequestParams params = new RequestParams();
		params.put("apiKey", Constants.PEMILUAPI_KEY);
		params.put("lat", latitude);
		params.put("long", longitude);
		HackathonRESTClient.get("/geographic/api/point", params,
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray areas = new JSONArray();
						try {
							areas = response.getJSONObject("data")
									.getJSONObject("results")
									.getJSONArray("areas");
							for (int i = 0; i < areas.length(); i++) {
								String kind = areas.getJSONObject(i).getString(
										"kind");
								String nama = areas.getJSONObject(i).getString(
										"nama");
								String lembaga = areas.getJSONObject(i)
										.getString("lembaga");
								String id;
								if (kind.equals("Provinsi")) {
									id = String.valueOf(areas.getJSONObject(i)
											.getInt("id"));
									session.setProvinceId(areas
											.getJSONObject(i).getInt("id"));
								}
								id = areas.getJSONObject(i).getString("id");
								if (lembaga.startsWith("DPRD")) {
									session.setDapilDPRD(id, lembaga);
								} else if (lembaga.startsWith("DPR")) {
									session.setDapilDPR(id);
								}
								Area area = new Area(kind, id, nama, lembaga);
								areaList.add(area);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@SuppressLint("DefaultLocale")
	private void getProvinceId() {
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
							showDialog(false);
							session.setProvinceId(provinceId);
							searchCandidateButton.setVisibility(View.VISIBLE);
							report.setVisibility(View.VISIBLE);
							reportText.setVisibility(View.VISIBLE);
							dataView.setVisibility(View.VISIBLE);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

}
