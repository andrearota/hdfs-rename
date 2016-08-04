@echo off
pushd %~dp0
java -cp "..\lib\*" com.tenaris.bigdata.hdfs.renamer.RenameCli %*
popd