package car.insurance.company.carinsuranceapi.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestApiControllerV1.class)
public class RestApiControllerV1Test {

    @Before
    public void setUp(){

    }

    //US01
    @Test
    public void requestQuotingTest() {
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
    public void quoteStatusTest() {
    }

    //US05 404
    @Test
    public void quoteStatus404Test() {
    }


    //US06
    @Test
    public void quoteInformationTest() {
    }

    //US06 404
    @Test
    public void quoteInformation404Test() {
    }

}