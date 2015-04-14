package nl.focalor.utobot.utopia.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import nl.focalor.utobot.config.TestConfig;
import nl.focalor.utobot.utopia.model.UtopiaDate;
import nl.focalor.utobot.utopia.model.UtopiaSettings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by luigibanzato on 13/04/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class, loader=AnnotationConfigContextLoader.class)
public class UtopiaServiceTest {

    @Autowired
    private UtopiaSettings utopiaSettings;
    private IUtopiaService utopiaService;

    @Before
    public void setup() throws JsonParseException, JsonMappingException, IOException, ParseException {
        utopiaService = new UtopiaService(utopiaSettings);
    }

    @Test
         public void testDateRealToUtopian() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date parsedDate = formatter.parse("01-04-2015 18:00:00");
        assertThat(utopiaService.getUtopianDateFromReal(parsedDate).toString(false), is(equalTo("February 23, YR9")));
    }

    @Test
    public void testDateUtopiaToReal() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date parsedDate = formatter.parse("01-04-2015 18:00:00");

        UtopiaDate utopianDate = new UtopiaDate();
        utopianDate.setYear(9);
        utopianDate.setMonth(1);
        utopianDate.setDay(23);
        assertThat(utopiaService.getRealDateFromUtopian(utopianDate), is(equalTo(parsedDate)));
    }

    @Test
    public void testgetUtopiaDateFromString() throws ParseException {
        String stringDate = "February 23, YR9";
        UtopiaDate utopianDate = new UtopiaDate();
        utopianDate.setYear(9);
        utopianDate.setMonth(1);
        utopianDate.setDay(23);
        assertThat(utopiaService.getUtopiaDateFromString(stringDate).toString(false), is(equalTo(utopianDate.toString(false))));
    }
}
