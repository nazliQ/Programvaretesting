package oslomet.testing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKontiTest_Innlogget() {
        // Arrange
        List<Konto> kontoListe = new ArrayList<>();
        Konto konto = new Konto();
        kontoListe.add(konto);

        Mockito.when(sjekk.loggetInn()).thenReturn("12345678910");
        Mockito.when(adminRepository.hentAlleKonti()).thenReturn(kontoListe);

        // Act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // Assert
        assertEquals(kontoListe, resultat);
    }

    @Test
    public void hentAlleKontiTest_IkkeInnlogget() {
        // Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        // Assert
        assertNull(resultat);
    }

    @Test
    public void registrerKontoTest_Innlogget() {
        // Arrange
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn("12345678910");
        Mockito.when(adminRepository.registrerKonto(any(Konto.class))).thenReturn("OK");

        // Act
        String resultat = adminKontoController.registrerKonto(konto);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerKontoTest_IkkeInnlogget() {
        // Arrange
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.registrerKonto(konto);

        // Assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void endreKontoTest_Innlogget() {
        // Arrange
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn("12345678910");
        Mockito.when(adminRepository.endreKonto(any(Konto.class))).thenReturn("OK");

        // Act
        String resultat = adminKontoController.endreKonto(konto);

        // Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKontoTest_IkkeInnlogget() {
        // Arrange
        Konto konto = new Konto();
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKontoController.endreKonto(konto);

        // Assert
        assertEquals("Ikke innlogget", resultat);
    }

   /* @Test
    public void slettKontoTest_Innlogget() {
        // Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn("12345678910");
        Mockito.when(adminRepository.slettKonto(any(String.class))).thenReturn("Konto slettet");

        // Act
        String resultat = AdminKontoController.slettKonto("1234567890123");

        // Assert
        assertEquals("Konto slettet", resultat);
    }*/
}
