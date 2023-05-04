package se.magnus.microservices.core.productcomposite;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.magnus.api.composite.product.services.ProductCompositeIntegration;
import se.magnus.api.composite.recommendation.Recommendation;
import se.magnus.api.composite.review.Review;
import se.magnus.api.core.product.Product;
import se.magnus.util.exceptions.InvalidInputException;
import se.magnus.util.exceptions.NotFoundException;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductCompositeServiceApplicationTests {

	private static final int PRODUCT_ID_OK = 1;
	private static final int PRODUCT_ID_NOT_FOUND = 13;
	private static final int PRODUCT_ID_INVALID = 0;

	@Test
	void contextLoads() {
	}

	@Autowired
	private WebTestClient client;

//	@MockBean
//	private ProductCompositeIntegration compositeIntegration;
//
//	@Before
//	public void setUp() {
//
//		when(compositeIntegration.getProduct(PRODUCT_ID_OK)).
//				thenReturn(new Product(PRODUCT_ID_OK, "name", 1, "mock-address"));
//		when(compositeIntegration.getRecommendations(PRODUCT_ID_OK)).
//				thenReturn(singletonList(new Recommendation(PRODUCT_ID_OK, 1, "author", 1, "content", "mock address")));
//		when(compositeIntegration.getReviews(PRODUCT_ID_OK)).
//				thenReturn(singletonList(new Review(PRODUCT_ID_OK, 1, "author", "subject", "content", "mock address")));
//
//		when(compositeIntegration.getProduct(PRODUCT_ID_NOT_FOUND)).thenThrow(new NotFoundException("NOT FOUND: " + PRODUCT_ID_NOT_FOUND));
//
//		when(compositeIntegration.getProduct(PRODUCT_ID_INVALID)).thenThrow(new InvalidInputException("INVALID: " + PRODUCT_ID_INVALID));
//	}


	@Test
	public void getProductById() {
		client.get()
				.uri("/product-composite/" + PRODUCT_ID_OK)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.productId").isEqualTo(PRODUCT_ID_OK)
				.jsonPath("$.recommendations.length()").isEqualTo(3)
				.jsonPath("$.reviews.length()").isEqualTo(3)
		;
	}

	@Test
	public void getProductNotFound() {
		client.get()
				.uri("/product-composite/" + PRODUCT_ID_NOT_FOUND)
				.accept(APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.path").isEqualTo("/product-composite/" + PRODUCT_ID_NOT_FOUND)
				.jsonPath("$.error").isEqualTo("Not Found")
		;

	}

}
