package nl.focalor.utobot.utopia.handler;

import nl.focalor.utobot.base.input.IResult;
import nl.focalor.utobot.base.input.Input;
import nl.focalor.utobot.base.input.ReplyResult;
import nl.focalor.utobot.base.input.handler.IRegexHandler;
import nl.focalor.utobot.base.model.entity.Person;
import nl.focalor.utobot.base.model.service.IPersonService;
import nl.focalor.utobot.utopia.model.AttackType;
import nl.focalor.utobot.utopia.model.entity.Attack;
import nl.focalor.utobot.utopia.service.IAttackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.regex.Matcher;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddAttackHandlerTest {
	@InjectMocks
	private AddAttackHandlerFactory handlerFactory;

	@Mock
	private IAttackService attackService;
	@Mock
	private IPersonService personService;

	@Before
	public void init() {
		AttackType type = new AttackType();
		type.setSyntax("Taking full control of your new land will take (\\d{1,2})\\.(\\d{1,2}) days");
		when(attackService.getKnownAttackTypes()).thenReturn(Arrays.asList(type));

		handlerFactory.init();
	}

//	@Test
//	public void matches() {
//		// Setup
//		Input input = new Input("user",
//				"recaptured 35 acres from our enemy! Taking full control of your new land will take 9.11 days. The new land wi");
//		IRegexHandler handler = handlerFactory.getRegexHandlers().get(0);
//
//		// Test
//		Matcher result = handler.getMatcher(input);
//
//		// Verify
//		assertNotNull(result);
//	}
//
//	@Test
//	public void handle() {
//		// Setup
//		Input input = new Input("piet",
//				"recaptured 35 acres from our enemy! Taking full control of your new land will take 9.11 days. The new land wi");
//		IRegexHandler handler = handlerFactory.getRegexHandlers().get(0);
//
//		// Test
//		IResult result = handler.handleInput(input);
//
//		// Verify
//		assertTrue(result instanceof ReplyResult);
//		assertEquals("Attack added for piet for 9.11 hours", ((ReplyResult) result).getMessage());
//
//		ArgumentCaptor<Attack> captor1 = ArgumentCaptor.forClass(Attack.class);
//		ArgumentCaptor<Boolean> captor2 = ArgumentCaptor.forClass(Boolean.class);
//		verify(attackService).create(captor1.capture(), captor2.capture());
//		assertEquals("piet", captor1.getValue().getPerson());
//		assertEquals(true, captor2.getValue());
//	}
//
//	@Test
//	public void handleKnownUser() {
//		// Setup
//		Input input = new Input("jan",
//				"recaptured 35 acres from our enemy! Taking full control of your new land will take 9.11 days. The new land wi");
//		Person p = new Person();
//		p.setId(234l);
//		when(personService.find("jan", true)).thenReturn(p);
//
//		IRegexHandler handler = handlerFactory.getRegexHandlers().get(0);
//
//		// Test
//		handler.handleInput(input);
//
//		// Verify
//		ArgumentCaptor<Attack> captor1 = ArgumentCaptor.forClass(Attack.class);
//		ArgumentCaptor<Boolean> captor2 = ArgumentCaptor.forClass(Boolean.class);
//		verify(attackService).create(captor1.capture(), captor2.capture());
//		assertEquals(Long.valueOf(234), captor1.getValue().getPersonId());
//		assertEquals(true, captor2.getValue());
//	}
}
