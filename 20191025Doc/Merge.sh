#!/bin/bash
export HADOOP_CLASSPATH=`hbase classpath`

sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

statInput="/data/qiwen/guoqi/output_TFIDF3_7days"
statOutput="/data/qiwen/guoqi/output_Merge_result"
addCacheFile="/data/qiwen/guoqi/MergeOfDays/part-r-00019"

/usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.Merge  ${statInput} ${statOutput} ${addCacheFile} > tempout 2>&1
