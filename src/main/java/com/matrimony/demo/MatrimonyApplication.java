package com.matrimony.demo;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.messaging.MessagingAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.matrimony.demo.config.AppProperties;

@EnableConfigurationProperties(AppProperties.class)
@EntityScan(basePackageClasses = { 
		MatrimonyApplication.class,
		Jsr310JpaConverters.class 
})
@EnableJpaAuditing
@SpringBootApplication ( exclude = {ContextRegionProviderAutoConfiguration.class, ContextStackAutoConfiguration.class, MessagingAutoConfiguration.class})
public class MatrimonyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatrimonyApplication.class, args);
	}
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
