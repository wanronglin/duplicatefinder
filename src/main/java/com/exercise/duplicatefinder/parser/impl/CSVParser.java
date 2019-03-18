package com.exercise.duplicatefinder.parser.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.exercise.duplicatefinder.parser.Parser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CSVParser implements Parser {

	@Override
	public List<String> parse(String file) throws Exception {

		
		List<String> lineList = new ArrayList<>();
		int processLine = 0;
		BufferedReader br=null;
		try {
			Resource resource = new ClassPathResource(file);

			InputStream is = resource.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				if (processLine > 0) { // skip 1st line which is title
					lineList.add(line);
				}
				processLine++;
			}
			
		} finally {
			if(br!=null) {
				br.close();
			}
		}

		return lineList;
	}
}
