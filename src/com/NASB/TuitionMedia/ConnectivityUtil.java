package com.NASB.TuitionMedia;

/*import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityUtil {
	
	
	ConnectivityManager nai = (ConnectivityManager) getSystemService(Context
			.CONNECTIVITY_SERVICE);
	NetworkInfo net = nai.getActiveNetworkInfo();
	if(net==null)
	{
		AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(LoginActivity.this);
		alertDialogBuilder.setMessage("No Internet connection." +
				"Please check to continue")
				.setCancelable(false)
				.setTitle("No Connection")
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
			
						
						public void onClick(DialogInterface dialog, int which) {
				
							startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
								return;
							}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});
			
		alertDialogBuilder.show();
	}
}*/
