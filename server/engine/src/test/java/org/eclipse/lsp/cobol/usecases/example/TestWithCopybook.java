/*
 * Copyright (c) 2021 Broadcom.
 * The term "Broadcom" refers to Broadcom Inc. and/or its subsidiaries.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Broadcom, Inc. - initial API and implementation
 *
 */
package org.eclipse.lsp.cobol.usecases.example;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.eclipse.lsp.cobol.test.CobolText;
import org.eclipse.lsp.cobol.test.engine.UseCaseEngine;
import org.junit.jupiter.api.Test;

/** UseCase test example with copybooks */
class TestWithCopybook {
  private static final String TEXT =
      "       IDENTIFICATION DIVISION.\n"
          + "       PROGRAM-ID.    TEST12.\n"
          + "       ENVIRONMENT DIVISION.\n"
          + "       DATA DIVISION.\n"
          + "       WORKING-STORAGE SECTION.\n"
          + "         COPY {~THEBOOK}.\n"
          + "       Procedure Division.\n"
          + "           display {$VAR}.";

  public static final String TEXT2 =
      "       IDENTIFICATION DIVISION.\n"
          + "       PROGRAM-ID. TEST12.\n"
          + "       ENVIRONMENT DIVISION.\n"
          + "       DATA DIVISION.\n"
          + "        WORKING-STORAGE SECTION.\n"
          + "        01 {$*test1} pic x(9).\n"
          + "       PROCEDURE DIVISION.\n"
          + "       {#*bug-test}.\n"
          + "           move 0 to {$test1}.\n"
          + "       copy \n"
          + "             {~bug0}. .\n"
          + "       {#*bug-test2}.\n"
          + "           move 0 to {$test1}.\n";

  public static final String TEXT3 =
      "       IDENTIFICATION DIVISION.\n"
          + "       PROGRAM-ID. TEST12.\n"
          + "       ENVIRONMENT DIVISION.\n"
          + "       DATA DIVISION.\n"
          + "        WORKING-STORAGE SECTION.\n"
          + "        01 {$*test1} pic x(9).\n"
          + "       PROCEDURE DIVISION.\n"
          + "       {#*bug-test}.\n"
          + "           move 0 to {$test1}.\n"
          + "       copy {~bug0}. .\n"
          + "       {#*bug-test2}.\n"
          + "           move 0 to {$test1}.\n";

  public static final String TEXT4 =
          "       IDENTIFICATION DIVISION.\n"
                  + "       PROGRAM-ID. TEST12.\n"
                  + "       ENVIRONMENT DIVISION.\n"
                  + "       DATA DIVISION.\n"
                  + "        WORKING-STORAGE SECTION.\n"
                  + "        01 {$*test1} pic x(9).\n"
                  + "       PROCEDURE DIVISION.\n"
                  + "       {#*bug-test}.\n"
                  + "           move 0 to {$test1}.\n"
                  + "           display copy {~bug0}. .\n"
                  + "       {#*bug-test2}.\n"
                  + "           move 0 to {$test1}.\n";

  public static final String TEXT5 =
      "       IDENTIFICATION DIVISION.\n"
          + "       PROGRAM-ID. TEST12.\n"
          + "       ENVIRONMENT DIVISION.\n"
          + "       DATA DIVISION.\n"
          + "        WORKING-STORAGE SECTION.\n"
          + "        01 {$*test1} pic x(9).\n"
          + "       PROCEDURE DIVISION.\n"
          + "       {#*bug-test}.\n"
          + "           move 0 to {$test1}.\n"
          + "           display copy \n"
          + "             {~bug0}. .\n"
          + "       {#*bug-test2}.\n"
          + "           move 0 to {$test1}.\n";

  public static final String TEXT6 =
      "       IDENTIFICATION DIVISION.\n"
          + "       PROGRAM-ID. TEST12.\n"
          + "       ENVIRONMENT DIVISION.\n"
          + "       DATA DIVISION.\n"
          + "        WORKING-STORAGE SECTION.\n"
          + "        01 {$*test1} pic x(9).\n"
          + "       PROCEDURE DIVISION.\n"
          + "       {#*bug-test}.\n"
          + "           move 0 to {$test1}.\n"
          + "           REPLACE ==:TAG:== BY ==BUG0==. display copy {~:TAG:^BUG0}. .\n"
          + "       {#*bug-test2}.\n"
          + "           move 0 to {$test1}.";

  public static final String TEXT7 =
      "       IDENTIFICATION DIVISION.\n"
          + "       PROGRAM-ID. ABCDEF.\n"
          + "       DATA DIVISION.\n"
          + "       WORKING-STORAGE SECTION.\n"
          + "       copy {~AMAZE}.    \n"
          + "       copy {~AMAZE2}.      \n"
          + "       PROCEDURE DIVISION.\n";

  public static final String AMAZE_TEXT =
      "       01  {$*CHK-PARMS}.\n" + "P#1325         07  {$*CHECK}    PIC 9(09) COMP SYNC.\n";

  public static final String AMAZE2_TEXT =
      "001600 01  {$*META-TABLE}.\n"
          + "001700     05  {$*META-FIELD} OCCURS 12500 TIMES.\n"
          + "FMC    COPY {~AMAZE3}.\n"
          + "       01  {$*TEST-ULTI} pic x.";

  public static final String AMAZE3_TEXT =
      "\n" + "         10  {$*META-FIELD-NAME}               PIC  X(35).\n";

  private static final String COPYBOOK_CONTENT = "       01  {$*VAR}     PIC S9(4) COMP.";

  @Test
  void test() {
    UseCaseEngine.runTest(
        TEXT, ImmutableList.of(new CobolText("THEBOOK", COPYBOOK_CONTENT)), ImmutableMap.of());
  }

  @Test
  void testCopybookSubstitutionOnACobolLinePartially() {
    UseCaseEngine.runTest(
        TEXT2,
        ImmutableList.of(new CobolText("BUG0", "           PERFORM {#BUG-TEST}\n")),
        ImmutableMap.of());
  }

  @Test
  void testCopybookSubstitutionOnACobolLinePartially2() {
    UseCaseEngine.runTest(
            TEXT3,
            ImmutableList.of(new CobolText("BUG0", "           PERFORM {#BUG-TEST}\n")),
            ImmutableMap.of());
  }

  @Test
  void testCopybookSubstitutionOnACobolLinePartially3() {
    UseCaseEngine.runTest(
            TEXT4,
            ImmutableList.of(new CobolText("BUG0", "            \"scenario 4\"\n")),
            ImmutableMap.of());
  }

  @Test
  void testCopybookSubstitutionOnACobolLinePartially4() {
    UseCaseEngine.runTest(
        TEXT5,
        ImmutableList.of(new CobolText("BUG0", "            \"scenario 4\"\n")),
        ImmutableMap.of());
  }

  @Test
  void testCopybookSubstitutionOnACobolLinePartially5() {
    UseCaseEngine.runTest(
            TEXT6,
            ImmutableList.of(new CobolText("BUG0", "            \"scenario 4\"\n")),
            ImmutableMap.of());
  }

  @Test
  void testEmbeddedCopybooks() {
    UseCaseEngine.runTest(
        TEXT7,
        ImmutableList.of(
            new CobolText("AMAZE", AMAZE_TEXT),
            new CobolText("AMAZE2", AMAZE2_TEXT),
            new CobolText("AMAZE3", AMAZE3_TEXT)),
        ImmutableMap.of());
  }
}
