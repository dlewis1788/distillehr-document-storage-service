package com.projectivesoftware.distillehr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;

@SpringBootApplication(exclude = {GsonAutoConfiguration.class})
public class DistillehrDocumentStorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistillehrDocumentStorageServiceApplication.class, args);
	}
}
