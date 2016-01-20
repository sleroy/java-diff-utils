/**
 * @author sleroy
 */
package difflib;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;


/**
 * @author sleroy
 *
 */
public class DiffUtilsTest {
	
	/**
	 * Test method for
	 * {@link difflib.DiffUtils#diff(java.io.File, java.io.File)}.
	 */
	@Test
	public void testDiffFileFile() throws Exception {


		final Patch<String> diff = DiffUtils.diff(new File("src/test/resources/mocks/5A.txt"),   //$NON-NLS-1$
		        new File("src/test/resources/mocks/5B.txt"));  //$NON-NLS-1$
		assertEquals(1, diff.getDeltas().size());
	}
	
	/**
	 * Test method for
	 * {@link difflib.DiffUtils#diff(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testDiffStringString() throws Exception {

		final Patch<String> diff = DiffUtils.diff("a\nb\n", "a"); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println(diff.getDeltas());
	}
	
}
