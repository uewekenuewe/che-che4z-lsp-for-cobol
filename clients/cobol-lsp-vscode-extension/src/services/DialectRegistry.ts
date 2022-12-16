/*
 * Copyright (c) 2022 Broadcom.
 * The term "Broadcom" refers to Broadcom Inc. and/or its subsidiaries.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Broadcom, Inc. - initial API and implementation
 */

import * as vscode from "vscode";
import { SETTINGS_DIALECT_REGISTRY } from "../constants";

/**
 * Holds information about registered dialect
 */
export type DialectInfo = {
    name: string;
    path: string;
    description: string;
    extensionId: string;
};

/**
 * DialectRegistry provides read/write dialect settings functionality
 */
 export class DialectRegistry {

    /**
     * Gets registered dialect infoes
     * @returns list of dialect info structures
     */
    public static getDialects(): DialectInfo[] {
        const result: DialectInfo[] = vscode.workspace.getConfiguration().get(SETTINGS_DIALECT_REGISTRY);
        if (result === undefined) {
            return [];
        }
        return result;
    }

    public static register(name: string, path: string, description: string, extensionId: string) {
        let dialects: DialectInfo[] = this.getDialects();
        const dialectInfo: DialectInfo = {
            name: name,
            path: path,
            description: description,
            extensionId: extensionId
        };
        dialects = dialects.filter(d => d.name !== name);
        dialects.push(dialectInfo);
        vscode.workspace.getConfiguration().update(SETTINGS_DIALECT_REGISTRY, dialects);
    }
 }
