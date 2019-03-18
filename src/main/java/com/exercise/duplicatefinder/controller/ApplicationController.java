package com.exercise.duplicatefinder.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.exercise.duplicatefinder.service.DuplicateFilter;

@Component
public class ApplicationController implements CommandLineRunner{
	
	@Autowired
	DuplicateFilter duplicateFilter;
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Override
	public void run(String... args) throws Exception {
		
		try {
			duplicateFilter.filter()
			.entrySet()
			.stream()
			.forEach(entry->{
							System.out.println(entry.getKey());
							entry.getValue()
							.stream()
							.forEach(System.out::println);
					}
			);
		}catch(Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
		
	}

}
