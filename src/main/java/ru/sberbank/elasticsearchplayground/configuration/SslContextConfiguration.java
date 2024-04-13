package ru.sberbank.elasticsearchplayground.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Slf4j
public class SslContextConfiguration {
    public void configuration() throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        Path caCertificatePath = Paths.get("/path/to/ca.crt");
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate trustedCa;
        try (InputStream inputStream = Files.newInputStream(caCertificatePath)) {
            trustedCa = certificateFactory.generateCertificate(inputStream);
        }
        KeyStore trustStore = KeyStore.getInstance("pkcs12");
        trustStore.load(null, null);
        trustStore.setCertificateEntry("ca", trustedCa);
        SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStore, null);
        final SSLContext sslContext = sslContextBuilder.build();

    }
}
