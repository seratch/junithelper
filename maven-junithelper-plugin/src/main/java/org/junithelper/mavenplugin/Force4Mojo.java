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
		printLogoAndVersion();
		String target = System.getProperty("target");
		try {
			ForceJUnitVersion4Command.config = loadConfig();
			ForceJUnitVersion4Command.main(new String[] { target });
		} catch (Exception e) {
			throw new MojoExecutionException("junithelper force4 error!", e);
		}
	}

}
