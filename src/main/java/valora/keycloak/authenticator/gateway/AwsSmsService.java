package valora.keycloak.authenticator.gateway;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Valora - originally derived from Niko Köbler, https://www.n-k.de
 */
public class AwsSmsService implements SmsService {

	private static final SnsClient sns = SnsClient.create();

	private final String senderId;

	AwsSmsService(Map<String, String> config) {
		senderId = config.get("senderId");
	}

	@Override
	public void send(String phoneNumber, String message) {
		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put("AWS.SNS.SMS.SenderID",
			MessageAttributeValue.builder().stringValue(senderId).dataType("String").build());
		messageAttributes.put("AWS.SNS.SMS.SMSType",
			MessageAttributeValue.builder().stringValue("Transactional").dataType("String").build());

		sns.publish(builder -> builder
			.message(message)
			.phoneNumber(phoneNumber)
			.messageAttributes(messageAttributes));
	}

}
