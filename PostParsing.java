package com.onlinemobilerecharge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;



import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class PostParsing {

	Activity context;
	public void addContact(Activity context,final String token, final String fname, final String lname, final String finalCountryID, final String finalCarrierID, final String phoneNo)
	{
		
		this.context=context;
		
		
		class AddContactAsync extends AsyncTask<String, Void, String>
		{

			ProgressDialog pd;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd = new ProgressDialog(PostParsing.this.context);
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
				
				ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("token", token));
				param.add(new BasicNameValuePair("firstName", fname));
				param.add(new BasicNameValuePair("lastName", lname));
				param.add(new BasicNameValuePair("countryID", finalCountryID));
				param.add(new BasicNameValuePair("carrierID", finalCarrierID));
				param.add(new BasicNameValuePair("mobileNumber", phoneNo));
				
				try {
					post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
					HttpResponse response = client.execute(post);
					InputStream is = response.getEntity().getContent();
					res = convertStreamToString(is);
					Log.d("response", res);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return res;
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Log.d("Response", result);
				// parseThis(result);
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
}
