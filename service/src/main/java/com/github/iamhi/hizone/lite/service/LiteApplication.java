package com.github.iamhi.hizone.lite.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.github.iamhi.hizone.lite"})
@ConfigurationPropertiesScan(basePackages = {
	"com.github.iamhi.hizone.lite.config",
	"com.github.iamhi.hizone.lite.authentication.config"
})
@EnableJpaRepositories(basePackages = {
	"com.github.iamhi.hizone.lite.authentication.database",
	"com.github.iamhi.hizone.lite.notes.database",
//	"com.github.iamhi.hizone.hub.app.planner.core.entryservice"
})
@EntityScan(basePackages = {
	"com.github.iamhi.hizone.lite.authentication.database",
	"com.github.iamhi.hizone.lite.notes.database",
//	"com.github.iamhi.hizone.hub.app.planner.core.entryservice"
})
public class LiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteApplication.class, args);
	}

}
