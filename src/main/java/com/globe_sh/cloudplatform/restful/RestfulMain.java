package com.globe_sh.cloudplatform.restful;

import org.apache.logging.log4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulMain {

	private static Logger logger = org.apache.logging.log4j.LogManager.getLogger(RestfulMain.class);
	
	public static void main(String args[]) {
		SpringApplication.run(RestfulMain.class, args);
		
		logger.info("Globe-sh Cloud Platform Restful Service Started...");
	}
}
