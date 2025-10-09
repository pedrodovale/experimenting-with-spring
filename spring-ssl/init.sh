# !/bin/sh
server_path=spring-ssl-server/src/main/resources
client_path=spring-ssl-client/src/main/resources

echo 'Generate server root certificate'
keytool -genkeypair -alias serverRoot -dname "CN=Server Root" -keyalg RSA -keysize 2048 -validity 365 -keystore "${server_path}/server-keystore.jks" -storepass changeit -keypass changeit -ext bc=ca:true
 
echo 'Generate server leaf certificate signed by the server root certificate'
keytool -genkeypair -alias serverLeaf -dname "CN=localhost" -keyalg RSA -keysize 2048 -validity 365 -keystore "${server_path}/server-keystore.jks" -storepass changeit -keypass changeit
keytool -certreq -alias serverLeaf -keystore "${server_path}/server-keystore.jks" -storepass changeit -file serverLeaf.csr
keytool -gencert -alias serverRoot -keystore "${server_path}/server-keystore.jks" -storepass changeit -infile serverLeaf.csr -outfile serverLeafCert.crt -validity 365
keytool -importcert -alias serverLeaf -keystore "${server_path}/server-keystore.jks" -storepass changeit -file serverLeafCert.crt -noprompt
 
echo 'Generate client root certificate'
keytool -genkeypair -alias clientRoot -dname "CN=Client Root" -keyalg RSA -keysize 2048 -validity 365 -keystore "${client_path}/client-keystore.jks" -storepass changeit -keypass changeit -ext bc=ca:true
 
echo 'Generate client leaf certificate signed by the client root certificate'
keytool -genkeypair -alias clientLeaf -dname "CN=Client Leaf" -keyalg RSA -keysize 2048 -validity 365 -keystore "${client_path}/client-keystore.jks" -storepass changeit -keypass changeit
keytool -certreq -alias clientLeaf -keystore "${client_path}/client-keystore.jks" -storepass changeit -file clientLeaf.csr
keytool -gencert -alias clientRoot -keystore "${client_path}/client-keystore.jks" -storepass changeit -infile clientLeaf.csr -outfile clientLeafCert.crt -validity 365
keytool -importcert -alias clientLeaf -keystore "${client_path}/client-keystore.jks" -storepass changeit -file clientLeafCert.crt -noprompt

echo 'Export client root certificate'
keytool -exportcert -alias clientRoot -keystore "${client_path}/client-keystore.jks" -storepass changeit -file clientRootCert.crt

echo 'Import client root certificate into server truststore'
keytool -importcert -alias clientRoot -keystore "${server_path}/server-truststore.jks" -storepass changeit -file clientRootCert.crt -noprompt

echo 'Export server root certificate'
keytool -exportcert -alias serverRoot -keystore "${server_path}/server-keystore.jks" -storepass changeit -file serverRootCert.crt

echo 'Import server root certificate into client truststore'
keytool -importcert -alias serverRoot -keystore "${client_path}/client-truststore.jks" -storepass changeit -file serverRootCert.crt -noprompt

rm \
  serverLeaf.csr \
  serverRootCert.crt \
  serverLeafCert.crt \
  clientLeaf.csr \
  clientLeafCert.crt \
  clientRootCert.crt