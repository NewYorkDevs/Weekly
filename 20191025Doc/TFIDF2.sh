#!/bin/bash 
export HADOOP_CLASSPATH=`hbase classpath`

sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

statInput="/data/qiwen/guoqi/MergeOfDays"
statOutput="/data/qiwen/guoqi/output_TFIDF2"

/usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.TFIDF2 ${statInput} ${statOutput} > tempout1 2>&1
