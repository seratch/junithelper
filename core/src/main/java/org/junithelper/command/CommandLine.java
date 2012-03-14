/* 
 * Copyright 2009-2010 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package org.junithelper.command;

import org.junithelper.core.Version;
import org.junithelper.core.util.Stdout;

public class CommandLine {

    private CommandLine() {
    }

    public static void main(String[] args) throws Exception {

        Stdout.p("  _                         ");
        Stdout.p("   /   _  ._/_/_/_  /_  _  _");
        Stdout.p("(_//_// // / / //_'//_//_'/ ");
        Stdout.p("                   /        ");
        Stdout.p("JUnit Helper version " + Version.get());
        Stdout.p("");

        if (args != null && args.length > 0) {
            String command = args[0];
            if (command.equals("make")) {
                if (args.length < 2) {
                    Stdout.p("  junithelper make [baseDir/targetJavaFile]");
                } else {
                    String dirOrFile = args[1];
                    MakeTestCommand.main(new String[] { dirOrFile });
                }
            } else if (command.equals("force3")) {
                if (args.length < 2) {
                    Stdout.p("  junithelper force3 [baseDir]");
                } else {
                    String baseDir = args[1];
                    ForceJUnitVersion3Command.main(new String[] { baseDir });
                }
            } else if (command.equals("force4")) {
                if (args.length < 2) {
                    Stdout.p("  junithelper force4 [baseDir]");
                } else {
                    String baseDir = args[1];
                    ForceJUnitVersion4Command.main(new String[] { baseDir });
                }
            } else {
                Stdout.p("-- Invalid command or parameter.");
            }
            Stdout.p("");

        } else {

            Stdout.p("Usage:");
            Stdout.p("  junithelper [command] [arg1] [arg2]");
            Stdout.p("");
            Stdout.p("Commands:");
            Stdout.p("  junithelper make [baseDir/targetJavaFile]");
            Stdout.p("  junithelper force3 [baseDir/targetJavaFile]");
            Stdout.p("  junithelper force4 [baseDir/targetJavaFile]");
            Stdout.p("");
            Stdout.p("JVM Options:");
            Stdout.p("  -Djunithelper.configProperties=[filepath]");
            Stdout.p("");

        }
    }

}
