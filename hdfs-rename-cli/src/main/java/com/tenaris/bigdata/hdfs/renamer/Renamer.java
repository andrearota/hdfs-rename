package com.tenaris.bigdata.hdfs.renamer;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Renamer {

	private Configuration config;
	
	public Renamer(String hadoopHome) {
		config = new Configuration();
		config.addResource(new Path(hadoopHome, "hdfs-site.xml"));
		config.addResource(new Path(hadoopHome, "core-site.xml"));
		config.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
		config.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
	}

	public void rename(String path, String searchRegexStr, String template, boolean verbose, boolean dryRun) throws IOException {
		
		// Check input variables
		if(path == null || path.trim().isEmpty()) {
			throw new IllegalArgumentException("The search regex is empty");
		}
		
		if(searchRegexStr == null || searchRegexStr.trim().isEmpty()) {
			throw new IllegalArgumentException("The search regex is empty");
		}
		
		if(template == null || template.trim().isEmpty()) {
			throw new IllegalArgumentException("The template string is empty");
		}
		
		int expectedGroups = countRegExGroups(searchRegexStr);
		int actualGroups = countTemplateUsedGroups(template); 
		
		if(expectedGroups != actualGroups) {
			throw new IllegalArgumentException("All the groups declared in the regex should be used in the template");
		}
		
		FileSystem hadoopFs = FileSystem.get(config);
		Path rootPath = new Path(path);
		
		FileStatus[] fileStatus = hadoopFs.listStatus(rootPath);
				
		for(int i = 0; i < fileStatus.length; i++) {
			if(fileStatus[i].isFile()) {
				String name = fileStatus[i].getPath().getName();
				String renamed = name.replaceAll(searchRegexStr, template);
				
				if(!name.equals(renamed)) {
					
					if(verbose && dryRun) {
						System.out.println("Would be renaming " + name + " in " + renamed);
					} else if(verbose && !dryRun) {
						System.out.println("Renaming " + name + " in " + renamed);
					}
					
					if(!dryRun) {
						boolean renameSuccess = hadoopFs.rename(fileStatus[i].getPath(), new Path(path, renamed));
						if(!renameSuccess) {
							System.err.println("Unable to create " + renamed + ", does the file exist already?");						
						}
					}
				}
			}
		}
	}

	// Groups are simply counted finding '$'
	private int countTemplateUsedGroups(String template) {
		return countOccurrences(template, '$');
	}

	// Groups are simply counted finding '('
	private int countRegExGroups(String searchRegexStr) {
		return countOccurrences(searchRegexStr, '(');
	}
	
	private int countOccurrences(String haystack, char needle) {
		
		int counter = 0;

		for(int i=0; i<haystack.length(); i++) {
			if(haystack.charAt(i) == needle) {
				counter++;
			}
		}
		
		return counter;
	}

}
