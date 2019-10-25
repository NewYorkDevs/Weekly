#!/bin/bash
export HADOOP_CLASSPATH=`hbase classpath`

sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

queueName="root.qiwen.xrec"
startDate="2019-08-26"
days="60"

statOutput="/data/qiwen/guoqi/usertag"

/usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.UserTagDateSeq  ${startDate} ${days} ${statOutput}  > tempout 2>&1
