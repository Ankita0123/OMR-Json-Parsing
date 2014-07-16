package com.onlinemobilerecharge;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.beans.GetAllContactsBean;
import com.beans.GetAvailableCountries;
import com.beans.GetCarriersForCountry;
import com.beans.GetCreditCardCountriesBean;
import com.beans.GetCreditCardType;
import com.beans.GetCreditCardsBean;
import com.beans.GetLDICarriersBean;
import com.beans.GetLoadIncrementBean;
import com.beans.GetRegistrationCarriesBean;
import com.beans.GetResgistrationCountriesBean;
import com.beans.GetTransactionsHistory;
import com.beans.GetUserBean;
import com.beans.ScheduleBean;

public class DoParsing {

	ArrayList<GetAllContactsBean> ContactsArrayList;
	ArrayList<GetAvailableCountries> getCountriesArrayList=new ArrayList<GetAvailableCountries>();
	
	public GetUserBean GetUserLogin(String data)
	{
		GetUserBean GetUserBean = new GetUserBean();
		try {
				JSONObject mainObj=new JSONObject(data);
				
				
				JSONObject dataObject=mainObj.getJSONObject("Data");
				
				String firstName=dataObject.getString("FirstName");
				GetUserBean.setFirstName(firstName);
				Log.e("FNAME---", firstName);
				
				String free_Load=dataObject.getString("FreeLoad");
				int freeLoad=Integer.parseInt(free_Load);
				Log.e("FreeLoad---", free_Load);
				GetUserBean.setFreeLoad(freeLoad);
				
				String language=dataObject.getString("Lang");
				GetUserBean.setLang(language);
				Log.e("Lang---", language);
				
				JSONObject monthlyChargeObject=dataObject.getJSONObject("MonthlyCharge");
				
				int valueOfMonthlyCharge=monthlyChargeObject.getInt("Value");
				GetUserBean.setValueOfMonthlyCharge(valueOfMonthlyCharge);
				Log.e("MonthlyCharge---", valueOfMonthlyCharge+"");
				
				JSONObject monthlyLimitObject=dataObject.getJSONObject("MonthlyLimit");
				
				double valueOfMonthlyLimit=monthlyLimitObject.getDouble("Value");
				GetUserBean.setValueOfMonthlyLimit(valueOfMonthlyLimit);
				Log.e("MonthlyLimit---", valueOfMonthlyLimit+"");
				
				int phoneVerificationStatus=dataObject.getInt("PhoneVerificationStatus");
				GetUserBean.setPhoneVerificationStatus(phoneVerificationStatus);
				Log.e("VerfStatus---", phoneVerificationStatus+"");
				
				boolean sendScheduleNotification=dataObject.getBoolean("SendscheduleNotification");
				GetUserBean.setSendScheduleNotification(sendScheduleNotification);
				Log.e("Notification---", sendScheduleNotification+"");
				
				int status=dataObject.getInt("Status");
				GetUserBean.setStatus(status);
				Log.e("status---", status+"");
				
				String token=dataObject.getString("Token");
				GetUserBean.setToken(token);
				Log.e("TOKEN---", token);
					
					
					
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return GetUserBean;
	}
	
	public ArrayList<GetAllContactsBean> getAllContact(String data)
	{
		ArrayList<GetAllContactsBean> GetallcontactsArrayList=new ArrayList<GetAllContactsBean>();
		ContactsArrayList=new ArrayList<GetAllContactsBean>();
		
		try {
			JSONObject mainObj=new JSONObject(data);
			
			JSONArray dataArray = mainObj.getJSONArray("Data");
			
			for(int i=0;i<dataArray.length();i++)
			{
				GetAllContactsBean getAllContact = new GetAllContactsBean();
				
				JSONObject dataobj = dataArray.getJSONObject(i);
			
				String AreaCode = dataobj.getString("AreaCode");
				getAllContact.setAreaCode(AreaCode);
				
				String CarrierAndNumber =dataobj.getString("CarrierAndNumber");
				getAllContact.setCarrierAndNumber(CarrierAndNumber);
				
				int CarrierID = dataobj.getInt("CarrierID");
				getAllContact.setCarrierID(CarrierID);
				
				String CarrierName = dataobj.getString("CarrierName");
				getAllContact.setCarrierName(CarrierName);
				
				String CountryCode = dataobj.getString("CountryCode");
				getAllContact.setCountryCode(CountryCode);
				
				String DisplayField = dataobj.getString("DisplayField");
				getAllContact.setDisplayField(DisplayField);
				
				String FirstName = dataobj.getString("FirstName");
				getAllContact.setFirstName(FirstName);
				
				int ForeignPhoneID = dataobj.getInt("ForeignPhoneID");
				getAllContact.setForeignPhoneID(ForeignPhoneID);
				
				int ID = dataobj.getInt("ID");
				getAllContact.setID(ID);
				
				String LastName = dataobj.getString("LastName");
				getAllContact.setLastName(LastName);
				
				String MobilePhone = dataobj.getString("MobilePhone");
				getAllContact.setMobilePhone(MobilePhone);
				
				GetallcontactsArrayList.add(getAllContact);
			}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ContactsArrayList=GetallcontactsArrayList;
		return GetallcontactsArrayList;
		
	}
	
	public ArrayList<GetLoadIncrementBean> GetLoadIncrement(String data)
	{
		ArrayList<GetLoadIncrementBean> getLoadIncrementsArrayList=new ArrayList<GetLoadIncrementBean>();
		//GetLoadIncrementBean getLoadIncrementBean = new GetLoadIncrementBean();
		
		try {
			JSONObject mainObj = new JSONObject(data);
			JSONObject dataObj = mainObj.getJSONObject("Data");
			
			
			JSONArray LoadIncrementsArray = dataObj.getJSONArray("LoadIncrements");
			
			for(int i=0;i<LoadIncrementsArray.length();i++)
			{
				GetLoadIncrementBean getLoadIncrementBean=new GetLoadIncrementBean();
				
				JSONObject LoadIncrementsObj = LoadIncrementsArray.getJSONObject(i);
				
				String Currency = LoadIncrementsObj.getString("Currency");
				getLoadIncrementBean.setCurrency(Currency);
				Log.e("CURRENCY--", getLoadIncrementBean.getCurrency());
				
				String ExpiresText = LoadIncrementsObj.getString("ExpiresText");
				getLoadIncrementBean.setExpiresText(ExpiresText);
				
				double ForeignValue = LoadIncrementsObj.getDouble("ForeignValue");
				getLoadIncrementBean.setForeignValue(ForeignValue);
				Log.e("FOREIGN VALUE--", ForeignValue+"");
				
				String LoadChoice = LoadIncrementsObj.getString("LoadChoice");
				getLoadIncrementBean.setLoadChoice(LoadChoice);
				Log.e("LOADCHOICE--", LoadChoice+"");
				
				double UsValue = LoadIncrementsObj.getDouble("UsValue");
				getLoadIncrementBean.setUsValue(UsValue);
				Log.e("US VALUE--", UsValue+"");
				
				getLoadIncrementsArrayList.add(getLoadIncrementBean);
			}	
			
			
			
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getLoadIncrementsArrayList;
		
	}
	
	public ArrayList<GetAvailableCountries> getAllAvailableCountries(String data)
	{
		ArrayList<GetAvailableCountries> getAvailableCountriesArrayList=new ArrayList<GetAvailableCountries>();
		
		try {
			JSONObject mainObj = new JSONObject(data);
			
			JSONArray dataArray=mainObj.getJSONArray("Data");
			
			for(int i=0;i<dataArray.length();i++)
			{
				GetAvailableCountries bean=new GetAvailableCountries();
				
				JSONObject dataObj=dataArray.getJSONObject(i);
				
				String ID=dataObj.getString("ID");
				bean.setID(ID);
				//Log.e("ID", ID);
				
				String areaCode=dataObj.getString("areaCode");
				bean.setAreaCode(areaCode);
				//Log.e("AREA CODE", areaCode);
				
				String name=dataObj.getString("name");
				bean.setName(name);
				//Log.e("NAME", name);
				
				getAvailableCountriesArrayList.add(bean);
				getCountriesArrayList.add(bean);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCPETION FOR GET AVAILABLE COUNTRIES--", e.toString());
		}
		getCountriesArrayList=getAvailableCountriesArrayList;
		
		return getAvailableCountriesArrayList;
	}
	
	public ArrayList<GetAvailableCountries> getArrayListOfCountries()
	{
		Log.e("SIZE----", getCountriesArrayList.size()+"");
		return getCountriesArrayList;
	}
	
	
	public ArrayList<GetResgistrationCountriesBean> RegistrationCountries(String data)
	{
		ArrayList<GetResgistrationCountriesBean> registrarioncountry=new ArrayList<GetResgistrationCountriesBean>();
		
		
		try {
			JSONObject mainObj=new JSONObject(data);
		//	JSONObject dataObj=mainObj.getJSONObject("data");
			JSONArray dataArray = mainObj.getJSONArray("Data");
			
			for(int i=0;i<dataArray.length();i++)
			{
				GetResgistrationCountriesBean getRegistrationBean = new GetResgistrationCountriesBean();
				
				JSONObject dataObj = dataArray.getJSONObject(i);
			
				String Name=dataObj.getString("Name");
				getRegistrationBean.setName(Name);
				//Log.e("Name----", Name);
				
				String Value=dataObj.getString("Value");
				getRegistrationBean.setValue(Value);
				//Log.e("Value",Value);
				
				registrarioncountry.add(getRegistrationBean);
			}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return registrarioncountry;
		
	}
	public ArrayList<GetRegistrationCarriesBean> RegistrationCarrier(String data)
	{
		ArrayList<GetRegistrationCarriesBean> registrarioncarrier=new ArrayList<GetRegistrationCarriesBean>();
		try {
			JSONObject mainObj=new JSONObject(data);
			JSONArray dataArray = mainObj.getJSONArray("Data");
			for(int i=0;i<dataArray.length();i++)
			{
				GetRegistrationCarriesBean getRegistrationCarriesBean = new GetRegistrationCarriesBean();
				
				JSONObject dataObj = dataArray.getJSONObject(i);
			
				String Name=dataObj.getString("Name");
				getRegistrationCarriesBean.setName(Name);
				Log.e("Name----", Name);
				
				int Value=dataObj.getInt("Value");
				getRegistrationCarriesBean.setValue(Value);
				Log.e("Value",Value+"");
				
				registrarioncarrier.add(getRegistrationCarriesBean);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return registrarioncarrier;
		
	}
	
	
	public ArrayList<GetCarriersForCountry> getCarriersForCountries(String data)
	{
		ArrayList<GetCarriersForCountry> getCarriersForCountryArrayList=new ArrayList<GetCarriersForCountry>();
		try {
			
			JSONObject mainObj=new JSONObject(data);
			
			JSONObject dataObj=mainObj.getJSONObject("Data");
			
			JSONArray carriersArray=dataObj.getJSONArray("Carriers");
			
			for(int i=0;i<carriersArray.length();i++)
			{
				GetCarriersForCountry bean=new GetCarriersForCountry();
				
				JSONObject obj=carriersArray.getJSONObject(i);
				
				int digits=obj.getInt("Digits");
				bean.setDigits(digits);
				
				int digits1=obj.getInt("Digits1");
				bean.setDigits1(digits1);
				
				int ID=obj.getInt("ID");
				bean.setID(ID);
				
				String name=obj.getString("Name");
				bean.setName(name);
				
				getCarriersForCountryArrayList.add(bean);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getCarriersForCountryArrayList;
	}
	
	public ArrayList<GetCreditCardsBean> getCreditCards(String data)
	{
		ArrayList<GetCreditCardsBean> getCreditCardsList=new ArrayList<GetCreditCardsBean>();
		try {
			
			JSONObject mainObj=new JSONObject(data);
			
			JSONArray dataArray=mainObj.getJSONArray("Data");
			
			for(int i=0;i<dataArray.length();i++)
			{
				GetCreditCardsBean bean=new GetCreditCardsBean();
				
				JSONObject dataObj=dataArray.getJSONObject(i);
				
				long id=dataObj.getLong("ID");
				bean.setId(id);
				
				int status=dataObj.getInt("Status");
				bean.setStatus(status);
				
				boolean expired=dataObj.getBoolean("Expired");
				bean.setExpired(expired);
				
				String holderName=dataObj.getString("CardHolderName");
				bean.setCardHolderName(holderName);
				
				String displayNumber=dataObj.getString("DisplayNumber");
				bean.setDisplayNumber(displayNumber);
				
				JSONObject addressObj=dataObj.getJSONObject("Address");
				
				String address1=addressObj.getString("Address1");
				bean.setAddress1(address1);
				
				String address2=addressObj.getString("Address2");
				bean.setAddress2(address2);
				
				String city=addressObj.getString("City");
				bean.setCity(city);
				
				String countryCode=addressObj.getString("CountryCode");
				bean.setCountryCode(countryCode);
				
				String state=addressObj.getString("State");
				bean.setState(state);
				
				String zip=addressObj.getString("Zip");
				bean.setZip(zip);
				
				getCreditCardsList.add(bean);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("EXCEPTION ", e.toString());
		}
		
		return getCreditCardsList;
	}
	
	public ArrayList< GetCreditCardType> getCreditCardtype(String data)
	{
		ArrayList<GetCreditCardType> creditcardtype=new ArrayList<GetCreditCardType>();
		

		try {
			JSONObject mainObj=new JSONObject(data);
		//	JSONObject dataObj=mainObj.getJSONObject("data");
			JSONArray dataArray = mainObj.getJSONArray("Data");
			
			for(int i=0;i<dataArray.length();i++)
			{
				GetCreditCardType creditcardtypebean =new GetCreditCardType();
				
				
				JSONObject dataObj = dataArray.getJSONObject(i);
			
				String Name=dataObj.getString("Name");
				creditcardtypebean.setName(Name);
				//Log.e("Name----", Name);
				
				int Value=dataObj.getInt("Value");
				creditcardtypebean.setValue(Value);
				//Log.e("Value",Value);
				
				creditcardtype.add(creditcardtypebean);
			}
		}catch (Exception e) {
				// TODO: handle exception
			}
			
			
		return creditcardtype;
		
	}
	

	public ArrayList<GetCreditCardCountriesBean> getCreditCardCountry(String data)
	{
		ArrayList<GetCreditCardCountriesBean> creditCardCountry=new ArrayList<GetCreditCardCountriesBean>();
		
		
		try {
			JSONObject mainObj=new JSONObject(data);
		//	JSONObject dataObj=mainObj.getJSONObject("data");
			JSONArray dataArray = mainObj.getJSONArray("Data");
			
			for(int i=0;i<dataArray.length();i++)
			{
				GetCreditCardCountriesBean getcreditCardBean = new GetCreditCardCountriesBean();
				
				JSONObject dataObj = dataArray.getJSONObject(i);
			
				String Name=dataObj.getString("Name");
				getcreditCardBean.setName(Name);
				//Log.e("Name----", Name);
				
				String Value=dataObj.getString("Value");
				getcreditCardBean.setValue(Value);
				//Log.e("Value",Value);
				
				creditCardCountry.add(getcreditCardBean);
			}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		
		return creditCardCountry;
		
	}

	public ArrayList<GetLDICarriersBean> getLDICarriers(String data)
	{
			ArrayList<GetLDICarriersBean> LDICarriers=new ArrayList<GetLDICarriersBean>();
			

			try {
				JSONObject mainObj=new JSONObject(data);
			//	JSONObject dataObj=mainObj.getJSONObject("data");
				JSONArray dataArray = mainObj.getJSONArray("Data");
				
				for(int i=0;i<dataArray.length();i++)
				{
					GetLDICarriersBean getLDICarriersBean = new GetLDICarriersBean();
					
					JSONObject dataObj = dataArray.getJSONObject(i);
				
					String Name=dataObj.getString("Name");
					getLDICarriersBean.setName(Name);
					//Log.e("Name----", Name);
					
					int Value=dataObj.getInt("Value");
					getLDICarriersBean.setValue(Value);
					//Log.e("Value",Value);
					
					LDICarriers.add(getLDICarriersBean);
				}
			}catch (Exception e) {
					// TODO: handle exception
			}
				
		return LDICarriers;
	}

	public ArrayList<GetTransactionsHistory> getTransactionsHistory(String data)
	{
		ArrayList<GetTransactionsHistory> getTransactionsList=new ArrayList<GetTransactionsHistory>();
		
		try {
			
			JSONObject mainObj=new JSONObject(data);
			
			JSONArray dataArray=mainObj.getJSONArray("Data");
			
			for(int i=0;i<dataArray.length();i++)
			{
				GetTransactionsHistory bean=new GetTransactionsHistory();
				
				JSONObject dataObj=dataArray.getJSONObject(i);
				
				String areaCode=dataObj.getString("AreaCode");
				bean.setAreaCode(areaCode);
				Log.e("areaCode", areaCode);
				
				String ccUsed=dataObj.getString("CCUsed");
				bean.setCcUsed(ccUsed);
				Log.e("CCUSed", ccUsed);
				
				int carrierID=dataObj.getInt("CarrierID");
				bean.setCarrierID(carrierID);
				Log.e("carrierID", carrierID+"");
				
				String currency=dataObj.getString("Currency");
				bean.setCurrency(currency);
				Log.e("currency", currency);
				
				String ForeignMobilePhone=dataObj.getString("ForeignMobilePhone");
				bean.setForeignMobilePhone(ForeignMobilePhone);
				Log.e("ForeignMobilePhone", ForeignMobilePhone);
				
				long ForeignMobilePhoneID=dataObj.getLong("ForeignMobilePhoneID");
				bean.setForeignMobilePhoneID(ForeignMobilePhoneID);
				Log.e("ForeignMobilePhoneID", ForeignMobilePhoneID+"");
				
				double ForeignValueRecevied=dataObj.getDouble("ForeignValueRecevied");
				bean.setForeignValueRecieved(ForeignValueRecevied);
				Log.e("ForeignValueRecevied", ForeignValueRecevied+"");
				
				long ID=dataObj.getLong("ID");
				bean.setID(ID);
				Log.e("ID", ID+"");
				
				String LoadChoice=dataObj.getString("LoadChoice");
				bean.setLoadChoice(LoadChoice);
				Log.e("LoadChoice", LoadChoice);
				
				String SendChannel=dataObj.getString("SendChannel");
				bean.setSendChannel(SendChannel);
				Log.e("SendChannel", SendChannel);
				
				String SendDate=dataObj.getString("SendDate");
				bean.setSendDate(SendDate);
				Log.e("SendDate", SendDate);
				
				int status=dataObj.getInt("Status");
				bean.setStatus(status);
				Log.e("status", status+"");
				
				JSONObject totalChargeObj=dataObj.getJSONObject("TotalCharge");
				
				double value=totalChargeObj.getDouble("Value");
				bean.setValue(value);
				Log.e("value", value+"");
				
				getTransactionsList.add(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getTransactionsList;
		
	}
	public ArrayList<ScheduleBean> GetScheduleLoads(String data) {
		ArrayList<ScheduleBean> getScheduleList = new ArrayList<ScheduleBean>();
		try {
			
		/*{
			"Status":
			{
			"Success":true,
			"ErrorDescription":null
			},
			"Data":null
		}*/

			
			JSONObject mainObject = new JSONObject(data);
			JSONArray dataArray = mainObject.getJSONArray("Data");
			/*if(dataArray.length()==0)
			{
				
			}*/
			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject dataobj = dataArray.getJSONObject(i);
				ScheduleBean bean = new ScheduleBean();
				
				String fname=dataobj.getString("RecipientFirstName");
				bean.setContactName(fname);
				Log.e("NAME------", fname);
				
				String str_number=dataobj.getString("ForeignPhoneNumber");
				bean.setNumber(str_number);
				Log.e("NUMBER------", str_number);
				
				String currency = dataobj.getString("Currency");
				bean.setCurrency(currency);
				Log.e("Currency-----", currency);
				
				String deliverydays = dataobj.getString("DeliveryDays");
				bean.setDeliveryDays(deliverydays);
				Log.e("Delivery Days--------",deliverydays);
				
				String deliverytime = dataobj.getString("DeliveryTime");
				bean.setDeliveryTime(deliverytime);
				Log.e("Delivery Time--------",deliverytime);
				
				String type = dataobj.getString("Type");
				bean.setType(type);
				Log.e("Type--------",type);
				
				String lastsent = dataobj.getString("LastSent");
				bean.setLastSend(lastsent);
				Log.e("Last Sent--------",lastsent);
				
				String nextsent = dataobj.getString("NextSend");
				bean.setNextSend(nextsent);
				Log.e("Next Sent--------",nextsent);
				
				JSONObject foriegnamount=dataobj.getJSONObject("LoadChoiceForeignAmount");
				
				int value=foriegnamount.getInt("Value");
				bean.setRechargeAmount(value);
				Log.e("Recharge Amount---", value+"");
							
				getScheduleList.add(bean);

			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return getScheduleList;

	}

}

