package com.onlinemobilerecharge;



import android.app.ProgressDialog;
import android.content.Context;
import com.onlinemobilerecharge.AsyncTaskCompleteListener;
import com.library.APIRequest;
import com.library.ServiceResponse;


public class AsyncTask extends android.os.AsyncTask<APIRequest, Void, ServiceResponse>{
	
	private ProgressDialog progressDialog;
	private Context context;
	private ServiceResponse serviceResponse;
	private AsyncTaskCompleteListener<ServiceResponse> asyncCallback = null;
	private String callBack = null;
	private String loadingMessage;
	
	public AsyncTask(Context context){
		this.context = context;
		progressDialog = new ProgressDialog(context);
	}

	public AsyncTask(Context context, String callBack, AsyncTaskCompleteListener<ServiceResponse> asyncCallback, String loadingMessage) {
		this.context = context;
		this.callBack = callBack;
		this.asyncCallback = asyncCallback;
		this.loadingMessage = loadingMessage;
		progressDialog = new ProgressDialog(context);
	}
	
	@Override
	protected void onPostExecute(ServiceResponse result) {
		if(progressDialog != null)
			progressDialog.dismiss();
		
		asyncCallback.onTaskComplete(result, callBack);
	}

	protected ServiceResponse doInBackground(APIRequest... apiReq) {
		
		if(apiReq != null)
		{
			serviceResponse = new ServiceResponse();
//			try
//			{
				serviceResponse = apiReq[0].getStreaming();
			/*}
			catch(SocketTimeoutException e)
			{
				serviceResponse = new ServiceResponse();
				serviceResponse.setData(null);
				serviceResponse.setResponseExceptionType(ResponseExceptionType.RESPONSE_TIMEOUT);
				serviceResponse.setExceptionMessage(e.getMessage());
			}
			catch(SocketException e)
			{
				serviceResponse = new ServiceResponse();
				serviceResponse.setData(null);
				serviceResponse.setResponseExceptionType(ResponseExceptionType.NO_CONNECTION);
				serviceResponse.setExceptionMessage(e.getMessage());
			}
			
			catch(IOException e)
			{
				serviceResponse = new ServiceResponse();
				serviceResponse.setData(null);
				serviceResponse.setResponseExceptionType(ResponseExceptionType.IO_EXCEPTION);
				serviceResponse.setExceptionMessage(e.getMessage());
			}
			catch(Exception e)
			{
				serviceResponse = new ServiceResponse();
				serviceResponse.setData(null);
				serviceResponse.setResponseExceptionType(ResponseExceptionType.UNKONWN_EXCEPTION);
				serviceResponse.setExceptionMessage(e.getMessage());
			}*/
		}
		return serviceResponse;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog.setMessage(loadingMessage);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		
	}
}

