package com.cyhee.rabit.cmm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

import com.cyhee.rabit.exception.cmm.UnauthorizedException;
import com.cyhee.rabit.exception.cmm.UnsupportedContentException;
import com.cyhee.rabit.model.cmm.BaseEntity;
import com.cyhee.rabit.model.cmm.ContentStatus;
import com.cyhee.rabit.model.cmm.ContentType;
import com.cyhee.rabit.model.cmm.RadioStatus;
import com.cyhee.rabit.model.file.FileStatus;
import com.cyhee.rabit.model.user.User;
import com.cyhee.rabit.model.user.UserStatus;
import com.cyhee.rabit.service.cmm.ContentHelper;

public class CmmTestUtil {
	
	/**
	 * 익명, 다른 사용자, 작성자, 관리자 권한으로 update 요청을 test
	 * @param content - update에 사용될 객체
	 * @param idType - service에서 update시 이용되는 id의 type
	 * @param service - update 메소드를 가진 service
	 * @param fields - update하려는 필드들
	 */
	public static void updateWithAuthentication(BaseEntity content, Class<?> idType, Object service, String... fields) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String updateStr = updater(content);
		Method update = service.getClass().getMethod(updateStr, idType, content.getClass());
		
		Set<String> setters = Stream.of(fields)
				.map(field -> setter(field))
				.collect(Collectors.toSet());
		
		Method[] methods = content.getClass().getMethods();
		List<Method> selectedMethod = new ArrayList<>();
		for(Method method : methods) {
			if(setters.contains(method.getName()))
				selectedMethod.add(method);
		}
		
		if(selectedMethod.size() != fields.length)
			throw new IllegalArgumentException("Unsupported field in parameters.");
		
		Constructor<? extends Object> constructor = content.getClass().getConstructor();
		Object form = constructor.newInstance();
		
		User owner = ContentHelper.getOwner(content);
		System.out.println(form);
		System.out.println(content);
		System.out.println(service.getClass());
		// anonymous
		AuthTestUtil.setAnonymous();
		assertThatThrownBy(() -> {
			try {
				update.invoke(service, content.getId(), form);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}).isInstanceOf(UnauthorizedException.class);//.isInstanceOf(UnauthorizedException.class);
		
		// other user
		AuthTestUtil.setPrincipal(owner.getUsername() + "X");
		assertThatThrownBy(() -> {
			try {
				update.invoke(service, content.getId(), form);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}).isInstanceOf(UnauthorizedException.class);

		Map<String, Object> fieldMap = null;
		
		// self
		fieldMap = setRandom(form, selectedMethod);
		AuthTestUtil.setPrincipal(owner.getUsername());
		update.invoke(service, content.getId(), form);
		fieldsChecker(content, fieldMap);
		
		// admin
		fieldMap = setRandom(form, selectedMethod);
		AuthTestUtil.setAdmin();
		update.invoke(service, content.getId(), form);
		fieldsChecker(content, fieldMap);
	}
	
	/**
	 * 익명, 다른 사용자, 작성자, 관리자 권한으로 delete 요청을 test
	 * @param content - delete에 사용될 객체
	 * @param idType - service에서 delete시 이용되는 id의 type
	 * @param service - delete 메소드를 가진 service
	 */
	public static void deleteWithAuthentication(BaseEntity content, Class<?> idType, Object service) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String deleteStr = deleter(content);
		Method delete = service.getClass().getMethod(deleteStr, idType);
		
		User owner = ContentHelper.getOwner(content);
		
		Map<String, Object> fieldMap = new HashMap<>();
		Object deleted = deletedStatus(content);
		fieldMap.put("status", deleted);

		// anonymous
		AuthTestUtil.setAnonymous();
		assertThatThrownBy(() -> {
			try {
				delete.invoke(service, content.getId());
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}).isInstanceOf(UnauthorizedException.class);

		// other user
		AuthTestUtil.setPrincipal(owner.getUsername()+"X");
		assertThatThrownBy(() -> {
			try {
				delete.invoke(service, content.getId());
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}).isInstanceOf(UnauthorizedException.class);
		
		// self
		AuthTestUtil.setPrincipal(owner.getUsername());
		delete.invoke(service, content.getId());
		fieldsChecker(content, fieldMap);
		
		Method setStatus = content.getClass()
				.getMethod(setter("status"), deleted.getClass());
		Method getStatus = content.getClass()
				.getMethod(getter("status"));
		
		while(getStatus.invoke(content).equals(deleted))
			setRandom(content, Arrays.asList(setStatus));
		
		// admin
		AuthTestUtil.setAdmin();
		delete.invoke(service, content.getId());
		fieldsChecker(content, fieldMap);
	}	

	/**
	 * 요청된 content에 대하여 deleted 상태의 객체를 반환
	 */
	public static Object deletedStatus(Object content) {
		ContentType type = ContentType.findByKey(content.getClass());
		switch(type) {
			case USER: return UserStatus.DELETED;
			case LIKE:
			case FOLLOW: return RadioStatus.INACTIVE;
			case COMMENT:
			case GOALLOG:
			case GOAL: return ContentStatus.DELETED;
			case FILE: return FileStatus.DELETED;
		}
		throw new UnsupportedContentException();
	}
	
	private static String getter(String field) {
		return "get" + StringUtils.capitalize(field);
	}
	
	private static String setter(String field) {
		return "set" + StringUtils.capitalize(field);
	}
	
	private static String deleter(Object content) {
		return "delete" + content.getClass().getSimpleName();
	}
	
	private static String updater(Object content) {
		return "update" + content.getClass().getSimpleName();
	}
	
	private static Object randomValue(Class<?> clazz) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Random random = new Random();
		if (clazz.equals(Date.class)) return new Date(random.nextLong());
		if (clazz.equals(Integer.class) || clazz.equals(int.class)) return new Integer(random.nextInt());
		if (clazz.equals(Long.class) || clazz.equals(long.class)) return new Long(random.nextLong());
		if (clazz.equals(String.class)) return new String(String.valueOf(random.nextInt()));
		if (clazz.isEnum()) {
			Method values = clazz.getMethod("values");
			Object[] candidates = (Object[])values.invoke(null);
			int index = random.nextInt(candidates.length);
			return candidates[index];
		}

		throw new IllegalArgumentException("Unsupported class");
	}
	
	/**
	 * content와 fieldMap의 정보가 일치하는지 test 
	 * @param content - test 하려는 content
	 * @param fieldMap - field의 name을 key, value를 값으로 가진 Map
	 */
	private static void fieldsChecker(Object content, Map<String, Object> fieldMap) {
		for(String field : fieldMap.keySet()) {
			assertThat(content).hasFieldOrPropertyWithValue(field, fieldMap.get(field));
		}
	}
	
	private static Map<String, Object> setRandom(Object content, List<Method> methods) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Map<String, Object> fieldMap = new HashMap<>();
		for(Method method : methods) {
			// setter has only one parameter
			Class<?> fieldType = method.getParameterTypes()[0];
			Object val = randomValue(fieldType);
			method.invoke(content, val);
			fieldMap.put(StringUtils.uncapitalize(method.getName().substring(3)), val);
		}
		return fieldMap;
	}
}
