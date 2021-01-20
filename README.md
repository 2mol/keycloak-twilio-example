# Keycloak + Twilio SDK

This repo is for reproducing a bug where the `log4j` dependency from Twilio seems to clash with the one from `keycloak-services` (transitive dependency, check with `mvn dependency:tree`).

Relevant repos:

- https://github.com/twilio/twilio-java
- https://github.com/keycloak/keycloak

This code example is based on `https://www.n-k.de/2020/12/keycloak-2fa-sms-authentication.html`, and its corresponding repo: https://github.com/dasniko/keycloak-2fa-sms-authenticator.

## How to reproduce

Dependencies are maven, openjdk 11, and docker.

- build the module with `./make.sh build`.
- Create an `.env` file, use `example.env` as a template. This is needed for the Twilio token.
- Start a keycloak container with `docker run --name keycloak -p 8080:8080 --env-file .env quay.io/keycloak/keycloak:12.0.1`
- Copy the compiled module .jar over with `./make.sh deploy-module`. The keycloak logs will show the module being loaded.

Now the more manual part is to trigger usage of the module:

- Log into Keycloak with admin/admin.
- Create a new realm (hover over the realm name).
- **> Realm Settings > Login:** Enable user registration
- register a new user account. You can find the link under **> Clients**
- **> Authentication:** Make a copy of the Browser Flow
    - Add execution under "Copy Of Browser Forms" or whatever you named it
    - Select the SMS Authentication
    - Set it to REQUIRED
    - For the SMS Authentication step, use the dropdown to go to **config**
        - give it a name, **save**, then switch off simulation mode, then **save again**
    - Go back. Under the **Bindings** tab, select your new flow as the new Browser Flow
    - Save
- **> Users** select your user, go to the **Attributes** tab, and add a new key:
    - `mobile_number`, and whatever value you want. Doesn't have to be a real phone number.
    - Save
- Now try to sign in (same URL that you used to register a user).

You should get an internal server error in the browser, and see the dreaded `No class provided` error in the Keycloak console output:

```
Caused by: java.lang.UnsupportedOperationException: No class provided, and an appropriate one cannot be found.
	at deployment.example-keycloak-2fa-sms-authenticator-1.0-SNAPSHOT.jar//org.apache.logging.log4j.LogManager.callerClass(LogManager.java:571)
...
```

## How to make it work

Downgrade the version number of the Twilio SDK in `pom.xml`: Change to 8.3.0 under `<twilio.version>8.6.0</twilio.version>`.

This version doesn't use `log4j`, and the integration works.

Note: for this it is better to have real phone numbers, both under your user account, as well as the `PHONE_NUMBER_FROM` variable in `.env`
