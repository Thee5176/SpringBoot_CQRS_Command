package com.thee5176.ledger_command.record.application.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

@Documented
@Constraint(validatedBy = BalanceCheckValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface BalanceCheck {
	String message() default "検証エラー:借方と貸方の金額が一致していない。";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}