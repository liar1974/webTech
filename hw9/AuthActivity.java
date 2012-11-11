package chenchen.yang;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class AuthActivity extends Activity {
	
	private String CONSUMER_KEY = "08iP9t9UBYsImtLFdXqdhA";
	private String CONSUMER_SECRET = "fMrArrSGgoASLSDmZc2K6ul4vfEQ0XtzOrUK58";
	private String CALLBACK_URL = "Flixster://AuthActivity";

	private CommonsHttpOAuthConsumer commonHttpOAuthConsumer;
	private OAuthProvider authProvider;
	private static Twitter twitter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		commonHttpOAuthConsumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
				CONSUMER_SECRET);
		authProvider = new DefaultOAuthProvider(
				"https://api.twitter.com/oauth/request_token",
				"https://api.twitter.com/oauth/access_token",
				"https://api.twitter.com/oauth/authorize");
		try {
			String oAuthURL = authProvider.retrieveRequestToken(
					commonHttpOAuthConsumer, CALLBACK_URL);
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(oAuthURL)));
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Uri uri = intent.getData();
		
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
			String verifier = uri
					.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
			try {
				twitter = new TwitterFactory().getInstance();
				authProvider.retrieveAccessToken(commonHttpOAuthConsumer,
						verifier);
				AccessToken accessToken = new AccessToken(
						commonHttpOAuthConsumer.getToken(),
						commonHttpOAuthConsumer.getTokenSecret());
				
				twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
				twitter.setOAuthAccessToken(accessToken);	
				startActivity(new Intent(AuthActivity.this,SearchActivity.class));
	        	
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), e.getMessage(),
						Toast.LENGTH_LONG);
			}
		}
	}

	/**
	 * after oAuth, other class get use this method to get Twitter Object
	 * @return
	 */
	public static Twitter getTwitter(){
		return twitter;
	}
}
