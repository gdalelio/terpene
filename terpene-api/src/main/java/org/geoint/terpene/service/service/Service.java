package org.geoint.terpene.service.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.annotation.Model;
import org.geoint.terpene.component.TerpeneComponent;

/**
 * Identifies an interface/class as a managed terpene service.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@Model
public @interface Service {

    String name() default "";
    
    String version() default TerpeneComponent.DEFAULT_VERSION;

    String description() default "";

}
