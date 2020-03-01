package com.jdbcservlets.server;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmbeddedServer {

    private static final EmbeddedServer SERVER = new EmbeddedServer();

    public static void start(Class<?> mainClass) {
        SERVER.launchServer(mainClass);
    }

    private void launchServer(Class<?> mainClass) {
        try {
            System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");

            Path tempPath = Files.createTempDirectory("tomcat-base-dir");

            Tomcat tomcat = new Tomcat();
            tomcat.setBaseDir(tempPath.toString());
            tomcat.setPort(Integer.parseInt(getPort()));

            File root = getRootFolder();
            File webContentFolder = getWebContentFolder(root);
            StandardContext ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());
            //Set execution independent of current thread context classloader (compatibility with exec:java mojo)
            ctx.setParentClassLoader(mainClass.getClassLoader());

            log.info("Configuring app with basedir: {}", webContentFolder.getAbsolutePath());

            // Declare an alternative location for your "WEB-INF/classes" dir
            // Servlet 3.0 annotation will work
            File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");
            WebResourceRoot resources = new StandardRoot(ctx);
            WebResourceSet resourceSet = getWebResourceSet(additionWebInfClassesFolder, resources);
            resources.addPreResources(resourceSet);
            ctx.setResources(resources);

            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException | IOException | URISyntaxException | ServletException e) {
            log.error("Failed to start embedded server.", e);
        }
    }

    private WebResourceSet getWebResourceSet(File additionWebInfClassesFolder, WebResourceRoot resources) {
        WebResourceSet resourceSet;
        if (additionWebInfClassesFolder.exists()) {
            resourceSet = new DirResourceSet(resources, "/WEB-INF/classes/", additionWebInfClassesFolder.getAbsolutePath(), "/");
            log.info("Loading WEB-INF resources from as '{}'", additionWebInfClassesFolder.getAbsolutePath());
        } else {
            resourceSet = new EmptyResourceSet(resources);
        }
        return resourceSet;
    }

    private File getWebContentFolder(File root) throws IOException {
        File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
        if (!webContentFolder.exists()) {
            webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
        }
        return webContentFolder;
    }

    //The port that we should run on can be set into an environment variable
    //Look for that variable and default to 8080 if it isn't there.
    private String getPort() {
        String webPort = System.getenv("PORT");
        if (isEmpty(webPort)) {
            webPort = "8080";
        }
        return webPort;
    }

    private File getRootFolder() throws URISyntaxException {
        File root;
        String runningJarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
        int lastIndexOf = runningJarPath.lastIndexOf("/target/");
        if (lastIndexOf < 0) {
            root = new File("");
        } else {
            root = new File(runningJarPath.substring(0, lastIndexOf));
        }
        log.info("Application resolved root folder: {}", root.getAbsolutePath());
        return root;
    }

}
