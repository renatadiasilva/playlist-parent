package dei.uc.pt.aor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class EncryptPassTest {
	
	@InjectMocks
	EncryptPass ep;	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
 	}

	@Test
	public void TestPassword() {
		
		String pass = "admin";
		String epass = "jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=";
		
		assertEquals(ep.encrypt(pass),epass);

		System.out.println("Checked successfully password encryptation.");
	}
	
}