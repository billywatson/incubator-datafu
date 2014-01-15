/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package datafu.test.pig.sampling;

import org.adrianwalker.multilinestring.Multiline;
import org.apache.pig.pigunit.PigTest;
import org.testng.annotations.Test;

import datafu.pig.sampling.SimpleRandomSampleWithReplacementElect;
import datafu.pig.sampling.SimpleRandomSampleWithReplacementVote;
import datafu.test.pig.PigTests;

/**
 * Tests for {@link SimpleRandomSampleWithReplacementVote} and
 * {@link SimpleRandomSampleWithReplacementElect}.
 * 
 * @author ximeng
 * 
 */
public class SimpleRandomSampleWithReplacementTest extends PigTests
{
  // @formatter:off
  /**
   * register $JAR_PATH
   * 
   * DEFINE SRSWR_VOTE datafu.pig.sampling.SimpleRandomSampleWithReplacementVote();
   * 
   * DEFINE SRSWR_ELECT datafu.pig.sampling.SimpleRandomSampleWithReplacementElect();
   * 
   * item = LOAD 'input' AS (A_id:chararray, B_id:chararray, C:int); 
   * 
   * summary = FOREACH (GROUP item ALL) GENERATE COUNT(item) AS count;
   * 
   * candidates = FOREACH (GROUP item ALL) GENERATE FLATTEN(SRSWR_VOTE(item, $SAMPLE_SIZE, summary.count));
   * 
   * sampled = FOREACH (GROUP candidates BY position) GENERATE FLATTEN(SRSWR_ELECT(candidates));
   * 
   * sampled = FOREACH (GROUP sampled ALL) GENERATE COUNT(sampled) AS size;
   * 
   * STORE sampled INTO 'output';
   */
  @Multiline
  private String simpleRandomSampleWithReplacementTest;
  // @formatter:on

  @Test
  public void testSimpleRandomSampleWithReplacement() throws Exception
  {
    writeLinesToFile("input",
                     "A1\tB1\t1",
                     "A1\tB1\t4",
                     "A1\tB3\t4",
                     "A1\tB4\t4",
                     "A2\tB1\t4",
                     "A2\tB2\t4",
                     "A3\tB1\t3",
                     "A3\tB1\t1",
                     "A3\tB3\t77",
                     "A4\tB1\t3",
                     "A4\tB2\t3",
                     "A4\tB3\t59",
                     "A4\tB4\t29",
                     "A5\tB1\t4",
                     "A6\tB2\t3",
                     "A6\tB2\t55",
                     "A6\tB3\t1",
                     "A7\tB1\t39",
                     "A7\tB2\t27",
                     "A7\tB3\t85",
                     "A8\tB1\t4",
                     "A8\tB2\t45",
                     "A9\tB3\t92",
                     "A9\tB3\t0",
                     "A9\tB6\t42",
                     "A9\tB5\t1",
                     "A10\tB1\t7",
                     "A10\tB2\t23",
                     "A10\tB2\t1",
                     "A10\tB2\t31",
                     "A10\tB6\t41",
                     "A10\tB7\t52");

    int s = 32;
    PigTest test =
        createPigTestFromString(simpleRandomSampleWithReplacementTest, "SAMPLE_SIZE=" + s
            + "L");

    test.runScript();

    assertOutput(test, "sampled", "(" + s + ")");
  }
}
