import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;

public class Testing {
	  private final FacebookClient facebookClient;
	  /**
	   * Entry point. You must provide a single argument on the command line: a valid Graph API access token. In order for
	   * publishing to succeed, you must use an access token for an application that has been granted stream_publish and
	   * create_event rights.
	   * 
	   * @param args
	   *          Command-line arguments.
	   * @throws IllegalArgumentException
	   *           If no command-line arguments are provided.
	   */
	  public static void main(String[] args) {
//	    if (args.length == 0)
//	      throw new IllegalArgumentException("You must provide an OAuth access token parameter. "
//	          + "See README for more information.");

	    new Testing("CAACEdEose0cBAGMWqrBW8cZCD4PTculHUf0zcVVZC2v3vwxZAZB49aRHzWlIcqPLkKbMERMZAw8YY1sSUNlLsyxE1FPOY4cHmpjxRBO25pcDPRXKOmlP0KX4xIQ0DIYSI0twyZCBZCgtP3FYLZAw2OO8XEcmZASUaQRqvQZAZARwIZCodEZB5S8yBO12wvoirZBS51q5iiIGpaspYSdQZDZD").runEverything();
	  }

	  Testing(String accessToken) {
		  accessToken="CAACEdEose0cBAGMWqrBW8cZCD4PTculHUf0zcVVZC2v3vwxZAZB49aRHzWlIcqPLkKbMERMZAw8YY1sSUNlLsyxE1FPOY4cHmpjxRBO25pcDPRXKOmlP0KX4xIQ0DIYSI0twyZCBZCgtP3FYLZAw2OO8XEcmZASUaQRqvQZAZARwIZCodEZB5S8yBO12wvoirZBS51q5iiIGpaspYSdQZDZD";
	    facebookClient = new DefaultFacebookClient(accessToken);
	  }

	  void runEverything() {
	    String messageId = publishMessage();
	  }

	  String publishMessage() {
	    System.out.println("* Feed publishing *");

	    FacebookType publishMessageResponse =
	        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", "RestFB test"));

	    System.out.println("Published message ID: " + publishMessageResponse.getId());
	    return publishMessageResponse.getId();
	  }
}
