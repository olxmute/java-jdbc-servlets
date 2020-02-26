package com.jdbcservlets.server;

import com.jdbcservlets.BankApplication;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@UtilityClass
public class EmbeddedServer {

    private final String RUNNING_JAR_PATH = "/build/"; // "/target/" for maven
    private final String BUILD_CLASSES_PATH = "build/classes/java/main"; // "/target/classes" for maven

    private File getRootFolder() throws URISyntaxException {
        File root;
        String runningJarPath = BankApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
        int lastIndexOf = runningJarPath.lastIndexOf(RUNNING_JAR_PATH);
        if (lastIndexOf < 0) {
            root = new File("");
        } else {
            root = new File(runningJarPath.substring(0, lastIndexOf));
        }
        log.info("Application resolved root folder: {}", root.getAbsolutePath());
        return root;
    }

    public void start() {
        try {
            File root = getRootFolder();
            System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
            Tomcat tomcat = new Tomcat();
            Path tempPath = Files.createTempDirectory("tomcat-base-dir");
            tomcat.setBaseDir(tempPath.toString());

            //The port that we should run on can be set into an environment variable
            //Look for that variable and default to 8080 if it isn't there.
            String webPort = System.getenv("PORT");
            if (webPort == null || webPort.isEmpty()) {
                webPort = "8080";
            }

            tomcat.setPort(Integer.parseInt(webPort));
            File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
            if (!webContentFolder.exists()) {
                webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
            }
            StandardContext ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
            //Set execution independent of current thread context classloader (compatibility with exec:java mojo)
            ctx.setParentClassLoader(BankApplication.class.getClassLoader());

            log.info("Configuring app with basedir: {}", webContentFolder.getAbsolutePath());

            // Declare an alternative location for your "WEB-INF/classes" dir
            // Servlet 3.0 annotation will work
            File additionWebInfClassesFolder = new File(root.getAbsolutePath(), BUILD_CLASSES_PATH);
            WebResourceRoot resources = new StandardRoot(ctx);

            WebResourceSet resourceSet;
            if (additionWebInfClassesFolder.exists()) {
                resourceSet = new DirResourceSet(resources, "/webapp/WEB-INF/classes/java/main", additionWebInfClassesFolder.getAbsolutePath(), "/");
                log.info("Loading WEB-INF resources from as '{}'", additionWebInfClassesFolder.getAbsolutePath());
            } else {
                resourceSet = new EmptyResourceSet(resources);
            }
            resources.addPreResources(resourceSet);
            ctx.setResources(resources);

            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException | IOException | URISyntaxException | ServletException e) {
            log.error("Failed to start embedded server.", e);
        }
    }
}
