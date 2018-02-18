package car.insurance.company.carinsuranceapi.model.enumerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class QuoteStatusTest {

    @Test
    public void fromDescriptionOpen() {
        assertEquals(QuoteStatus.OPEN, QuoteStatus.fromDescription("open"));
        assertEquals(QuoteStatus.OPEN, QuoteStatus.fromDescription("oPEn"));
        assertEquals(QuoteStatus.OPEN, QuoteStatus.fromDescription("OPEN"));
    }

    @Test
    public void fromDescriptionProcessing() {
        assertEquals(QuoteStatus.PROCESSING, QuoteStatus.fromDescription("processing"));
        assertEquals(QuoteStatus.PROCESSING, QuoteStatus.fromDescription("proCESsIng"));
        assertEquals(QuoteStatus.PROCESSING, QuoteStatus.fromDescription("PROCESSING"));
    }

    @Test
    public void fromDescriptionDone() {
        assertEquals(QuoteStatus.DONE, QuoteStatus.fromDescription("done"));
        assertEquals(QuoteStatus.DONE, QuoteStatus.fromDescription("DonE"));
        assertEquals(QuoteStatus.DONE, QuoteStatus.fromDescription("DONE"));
    }

    @Test
    public void fromDescriptionNull() {
        assertNull(QuoteStatus.fromDescription(""));
        assertNull(QuoteStatus.fromDescription(null));
        assertNull(QuoteStatus.fromDescription("anything else"));
    }

    @Test
    public void getDescription() {
        assertEquals("open", QuoteStatus.OPEN.getDescription());
        assertEquals("processing", QuoteStatus.PROCESSING.getDescription());
        assertEquals("done", QuoteStatus.DONE.getDescription());
    }
}