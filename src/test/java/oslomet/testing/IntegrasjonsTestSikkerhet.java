package oslomet.testing;
import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.test.context.junit.jupiter.SpringExtension;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
        import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

        import javax.servlet.http.HttpSession;

        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrasjonsTestSikkerhet {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HttpSession session;

    @Test
    public void testSjekkLoggInn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/loggInn")
                        .param("personnummer", "12345678901")
                        .param("passord", "passord123"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("OK"));

        // Verifiser at brukeren er logget inn ved å sjekke session
        Object loggedInUser = session.getAttribute("Innlogget");
        assert loggedInUser != null && loggedInUser.equals("12345678901");
    }

    @Test
    public void testLoggUt() throws Exception {
        // Logg inn først
        session.setAttribute("Innlogget", "12345678901");

        mockMvc.perform(MockMvcRequestBuilders.get("/loggUt"))
                .andExpect(status().isOk());

        // Verifiser at brukeren er logget ut ved å sjekke session
        Object loggedInUser = session.getAttribute("Innlogget");
        assert loggedInUser == null;
    }

    @Test
    public void testLoggInnAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/loggInnAdmin")
                        .param("bruker", "Admin")
                        .param("passord", "Admin"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Logget inn"));

        // Verifiser at Admin er logget inn ved å sjekke session
        Object loggedInUser = session.getAttribute("Innlogget");
        assert loggedInUser != null && loggedInUser.equals("Admin");
    }

    @Test
    public void testLoggetInn() throws Exception {
        // Logg inn først
        session.setAttribute("Innlogget", "12345678901");

        mockMvc.perform(MockMvcRequestBuilders.get("/loggetInn"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("12345678901"));
    }
}


