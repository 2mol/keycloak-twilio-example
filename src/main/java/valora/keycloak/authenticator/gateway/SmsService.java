package example.keycloak.authenticator.gateway;

/**
 * @author example - originally derived from Niko Köbler, https://www.n-k.de
 */
public interface SmsService {

	void send(String phoneNumber, String message);

}
