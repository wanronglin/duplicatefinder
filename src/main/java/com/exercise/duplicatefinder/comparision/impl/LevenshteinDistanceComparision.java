package com.exercise.duplicatefinder.comparision.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import com.exercise.duplicatefinder.comparision.DuplicateComparison;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LevenshteinDistanceComparision implements DuplicateComparison {

	@Value("${duplication.finder.comparsion.ratio}")
	private double comparisonRatio; // .3 is good value to filter out the duplicated data

	public List<List<String>> findDuplicates(List<String> lines) {
		LevenshteinDistance levenshteinDistance = LevenshteinDistance.getDefaultInstance();
		List<List<String>> duplicateList = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++) {
			String line1 = lines.get(i);
			List<String> resultList = new ArrayList<>();
			resultList.add(line1);
			String line2;
			Integer distance;
			int longerLength;
			double ratio;
			for (int j = i + 1; j < lines.size(); j++) {
				line2 = lines.get(j);
				distance = levenshteinDistance.apply(line1, line2);
				longerLength = line1.length() >= line2.length() ? line1.length() : line2.length();
				ratio = (double) distance / longerLength;
				if (ratio < comparisonRatio) {
					resultList.add(lines.get(j));
				} else { // handles id 66, and emailAddress is a unique identifier for this data file
					String email1 = getEmail(line1);
					if (email1 != StringUtils.EMPTY) {
						if (line2.indexOf(email1) > -1) {
							resultList.add(lines.get(j));
						}
					}
				}
			}
			if (resultList.size() > 1) {
				boolean isExisted = resultList.stream().anyMatch(line -> isExist(line, duplicateList));
				if (!isExisted) { // if existed means already handle this, no need to store again. id 19 in
									// advanced.csv
					duplicateList.add(resultList);
				}
			}
		}

		return duplicateList;

	}

	private String getEmail(String line) {

		StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
		String email = StringUtils.EMPTY;
		while (stringTokenizer.hasMoreTokens()) {
			email = stringTokenizer.nextToken();
			if (email.indexOf("@") > -1) {
				break;
			}
		}
		return email;
	}

	private boolean isExist(String line, List<List<String>> duplicateList) {
		Set<String> duplicatedSet = duplicateList.stream().flatMap(List::stream)
				.collect(Collectors.toCollection(LinkedHashSet::new));
		return duplicatedSet.contains(line);

	}

}
