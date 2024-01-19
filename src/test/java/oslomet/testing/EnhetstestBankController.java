package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {
        //Arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        Mockito.when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        //Act
        Kunde resultat = bankController.hentKundeInfo();

        //Assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {
        //Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //Act
        Kunde resultat = bankController.hentKundeInfo();

        //Assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        //Arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        Mockito.when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentKonti(anyString())).thenReturn(konti);

        //Act
        List<Konto> resultat = bankController.hentKonti();

        //Assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        //Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //Act
        List<Konto> resultat = bankController.hentKonti();

        //Assert
        assertNull(resultat);
    }

    //Utkast for tester

    @Test
    public void hentTransaksjoner_loggetInn(){
        // Arrange
        String personnummer = "12345678901";
        String kontoNr = "12345678901";
        String fraDato = "2024-21-01";
        String tilDato = "2024-12-01";
        Konto konto1 = new Konto();
        List<Transaksjon> transaksjoner = new ArrayList<>();
        konto1.setTransaksjoner(transaksjoner);

        Mockito.when(sjekk.loggetInn()).thenReturn(personnummer);
        Mockito.when(repository.hentTransaksjoner(kontoNr, fraDato, tilDato)).thenReturn(konto1);

        //Act
        Konto resultat = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        //Assert
        assertEquals(konto1, resultat);
        assertEquals(transaksjoner, resultat.getTransaksjoner());

    }

    @Test
    public void hentTransaksjoner_ikkeloggetInn(){
        //Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //Act
        Konto resultat = bankController.hentTransaksjoner("12345678901", "2022-01-01", "2022-12-31");

        // Assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_loggetInn(){
        //Arrange
        String personnummer = "12345678901";
        Konto konto2 = new Konto();

        //Ønsker å teste saldo fra spesifikk kontonummer, mer detaljert men kanskje ikke nødvendig
        konto2.setKontonummer("1234567");
        konto2.setSaldo(1000.0);
        List<Konto> eksemplekonti = new ArrayList<>();
        eksemplekonti.add(konto2);

        Mockito.when(sjekk.loggetInn()).thenReturn(personnummer);
        Mockito.when(repository.hentSaldi(personnummer)).thenReturn(eksemplekonti);

        //Act
        List<Konto> resultatet = bankController.hentSaldi();

        //Assert
        assertEquals(eksemplekonti,resultatet);
        assertEquals("1234567", resultatet.get(0).getKontonummer());
        assertEquals(Double.parseDouble("1000.0"), resultatet.get(0).getSaldo(), 0.01);

    }

    @Test
    public void hentSaldi_ikkeLoggetInn(){
        //Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //Act
        List<Konto> resultatet = bankController.hentSaldi();

        //Assert
        assertNull(resultatet);

    }

    @Test
    public void registrerBetaling_loggetInn(){
        //Arrange
        String personnummer = "12345678901";
        Transaksjon betaling = new Transaksjon();
        betaling.setBelop(1000);
        betaling.setKontonummer("12345678901");
        betaling.setDato("2024-21-01");
        betaling.setMelding("Denne melding er OK");
        betaling.setFraTilKontonummer("Dette er en test");

        Mockito.when(sjekk.loggetInn()).thenReturn(personnummer);
        Mockito.when(repository.registrerBetaling(betaling)).thenReturn("OK");

        //Act
        String resultat = bankController.registrerBetaling(betaling);

        //Assert
        assertEquals("OK", resultat);

        // Her bør en kanskje igsp sjekke de andre attributtene i en transaksjon, for å se at de faktisk er innafor
        // Med dette mener jeg ar det er en streng, at verdiene er gyldige, det samme med dato osv.
    }

    @Test
    public void registrerBetaling_ikkeLoggetInn(){
        //Arrange
        Transaksjon betaling = new Transaksjon();
        betaling.setBelop(1000);
        betaling.setAvventer("OK");
        betaling.setKontonummer("12345678901");
        betaling.setDato("2024-21-01");
        betaling.setMelding("Denne melding er OK");
        betaling.setFraTilKontonummer("Dette er en test");

        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //Act
        String resultat = bankController.registrerBetaling(betaling);

        //Assert
        assertNull(resultat);

    }

    @Test
    public void hentBetaling_loggetInn(){
        //Arrange
        String personnummer = "12345678901";

        List<Transaksjon> transaksjoner = new ArrayList<>();

        //Eksempel object for 1 transaksjon
        Transaksjon eksempel1 = new Transaksjon();
        eksempel1.setDato("2024-12-01");
        eksempel1.setKontonummer("109679902");
        eksempel1.setMelding("Her skal det være en melding");
        eksempel1.setFraTilKontonummer("mfefef");
        eksempel1.setTxID(101);
        eksempel1.setBelop(1000.0);
        eksempel1.setAvventer("1");

        Transaksjon eksempel2 = new Transaksjon();
        eksempel2.setDato("2024-12-01");
        eksempel2.setKontonummer("109679902");
        eksempel2.setMelding("Her skal det være en melding");
        eksempel2.setFraTilKontonummer("mfefef");
        eksempel2.setTxID(102);
        eksempel2.setBelop(1000.0);
        eksempel2.setAvventer("2");
        transaksjoner.add(eksempel2);

        Mockito.when(sjekk.loggetInn()).thenReturn(personnummer);
        Mockito.when(repository.hentBetalinger(personnummer)).thenReturn(transaksjoner);

        //Act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //Assert
        assertEquals(transaksjoner, resultat);
        assertEquals(2, resultat.size());
        assertEquals(eksempel1, resultat.get(0));
        assertEquals(eksempel2, resultat.get(1));

    }

    @Test
    public void hentBetaling_ikkeLoggetInn(){
        // Arrange
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // Assert
        assertNull(resultat);
    }


    @Test
    public void utforBetaling_loggetInnSuksess(){
        String personnummer = "12345678901";
        int txID = 1;
        List<Transaksjon> eksempel1transaksjon = new ArrayList<>();
        eksempel1transaksjon.add(new Transaksjon()); // Sett opp eksempeltransaksjoner

        //Eksempel object for 1 transaksjon
        Transaksjon eksempel1 = new Transaksjon();
        eksempel1.setDato("2024-12-01");
        eksempel1.setKontonummer("109679902");
        eksempel1.setMelding("Her skal det være en melding");
        eksempel1.setFraTilKontonummer("mfefef");
        eksempel1.setTxID(1);
        eksempel1.setBelop(1000.0);
        eksempel1.setAvventer("1");

        eksempel1transaksjon.add(eksempel1);

        Mockito.when(sjekk.loggetInn()).thenReturn(personnummer);
        Mockito.when(repository.utforBetaling(txID)).thenReturn("OK");
        Mockito.when(repository.hentBetalinger(personnummer)).thenReturn(eksempel1transaksjon);

        // Act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        // Assert
        assertNotNull(resultat);
        assertEquals(eksempel1transaksjon, resultat);

    }
    @Test
    public void utforBetaling_loggetInnSuksessNei(){
        String personnummer = "12345678901";
        int txID = 1;

        Mockito.when(sjekk.loggetInn()).thenReturn(personnummer);
        Mockito.when(repository.utforBetaling(txID)).thenReturn("Feil");

        // Act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        // Assert
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_ikkeLoggetInn(){
        int txID = 1;
        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        // Assert
        assertNull(resultat);
    }


    @Test
    public void endreKundeInfo_loggetInn(){
        //Arrange
        Kunde innKunde = new Kunde("12345678901",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");


        Mockito.when(sjekk.loggetInn()).thenReturn(innKunde.getPersonnummer());
        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        //Act
        String resultat = bankController.endre(innKunde);

        //Assert
        assertEquals(resultat,innKunde ,"OK");

    }

    @Test
    public void endreKundeInfo_ikkeLoggetInn(){
        //Arrange
        Kunde innKunde = new Kunde("12345678901",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        Mockito.when(sjekk.loggetInn()).thenReturn(null);

        //Act
        String resultat = bankController.endre(innKunde);

        // Assert
        assertNull(resultat);
    }

}

