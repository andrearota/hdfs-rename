package com.tenaris.bigdata.hdfs.renamer;

public class RenameCli {

	public static void main(String[] args) {
		
		if(args.length != 4) {
			System.err.println("Syntax: hdfs-rename <hadoop-home> <hdfs-path> <search-regex> <replace-regex>");
			System.exit(1);
		}
		
		String hadoopHome = args[0];
		String path = args[1];
		String searchRegexStr = args[2];
		String replaceRegexStr = args[3];
		boolean verbose = true;
		
		Renamer renamer = new Renamer(hadoopHome);
		
		try {
			renamer.rename(path, searchRegexStr, replaceRegexStr, verbose);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		System.exit(0);
	}

}
