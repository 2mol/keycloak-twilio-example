#!/usr/bin/env bash

set -euxo pipefail

function build {
	mvn clean package
}

function run-keycloak {
	docker run --name keycloak -p 8080:8080 --env-file .env quay.io/keycloak/keycloak:12.0.1
}

function deploy-module {
	docker cp target/example-keycloak-2fa-sms-authenticator-*.jar keycloak:/opt/jboss/keycloak/standalone/deployments/
}

"$@"
