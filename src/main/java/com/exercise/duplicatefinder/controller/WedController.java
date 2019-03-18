package com.exercise.duplicatefinder.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.exercise.duplicatefinder.service.DuplicateFilter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WedController {
	
	@Autowired
    DuplicateFilter duplicateFilter;
	
	private static final Logger logger = LoggerFactory.getLogger(WedController.class);
	
	@RequestMapping("/findduplicated")
    public @ResponseBody ResponseEntity<Map<String, List<String>>>  find()  {
		
		ResponseEntity<Map<String,List<String>>> responseEntity=null;
		try {
			Map<String,List<String>> result=duplicateFilter.filter();
			responseEntity =new ResponseEntity<Map<String,List<String>>>(result, HttpStatus.OK);
		}catch(Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			try {
				responseEntity= new ResponseEntity<Map<String,List<String>>>(new LinkedHashMap<>(), HttpStatus.BAD_REQUEST);
			}catch(Exception ei) {
				logger.error(ExceptionUtils.getStackTrace(ei));
			}
		}
		
		return responseEntity;
		 
    }
	

}
