
package org.geoint.terpene.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Configured annotation is added to a class definition to define the 
 * base configuration context for the object.
 * 
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Configured {

    /**
     * Defines the root namespace of the configuration names that are refernced
     * in the class.
     * <p>
     * For example:
     * {@code 
     * 
     * @Configured(namespace="my.config.")
     * public class Foo {
     * 
     *    @Inject @Config(name="exampleString")
     *    private String config1; //named config resolves to "my.config.exampleString"
     *    @Inject @Config
     *    private String config2; //unnamed config resolves to "my.config.config2" 
     * }
     * 
     * @return the root namespace of the configuration
     */
    String namespace() default "";
    
}
