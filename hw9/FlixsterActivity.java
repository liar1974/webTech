package chenchen.yang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class FlixsterActivity extends Activity {



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				Intent intent = new Intent(FlixsterActivity.this,AuthActivity.class);
	        	startActivity(intent);
			}
		});
	}

	

}