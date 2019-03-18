package com.exercise.duplicatefinder.parser;

import java.util.List;

public interface Parser {
	
	public List<String> parse(String file)throws Exception;

}
