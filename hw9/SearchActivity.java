package chenchen.yang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends Activity {

	private static String zipcode = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		Button button1 = (Button) findViewById(R.id.button1);

		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				EditText editText1 = (EditText) findViewById(R.id.editText1);
				zipcode = editText1.getText().toString().trim();
				if (checkZipcode(zipcode)) {
					Intent intent = new Intent(SearchActivity.this,
							MovieActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Zipcode must have 5 digits.Please, enter a correct zipcode",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	public static String getZipcode() {
		return zipcode;
	}

	/**
	 * check zipcode format
	 * 
	 * @param zipcode
	 * @return
	 */
	private boolean checkZipcode(String zipcode) {
		if (zipcode.length() != 5) {
			return false;
		}
		try {
			Integer.parseInt(zipcode);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}