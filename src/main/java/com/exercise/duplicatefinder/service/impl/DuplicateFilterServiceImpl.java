package com.exercise.duplicatefinder.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.exercise.duplicatefinder.comparision.DuplicateComparison;
import com.exercise.duplicatefinder.parser.Parser;
import com.exercise.duplicatefinder.service.DuplicateFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DuplicateFilterServiceImpl implements DuplicateFilter {

	@Autowired
    DuplicateComparison duplicateComparison;

	@Autowired
    Parser parser;

	@Value("${duplication.finder.file}")
	private String dataFile;
	
	private static final Logger logger = LoggerFactory.getLogger(DuplicateFilterServiceImpl.class);

	private enum RESULT_LABEL {
		Duplicated, NotDuplicated
	};

	@Override
	public Map<String, List<String>> filter() throws Exception {
		Map<String, List<String>> result = new LinkedHashMap<>();

		logger.info("process file "+dataFile); 
        
		List<String> fileContent = parser.parse(dataFile);
		List<List<String>> duplicates = duplicateComparison.findDuplicates(fileContent);
		
		
		Set<String> duplicatedSet = duplicates.stream().flatMap(List::stream)  
				.collect(Collectors.toCollection(LinkedHashSet::new));

		List<String> notDuplicatedJSONList = fileContent.stream()
				.filter(line -> !duplicatedSet.contains(line))
				.collect(Collectors.toList());

		List<String> duplicateJSONList = duplicates.stream()
										.flatMap(List::stream)  
										.collect(Collectors.toList());

		result.put(RESULT_LABEL.Duplicated.toString(), duplicateJSONList);
		result.put(RESULT_LABEL.NotDuplicated.toString(), notDuplicatedJSONList);
		return result;
	}

}
