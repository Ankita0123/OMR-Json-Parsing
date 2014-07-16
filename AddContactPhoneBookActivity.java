package com.onlinemobilerecharge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.beans.GetAvailableCountries;
import com.beans.GetCarriersForCountry;
import com.beans.GetResgistrationCountriesBean;
import com.library.APIRequest;
import com.library.ServiceResponse;
import com.library.APIRequest.RequestMethod;

public class AddContactPhonebookActivity extends Activity implements AsyncTaskCompleteListener<ServiceResponse>,OnClickListener, OnItemSelectedListener {

	APIRequest apiRequest;
	AsyncTask asyncTask;
	DoParsing parsing;
	Spinner spinner_selectCountry,spinner_selectCarrier;
	boolean success;
	String token,time;
	EditText edt_fname,edt_lname,edt_phonebook;
	String finalCountryID,finalCarrierID;
	String[] countryNames,countryValues,carriers;
	int[] carrierID;
	Button btn_submit,btn_cancel;
	ArrayList<GetAvailableCountries> getAvailableCountriesArrayList=new ArrayList<GetAvailableCountries>();
	ArrayList<GetCarriersForCountry> getCarriersForCountryArrayList=new ArrayList<GetCarriersForCountry>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact_phonebook);
		
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		token =sharedPreferences.getString("Token", "");
		spinner_selectCountry=(Spinner)findViewById(R.id.spinner_AddPhoneBook_selectcountry);
		spinner_selectCarrier=(Spinner)findViewById(R.id.spinner_AddPhoneBook_selectcarrier);
		
		spinner_selectCountry.setOnItemSelectedListener(this);
		spinner_selectCarrier.setOnItemSelectedListener(this);
		
		edt_fname=(EditText)findViewById(R.id.edt_AddPhoneBook_firstname);
		edt_lname=(EditText)findViewById(R.id.edt_AddPhoneBook_Lastname);
		edt_phonebook=(EditText)findViewById(R.id.edt_AddPhoneBook_Phonenumber);
		
		btn_submit=(Button)findViewById(R.id.btn_AddPhoneBook_submit);
		btn_cancel=(Button)findViewById(R.id.btn_AddPhoneBook_cancel);
		String loading="Loading Countries";
		apiRequest = new APIRequest("https://www.aryty.com/API/getAllAvailableCountries", RequestMethod.GET);
		asyncTask=new AsyncTask(AddContactPhonebookActivity.this, "GetAllAvailableCountries",this , loading);
		asyncTask.execute(apiRequest);
		
		btn_submit.setOnClickListener(this);
		
	}

	@Override
	public void onTaskComplete(ServiceResponse result, String method) {
		// TODO Auto-generated method stub
		if(method.equals("GetAllAvailableCountries"))
		{
			String data=result.getData().toString();
			Log.e("GetAllAvailableCountries", data);
			Log.e("METHOD NAME--", method);
			
			JSONObject mainObj;
			
			try {
				
				mainObj = new JSONObject(data);
				JSONObject statusObj=mainObj.getJSONObject("Status");
				success=statusObj.getBoolean("Success");
				Log.e("SUCCESS STATUS--", success+"");
				
				if(success==false)
				{
					String errorDescription=statusObj.getString("ErrorDescription");
					
					final AlertDialog unableToLoadCountries=new AlertDialog.Builder(AddContactPhonebookActivity.this).create();
					
					unableToLoadCountries.setMessage("Failed to load Countries");
					unableToLoadCountries.setTitle("OMR");
					unableToLoadCountries.setButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//invalidemail.dismiss();
						}
					});
					unableToLoadCountries.show();
				}
				else
				{
					parsing=new DoParsing();
					getAvailableCountriesArrayList=parsing.getAllAvailableCountries(data);
					
					countryNames= new String[getAvailableCountriesArrayList.size()+1];
					countryValues=new String[getAvailableCountriesArrayList.size()+1];
					countryNames[0]="Select Your Country";
					countryValues[0]="";
					for(int i=0;i<getAvailableCountriesArrayList.size();i++)
					{
						GetAvailableCountries bean=getAvailableCountriesArrayList.get(i);
						String country=bean.getName();
						String val=bean.getID();
						countryNames[i+1]=country;
						countryValues[i+1]=val;
					}
					
					ArrayAdapter<CharSequence> countryAdapter=new ArrayAdapter<CharSequence>(AddContactPhonebookActivity.this, R.layout.spinner_layout, R.id.textView1, countryNames);
					spinner_selectCountry.setAdapter(countryAdapter);
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("EXCEPTION", e.toString());
			}
		}
		
		if (method.equals("getCarriersForCountry")) 
		{
			String data=result.getData().toString();
			JSONObject mainObj;
			
			try {
				
				mainObj = new JSONObject(data);
				JSONObject statusObj=mainObj.getJSONObject("Status");
				success=statusObj.getBoolean("Success");
				Log.e("SUCCESS STATUS--", success+"");
				
				if(success==false)
				{
					String errorDescription=statusObj.getString("ErrorDescription");
					
					final AlertDialog unableToLoadCarriers=new AlertDialog.Builder(AddContactPhonebookActivity.this).create();
					
					unableToLoadCarriers.setMessage("Failed to load Carriers");
					unableToLoadCarriers.setTitle("OMR");
					unableToLoadCarriers.setButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//invalidemail.dismiss();
						}
					});
					unableToLoadCarriers.show();
				}
				else
				{
					parsing=new DoParsing();
					getCarriersForCountryArrayList=parsing.getCarriersForCountries(data);
					carriers=new String[getCarriersForCountryArrayList.size()+1];
					carrierID=new int[getCarriersForCountryArrayList.size()+1];
					carriers[0]="Select Carrier";
					carrierID[0]=0;
					for(int i=0;i<getCarriersForCountryArrayList.size();i++)
					{
						GetCarriersForCountry bean=new GetCarriersForCountry();
						bean=getCarriersForCountryArrayList.get(i);
						carriers[i+1]=bean.getName();
						carrierID[i+1]=bean.getID();
					}
					ArrayAdapter<CharSequence> carrierAdapter=new ArrayAdapter<CharSequence>(AddContactPhonebookActivity.this, R.layout.spinner_layout, R.id.textView1, carriers);
					spinner_selectCarrier.setAdapter(carrierAdapter);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (method.equals("AddContact")) {
			
			/*String data=result.getData().toString();
			Log.e("AddContact", data);
			Log.e("METHOD NAME--", method);*/
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
		switch (arg0.getId()) {
		case R.id.spinner_AddPhoneBook_selectcountry:
			
			if(arg2!=0)
			{
				
				String name=arg0.getItemAtPosition(arg2).toString();
				String val=countryValues[arg2];
				finalCountryID=val;
				apiRequest = new APIRequest("https://www.aryty.com/API/getCarriersForCountry/"+val, RequestMethod.GET);
				Log.e("Country Name", name);
				Log.e("val--", val);
				
				String loading="Loading Carriers";
				asyncTask=new AsyncTask(AddContactPhonebookActivity.this, "getCarriersForCountry",this , loading);
				asyncTask.execute(apiRequest);
				
			}
			
			break;

		case R.id.spinner_AddPhoneBook_selectcarrier:
				if(arg2!=0)
				{
					int val=carrierID[arg2];
					finalCarrierID=val+"";
				}
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_AddPhoneBook_submit:
			
			
			/*String loading="Loading";
			apiRequest = new APIRequest("https://www.aryty.com/API/AddContact", RequestMethod.POST);
			
			apiRequest.addParam("token", token);
			apiRequest.addParam("firstName", edt_fname.getText().toString());
			apiRequest.addParam("lastName", edt_lname.getText().toString());
			apiRequest.addParam("countryID", finalCountryID);
			apiRequest.addParam("carrierID", finalCarrierID);
			
			apiRequest.addParam("mobileNumber", edt_phonebook.getText().toString());
			asyncTask=new AsyncTask(AddContactPhonebookActivity.this, "AddContact",this , loading);
			asyncTask.execute(apiRequest);
			*//*
			PostParsing postParsing=new PostParsing();
			postParsing.addContact(AddContactPhonebookActivity.this,token,edt_fname.getText().toString(),edt_lname.getText().toString(),finalCountryID,finalCarrierID,edt_phonebook.getText().toString());*/
			new addContactAsync().execute();
			
			break;

		default:
			break;
		}
		
		
	}
	
	public class addContactAsync extends android.os.AsyncTask<String, Void, String>
	{
		ProgressDialog pd;
		/*public addContactAsync(int id ) 
		{
			// TODO Auto-generated constructor stub
		}*/
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = new ProgressDialog(AddContactPhonebookActivity.this);
			pd.setMessage("Loading");
			pd.setIndeterminate(true);
			pd.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String res = "";
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("https://www.aryty.com/API/AddContact");
			post.setHeader("Content-Type", "application/json");
			
			JSONObject mainObj= new JSONObject();;
			try {
				 
				mainObj.put("token", token);
				mainObj.put("firstName", edt_fname.getText().toString());
				mainObj.put("lastName", edt_lname.getText().toString());
				mainObj.put("countryID", finalCountryID);
				mainObj.put("carrierID", finalCarrierID);
				Log.e("CarrierID", finalCarrierID);
				mainObj.put("mobileNumber",  edt_phonebook.getText().toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				Log.e("Json Exception", e1.toString());
			}
			try {
				
				post.setEntity(new ByteArrayEntity(mainObj.toString().getBytes("UTF8")));
				HttpResponse response = client.execute(post);
				Log.e("METHOD", "method executed");
				InputStream is = response.getEntity().getContent();
				res = convertStreamToString(is);
				Log.d("response", res);

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				Log.e("Unsupported Exception", e.toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				Log.e("Clent Protocol Exception", e.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.e("IOException Exception", e.toString());
			}
			
			return res;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("Response", result);
			// parseThis(result);
			try {
				JSONObject mainObj=new JSONObject(result);
				
				JSONObject statusObj=mainObj.getJSONObject("Status");
				
				boolean success=statusObj.getBoolean("Success");
				
				if(success==true)
				{
					Intent phonebook=new Intent(AddContactPhonebookActivity.this, PhonebookActivity.class);
					startActivity(phonebook);
					finish();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pd.dismiss();
		}

		private String convertStreamToString(InputStream is) {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			StringBuilder data = new StringBuilder();
			try {
				while ((line = br.readLine()) != null)
					data.append(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data.toString();
		}
		
	}
	
}
