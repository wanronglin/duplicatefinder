package com.exercise.duplicatefinder.service;

import java.util.List;
import java.util.Map;

public interface DuplicateFilter {
	
	public Map<String,List<String>> filter()throws Exception;

}
