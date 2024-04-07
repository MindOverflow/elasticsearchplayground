package ru.sberbank.elasticsearchplayground;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import ru.sberbank.elasticsearchplayground.models.Product;
import ru.sberbank.elasticsearchplayground.repositories.ProductRepository;

import java.io.InputStream;
import java.util.*;

@Slf4j
@SpringBootApplication
public class ElasticsearchplaygroundApplication {

	private static final String COMMA_DELIMITER = ",";

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchplaygroundApplication.class, args);
	}

	@PreDestroy
	public void deleteIndex() {
		elasticsearchOperations.indexOps(Product.class).delete();
	}

	@PostConstruct
	public void buildIndex() {
		elasticsearchOperations.indexOps(Product.class).refresh();
		productRepository.deleteAll();
		productRepository.saveAll(prepareDataset());
	}

	private Collection<Product> prepareDataset() {
		Resource resource = new ClassPathResource("fashion-products.csv");
		List<Product> productList = new ArrayList<>();

		try (InputStream inputStream = resource.getInputStream();
			 Scanner scanner = new Scanner(resource.getInputStream())) {
			int lineNumber = 0;
			while (scanner.hasNextLine()) {
				++lineNumber;
				String line = scanner.nextLine();
				if (lineNumber == 1) {
					continue;
				}
				Optional<Product> product = csvRowToProductMapper(line);
				if(product.isPresent()) {
					productList.add(product.get());
				}
			}
		} catch (Exception exception) {
			log.error("File read error ", exception);
		}

		return productList;
	}

	private Optional<Product> csvRowToProductMapper(final String line) {
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);
			while (rowScanner.hasNext()) {
				String name = rowScanner.next();
				String description = rowScanner.next();
				String manufacturer = rowScanner.next();
				return Optional.of(
						Product.builder()
								.name(name)
								.description(description)
								.manufacturer(manufacturer)
								.build());
			}
		}
		return Optional.of(null);
	}
}
