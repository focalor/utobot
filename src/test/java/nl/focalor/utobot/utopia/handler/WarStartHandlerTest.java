package nl.focalor.utobot.utopia.handler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Date;
import nl.focalor.utobot.base.input.CommandInput;
import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.util.ReflectionUtil;
import nl.focalor.utobot.utopia.model.entity.War;
import nl.focalor.utobot.utopia.service.IWarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by luigibanzato on 12/04/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class WarStartHandlerTest {

	@InjectMocks
	private WarStartHandler warStartHandler;

	@Mock
	private IWarService warService;

	@Test
	public void testAddWar() {
		// Setup
		War war = new War();
		war.setId(1l);
		war.setStartDate(new Date());
		when(warService.startWar()).thenReturn(war);

		// Test
		IResult reply = warStartHandler.handleCommand(CommandInput.createFor("oneguy", "!startwar"));

		// Verify
		String msg = ReflectionUtil.getField(reply, "message", String.class);
		assertTrue(msg.startsWith("New War started. War Id: 1. Start Date: "));
	}
}
