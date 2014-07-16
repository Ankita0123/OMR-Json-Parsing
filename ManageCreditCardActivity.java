package com.onlinemobilerecharge;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.adapters.ManageCCAdapter;
import com.beans.GetCreditCardsBean;
import com.library.APIRequest;
import com.library.ServiceResponse;
import com.library.APIRequest.RequestMethod;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ManageCreditCardsActivity extends Activity implements AsyncTaskCompleteListener<ServiceResponse>{

	ListView cardsList;
	APIRequest apiRequest;
	AsyncTask asyncTask; 
	String data,token;
	boolean success;
	Button btn_addCard;
	DoParsing parsing;
	ManageCCAdapter adapter;
	ArrayList<GetCreditCardsBean> getCreditCardsList=new ArrayList<GetCreditCardsBean>();
	RelativeLayout layout_list,layout_null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credit_card_main);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		final String token =sharedPreferences.getString("Token", "");
		//token=getIntent().getStringExtra("Token");
		cardsList=(ListView)findViewById(R.id.list_manageCards_listCards);
		layout_list=(RelativeLayout)findViewById(R.id.layout_list);
		layout_null=(RelativeLayout)findViewById(R.id.img1_creditcard1);
		btn_addCard=(Button)findViewById(R.id.btn_creditcard_add_card);
		
		apiRequest = new APIRequest("https://www.aryty.com/API/getCreditCards/"+token, RequestMethod.GET);
		asyncTask=new AsyncTask(ManageCreditCardsActivity.this, "getCreditCards",this , getString(R.string.loading));
		asyncTask.execute(apiRequest);
		
		
		btn_addCard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent addCard=new Intent(ManageCreditCardsActivity.this, AddCreditCardActivity.class);
				startActivity(addCard);
			}
		});
	}
	@Override
	public void onTaskComplete(ServiceResponse result, String method) {
		// TODO Auto-generated method stub

		if(method.equals("getCreditCards"))
		{
			data=result.getData().toString();
			Log.e("GetCreditCards", data);
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
					
					final AlertDialog invalidunamepassword=new AlertDialog.Builder(ManageCreditCardsActivity.this).create();
					
					invalidunamepassword.setMessage("Failed to get Load Cards");
					invalidunamepassword.setTitle("OMR");
					invalidunamepassword.setButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//invalidemail.dismiss();
						}
					});
					invalidunamepassword.show();
				}
				else
				{
					parsing=new DoParsing();
					getCreditCardsList=parsing.getCreditCards(data);
					
					/*GetCreditCardsBean bean=new GetCreditCardsBean();
					bean.setCardHolderName("Ami");
					bean.setDisplayNumber("AmericanExpress************4005");
					getCreditCardsList.add(bean);
					*/
					if(getCreditCardsList.size()==0)
					{
						layout_list.setVisibility(View.GONE);
						layout_null.setVisibility(View.VISIBLE);
					}
					adapter=new ManageCCAdapter(ManageCreditCardsActivity.this, 0, 0, getCreditCardsList);
					cardsList.setAdapter(adapter);
					
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
