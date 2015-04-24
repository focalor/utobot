package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Date;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.handler.IInputHandler;
import nl.focalor.utobot.util.ReflectionUtil;
import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.service.IWarService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * Created by luigibanzato on 12/04/2015.
 */
public class WarEndHandlerTest extends AbstractUtoHandlerTest {

	@InjectMocks
	private WarEndHandler warEndHandler;

	@Mock
	private IWarService warService;

	@Test
	public void testEndWar() {
		// Setup
		War war = new War();
		war.setId(1l);
		war.setStartDate(new Date());
		when(warService.getCurrentWar()).thenReturn(war);
		doNothing().when(warService).endWar();

		// Test
		IResult reply = warEndHandler.handleCommand(CommandInput.createFor("oneguy", "!endwar"));

		// Verify
		String msg = ReflectionUtil.getField(reply, "message", String.class);
		assertTrue(msg.equals("War Ended. War Id: 1."));
	}

	@Override
	public IInputHandler getHandler() {
		return warEndHandler;
	}

}
