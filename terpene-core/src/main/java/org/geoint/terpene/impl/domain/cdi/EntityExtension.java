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
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import org.geoint.terpene.domain.DomainEntity;
import org.geoint.terpene.domain.DomainException;
import org.geoint.terpene.domain.Entity;
import org.geoint.terpene.domain.NotDomainClassException;
import org.geoint.terpene.domain.UnknownComponentException;
import org.geoint.terpene.domain.model.DomainModel;
import org.geoint.terpene.domain.model.InvalidDomainException;
import org.geoint.terpene.domain.repo.EntityRepository;
import org.geoint.terpene.impl.domain.Domains;

/**
 * CDI portable extension for acetate entity management.
 *
 * @author steve_siebert
 */
public class EntityExtension implements Extension {

    private static final Domains domains = Domains.INSTANCE;
    
    private static final Logger LOGGER
            = Logger.getLogger(EntityExtension.class.getName());
    
    void beforeBeanDiscovery(@Observes BeforeBeanDiscovery event) {
       
        //verify domain models have been loaded
        try {
            DomainModel terpeneDM = domains.getModel(DomainEntity.class);
        } catch (DomainException  ex) {
            LOGGER.log(Level.SEVERE, "Unable to load domains via CDI; terpene "
                    + "domain metamodel could not be found.", ex);
            return;
        } 
        
//        Optional<DomainType<EntityRepository>> repos
//                = acetate.get().getType(EntityRepository.class);
//        if (!repos.isPresent()) {
//            LOGGER.log(Level.SEVERE, "EntityRepository domain type was not found "
//                    + "in acetate domain.");
//        }
//        LOGGER.log(Level.INFO, "Found the following entity "
//                + "repository instances: {0}",
//                repos.get().getSubclasses().stream()
//                .map(DomainType::getTypeClass)
//                .map(Class::getCanonicalName)
//                .collect(Collectors.joining(",")));
    }

//    <T> void processEntities(
//            @Observes @WithAnnotations(Entity.class) ProcessAnnotatedType<T> annotatedType) {
//        //register each entity with the repository map, allowing us to ensure 
//        //each discovered entity has a registry
//        repositories.put(annotatedType.getAnnotatedType().getJavaClass(), null);
//    }

//    void afterDeploymentValidation(@Observes AfterDeploymentValidation abv, 
//            BeanManager bm) {
//          Iterator<Bean<?>> iter = bm.getBeans(EntityRepositories.class).iterator();
//        if (!iter.hasNext()) {
//            throw new IllegalStateException("Unable to resolve ConfigService implementation");
//        }
//        Bean<EntityRepositories> bean = (Bean<EntityRepositories>) iter.next();
//        CreationalContext<EntityRepositories> ctx = bm.createCreationalContext(bean);
//        EntityRepositories er = (EntityRepositories) bm.getReference(bean, EntityRepositories.class, ctx);
//
//        er.set(repositories);
//    }

}
