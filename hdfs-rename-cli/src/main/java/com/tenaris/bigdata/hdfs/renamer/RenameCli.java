package com.tenaris.bigdata.hdfs.renamer;

public class RenameCli {

	public static void main(String[] args) {
		
		if(args.length < 4) {
			System.err.println("Syntax: hdfs-rename <hadoop-home> <hdfs-path> <search-regex> <replace-regex> [--dry-run]");
			System.exit(1);
		}
		
		String hadoopHome = args[0];
		String path = args[1];
		String searchRegexStr = args[2];
		String replaceRegexStr = args[3];
		Boolean dryRun = false;
		
		if(args.length == 5) {
			dryRun = args[4].trim().equals("--dry-run");
		}
		
		boolean verbose = true;
	
		if(dryRun) {
			System.out.println("** RUN DRY Mode ON: no actual modification will be applied **");
		}
		
		Renamer renamer = new Renamer(hadoopHome);
	
		try {
			renamer.rename(path, searchRegexStr, replaceRegexStr, verbose, dryRun);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		System.exit(0);
	}

}
