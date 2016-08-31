# HDFS Rename
A simple utility to bulk rename files on HDFS according to regular expressions.

The tool renames files in a folder on HDFS, according to rules written with regular expressions.

The tool takes as input:
*  the HDFS folder. Url form, such as `hdfs://localhost:9000/foo/bar` and absolute path form, such as `/foo/bar`, are both valid
*  a regular expression that is applied to file names to distinguish which files should be renamed. The regular expression should also define groups that can be kept in the final name
*  a string defining the new name of the files, including one or more groups defined in the previous regular expression.

The tool requires the path to `HADOOP_CONFIG`, i.e. the folder containing `hdfs-site.xml` and `core-site.xml`. Under Cloudera CDH, they are under `/etc/hadoop/conf.cloudera.hdfs`.

## Syntax

```
hdfs-rename hadoop_config_path folder regex new_name_template [--dry-run]
```

Please note that:
* `regex should` be in Java format, no extra escaping in required (see the example)
* `new_name_template` should recall group values using `$n`, where `n` is the number of the groups starting from 1
* both `regex` an `new_name_template` should be included in single quotes (Linux) or double quotes (Windows)
* when `--dry-run` option is specified, the tools will tell which files will be renamed, without actually renaming them

Here an example of tool usage:
```
hdfs-rename /etc/hadoop/conf.cloudera.hdfs /staging/foo '^(events\.\d+)\.\d+-\d+\.processed\.avro$' '$1.avro'
```
The previous command will connect to the HDFS instance of the CDH cluster, will look in `/staging/foo` for files with a name such as `events.1469447116780.20160728-120515.processing.avro` and will rename them as `events.1469447116780.avro`.

## Building

To build the tool, type:

```
mvn package
```

The created package will be under `hdfs-rename-dist/target`.

Test are very simple, and they would be required to run against a simulated HDFS. We had no time to that, so please feel free to contribute.
