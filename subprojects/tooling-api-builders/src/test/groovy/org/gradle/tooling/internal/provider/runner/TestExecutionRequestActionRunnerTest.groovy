/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.tooling.internal.provider.runner
import org.gradle.internal.invocation.BuildAction
import org.gradle.internal.invocation.BuildController
import spock.lang.Specification

class TestExecutionRequestActionRunnerTest extends Specification {

    def "does not handle non TestExecutionRequestAction"(){
        given:
        def runner = new TestExecutionRequestActionRunner()
        BuildAction buildAction = Mock(BuildAction)
        BuildController buildController= Mock(BuildController)
        when:
        runner.run(buildAction, buildController)
        then:
        0 * buildController._
        0 * buildAction._
    }
}
