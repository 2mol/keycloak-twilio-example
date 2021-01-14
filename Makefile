.PHONY: build deploy run twiliotest

.DEFAULT_GOAL := build

## Compile module
build:
	mvn clean package

## Copy module into a running keycloak container
deploy:
	docker cp target/valora-keycloak-2fa-sms-authenticator-*.jar keycloak:/opt/jboss/keycloak/standalone/deployments/

# run keycloak in its container
run:
	docker run --name keycloak -p 8080:8080 --env-file .env quay.io/keycloak/keycloak:12.0.1

# test the Twilio API
twiliotest:
	curl -X POST https://api.twilio.com/2010-04-01/Accounts/$TWILIO_ACCOUNT_SID/Messages.json \
	--data-urlencode "Body=McAvoy or Stewart? These timelines can get so confusing." \
	--data-urlencode "From=Valora" \
	--data-urlencode "To=+41786810340" \
	-u $TWILIO_ACCOUNT_SID:$TWILIO_AUTH_TOKEN
