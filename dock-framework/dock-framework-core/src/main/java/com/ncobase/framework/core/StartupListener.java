package com.ncobase.framework.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Application Startup Listener
 */
@Slf4j
@Configuration
public class StartupListener implements org.springframework.context.ApplicationListener<ApplicationStartedEvent> {

    @Value("${spring.application.name:unknown}")
    private String applicationName;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Wait for the service to fully start
                TimeUnit.SECONDS.sleep(1);

                Environment env = event.getApplicationContext().getEnvironment();
                String port = env.getProperty("server.port", "8080");
                String contextPath = env.getProperty("server.servlet.context-path", "");
                String hostAddress = InetAddress.getLocalHost().getHostAddress();

                // Get JVM information
                RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
                String jvmName = runtimeBean.getVmName();
                String jvmVersion = runtimeBean.getVmVersion();

                // Print startup arguments
                String[] args = event.getArgs();
                if (args.length > 0) {
                    log.info("Application Arguments: {}", Arrays.toString(args));
                }

                // Build access URLs
                String localUrl = String.format("http://localhost:%s%s", port, contextPath);
                String externalUrl = String.format("http://%s:%s%s", hostAddress, port, contextPath);

                String startupInfo = String.format("""

                        ----------------------------------------------------------
                        Application '%s' is running! Access URLs:
                        Local URL: %s
                        External URL: %s
                        JVM Information: %s %s
                        Profile(s): %s
                        ----------------------------------------------------------
                        """,
                    applicationName,
                    localUrl,
                    externalUrl,
                    jvmName,
                    jvmVersion,
                    String.join(", ", env.getActiveProfiles())
                );

                log.info(startupInfo);

                // Check if port is in use
                try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port))) {
                    serverSocket.close();
                } catch (Exception e) {
                    log.warn("Port {} may already be in use.", port);
                }

            } catch (Exception e) {
                log.error("Application startup failed. Please check the configuration.", e);
            }
        });
    }
}
