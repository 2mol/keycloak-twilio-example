package valora.keycloak.authenticator.gateway;

/**
 * @author Valora - originally derived from Niko Köbler, https://www.n-k.de
 */
public interface SmsService {

	void send(String phoneNumber, String message);

}
