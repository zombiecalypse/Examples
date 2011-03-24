
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Expectation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/*
 * Protocol definition:
 * First Line:    Name of the Player
 * Second Line:   Score
 * Third Line:    Number
 * 3..3+Nth Line: Names of Players that were beaten by this Player.
 * 4+Nth Line:    \KTHXBAI
 */
public class DataStreamTest {

	Mockery mockery = new JUnit4Mockery();
	private IServerConnection server;
	private DataStream datastream;
	
	@Before
	public void setUp() throws Exception {
		/*
		 * I can't really use a server here, so I'll just tell the
		 * mock server what to do.
		 */
		server = mockery.mock(IServerConnection.class);
	}

	@Ignore
	@Test
	public void shouldReadSimple() {
		mockery.checking(new Expectations() {{
			/*
			 * The protocol defines, that the client will ask me to connect,
			 * then requests data and then I'll give the values (when
			 * asked `pull()`).
			 */
			exactly(1).of(server).connectToServer();
			exactly(1).of(server).requestData();
			oneOf(server).pull(); will(returnValue("Ueli"));
			oneOf(server).pull(); will(returnValue("192"));
			oneOf(server).pull(); will(returnValue("0"));
			oneOf(server).pull(); will(returnValue("\\KTHXBAI"));
		}});
		datastream = new DataStream(server);
		datastream.receiveComplete();
		mockery.assertIsSatisfied();
	}
	
	/*
	 * I expect the datastream to throw an exception here. It
	 * fails if it does not!
	 */
	@Test(expected=ConnectionException.class)
	public void shouldntReadSimpleError() {
		mockery.checking(new Expectations() {{
			exactly(1).of(server).connectToServer();
			exactly(1).of(server).requestData();
			oneOf(server).pull(); will(returnValue("Ueli"));
			oneOf(server).pull(); will(returnValue("192"));
			oneOf(server).pull(); will(returnValue("1"));
			oneOf(server).pull(); will(returnValue("\\KTHXBAI"));
		}});
		datastream = new DataStream(server);
		datastream.receiveComplete();
		mockery.assertIsSatisfied();
	}
}
