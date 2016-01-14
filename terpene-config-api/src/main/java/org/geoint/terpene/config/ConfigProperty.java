package org.geoint.terpene.config;

import java.util.Collection;
import org.geoint.terpene.config.store.ConfigStore;
import org.geoint.util.hierarchy.HierarchicalVisitor;

/**
 * Thread-safe hierarchical multivalued configuration property from a
 * {@link ConfigStore}.
 *
 * <b>NOTE:</b> A ConfigProperty is associated with the ConfigStore from which
 * it was retrieved. A ConfigProperty instance may, or may not, be the
 * <i>derived</i> property (the property that will be injected into a
 * component), depending on how it was retrieved from the ConfigService. If in
 * doubt, use the {@link #isDerived() } method to determine if the property is
 * the derived configuration.
 *
 *
 * @param <T> configuration property value type
 * @see ConfigPropertyArray
 */
public interface ConfigProperty<T> {

    /**
     * Unique name of the property.
     *
     * The configuration property name is used to map configuration values to
     * properties and must be unique in that the configuration name used in one
     * place in a component will be the same value in another part of the
     * component.
     *
     * Configuration property names should be namespaced, using a dot (.) to
     * separate the hierarchical nodes, and managed by an organization, allowing
     * configuration properties that can be shared between component instances
     * to be defined just once.
     *
     * @return the unique name of the configuration property
     */
    String getName();

    /**
     * Return the priority value of the {@link ConfigStore} from which the value
     * of this property is derived.
     *
     * @return
     */
    int getPriority();

    /**
     * Returns if this property instance is the <i>derived</i> property
     * instance.
     *
     * A <i>derived</i> property is a special designation which indicates that
     * the property state (value, priority, descendents) will always be the
     * "correct" value based on the current configuration context (as known by
     * terpene). Configuration properties that are not designated as derived do
     * not necessarily contain invalid information but they are not guaranteed
     * to always return the correct state.
     *
     * @return true if this is the derived instance, false otherwise
     */
    boolean isDerived();

    /**
     * Returns a collection of values for the property.
     * <p>
     * The collection is immutable, as are the objects within the collection.
     *
     * @return returns an immutable collection of values or an empty collection
     * if no values were set
     */
    public Collection<T> getValues();

    /**
     * Return any configuration properties that are hierarchical children of
     * this property.
     *
     * @return an immutable collection containing zero or more child properties;
     * if no children, returns an empty collection
     */
    Collection<ConfigProperty<?>> getChildren();

    /**
     * Visits the descendent configuration properties of this property.
     *
     * The first node visited is this node.
     *
     * @param visitor
     */
    void visit(HierarchicalVisitor visitor);
}
