package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

	@InjectMocks
	private AdminKundeController adminKundeController;

	@Mock
	private AdminRepository adminRepository;

	@Mock
	private Sikkerhet sjekk;

	@Test
	public void test_hentAlle_loggetInn() {
		//Arrange
		List<Kunde> kundeListe = new ArrayList<>();
		Kunde kunde1 = new Kunde("12345678910", "Jens", "Knutsen",
				"Veienveien98", "9787", "Buskerud", "8976576", "passord123");
		kundeListe.add(kunde1);

		Mockito.when(sjekk.loggetInn()).thenReturn("12345678910");

		Mockito.when(adminRepository.hentAlleKunder()).thenReturn(kundeListe);

		//Act
		List<Kunde> resultat =adminKundeController.hentAlle();

		//Assert
		assertEquals(kundeListe, resultat);
	}

	@Test
	public void test_hentAlle_ikkeLoggetInn() {
		//Arrange
		Mockito.when(sjekk.loggetInn()).thenReturn(null);

		//Act
		List<Kunde> resultat = adminKundeController.hentAlle();

		//Assert
		assertNull(resultat);
	}

}
