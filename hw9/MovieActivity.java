package chenchen.yang;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MovieActivity extends Activity {

	static JSONArray movies = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movies);

		String zipcode = SearchActivity.getZipcode();
		String urlString = "http://cs-server.usc.edu:18569/my_hw8/getJSON?zipcode=";
		try {
			urlString += zipcode;
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setAllowUserInteraction(false);
			InputStream urlStream = url.openStream();
			// get
			StringBuffer sb = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = urlStream.read(b)) != -1;) {
				sb.append(new String(b, 0, n));
			}

			JSONArray jsonArray = new JSONArray("[" + sb.toString() + "]");
			JSONArray subArray = new JSONArray("["
					+ ((JSONObject) jsonArray.get(0)).get("movies") + "]");
			movies = (JSONArray) ((JSONObject) subArray.get(0)).get("movie");
			TableLayout table = (TableLayout) findViewById(R.id.tableLayout1);
			for (int i = 0; i < movies.length(); i++) {
				JSONObject movieObj = (JSONObject) movies.get(i);
				TableRow row = new TableRow(this);
				TextView text = new TextView(this);
				// Title
				text.setText(movieObj.getString("title"));
				row.addView(text);

				ImageView imageView = new ImageView(this);
				URL imgUrl = new URL(movieObj.getString("cover"));
				HttpURLConnection connection = (HttpURLConnection) imgUrl
						.openConnection();
				InputStream is = connection.getInputStream();
				Bitmap img = BitmapFactory.decodeStream(is);
				imageView.setImageBitmap(img);
				imageView.setAdjustViewBounds(true);
				imageView.setMaxHeight(120);
				imageView.setMaxWidth(80);
				row.addView(imageView);
				RadioButton radioButton = new RadioButton(this);
				radioButton.setId(i);
				radioButton.setGravity(Gravity.RIGHT);
				radioButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						RadioButton radioButton = (RadioButton) v;
						for (int i = 0; i < movies.length(); i++) {
							RadioButton rb = (RadioButton) findViewById(i);
							rb.setChecked(false);
						}
						radioButton.setChecked(true);
						Button tweetButton = (Button) findViewById(R.id.tweetButton);
						tweetButton.setVisibility(View.VISIBLE);
						tweetButton.setId(radioButton.getId() + 100);
						tweetButton.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Button tweetButton = (Button) v;
								int id = tweetButton.getId()-100;
								sendTweet(id);
								Toast.makeText(getApplicationContext(),"Tweet Successful!",
								Toast.LENGTH_SHORT).show();
							}
						});
					}
				});
				row.addView(radioButton);
				table.addView(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sendTweet(int id){
		try {
		Twitter twitter = AuthActivity.getTwitter();
		JSONObject movieObj = (JSONObject) movies.get(id);
		String tweet = "I will see \"";
		tweet += movieObj.getString("title")+"\"";
		tweet += " at ";
		tweet += movieObj.getString("theater");
		tweet += " at ";
		String showtime = movieObj.getString("showtime");
		tweet += getShowTime(showtime);
		tweet += ". Link: ";
		tweet += movieObj.getString("url");
			twitter.updateStatus(tweet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getShowTime(String showtime){
		if(showtime.indexOf(",")== -1){
			return showtime;
		}else{
			return showtime.substring(0, showtime.indexOf(","));
		}
	}
}