#!/bin/bash
export HADOOP_CLASSPATH=`hbase classpath`

sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

statInput="/data/qiwen/guoqi/usertag"
statOutput="/data/qiwen/guoqi/TagEntropy"

/usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.TagDateEntropy  ${statInput} ${statOutput}  > tempout 2>&1
