package ru.sberbank.elasticsearchplayground.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.elasticsearchplayground.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> findByName(String name);

    List<Product> findByNameContaining(String name);

    List<Product> findByManufacturerAndCategory(String manufacturer,String category);
}
