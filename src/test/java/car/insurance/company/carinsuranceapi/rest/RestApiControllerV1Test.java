package car.insurance.company.carinsuranceapi.rest;

import car.insurance.company.carinsuranceapi.model.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestApiControllerV1Test {

    @Autowired
    private HttpMessageConverter<?> mappingJackson2HttpMessageConverter;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Value(value = "classpath:contracts_json/create_quote.json")
    private Resource createQuoteMetadata;

    @Value(value = "classpath:contracts_json/create_quote_wrong_parameters.json")
    private Resource createQuoteWrongMetadata;

    @Value(value = "classpath:contracts_json/create_quote.json")
    private Resource quoteMetadaStatus;

    @Value(value = "classpath:contracts_json/create_quote.json")
    private Resource quoteMetadaInformation;

    private final String CREATE_QUOTE_URI = "/api/v1/quote";
    private final String STATUS_QUOTE_URI = "/api/v1/quote/status";
    private final String INFORMATION_QUOTE_URI = "/api/v1/quote/information";


    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //US01
    @Test
    public void requestQuotingTest() throws Exception {
        String createQuote = IOUtils.toString(createQuoteMetadata.getInputStream(), "UTF-8");

        ResultActions result = performPostQuoteRequestStatusOk(CREATE_QUOTE_URI,createQuote);

        String response = result.andReturn().getResponse().getContentAsString();
        assertNotNull(response);
        assertFalse(response.isEmpty());
    }

    //US01
    @Test
    public void requestQuotingEmptyTest() throws Exception  {
        performPostQuoteRequestStatusBadRequest(CREATE_QUOTE_URI,"");
    }

    //US01
    @Test
    public void requestQuotingWrongParameterTest() throws Exception  {
        String createQuoteWrongParameters = IOUtils.toString(createQuoteWrongMetadata.getInputStream(), "UTF-8");

        performPostQuoteRequestStatusBadRequest(CREATE_QUOTE_URI,createQuoteWrongParameters);
    }

    //US05
    @Test
    public void requestQuoteStatusTest() throws Exception  {
        String quoteStatus = IOUtils.toString(quoteMetadaStatus.getInputStream(), "UTF-8");

        Quote quote = new ObjectMapper().readValue(quoteStatus, Quote.class);

        ResultActions resultStatus = mockMvc.perform(
                        get(STATUS_QUOTE_URI + "/" + quote.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(quote.getId())))
                .andExpect(jsonPath("$.status", is(quote.getStatus().getDescription())))
                .andExpect(jsonPath("$.price", is(quote.getPrice())));

        String responseStatus = resultStatus.andReturn().getResponse().getContentAsString();

        assertNotNull(responseStatus);
        assertFalse(responseStatus.isEmpty());
    }

    //US05 404
    @Test
    public void requestQuoteStatus404Test() throws Exception {
    }


    //US06
    @Test
    public void requestQuoteInformationTest() throws Exception {
    }

    //US06 404
    @Test
    public void requestQuoteInformation404Test() throws Exception {
    }

    private ResultActions performPostQuoteRequestStatusOk(String uri, String content)
            throws Exception {
        return mockMvc.perform(
                post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isOk());
    }

    private ResultActions performPostQuoteRequestStatusBadRequest(String uri, String content)
            throws Exception {
        return mockMvc.perform(
                post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content))
                .andExpect(status().isBadRequest());
    }

}