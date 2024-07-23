package de.ait_tr.gxx_shop.controller;

import de.ait_tr.gxx_shop.domain.dto.ProductDto;
import de.ait_tr.gxx_shop.domain.entity.Role;
import de.ait_tr.gxx_shop.domain.entity.User;
import de.ait_tr.gxx_shop.repository.ProductRepository;
import de.ait_tr.gxx_shop.repository.RoleRepository;
import de.ait_tr.gxx_shop.repository.UserRepository;
import de.ait_tr.gxx_shop.security.dto.LoginRequestDTO;
import de.ait_tr.gxx_shop.security.dto.TokenResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    private static final String TEST_PRODUCT_TITLE = "Test Product";
    private static final int TEST_PRODUCT_PRICE = 777;
    private static final String TEST_ADMIN_NAME = "Test Admin";
    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_PASSWORD = "Test password";

    private static final String ROLE_ADMIN_TITLE = "ROLE_ADMIN";
    private static final String ROLE_USER_TITLE = "ROLE_USER";
    private static final String BEARER_PREFIX = "Bearer ";


    private static final String URL_PREFIX = "http://localhost:";
    private static final String AUTH_RESOURCE_NAME = "/api/auth";
    private static final String PRODUCTS_RESOURCE_NAME = "/api/products";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String AUTH_HEADER_NAME = "Authorization";



    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    private TestRestTemplate template;
    private HttpHeaders headers;
    private ProductDto testProduct;

    private String adminAccessToken;
    private String userAccessToken;
    private static Long testProductId;



    @Autowired
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        template = new TestRestTemplate();
        headers = new HttpHeaders();

        testProduct = new ProductDto();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(new BigDecimal(TEST_PRODUCT_PRICE));

        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        if (admin == null) {
            roleAdmin = roleRepository.findByTitle(ROLE_ADMIN_TITLE).orElseThrow(() -> new RuntimeException("Role ADMIN not found in the database"));
            roleUser = roleRepository.findByTitle(ROLE_USER_TITLE).orElseThrow(() -> new RuntimeException("Role USER not found in the database"));

            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userRepository.save(admin);
        }
        if (user == null) {
            roleUser = (roleUser == null) ? roleRepository.findByTitle(ROLE_USER_TITLE).orElseThrow(() -> new RuntimeException("Role USER not found in the database")) : roleUser;

            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser));

            userRepository.save(user);
        }

        LoginRequestDTO loginAdminDto = new LoginRequestDTO(TEST_ADMIN_NAME, TEST_PASSWORD);
        LoginRequestDTO loginUserDto = new LoginRequestDTO(TEST_USER_NAME, TEST_PASSWORD);

        // POST -> http://localhost:56500/auth/login
        String authUrl = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;


        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginAdminDto);

        ResponseEntity<TokenResponseDto> response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDto.class
        );

        assertTrue(response.hasBody(), "Authorization response body is empty");

        TokenResponseDto tokenResponse = response.getBody();
        adminAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();


        request = new HttpEntity<>(loginUserDto);

        response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDto.class
        );

        assertTrue(response.hasBody(), "Authorization response body is empty");

        tokenResponse = response.getBody();
        userAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();

    }

    @Test
    public void positiveGettingAllProductsWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List<ProductDto>> response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ProductDto>>() {}
        );

        // Проверка статуса ответа
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");

        // Проверка наличия тела
        assertTrue(response.hasBody(), "Response doesn't have a body");
    }

    @Test
    public void negativeSavingProductWithoutAuthorization(){

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;

        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template.exchange(
                url,
                HttpMethod.POST,
                request,
                ProductDto.class
        );

        // Проверка ответа: статус Forbidden 403
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");

        // Проверка ответа: отсутствие тела
        assertFalse(response.hasBody(), "Response has unexpected body");
    }

    // --------------------------- Homework

    @Test
    public void negativeSavingProductWithUserAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
    }

    @Test
    @Order(1)
    public void positiveSavingProductWithAdminAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");

        ProductDto savedProduct = response.getBody();
        assertNotNull(savedProduct, "Response body doesn't have a saved product");
        assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");

        testProductId = savedProduct.getId();
    }



    @Test
    @Order(2)
    public void negativeGettingProductByIdWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + "/" + testProductId;
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.GET, request, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }


    @Test
    @Order(3)
    public void negativeGettingProductByIdWithBasicAuthorization() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + "/" + testProductId;
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .withBasicAuth(TEST_USER_NAME, TEST_PASSWORD)
                .exchange(url, HttpMethod.GET, request, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }


    @Test
    @Order(4)
    public void negativeGettingProductByIdWithIncorrectToken() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + "/" + 1;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken + "a"));
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.GET, request, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body");
    }



    @Test
    @Order(5)
    public void positiveGettingProductByIdWithCorrectToken() {

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + "/" + testProductId;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.GET, request, ProductDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");

        ProductDto savedProduct = response.getBody();
        assertNotNull(savedProduct, "Response body doesn't have a saved product");
        assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");

        productRepository.deleteById(savedProduct.getId());
    }


}