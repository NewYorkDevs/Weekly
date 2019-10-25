#!/bin/bash
export HADOOP_CLASSPATH=`hbase classpath`

sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

startDate="2019-08-01"
days="7"

statOutput="/data/qiwen/guoqi/MergeOfDays"

/usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.TFIDF1Merge  ${startDate} ${days} ${statOutput}  > tempout 2>&1


