package org.bluebits.mocki;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.bluebits.mocki.client.activities.MockiMenuActivity;
import org.bluebits.mocki.client.utils.ActivityLoaderUtil;
import org.bluebits.mocki.client.utils.LoginUtils;

public class Mocki extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new ButtonClickListener(this));
	}
	
	private class ButtonClickListener implements OnClickListener {

		private Context ctx;
		
		public ButtonClickListener(Context ctx) {
			this.ctx = ctx;
		}

		@Override
		public void onClick(View view) {
			
			final EditText txtUserName = (EditText) findViewById(R.id.txtUserName);
			final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
			
			if (LoginUtils.authenticate(txtUserName.getText().toString(),
					txtPassword.getText().toString())) {
				ActivityLoaderUtil.load(ctx, MockiMenuActivity.class);
			}
		}
	}
}
