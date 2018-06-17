package com.cyhee.rabit.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Email;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.Getter;
import lombok.Setter;

@RunWith(SpringRunner.class)
public class ValidTest {
	private Validator validator;
	
	@Getter @Setter
	private static class PasswordSample {
		@Password(needSpecial=true)
		String password;
	}
	
	@Getter @Setter
	private static class EmailSample {
		@Email
		String email;
	}
	
	@Before
	public void setup() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}
	
	@Test
	public void PasswordValid() {
		PasswordSample sample = new PasswordSample();

		Set<ConstraintViolation<PasswordSample>> violations = validator.validate(sample);
		assertThat(violations.size()).isPositive();
		
		sample.setPassword("password1");
		violations = validator.validate(sample);
		assertThat(violations.size()).isPositive();
		
		sample.setPassword("#pa3123asd");
		violations = validator.validate(sample);
		assertThat(violations.size()).isZero();
		
		sample.setPassword("#pa31");
		violations = validator.validate(sample);
		assertThat(violations.size()).isPositive();
	}
	
	@Test
	public void EmailValid() {
		EmailSample sample = new EmailSample();
		
		Set<ConstraintViolation<EmailSample>> violations = validator.validate(sample);
		assertThat(violations.size()).isZero();
		
		sample.setEmail("wjiaos");
		violations = validator.validate(sample);
		assertThat(violations.size()).isPositive();
		
		sample.setEmail("ajif@na.co");
		violations = validator.validate(sample);
		assertThat(violations.size()).isZero();
	}
}
