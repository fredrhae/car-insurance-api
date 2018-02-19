package car.insurance.company.carinsuranceapi.rest;

import car.insurance.company.carinsuranceapi.model.Customer;
import car.insurance.company.carinsuranceapi.model.Quote;
import car.insurance.company.carinsuranceapi.model.Vehicle;
import car.insurance.company.carinsuranceapi.service.QuoteService;
import car.insurance.company.carinsuranceapi.utils.EntitiesGeneratorHelper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private QuoteService quoteService;

    private final String CREATE_QUOTE_URI = "/api/v1/quote";
    private final String STATUS_QUOTE_URI = "/api/v1/quote/status";
    private final String INFORMATION_QUOTE_URI = "/api/v1/quote/information";

    private Quote quoteStatusAndInfoTest;
    private Customer customer4Test;
    private Vehicle vehicle4Test;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        customer4Test = EntitiesGeneratorHelper.generateCustomer4Test();
        vehicle4Test = EntitiesGeneratorHelper.generateVehicle4Test();

        quoteStatusAndInfoTest = quoteService.generateQuote(customer4Test, vehicle4Test);
    }

    //US01
    @Test
    public void requestQuotingTest() throws Exception {
        String createQuote = IOUtils.toString(createQuoteMetadata.getInputStream(), "UTF-8");

        ResultActions result = performPostQuoteRequestStatusOk(CREATE_QUOTE_URI, createQuote);

        ResponseEntity<Long> responseEntity = (ResponseEntity<Long>) result.andReturn().getAsyncResult();

        Long number = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity);
        assertNotNull(number);
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
        ResultActions resultStatus = mockMvc.perform(
                        get(STATUS_QUOTE_URI + "/" + quoteStatusAndInfoTest.getNumber()))
                .andExpect(status().isOk());

        ResponseEntity<Quote> responseEntity = (ResponseEntity<Quote>) resultStatus.andReturn().getAsyncResult();

        Quote quoteReturned = responseEntity.getBody();

        assertNotNull(quoteReturned);
        assertEquals(quoteStatusAndInfoTest.getNumber(),quoteReturned.getNumber());
        assertEquals(quoteStatusAndInfoTest.getStatus(),quoteReturned.getStatus());
        assertEquals(quoteStatusAndInfoTest.getPrice(),quoteReturned.getPrice());
    }

    //US05 404
    @Test
    public void requestQuoteStatus404Test() throws Exception {
        ResultActions resultStatus = mockMvc.perform(
                get(STATUS_QUOTE_URI + "/" + 200000000000L))
                .andExpect(status().isOk());

        ResponseEntity<QuoteMetadata> responseEntity = (ResponseEntity<QuoteMetadata>)
                resultStatus.andReturn().getAsyncResult();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    //US05 400
    @Test
    public void requestQuoteStatus400AgainTest() throws Exception {
        ResultActions resultStatus = mockMvc.perform(
                get(STATUS_QUOTE_URI + "/anything_else"))
                .andExpect(status().isBadRequest());
    }

    //US06
    @Test
    public void requestQuoteInformationTest() throws Exception {
        ResultActions resultStatus = mockMvc.perform(
                get(INFORMATION_QUOTE_URI + "/" + quoteStatusAndInfoTest.getNumber()))
                .andExpect(status().isOk());

        ResponseEntity<QuoteMetadata> responseEntity = (ResponseEntity<QuoteMetadata>)
                                                        resultStatus.andReturn().getAsyncResult();

        QuoteMetadata quoteMetadata = responseEntity.getBody();

        assertNotNull(quoteMetadata);
        assertEquals(customer4Test, quoteMetadata.getCustomer());
        assertEquals(vehicle4Test, quoteMetadata.getVehicle());
    }

    //US06 404
    @Test
    public void requestQuoteInformation404Test() throws Exception {
        ResultActions resultStatus = mockMvc.perform(
                get(INFORMATION_QUOTE_URI + "/" + 200000000000L))
                .andExpect(status().isOk());

        ResponseEntity<QuoteMetadata> responseEntity = (ResponseEntity<QuoteMetadata>)
                resultStatus.andReturn().getAsyncResult();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    //US06 400
    @Test
    public void requestQuoteInformation400Test() throws Exception {
        ResultActions resultStatus = mockMvc.perform(
                get(INFORMATION_QUOTE_URI + "/anything_else"))
                .andExpect(status().isBadRequest());
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