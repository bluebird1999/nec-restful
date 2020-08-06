package com.globe_sh.cloudplatform.restful;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulMain {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RestfulMain.class);
	
	public static void main(String args[]) {
		SpringApplication.run(RestfulMain.class, args);
		
		logger.info("Globe-sh Cloud Platform Restful Service Started...");
	}
}
