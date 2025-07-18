package com.ch.carinspection;

import io.github.cdimascio.dotenv.Dotenv;

public class CarInspectionEnvApplication {
    public static void init() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> {
            // Set as system environment
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}
