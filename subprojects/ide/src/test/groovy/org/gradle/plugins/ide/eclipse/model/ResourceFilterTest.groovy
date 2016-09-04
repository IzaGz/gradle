/*
 * Copyright 2009 the original author or authors.
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
package org.gradle.plugins.ide.eclipse.model;


import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import spock.lang.Specification

public class ResourceFilterTest extends Specification {
    def "ResourceFilter equals and hashCode satisfies contract"() {
        when:
        EqualsVerifier.forClass(ResourceFilter.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withPrefabValues(Set.class, [new ResourceFilterMatcher('org.eclipse.ui.ide.multiFilter', '1.0-name-matches-false-false-node_modules', [] as LinkedHashSet), new ResourceFilterMatcher('org.eclipse.ui.ide.multiFilter', '1.0-name-matches-false-false-target', [] as LinkedHashSet)] as LinkedHashSet, [] as LinkedHashSet)
                .verify()

        then:
        noExceptionThrown()
    }
}