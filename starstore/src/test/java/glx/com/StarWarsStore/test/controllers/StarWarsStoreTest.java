package glx.com.StarWarsStore.test.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import glx.com.StarWarsStore.Application;
import glx.com.StarWarsStore.entities.CreditCard;
import glx.com.StarWarsStore.entities.Product;
import glx.com.StarWarsStore.entities.Transaction;
import glx.com.StarWarsStore.repositories.ProductRepository;
import glx.com.StarWarsStore.repositories.TransactionRespository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class StarWarsStoreTest {

	private static final String CLIENT_ID_2 = "7e655c6e-e8e5-4349-8348-e51e0ff3098e";
	private static final String CLIENT_ID_1 = "7e655c6e-e8e5-4349-8348-e51e0ff3072e";
	private static final String PRODUCT_2_ZIPCODE = "13500-110";
	private static final int PRODUCT_2_PRICE = 10000;
	private static final String PRODUCT_2_TITLE = "Sabre de luz";
	private static final String PRODUCT_1_ZIPCODE = "78993-555";
	private static final int PRODUCT_1_PRICE = 8000;
	private static final String PRODUCT_1_TITLE = "Blusa do Kylo Ren";

	private MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private TransactionRespository transactionRespository;
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);
		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.productRepository.deleteAllInBatch();
		this.transactionRespository.deleteAllInBatch();
		
				this.productRepository.save(new Product(PRODUCT_1_TITLE, PRODUCT_1_PRICE, PRODUCT_1_ZIPCODE, "Eduardo ",
						"https://cdn.awsli.com.br/600x450/21/21351/produto/00000/tttggghhh888.jpg", "26/12/2018"));
		this.productRepository
				.save(new Product(PRODUCT_2_TITLE, PRODUCT_2_PRICE, PRODUCT_2_ZIPCODE, "Luiz da silva ",
						"https://cdn.awsli.com.br/600x450/21/21351/produto/00000/sssssssss.jpg", "30/12/2018"));
		
		
		this.transactionRespository.save(new Transaction(CLIENT_ID_1, "Luke Skywalker", 1236,
				new CreditCard("1234120987654321", 7990, 789, "Luke Skywalker", "12/24")));
		this.transactionRespository.save(new Transaction(CLIENT_ID_2, "Leia Organa", 999,
				new CreditCard("1234123412341234", 8860, 554, "Leia Organa", "12/30")));

	}

	@Test
	public void readProductTest() throws Exception {
		mockMvc.perform(get("/starstore/product/1")).andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeJson))
				.andExpect(jsonPath("$.title", is(this.PRODUCT_1_TITLE)))
				.andExpect(jsonPath("$.price", is(this.PRODUCT_1_PRICE)))
				.andExpect(jsonPath("$.zipcode", is(this.PRODUCT_1_ZIPCODE)));
	}

	@Test
	public void readProdcutsTest() throws Exception {
		mockMvc.perform(get("/starstore/products")).andExpect(status().isOk())
				.andExpect(content().contentType(contentTypeJson))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title", is(this.PRODUCT_1_TITLE)))
				.andExpect(jsonPath("$[0].price", is(this.PRODUCT_1_PRICE)))
				.andExpect(jsonPath("$[0].zipcode", is(this.PRODUCT_1_ZIPCODE)))
				.andExpect(content().contentType(contentTypeJson))
				.andExpect(jsonPath("$[1].title", is(this.PRODUCT_2_TITLE)))
				.andExpect(jsonPath("$[1].price", is(this.PRODUCT_2_PRICE)))
				.andExpect(jsonPath("$[1].zipcode", is(this.PRODUCT_2_ZIPCODE)));
	}

	@Test
	public void createProductTest() throws Exception {
		String productJson = json(new Product("Blusa da Princesa Leia", 8000, "5555666666", "Alessandro",
				"https://cdn.awsli.com.br/600x450/21/21351/produto/00000/tttggghhh888.jpg", "26/12/2018"));
		mockMvcPerform("/starstore/product", productJson);

	}

	@Test
	public void createTransactionTest() throws Exception {
		String transactionJson = json(new Transaction(CLIENT_ID_1, "Luke Skywalker", 1236,
				new CreditCard("1234123412341234", 7990, 789, "Luke Skywalker", "12/24")));
		mockMvcPerform("/starstore/buy", transactionJson);
	}

	@Test
	public void readStoryTest() throws Exception {
		mockMvc.perform(get("/starstore/history")).andExpect(status().isOk())
		.andExpect(content().contentType(contentTypeJson))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$[0].client_id", is(this.CLIENT_ID_1)))
		.andExpect(jsonPath("$[1].client_id", is(this.CLIENT_ID_2)));
	}

	
	public void readStoryClient() throws Exception{
		mockMvc.perform(get("/starstore/history/"+CLIENT_ID_1)).andExpect(status().isOk())
		.andExpect(content().contentType(contentTypeJson))
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].client_id", is(this.CLIENT_ID_1)));
	}
	
	
	
	private void mockMvcPerform(String url, String entityJson) throws Exception {
		this.mockMvc.perform(post(url).contentType(this.contentTypeJson).content(entityJson))
				.andExpect(status().isCreated());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
