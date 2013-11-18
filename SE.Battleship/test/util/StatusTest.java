package util;

import static org.junit.Assert.*;

import org.junit.*;

import interfaces.IStatus;
import util.Status;

public class StatusTest {
	private IStatus status;
	
	@Before
	public void setUp() {
		status = new Status();
	}
	
	@Test
	public void testStatus() {
		try {
			new Status();
		} catch (Exception exc) {
			fail("Should not throw exception at this point.");
		}
	}
	
	@Test
	public void testgetText() {
		String text = "This is some text.";
		String result = " " + text;
		status.addText(text);
		assertEquals(result, status.getText());
	}
	
	@Test
	public void testgetError() {
		String error = "This is some error.";
		String result = " " + error;
		status.addError(error);
		assertEquals(result, status.getError());
	}
	
	@Test
	public void testClearText() {
		String text = "This is some text.";
		status.addText(text);
		status.clearText();
		assertEquals("", status.getText());
	}
	
	@Test
	public void testClearError() {
		String error = "This is some error.";
		status.addText(error);
		status.clearText();
		assertEquals("", status.getError());
	}
	
	@Test
	public void testaddText() {
		int count = status.getTextCount();
		status.addText("some text");
		assertEquals(++count, status.getTextCount());
	}
	
	@Test
	public void testaddError() {
		int count = status.getErrorCount();
		status.addError("some error");
		assertEquals(++count, status.getErrorCount());
	}
	
	@Test
	public void testgetErrorCount() {
		int result = 2;
		status.addError("error 1");
		status.addError("error 2");
		assertEquals(result, status.getErrorCount());
	}
	
	@Test
	public void testgetTextCount() {
		int result = 2;
		status.addText("text 1");
		status.addText("text 2");
		assertEquals(result, status.getTextCount());
	}
	
	@Test
	public void testclear() {
		status.addText("text 1");
		status.addError("error 1");
		status.clear();
		assertEquals(status.getErrorCount(), status.getTextCount());
	}
	
	@Test
	public void testcopyStatus() {
		Status source = new Status();
		String text = "some text";
		String error = "some error";
		source.addText(text);
		source.addError(error);
		status.copyStatus(source);
		assertEquals(source.getText(), status.getText());
		assertEquals(source.getError(), status.getError());
		assertEquals(source.getTextCount(), status.getTextCount());
		assertEquals(source.getErrorCount(), status.getErrorCount());
	}
	
	@Test
	public void testmoveStatus() {
		Status source = new Status();
		String text = "some text";
		String textResult = " " + text;
		String error = "some error";
		String errorResult = " " + error;
		source.addText(text);
		source.addError(error);
		source.moveStatus(status);
		assertEquals("", status.getText());
		assertEquals("", status.getError());
		assertEquals(textResult, source.getText());
		assertEquals(errorResult, source.getError());
	}
	
	@Test
	public void testtextExists() {
		assertFalse(status.textExist());
		status.addText("some text");
		assertTrue(status.textExist());
	}
	
	@Test
	public void testerrorExists() {
		assertFalse(status.errorExist());
		status.addError("some error");
		assertTrue(status.errorExist());
	}
}

