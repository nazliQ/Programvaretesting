package oslomet.testing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    private AdminRepository adminRepository;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testHentAlleKunder() {
        // Mocking JdbcTemplate query metoden
        when(jdbcTemplate.query(Mockito.anyString(), Mockito.any(BeanPropertyRowMapper.class)))
                .thenReturn(Collections.emptyList());

        // Testing av hentAlleKunder metoden
        List<Kunde> result = adminRepository.hentAlleKunder();

        // Verifisering av resultat
        assertEquals(Collections.emptyList(), result);
    }
    public void testEndreKundeInfo() {
        // Mocking JdbcTemplate queryForObject metoden
        when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.any()))
                .thenReturn(1);

        // Mocking JdbcTemplate oppdateringsmetoden
        when(jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(1);

        // Testing av endreKundeInfo metoden
        Kunde kunde = new Kunde(/* populate with required fields */);
        String result = adminRepository.endreKundeInfo(kunde);

        // Verifying the result
        assertEquals("OK", result);
        // Verifying interactions with the JdbcTemplate
        Mockito.verify((jdbcTemplate), Mockito.times(2)).update(Mockito.anyString(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }
    @Test
    public void testRegistrerKunde() {
        // Mocking JdbcTemplate queryForObject metoden
        when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.any()))
                .thenReturn(0);

        // Mocking JdbcTemplate oppdateringsmetoden
        when(jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(1);

        // Testing av registrerKunde metoden
        Kunde kunde = new Kunde();
        String result = adminRepository.registrerKunde(kunde);

        // Verifisering av resultat
        assertEquals("OK", result);
        // Verifisering av interactioner med JdbcTemplate
        Mockito.verify(jdbcTemplate, Mockito.times(2)).update(Mockito.anyString(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void testSlettKunde() {
        // Mocking JdbcTemplate oppdateringsmetoden
        when(jdbcTemplate.update(Mockito.anyString(), Optional.ofNullable(Mockito.any())))
                .thenReturn(1);

        // Testing av slettKunde metoden
        String personnummer = "12345678901";
        String result = adminRepository.slettKunde(personnummer);

        // Verifisering av resultat
        assertEquals("OK", result);
        // Verifisering av interaksjon med JdbcTemplate
        Mockito.verify(jdbcTemplate).update(Mockito.anyString(), Optional.ofNullable(Mockito.any()));
    }

    @Test
    public void testRegistrerKonto() {
        // Mocking JdbcTemplate queryForObject metoden
        when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.any()))
                .thenReturn(1);

        // Mocking JdbcTemplate oppdateringsmetoden
        when(jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any()))
                .thenReturn(1);

        // Testing av registrerKonto metoden
        Konto konto = new Konto();
        String result = adminRepository.registrerKonto(konto);

        // Verifisering av resultat
        assertEquals("OK", result);
        // Verifisering interaksjoner med JdbcTemplate
        Mockito.verify(jdbcTemplate, Mockito.times(2)).update(Mockito.anyString(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void testEndreKonto() {
        // Mocking av JdbcTemplate queryForObject metoden for personnummer
        when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.any()))
                .thenReturn(1);

        // Mocking av JdbcTemplate queryForObject metoden for kontonummer
        when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class), Mockito.any()))
                .thenReturn(1);

        // Mocking av JdbcTemplate oppdateringsmetoden
        when(jdbcTemplate.update(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(1);

        // Testing av endreKonto methoden
        Konto konto = new Konto(/* populate with required fields */);
        String result = adminRepository.endreKonto(konto);

        // Verifisering av resultat
        assertEquals("OK", result);
        // Verifisering av interaksjon med jdbcTemplate
        Mockito.verify(jdbcTemplate, Mockito.times(3)).update(Mockito.anyString(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test
    public void testSlettKonto() {
        // Mocking av dbcTemplate oppdateringsmetoden
        when(jdbcTemplate.update(Mockito.anyString(), Optional.ofNullable(Mockito.any())))
                .thenReturn(1);

        // Testing av slettKontometoden
        String kontonummer = "12345";
        String result = adminRepository.slettKonto(kontonummer);

        // Verifisering av resultat
        assertEquals("OK", result);
        // Verifisering av interaksjoner med jdbctemplate
        Mockito.verify(jdbcTemplate).update(Mockito.anyString(), Optional.ofNullable(Mockito.any()));
    }


}
