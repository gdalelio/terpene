package org.geoint.terpene.config.cdi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * CDI injection qualifier used to inject configuration properties.
 *
 */
@Qualifier
@Documented
@Target({ElementType.TYPE,
    ElementType.METHOD,
    ElementType.FIELD,
    ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     * Global configuration property name.
     *
     * <p>
     * If not named, the configuration name is the concatenated fully qualified
     * field name (example: {@code foo.Bar#baz})
     *
     * <p>
     * Configuration names are managed hierarchically, defined with a dot (.)
     * separator, and the value(s) of a configuration property available to a
     * component at runtime is determined by the tier that the configuration
     * value is assigned:
     *
     * <ol>
     * <li>Default configuration, defined in code, is overridden by terpene-wide
     * configuration value.</li>
     * <li>Terpene-wide configuration values are overridden by configuration
     * values defined for a specific terpene node.</li>
     * <li>Terpene-node specific configuration is overridden by component-
     * instance defined configuration values.</li>
     * </ol>
     *
     * A dot (.) is used to as a hierarchical separator and the the namespace
     * prefix "terpene." is reserved.
     *
     * @return the unique configuration property name
     */
    String name() default "";

    /**
     * Indicates if a value is required for configuration to be valid for the
     * application.
     *
     * <p>
     * Depending on settings, and optional extensions, terpene config can
     * prevent and application from initializing, or only work in a limited
     * capacity, if configuration is incomplete.
     *
     * @return true if a configuration value must be set for the component to
     * deploy
     */
    @Nonbinding
    boolean required() default true;

    /**
     * Optional usage description that explains how the configuration property
     * is used in the specific point of use.
     *
     * @return
     */
    @Nonbinding
    String usage() default "";

    /**
     * Optional attribute that will be used as the value if no configuration
     * value is set.
     *
     * If no value is available in the store, an empty string for this attribute
     * (the default) will result in no value being injected, allowing any value
     * that may have been set during initialization to remain.
     *
     * @return the stringified default value
     */
    @Nonbinding
    String defaultValue() default "";

}
