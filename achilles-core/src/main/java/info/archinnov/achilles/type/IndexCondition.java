/*
 * Copyright (C) 2012-2014 DuyHai DOAN
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package info.archinnov.achilles.type;

import com.google.common.base.Objects;

public class IndexCondition {

	private final String columnName;

	private final IndexRelation indexRelation;

	private final Object columnValue;

	/**
	 * Shortcut constructor to build an EQUAL index condition
	 * 
	 * @param columnName
	 *            name of indexed column
	 * @param columnValue
	 *            value of indexed column
	 */
	public IndexCondition(String columnName, Object columnValue) {
		this.columnName = columnName;
		this.indexRelation = IndexRelation.EQUAL;
		this.columnValue = columnValue;
	}

	public String getColumnName() {
		return columnName;
	}

	public IndexRelation getIndexRelation() {
		return indexRelation;
	}

	public Object getColumnValue() {
		return columnValue;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(IndexCondition.class).add("columnName", columnValue)
				.add("columnValue", columnValue).add("index relation", indexRelation).toString();
	}

}
