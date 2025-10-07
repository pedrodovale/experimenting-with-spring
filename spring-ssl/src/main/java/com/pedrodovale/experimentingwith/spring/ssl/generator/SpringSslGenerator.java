//package com.pedrodovale.experimentingwith.spring.ssl.generator;
//
//import java.io.FileOutputStream;
//import java.math.BigInteger;
//import java.security.GeneralSecurityException;
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.security.cert.Certificate;
//import java.security.cert.Extension;
//import java.security.cert.X509Certificate;
//import java.util.Map;
//
//import com.sun.jarsigner.ContentSigner;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import javax.security.auth.x500.X500Principal;
//
//@SpringBootApplication
//public class SpringSslGenerator implements CommandLineRunner {
//
//  public static final String GENERATE_COMMAND = "generate";
//
//  public static void main(String[] args) {
//    SpringApplication.run(SpringSslGenerator.class, args);
//  }
//
//  @Override
//  public void run(String... args) throws Exception {
//    if (args.length == 0 || !GENERATE_COMMAND.equals(args[0])) {
//      return;
//    }
//
//    // Paths to the keystore and truststore files
//    String serverKeystorePath = "./spring-ssl-server/src/main/resources/server-keystore.jks";
//    String clientKeystorePath = "./spring-ssl-client/src/main/resources/client-keystore.jks";
//    String serverTruststorePath = "./spring-ssl-server/src/main/resources/server-truststore.jks";
//    String clientTruststorePath = "./spring-ssl-client/src/main/resources/client-truststore.jks";
//
//    // Generate server root certificate
//    KeyStore serverKeystore = createKeyStore(serverKeystorePath, "changeit");
//    X509Certificate serverRootCert =
//        generateCertificate("CN=Server Root", serverKeystore, "serverRoot", "changeit");
//
//    // Generate server leaf certificate
//    X509Certificate serverLeafCert =
//        generateCertificate("CN=Server Leaf", serverKeystore, "serverLeaf", "changeit");
//
//    // Generate client root certificate
//    KeyStore clientKeystore = createKeyStore(clientKeystorePath, "changeit");
//    X509Certificate clientRootCert =
//        generateCertificate("CN=Client Root", clientKeystore, "clientRoot", "changeit");
//
//    // Generate client leaf certificate
//    X509Certificate clientLeafCert =
//        generateCertificate("CN=Client Leaf", clientKeystore, "clientLeaf", "changeit");
//
//    // Import client root certificate into server truststore
//    importCertificateIntoTruststore(serverTruststorePath, clientRootCert, "clientRoot", "changeit");
//
//    // Import server root certificate into client truststore
//    importCertificateIntoTruststore(clientTruststorePath, serverRootCert, "serverRoot", "changeit");
//
//    System.out.println("Certificates and truststore entries generated successfully.");
//  }
//
//  private KeyStore createKeyStore(String keystorePath, String password) throws Exception {
//    KeyStore ks = KeyStore.getInstance("JKS");
//    ks.load(null, password.toCharArray());
//    try (FileOutputStream fos = new FileOutputStream(keystorePath)) {
//      ks.store(fos, password.toCharArray());
//    }
//    return ks;
//  }
//
//  public static Secret generateSelfSignedCertificateSecret(String name, Map<String, String> labels, String host) {
//    Security.addProvider(new BouncyCastleProvider());
//
//    X500Principal subject = new X500Principal("CN=" + host);
//    X500Principal signedByPrincipal = subject;
//    KeyPair keyPair = generateKeyPair();
//    KeyPair signedByKeyPair = keyPair;
//
//    long notBefore = System.currentTimeMillis();
//    long notAfter = notBefore + (1000L * 3600L * 24 * 365);
//
//    ASN1Encodable[] encodableAltNames = new ASN1Encodable[]{new GeneralName(GeneralName.dNSName, host)};
//    KeyPurposeId[] purposes = new KeyPurposeId[]{KeyPurposeId.id_kp_serverAuth, KeyPurposeId.id_kp_clientAuth};
//
//    X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(signedByPrincipal,
//            BigInteger.ONE, new Date(notBefore), new Date(notAfter), subject, keyPair.getPublic());
//
//    try {
//      certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
//      certBuilder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.digitalSignature + KeyUsage.keyEncipherment));
//      certBuilder.addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(purposes));
//      certBuilder.addExtension(Extension.subjectAlternativeName, false, new DERSequence(encodableAltNames));
//
//      final ContentSigner signer = new JcaContentSignerBuilder(("SHA256withRSA")).build(signedByKeyPair.getPrivate());
//      X509CertificateHolder certHolder = certBuilder.build(signer);
//
//      return new SecretBuilder()
//              .withNewMetadata()
//              .withName(name)
//              .addToLabels(labels)
//              .endMetadata()
//              .withType("kubernetes.io/tls")
//              .addToStringData("tls.key", getPrivateKeyPkcs1Pem(keyPair))
//              .addToStringData("tls.crt", getCertificatePem(certHolder))
//              .build();
//    } catch (Exception e) {
//      Logger.getLogger(IngressSpecUtil.class).error(e.getMessage());
//      throw new AssertionError(e.getMessage());
//    }
//  }
//
//  private static KeyPair generateKeyPair() {
//    try {
//      KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//      keyPairGenerator.initialize(2048, new SecureRandom());
//      return keyPairGenerator.generateKeyPair();
//    } catch (GeneralSecurityException var2) {
//      throw new AssertionError(var2);
//    }
//  }
//
//  private void importCertificateIntoTruststore(
//      String truststorePath, X509Certificate cert, String alias, String password) throws Exception {
//    KeyStore truststore = KeyStore.getInstance("JKS");
//    truststore.load(null, password.toCharArray());
//    try (FileOutputStream fos = new FileOutputStream(truststorePath)) {
//      truststore.setCertificateEntry(alias, cert);
//      truststore.store(fos, password.toCharArray());
//    }
//  }
//}
