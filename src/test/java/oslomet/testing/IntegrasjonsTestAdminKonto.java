package oslomet.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IntegrasjonsTestAdminKonto{

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AdminRepository adminRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHentAlleKunder() {
        // Oppsett av testdata
        List<Kunde> Kunder = new ArrayList<>();
        Kunder.add(new Kunde());
        // Legg til mer testdata om nødvendig

        // Mocking JDBC query
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Kunder);

        // Utfør metoden som skal testes
        List<Kunde> faktiskeKunder = adminRepository.hentAlleKunder();

        // test av resultat
        assertEquals(Kunder.size(), faktiskeKunder.size());
    }

    @Test
    public void testEndreKundeInfo() {
        // Oppsett av testdata
        Kunde kunde = new Kunde();
        // Mocking JDBC operations
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any()))
                .thenReturn(1);
        // Mock at PostNr eksisterer
        doNothing().when(jdbcTemplate).update(anyString(), any(), any(), any(), any(), any(), any(), any());

        //Utførelse av metode som testes
        String resultat = adminRepository.endreKundeInfo(kunde);
        assertEquals("OK", resultat);

    }

    // Legg til flere tester av metoder
}
