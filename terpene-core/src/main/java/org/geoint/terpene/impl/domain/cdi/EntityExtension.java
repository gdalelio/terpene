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
package org.geoint.terpene.impl.domain.cdi;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import org.geoint.acetate.domain.DomainComponent;
import org.geoint.acetate.domain.DomainModel;
import org.geoint.acetate.domain.DomainModelException;
import org.geoint.acetate.domain.DomainModels;
import org.geoint.acetate.domain.DomainType;
import org.geoint.acetate.domain.entity.Entity;
import org.geoint.acetate.domain.entity.EntityRepository;

/**
 * CDI portable extension for acetate entity management.
 *
 * @author steve_siebert
 */
public class EntityExtension implements Extension {

    private Map<Class<?>, EntityRepository<?>> repositories;
    private static final Logger logger
            = Logger.getLogger(EntityExtension.class.getName());

    void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event) {
        DomainModels models;
        try {
            models = DomainModels.loadModels();
        } catch (DomainModelException ex) {
            logger.log(Level.SEVERE, "Acetate domain could not be loaded, "
                    + "unable to resolve entity repositories.", ex);
            return;
        }

        Optional<DomainModel> acetate = models.getModel(DomainComponent.ACETATE_DOMAIN_MODEL,
                DomainComponent.ACETATE_DOMAIN_VERSION);
        if (!acetate.isPresent()) {
            logger.log(Level.SEVERE, "Acetate domain model not found.");
            return;
        }

        Optional<DomainType<EntityRepository>> repos
                = acetate.get().getType(EntityRepository.class);
        if (!repos.isPresent()) {
            logger.log(Level.SEVERE, "EntityRepository domain type was not found "
                    + "in acetate domain.");
        }
        logger.log(Level.INFO, "Found the following entity "
                + "repository instances: {0}",
                repos.get().getSubclasses().stream()
                .map(DomainType::getTypeClass)
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(",")));
    }

    <T> void processEntities(
            @Observes @WithAnnotations(Entity.class) ProcessAnnotatedType<T> annotatedType) {
        //register each entity with the repository map, allowing us to ensure 
        //each discovered entity has a registry
        repositories.put(annotatedType.getAnnotatedType().getJavaClass(), null);
    }

    void afterDeploymentValidation(@Observes AfterDeploymentValidation abv, 
            BeanManager bm) {
          Iterator<Bean<?>> iter = bm.getBeans(EntityRepositories.class).iterator();
        if (!iter.hasNext()) {
            throw new IllegalStateException("Unable to resolve ConfigService implementation");
        }
        Bean<EntityRepositories> bean = (Bean<EntityRepositories>) iter.next();
        CreationalContext<EntityRepositories> ctx = bm.createCreationalContext(bean);
        EntityRepositories er = (EntityRepositories) bm.getReference(bean, EntityRepositories.class, ctx);

        er.set(repositories);
    }

}
