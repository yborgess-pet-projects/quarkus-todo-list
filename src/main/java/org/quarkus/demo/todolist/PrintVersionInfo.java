package org.quarkus.demo.todolist;

import io.quarkus.runtime.Startup;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

public class PrintVersionInfo {

    private static final Logger LOG = Logger.getLogger(PrintVersionInfo.class);

    @ConfigProperty(name = "org.demo.todolist.app-version")
    String applicationVersion;

    @Startup
    public void init() {
        LOG.info("Application Version: " + applicationVersion);
    }
}
