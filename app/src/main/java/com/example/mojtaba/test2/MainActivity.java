package com.example.mojtaba.test2;

import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	int interval = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		final EditText field1 = (EditText)findViewById(R.id.interval);

		field1.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				WriteToFile.Write("after change" + (field1.getText()));
				interval = Integer.parseInt(field1.getText().toString());

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
				//WriteToFile.Write("beforeChange"+ field1.getText());
			}

			@Override
			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				//WriteToFile.Write("onChange"+ field1.getText());
			}
		});




		Intent intent = new Intent(this, NewAppWidget.class);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
		// since it seems the onUpdate() is only fired on that:
		int[] ids = AppWidgetManager.getInstance(getApplication())
				.getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		sendBroadcast(intent);

		//StopService();

		if (false/*isMyServiceRunning(MyService.class)*/) {

		} else {
			StartService();
		}
	}

	public void StartService() {

		Intent intent = new Intent(getBaseContext(), MyService.class);
		intent.putExtra("interval", interval);
		startService(intent);
	}

	// Method to stop the service
	public void StopService() {

		stopService(new Intent(getBaseContext(), MyService.class));
		//Toast.makeText(NewAppWidget.getAppContext(), "service stop", Toast.LENGTH_SHORT).show();

	}


	/*private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}*/
}
