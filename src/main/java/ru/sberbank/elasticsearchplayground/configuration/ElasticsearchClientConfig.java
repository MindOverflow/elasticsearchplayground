package ru.sberbank.elasticsearchplayground.configuration;

import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;


@Configuration
@EnableElasticsearchRepositories(basePackages = "ru.sberbank.elasticsearchplayground.repositories")
@ComponentScan(basePackages = {"ru.sberbank.elasticsearchplayground"})
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        Path caCertificatePath = Paths.get("/etc/elasticsearch/certs/http_ca.crt");
        CertificateFactory certificateFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate trustedCa;
            try (InputStream inputStream = Files.newInputStream(caCertificatePath)) {
                trustedCa = certificateFactory.generateCertificate(inputStream);
            }
            KeyStore trustStore = KeyStore.getInstance("pkcs12");
            trustStore.load(null, null);
            trustStore.setCertificateEntry("ca", trustedCa);
            SSLContextBuilder sslContextBuilder = SSLContexts.custom().loadTrustMaterial(trustStore, null);
            final SSLContext sslContext = sslContextBuilder.build();
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }


        return ClientConfiguration
                .builder()
                .connectedTo("185.198.152.125:9200")
                // TODO: remove it later: javax.net.ssl.SSLContext
                .usingSsl()
                .build();
    }
}