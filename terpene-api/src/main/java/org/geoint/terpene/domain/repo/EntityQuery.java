/*
 * Copyright 2016 geoint.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geoint.terpene.domain.repo;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.geoint.terpene.domain.model.DataModel;

/**
 * Entity query builder.
 *
 * @author steve_siebert
 * @param <T> java representation of domain entity
 */
public interface EntityQuery<T> {

    EntityQuery<T> equalTo(String componentName, Object value);

    <V> EntityQuery<T> equalTo(DataModel<V> model, V value);

    EntityQuery<T> greaterThan(String componentName, Object value);

    <V> EntityQuery<T> greaterThan(DataModel<V> model, V value);

    EntityQuery<T> greaterThanOrEqual(String componentName, Object value);

    <V> EntityQuery<T> greaterThanOrEqual(DataModel<V> model, V value);

    EntityQuery<T> lessThan(String componentName, Object value);

    <V> EntityQuery<T> lessThan(DataModel<V> model, V value);

    EntityQuery<T> lessThanOrEqual(String componentName, Object value);

    <V> EntityQuery<T> lessThanOrEqual(DataModel<V> model, V value);

    EntityQuery<T> matches(String componentName, Pattern pattern);

    <V> EntityQuery<T> matches(DataModel<V> model, Pattern pattern);

    EntityQuery<T> sort(Comparator<T> sort);

    EntityQuery<T> filter(Predicate<T> filter);

    void forEach(Consumer<T> consumer);

    T first();

    Collection<T> get();

}
