package oslomet.testing;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet{

    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository rep;

    @Mock
    private HttpSession session;

    @Test
    public void testSjekkLoggInnValidInput() {
        // Mocking av BankRepository metoden
        when(rep.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        // Mocking av HttpSession
        when(session.getAttribute("Innlogget")).thenReturn(null);

        // Testing av sjekkLoggInn metoden
        String result = sikkerhet.sjekkLoggInn("12345987612", "validPassword");

        // Verifisering av resultat
        assertEquals("OK", result);
        Mockito.verify(session).setAttribute("Innlogget", "12345987612");
    }

    @Test
    public void testSjekkLoggInnInvalidPersonnummer() {
        // testing av sjekkLoggInn mot feil personnummer
        String result = sikkerhet.sjekkLoggInn("invalidPersonnummer", "validPassword");

        // Verifisering av resultat
        assertEquals("Feil i personnummer", result);
    }

    @Test
    public void testSjekkLoggInnInvalidPassord() {
        // Testing av sjekkLoggInn metoden with feil passord
        String result = sikkerhet.sjekkLoggInn("12345678901", "short");

        // Verifisering av resultat
        assertEquals("Feil i passord", result);
    }
    @Test
    public void testLoggInnAdminValidInput() {
        // Testiing av loggInnAdmin metoden med riktig input
        String result = sikkerhet.loggInnAdmin("Admin", "Admin");

        // Verifisering av resultat
        assertEquals("Logget inn", result);
        Mockito.verify(session).setAttribute("Innlogget", "Admin");
    }

    @Test
    public void testLoggInnAdminInvalidInput() {
        // Testing av loggInnAdmin metoden med feil input
        String result = sikkerhet.loggInnAdmin("Feilbrukernavn", "FeilPassord");

        // Verifisering av resultat
        assertEquals("Ikke logget inn", result);
        Mockito.verify(session).setAttribute("Innlogget", null);
    }
}

