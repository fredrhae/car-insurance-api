package car.insurance.company.carinsuranceapi.rest;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

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

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //US01
    @Test
    public void requestQuotingTest() throws Exception {
        String createQuote = IOUtils.toString(createQuoteMetadata.getInputStream(), "UTF-8");

        ResultActions result = mockMvc.perform(
                post("/api/v1/quote")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(createQuote))
                .andExpect(status().isOk());

        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    //US01
    @Test
    public void requestQuotingEmptyTest() {
    }

    //US01
    @Test
    public void requestQuotingWrongParametersTest() {
    }

    //US05
    @Test
    public void requestQuoteStatusTest() {
    }

    //US05 404
    @Test
    public void requestQuoteStatus404Test() {
    }


    //US06
    @Test
    public void requestQuoteInformationTest() {
    }

    //US06 404
    @Test
    public void requestQuoteInformation404Test() {
    }

}