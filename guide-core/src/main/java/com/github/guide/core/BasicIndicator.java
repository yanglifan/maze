package com.github.guide.core;

import com.github.guide.core.processor.BasicIndicatorProcessor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@IndicatorType(processor = BasicIndicatorProcessor.class)
public @interface BasicIndicator {
    String paramPath();

    String targetValue();
}