package oslomet.testing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository bankRepository;

    private MockHttpSession session;

    @Before
    public void setUp() {
        session = new MockHttpSession();
        sikkerhet.session = session;
    }

    @Test
    public void sjekkLoggInnTest() {
        when(bankRepository.sjekkLoggInn("12345678901", "password")).thenReturn("OK");

        String result = sikkerhet.sjekkLoggInn("12345678901", "password");

        assertEquals("OK", result);
        assertEquals("12345678901", session.getAttribute("Innlogget"));
    }

    @Test
    public void sjekkLoggInnFeilPersonnummerTest() {
        String result = sikkerhet.sjekkLoggInn("1234567890", "password");

        assertEquals("Feil i personnummer", result);
    }

    @Test
    public void sjekkLoggInnFeilPassordTest() {
        String result = sikkerhet.sjekkLoggInn("12345678901", "pass");

        assertEquals("Feil i passord", result);
    }
    @Test
    public void sjekkLoggInnFeilInnloggingTest() {
        when(bankRepository.sjekkLoggInn("12345678901", "wrongpassword")).thenReturn("Feil");

        String result = sikkerhet.sjekkLoggInn("12345678901", "wrongpassword");

        assertEquals("Feil i personnummer eller passord", result);
        assertNull(session.getAttribute("Innlogget"));
    }

    @Test
    public void loggInnAdminFeilInnloggingTest() {
        String result = sikkerhet.loggInnAdmin("WrongAdmin", "Admin");

        assertEquals("Ikke logget inn", result);
        assertNull(session.getAttribute("Innlogget"));
    }

    @Test
    public void loggUtTest() {
        session.setAttribute("Innlogget", "12345678901");

        sikkerhet.loggUt();

        assertNull(session.getAttribute("Innlogget"));
    }

    @Test
    public void loggInnAdminTest() {
        String result = sikkerhet.loggInnAdmin("Admin", "Admin");

        assertEquals("Logget inn", result);
        assertEquals("Admin", session.getAttribute("Innlogget"));
    }

    @Test
    public void loggInnAdminFeilBrukerTest() {
        String result = sikkerhet.loggInnAdmin("admin", "Admin");

        assertEquals("Ikke logget inn", result);
    }

    @Test
    public void loggInnAdminFeilPassordTest() {
        String result = sikkerhet.loggInnAdmin("Admin", "admin");

        assertEquals("Ikke logget inn", result);
    }

    @Test
    public void loggetInnTest() {
        session.setAttribute("Innlogget", "12345678901");

        String result = sikkerhet.loggetInn();

        assertEquals("12345678901", result);
    }
    
    @Test
    public void sjekkLoggInnEmptyInputTest() {
        String result = sikkerhet.sjekkLoggInn("", "");
        assertEquals("Feil i personnummer", result);

        result = sikkerhet.sjekkLoggInn("12345678901", "");
        assertEquals("Feil i passord", result);

        result = sikkerhet.sjekkLoggInn("", "password");
        assertEquals("Feil i personnummer", result);
    }


    @Test
    public void loggInnAdminEmptyInputTest() {
        String result = sikkerhet.loggInnAdmin("", "");
        assertEquals("Ikke logget inn", result);
    }

    @Test
    public void loggetInnNotLoggedInTest() {
        String result = sikkerhet.loggetInn();
        assertNull(result);
    }
}
