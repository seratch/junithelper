package org.junithelper.mavenplugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.junithelper.command.ForceJUnitVersion4Command;

/**
 * @goal force4
 * @phase process-sources
 */
public class Force4Mojo extends AbstractJUnitHelperMojo {

    @Override
    public void execute() throws MojoExecutionException {

        try {
            ForceJUnitVersion4Command.config = loadConfig();
            ForceJUnitVersion4Command.main(new String[]{});
        } catch (Exception e) {
            throw new MojoExecutionException("junithelper force4 error!", e);
        }
    }

}
