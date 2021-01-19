/*
 * Copyright (c) 2020 Broadcom.
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

package com.broadcom.lsp.cobol.core.preprocessor.delegates.util;

import com.broadcom.lsp.cobol.core.model.variables.StructuredVariable;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Deque;
import java.util.Optional;

/** This util class applies operations over variable qualifiers */
@UtilityClass
public class VariableUtils {
  public static final String INTERMEDIATE_QUALIFIER_PLACEHOLDER = " .* ";
  public static final String QUALIFIER_PLACEHOLDER_PREFIX = ".* ";
  private static final String QUALIFIER_PARTS_DELIMITER = "  ";
  private static final String QUALIFIER_PREFIX = " ";

  /**
   * Change the qualifier of a renamed variable. Note: it is applicable only for RENAME clauses.
   *
   * @param qualifier - the qualifier of the renamed variable
   * @param renameItem - the name of the renaming item (level 66)
   * @return qualifier for a renamed variable
   */
  public @NonNull String renameQualifier(@NonNull String qualifier, @NonNull String renameItem) {
    return qualifier.replaceFirst("[^ ]+", renameItem);
  }

  /**
   * Create a qualifier for given variable name using the variable structure.
   *
   * @param structure - the structure of the variable
   * @param name - the name of this variable
   * @return qualifier for this variable
   */
  public @NonNull String createQualifier(
      @NonNull Deque<StructuredVariable> structure, @NonNull String name) {
    return Optional.ofNullable(structure.peekFirst())
            .map(it -> it.getQualifier() + QUALIFIER_PARTS_DELIMITER)
            .orElse(QUALIFIER_PREFIX)
        + name;
  }

  /**
   * Create a qualifier from the given name assuming an empty structure
   *
   * @param name - the name of this variable
   * @return qualifier for this variable
   */
  public @NonNull String createQualifier(@NonNull String name) {
    return QUALIFIER_PREFIX + name;
  }
}
