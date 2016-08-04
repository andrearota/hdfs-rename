package com.tenaris.bigdata.hdfs.renamer.test;

import java.io.IOException;

import org.junit.Test;

import com.tenaris.bigdata.hdfs.renamer.Renamer;

public class RenamerTest {

	private final static String HADOOP_HOME = "/bigdata/hadoop-2.7.1/etc/hadoop";

	// TODO: find a way to test without an actual HDFS cluster
	@Test
	public void renameTest() throws IOException {
		
		Renamer renamer = new Renamer(HADOOP_HOME);
		String path = "/user/bar/foo";
		String searchRegexStr = "^(events\\.\\d+)\\.\\d+-\\d+\\.processed\\.avro$";
		String replaceRegexStr = "$1.avro";
		boolean verbose = true;
		
		renamer.rename(path, searchRegexStr, replaceRegexStr, verbose );
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyPathTest() throws IOException {
		
		Renamer renamer = new Renamer(HADOOP_HOME);
		String path = "";
		String searchRegexStr = "^(events\\.\\d+)\\.\\d+-\\d+\\.processed\\.avro$";
		String replaceRegexStr = "$1.avro";
		boolean verbose = true;
		
		renamer.rename(path, searchRegexStr, replaceRegexStr, verbose );
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyRegexTest() throws IOException {
		
		Renamer renamer = new Renamer(HADOOP_HOME);
		String path = "/user/bar/foo";
		String searchRegexStr = "";
		String replaceRegexStr = "$1.avro";
		boolean verbose = true;
		
		renamer.rename(path, searchRegexStr, replaceRegexStr, verbose );
		
	}

	@Test(expected=IllegalArgumentException.class)
	public void emptyTemplateTest() throws IOException {
		
		Renamer renamer = new Renamer(HADOOP_HOME);
		String path = "/user/bar/foo";
		String searchRegexStr = "^(events\\.\\d+)\\.\\d+-\\d+\\.processed\\.avro$";
		String replaceRegexStr = "";
		boolean verbose = true;
		
		renamer.rename(path, searchRegexStr, replaceRegexStr, verbose );
		
	}

	@Test(expected=IllegalArgumentException.class)
	public void notMatchingNumberOfGroupsTest() throws IOException {
		
		Renamer renamer = new Renamer(HADOOP_HOME);
		String path = "/user/bar/foo";
		String searchRegexStr = "^(events\\.\\d+)\\.\\d+-\\d+\\.processed\\.avro$";
		String replaceRegexStr = "foo.avro";
		boolean verbose = true;
		
		renamer.rename(path, searchRegexStr, replaceRegexStr, verbose );
		
	}
	
}
