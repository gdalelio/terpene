package org.geoint.terpene.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.geoint.metamodel.annotation.Model;
import org.geoint.metamodel.annotation.NoArg;

/**
 * Annotates a terpene component method used to start the component.
 *
 * @author steve_siebert
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Model
@NoArg
public @interface Start {

}
