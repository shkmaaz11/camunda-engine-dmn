/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.dmn.engine.api;

import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.camunda.bpm.dmn.engine.test.asserts.DmnEngineTestAssertions.assertThat;
import static org.camunda.bpm.engine.variable.Variables.createVariables;
import static org.camunda.bpm.engine.variable.Variables.emptyVariableContext;

import java.io.InputStream;
import java.util.Map;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnEvaluationException;
import org.camunda.bpm.dmn.engine.impl.transform.DmnTransformException;
import org.camunda.bpm.dmn.engine.test.DecisionResource;
import org.camunda.bpm.dmn.engine.test.DmnEngineTest;
import org.camunda.bpm.engine.variable.context.VariableContext;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.commons.utils.IoUtil;
import org.junit.Test;

/**
 * Simple api test making sure the api methods are there and accept the right parameters
 *
 * @author Daniel Meyer
 *
 */
public class DmnEngineApiTest extends DmnEngineTest {

  public static final String ONE_RULE_DMN = "org/camunda/bpm/dmn/engine/api/OneRule.dmn";
  public static final String NOT_EXISTING_DMN = "not/existing/file.dmn";
  public static final String NOT_A_DMN_FILE = "org/camunda/bpm/dmn/engine/api/NotADmnFile.bpmn";

  public static final String INPUT_VALUE = "ok";
  public static final String EXPECTED_OUTPUT_VALUE = "ok";
  public static final String DECISION_KEY = "decision";

