package ru.sberbank.elasticsearchplayground.services;


//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import ru.sberbank.elasticsearchplayground.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSearchService {

    private static final String PRODUCT_INDEX = "productindex";

    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ProductSearchService(final ElasticsearchOperations elasticsearchOperations) {
        super();
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public String createProductIndex(Product product) {

        IndexQuery indexQuery = new IndexQueryBuilder().withId(product.getId()).withObject(product).build();

        return elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of(PRODUCT_INDEX));
    }

    public List<String> createProductIndexBulk(final List<Product> products) {

        List<IndexQuery> queries = products.stream()
                .map(product -> new IndexQueryBuilder().withId(product.getId()).withObject(product).build())
                .collect(Collectors.toList());
        ;

        return elasticsearchOperations
                .bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX))
                .stream()
                .map(IndexedObjectInformation::id)
                .collect(Collectors.toList());
    }

    public void findByProductName(final String productName) {
        Query query = new StringQuery("{\"match\":{\"name\":{\"query\":\"" + productName + "\"}}}\"");
        SearchHits<Product> productSearchHits = elasticsearchOperations.search(query, Product.class, IndexCoordinates.of(PRODUCT_INDEX));
    }

    public List<String> fetchSuggestions(String query) {

        //SearchRequest
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withFilter(queryBuilder)
//                .withPageable(PageRequest.of(0, 5))
//                .build();

//        SearchHits<Product> searchSuggestions =
//                elasticsearchOperations.search(searchQuery,
//                        Product.class,
//                        IndexCoordinates.of(PRODUCT_INDEX));

        List<String> suggestions = new ArrayList<String>();

//        searchSuggestions.getSearchHits().forEach(searchHit -> {
//            suggestions.add(searchHit.getContent().getName());
//        });
        return suggestions;
    }


}
