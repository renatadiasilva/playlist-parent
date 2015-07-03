package dei.uc.pt.aor;

import static org.junit.Assert.*;

import java.security.Principal;

import javax.servlet.http.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LoginMBTest extends Mockito {
	
	@InjectMocks
	private LoginMB logMB;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private User u;
	
	@Mock
	private Principal principal;
	
	@Mock
	private HttpSession session;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
    public void testlogin() {
        
        boolean result = true;
        try {
	        request.login("admin@admin.com", "12345");
	        when(request.getParameter("email")).thenReturn("someemail");
	        when(request.getParameter("password")).thenReturn("somepass");
	        assertEquals(response.getStatus(), 0);
	        when(request.isUserInRole("CLIENT")).thenReturn(true);
	    	when(principal.getName()).thenReturn("samename");
	        when(request.getUserPrincipal()).thenReturn(principal);
			when(request.getSession()).thenReturn(session);
	    } catch (Exception e) {
			result = false;
		}
		assertTrue(result);

    }
    
}