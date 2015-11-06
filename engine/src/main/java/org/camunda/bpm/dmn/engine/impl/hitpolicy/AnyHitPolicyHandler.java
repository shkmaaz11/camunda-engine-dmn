/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.dmn.engine.impl.hitpolicy;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.dmn.engine.delegate.DmnDecisionTableEvaluationEvent;
import org.camunda.bpm.dmn.engine.delegate.DmnEvaluatedDecisionRule;
import org.camunda.bpm.dmn.engine.delegate.DmnEvaluatedOutput;
import org.camunda.bpm.dmn.engine.impl.DmnLogger;
import org.camunda.bpm.dmn.engine.impl.delegate.DmnDecisionTableEvaluationEventImpl;
import org.camunda.bpm.dmn.engine.impl.spi.hitpolicy.DmnHitPolicyHandler;

public class AnyHitPolicyHandler implements DmnHitPolicyHandler {

  public static final DmnHitPolicyLogger LOG = DmnLogger.HIT_POLICY_LOGGER;

  public DmnDecisionTableEvaluationEvent apply(DmnDecisionTableEvaluationEvent decisionTableEvaluationEvent) {
    List<DmnEvaluatedDecisionRule> matchingRules = decisionTableEvaluationEvent.getMatchingRules();

    if (!matchingRules.isEmpty()) {
      if (allOutputsAreEqual(matchingRules)) {
        DmnEvaluatedDecisionRule firstMatchingRule = matchingRules.get(0);
        ((DmnDecisionTableEvaluationEventImpl) decisionTableEvaluationEvent).setMatchingRules(Collections.singletonList(firstMatchingRule));
      } else {
        throw LOG.anyHitPolicyRequiresThatAllOutputsAreEqual(matchingRules);
      }
    }

    return decisionTableEvaluationEvent;
  }

  protected boolean allOutputsAreEqual(List<DmnEvaluatedDecisionRule> matchingRules) {
    Map<String, DmnEvaluatedOutput> firstOutputEntries = matchingRules.get(0).getOutputEntries();
    if (firstOutputEntries == null) {
      for (int i = 1; i < matchingRules.size(); i++) {
        if (matchingRules.get(i).getOutputEntries() != null) {
          return false;
        }
      }
    } else {
      for (int i = 1; i < matchingRules.size(); i++) {
        if (!firstOutputEntries.equals(matchingRules.get(i).getOutputEntries())) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public String toString() {
    return "AnyHitPolicyHandler{}";
  }

}