  @Test
  public void shouldFailParsingIfFilenameIsNull() {
    try{
      dmnEngine.parseDecisions((String) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.parseFirstDecision((String) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.parseDecision(DECISION_KEY, (String) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }
  }

  @Test
  public void shouldFailParsingIfFilenameDoesNotExist() {
    try{
      dmnEngine.parseDecisions(NOT_EXISTING_DMN);
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-01001");
    }

    try{
      dmnEngine.parseFirstDecision(NOT_EXISTING_DMN);
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-01001");
    }

    try{
      dmnEngine.parseDecision(DECISION_KEY, NOT_EXISTING_DMN);
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-01001");
    }
  }

  @Test
  public void shouldFailParsingIfFilenameIsInvalid() {
    try{
      dmnEngine.parseDecisions(NOT_A_DMN_FILE);
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.parseFirstDecision(NOT_A_DMN_FILE);
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.parseDecision(DECISION_KEY, NOT_A_DMN_FILE);
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }
  }

  @Test
  public void shouldFailParsingIfInputStreamIsNull() {
    try{
      dmnEngine.parseDecisions((InputStream) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.parseFirstDecision((InputStream) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.parseDecision(DECISION_KEY, (InputStream) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }
  }

  @Test
  public void shouldFailParsingIfInputStreamIsInvalid() {
    try{
      dmnEngine.parseDecisions(createInvalidInputStream());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.parseFirstDecision(createInvalidInputStream());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.parseDecision(DECISION_KEY, createInvalidInputStream());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }
  }

  @Test
  public void shouldFailParsingIfModelInstanceIsNull() {
    try{
      dmnEngine.parseDecisions((DmnModelInstance) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.parseFirstDecision((DmnModelInstance) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.parseDecision(DECISION_KEY, (DmnModelInstance) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }
  }

  @Test
  public void shouldFailParsingIfDecisionKeyIsNull() {
    try{
      dmnEngine.parseDecision(null, ONE_RULE_DMN);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.parseDecision(null, createInputStream());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }


    try{
      dmnEngine.parseDecision(null, createDmnModelInstance());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }
  }

  @Test
  public void shouldFailEvaluatingIfFilenameIsNull() {
    try{
      dmnEngine.evaluateFirstDecisionTable((String) null, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateFirstDecisionTable((String) null, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, (String) null, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, (String) null, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }
  }

  @Test
  public void shouldFailEvaluatingIfFilenameDoesNotExist() {
    try{
      dmnEngine.evaluateFirstDecisionTable(NOT_EXISTING_DMN, createVariables());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-01001");
    }

    try{
      dmnEngine.evaluateFirstDecisionTable(NOT_EXISTING_DMN, emptyVariableContext());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-01001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, NOT_EXISTING_DMN, createVariables());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-01001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, NOT_EXISTING_DMN, emptyVariableContext());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-01001");
    }
  }

  @Test
  public void shouldFailEvaluatingIfFilenameIsInvalid() {
    try{
      dmnEngine.evaluateFirstDecisionTable(NOT_A_DMN_FILE, createVariables());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.evaluateFirstDecisionTable(NOT_A_DMN_FILE, emptyVariableContext());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, NOT_A_DMN_FILE, createVariables());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, NOT_A_DMN_FILE, emptyVariableContext());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }
  }

  @Test
  public void shouldFailEvaluatingIfInputStreamIsNull() {
    try{
      dmnEngine.evaluateFirstDecisionTable((InputStream) null, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateFirstDecisionTable((InputStream) null, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, (InputStream) null, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, (InputStream) null, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }
  }

  @Test
  public void shouldFailEvaluatingIfInputStreamIsInvalid() {
    try{
      dmnEngine.evaluateFirstDecisionTable(createInvalidInputStream(), createVariables());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.evaluateFirstDecisionTable(createInvalidInputStream(), emptyVariableContext());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, createInvalidInputStream(), createVariables());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, createInvalidInputStream(), emptyVariableContext());
      failBecauseExceptionWasNotThrown(DmnTransformException.class);
    }
    catch (DmnTransformException e) {
      assertThat(e)
        .hasMessageContaining("DMN-02003");
    }
  }

  @Test
  public void shouldFailEvaluatingIfModelInstanceIsNull() {
    try{
      dmnEngine.evaluateFirstDecisionTable((DmnModelInstance) null, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateFirstDecisionTable((DmnModelInstance) null, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, (DmnModelInstance) null, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }

    try{
      dmnEngine.evaluateDecisionTable(DECISION_KEY, (DmnModelInstance) null, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch (IllegalArgumentException e) {
      assertThat(e)
        .hasMessageContaining("UTILS-02001");
    }
  }

  @Test
  public void shouldFailEvaluatingIfDecisionKeyIsNull() {
    try {
      dmnEngine.evaluateDecisionTable(null, ONE_RULE_DMN, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(null, ONE_RULE_DMN, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(null, createInputStream(), createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(null, createInputStream(), emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(null, createDmnModelInstance(), createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(null, createDmnModelInstance(), emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }
  }

  @Test
  public void shouldFailEvaluatingIfDecisionIsNull() {
    try {
      dmnEngine.evaluateDecisionTable(null, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(null, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }
  }

  @Test
  @DecisionResource(resource = ONE_RULE_DMN)
  public void shouldFailEvaluatingIfVariablesIsNull() {
    try {
      dmnEngine.evaluateFirstDecisionTable(ONE_RULE_DMN, (Map<String, Object>) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateFirstDecisionTable(createInputStream(), (Map<String, Object>) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateFirstDecisionTable(createDmnModelInstance(), (Map<String, Object>) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(DECISION_KEY, ONE_RULE_DMN, (Map<String, Object>) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(DECISION_KEY, createInputStream(), (Map<String, Object>) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(DECISION_KEY, createDmnModelInstance(), (Map<String, Object>) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(decision, (Map<String, Object>) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }
  }

  @Test
  @DecisionResource(resource = ONE_RULE_DMN)
  public void shouldFailEvaluatingIfVariableContextIsNull() {
    try {
      dmnEngine.evaluateFirstDecisionTable(ONE_RULE_DMN, (VariableContext) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateFirstDecisionTable(createInputStream(), (VariableContext) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateFirstDecisionTable(createDmnModelInstance(), (VariableContext) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(DECISION_KEY, ONE_RULE_DMN, (VariableContext) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(DECISION_KEY, createInputStream(), (VariableContext) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(DECISION_KEY, createDmnModelInstance(), (VariableContext) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }

    try {
      dmnEngine.evaluateDecisionTable(decision, (VariableContext) null);
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(IllegalArgumentException e) {
      assertThat(e).hasMessageStartingWith("UTILS-02001");
    }
  }

  @Test
  @DecisionResource(resource = ONE_RULE_DMN)
  public void shouldNotEvaluateDecisionWithEmptyVariableMap() {
    try {
      dmnEngine.evaluateDecisionTable(decision, createVariables());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(DmnEvaluationException e) {
      // expected
    }
  }

  @Test
  @DecisionResource(resource = ONE_RULE_DMN)
  public void shouldNotEvaluateDecisionWithEmptyVariableContext() {
    try {
      dmnEngine.evaluateDecisionTable(decision, emptyVariableContext());
      failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }
    catch(DmnEvaluationException e) {
      // expected
    }
  }

  @Test
  @DecisionResource(resource = ONE_RULE_DMN)
  public void shouldEvaluateDecisionWithVariableMap() {
    DmnDecisionTableResult results = dmnEngine.evaluateDecisionTable(decision, createVariables().putValue("input", INPUT_VALUE));
    assertThat(results)
      .hasSingleResult()
      .hasSingleEntry(EXPECTED_OUTPUT_VALUE);
  }

  @Test
  @DecisionResource(resource = ONE_RULE_DMN)
  public void shouldEvaluateDecisionWithVariableContext() {
    DmnDecisionTableResult results = dmnEngine.evaluateDecisionTable(decision, createVariables().putValue("input", INPUT_VALUE).asVariableContext());
    assertThat(results)
      .hasSingleResult()
      .hasSingleEntry(EXPECTED_OUTPUT_VALUE);
  }

  // helper ///////////////////////////////////////////////////////////////////

  protected InputStream createInputStream() {
    return IoUtil.fileAsStream(ONE_RULE_DMN);
  }

  protected InputStream createInvalidInputStream() {
    return IoUtil.fileAsStream(NOT_A_DMN_FILE);
  }

  protected DmnModelInstance createDmnModelInstance() {
    return Dmn.readModelFromStream(createInputStream());
  }

}
