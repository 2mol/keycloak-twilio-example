# { pkgs ? import <nixpkgs> {} }:
{ pkgs ? import (fetchTarball "https://github.com/NixOS/nixpkgs/archive/2a058487cb7a50e7650f1657ee0151a19c59ec3b.tar.gz") {}}:

with pkgs; mkShell {
  buildInputs = [
    openjdk11_headless
    maven
    # keycloak
    # dotnet-sdk_5
    # qrencode
    # google-cloud-sdk
  ];
}
