package valora.keycloak.authenticator.gateway;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

// import java.util.HashMap;
import java.util.Map;

/**
 * @author Valora - originally derived from Niko Köbler, https://www.n-k.de
 */
public class TwilioSmsService implements SmsService {

    public static final String accountSid = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String authToken = System.getenv("TWILIO_AUTH_TOKEN");
    // public static final String PhoneNumberFrom = System.getenv("PHONE_NUMBER_FROM");

	private final String senderId;

	TwilioSmsService(Map<String, String> config) {
		senderId = config.get("senderId");
	}

	@Override
	public void send(String phoneNumber, String message) {
        Twilio.init(accountSid, authToken);

        System.out.println(accountSid);

        Message twilioMessage = Message
            .creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(senderId),
                message)
            .create();

        System.out.println(twilioMessage.getSid());
        // System.out.println("ya");
	}
}
