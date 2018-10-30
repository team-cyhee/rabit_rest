package com.cyhee.rabit.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Password validation annotation
 * @{link #PasswordValidator}
 * @author chy
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Invalid Password!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    /**
     * 특수 문자가 필요한지 default false
     */
    boolean needSpecial() default false;
    
    
    /**
     * 대문자가 필요한지 default false
     */
    boolean needUpper() default false;
        
    /**
     * 최소길이 default 8
     */
    int min() default 8;
    
    /**
     * 최대길이 default 20
     */
    int max() default 20;
}
