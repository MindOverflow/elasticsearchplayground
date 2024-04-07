package ru.sberbank.elasticsearchplayground.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Configuration
@EnableElasticsearchRepositories(basePackages = "ru.sberbank.elasticsearchplayground.repositories")
@ComponentScan(basePackages = {"ru.sberbank.elasticsearchplayground"})
public class ElasticsearchClientConfig extends ElasticsearchConfiguration {
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration
                .builder()
                .connectedTo("185.198.152.125:9200")
                .build();
    }
}