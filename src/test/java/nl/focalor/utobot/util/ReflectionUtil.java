package nl.focalor.utobot.util;

import java.lang.reflect.Field;

public class ReflectionUtil {
	@SuppressWarnings("unchecked")
	public static <T> T getField(Object input, String fieldName, Class<T> expectedClass) {
		try {
			Field field = input.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return (T) field.get(input);
		} catch (Exception ex) {
			throw new ReflectionException(ex);
		}
	}

	private static class ReflectionException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ReflectionException(Exception ex) {
			super(ex);
		}
	}
}
