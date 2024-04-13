 package ru.sberbank.elasticsearchplayground.services;

 import lombok.extern.slf4j.Slf4j;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
 import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
 import org.springframework.data.elasticsearch.core.SearchHits;
 import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
 import org.springframework.data.elasticsearch.core.query.Criteria;
 import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
 import org.springframework.data.elasticsearch.core.query.IndexQuery;
 import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
 import org.springframework.data.elasticsearch.core.query.Query;
 import org.springframework.data.elasticsearch.core.query.StringQuery;
 import org.springframework.stereotype.Service;
 import ru.sberbank.elasticsearchplayground.models.Product;
 import ru.sberbank.elasticsearchplayground.repositories.ProductRepository;

 import java.util.ArrayList;
 import java.util.List;
 import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductSearchService {

    private static final String PRODUCT_INDEX = "productindex";

    private final ElasticsearchOperations elasticsearchOperations;
    private final ProductRepository productRepository;

    @Autowired
    public ProductSearchService(final ElasticsearchOperations elasticsearchOperations, final ProductRepository productRepository) {
        super();
        this.elasticsearchOperations = elasticsearchOperations;
        this.productRepository = productRepository;
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
        Criteria criteria = new Criteria("name").contains(query);

        Query searchQuery = new CriteriaQuery(criteria);
        Pageable pageable = PageRequest.of(0, 15);
        searchQuery.setPageable(pageable);
        SearchHits<Product> searchSuggestions = elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        List<String> suggestions = new ArrayList<>();

        searchSuggestions.getSearchHits().forEach(searchHit -> suggestions.add(searchHit.getContent().getName()));
        return suggestions;
    }

    public List<Product> processSearch(final String query) {
        log.info("Search with query {}", query);

        Criteria criteria = new Criteria("name").and("description").contains(query);
        Query searchQuery = new CriteriaQuery(criteria);

        SearchHits<Product> productHits =
                elasticsearchOperations
                        .search(searchQuery, Product.class,
                                IndexCoordinates.of(PRODUCT_INDEX));

        List<Product> productMatches = new ArrayList<Product>();
        productHits.forEach(srchHit->{
            productMatches.add(srchHit.getContent());
        });
        return productMatches;
    }
}
