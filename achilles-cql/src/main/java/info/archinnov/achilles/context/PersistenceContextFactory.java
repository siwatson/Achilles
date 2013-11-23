/**
 *
 * Copyright (C) 2012-2013 DuyHai DOAN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.archinnov.achilles.context;

import info.archinnov.achilles.entity.metadata.EntityMeta;
import info.archinnov.achilles.entity.metadata.PropertyMeta;
import info.archinnov.achilles.entity.operations.EntityProxifier;
import info.archinnov.achilles.proxy.ReflectionInvoker;
import info.archinnov.achilles.type.ConsistencyLevel;
import info.archinnov.achilles.type.Options;
import info.archinnov.achilles.type.OptionsBuilder;
import info.archinnov.achilles.validation.Validator;

import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

public class PersistenceContextFactory {

	public static final Optional<Integer> NO_TTL = Optional.<Integer> absent();

	private DaoContext daoContext;
	private ConfigurationContext configContext;
	private Map<Class<?>, EntityMeta> entityMetaMap;
	private EntityProxifier proxifier = new EntityProxifier();
	private ReflectionInvoker invoker = new ReflectionInvoker();

	public PersistenceContextFactory(DaoContext daoContext, ConfigurationContext configContext,
                                     Map<Class<?>, EntityMeta> entityMetaMap) {
		this.daoContext = daoContext;
		this.configContext = configContext;
		this.entityMetaMap = entityMetaMap;
	}

	public PersistenceContext newContext(Object entity, Options options) {
		ImmediateFlushContext flushContext = buildImmediateFlushContext(options);
		return newContextWithFlushContext(entity, options, flushContext);
	}

    public PersistenceContext newContextWithFlushContext(Object entity, Options options,AbstractFlushContext flushContext) {
		Validator.validateNotNull(entity, "entity should not be null for persistence context creation");
		Class<?> entityClass = proxifier.deriveBaseClass(entity);
		EntityMeta meta = entityMetaMap.get(entityClass);
		return new PersistenceContext(meta, configContext, daoContext, flushContext, entity, options);
	}

	public PersistenceContext newContext(Object entity) {
		return newContext(entity, OptionsBuilder.noOptions());
	}

	public PersistenceContext newContext(Class<?> entityClass, Object primaryKey, Options options) {
		ImmediateFlushContext flushContext = buildImmediateFlushContext(options);

		return newContextWithFlushContext(entityClass,primaryKey,options,flushContext);
	}

	public PersistenceContext newContextWithFlushContext(Class<?> entityClass, Object primaryKey, Options options,AbstractFlushContext flushContext) {
		Validator.validateNotNull(entityClass, "entityClass should not be null for persistence context creation");
		Validator.validateNotNull(primaryKey, "primaryKey should not be null for persistence context creation");
		EntityMeta meta = entityMetaMap.get(entityClass);
		return new PersistenceContext(meta, configContext, daoContext, flushContext, entityClass, primaryKey,
				options);
	}

	public PersistenceContext newContextForSliceQuery(Class<?> entityClass, List<Object> partitionComponents,
			ConsistencyLevel cl) {
		EntityMeta meta = entityMetaMap.get(entityClass);
		PropertyMeta idMeta = meta.getIdMeta();
		Object embeddedId = invoker.instanciateEmbeddedIdWithPartitionComponents(idMeta, partitionComponents);

		ImmediateFlushContext flushContext = buildImmediateFlushContext(OptionsBuilder.withConsistency(cl));

		return new PersistenceContext(meta, configContext, daoContext, flushContext, entityClass, embeddedId,
				OptionsBuilder.withConsistency(cl));
	}

	private ImmediateFlushContext buildImmediateFlushContext(Options options) {
		return new ImmediateFlushContext(daoContext, options.getConsistencyLevel().orNull());
	}
}