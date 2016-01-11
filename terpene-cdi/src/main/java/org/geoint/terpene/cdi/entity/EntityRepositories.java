/*
 * Copyright 2015 geoint.org.
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
package org.geoint.terpene.cdi.entity;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.geoint.acetate.domain.entity.Entity;
import org.geoint.acetate.domain.entity.EntityRepository;

/**
 *
 * @author steve_siebert
 */
@ApplicationScoped
public class EntityRepositories {

    private Map<Class<?>, EntityRepository<?>> repositories;

    private final static Logger logger
            = Logger.getLogger(EntityRepositories.class.getName());

    @Produces
    public EntityRepository<Entity> repository() {
        logger.log(Level.INFO, "Produces for entity repository called.");
        return null;
    }

    void set(Map<Class<?>, EntityRepository<?>> repositories) {
        this.repositories = repositories;
    }
}
