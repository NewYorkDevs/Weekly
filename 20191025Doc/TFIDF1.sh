#!/bin/bash
export HADOOP_CLASSPATH=`hbase classpath`

sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

queueName="root.qiwen.xrec"
startDate=20190701
endDate=20190825
startSec=`date -d "$startDate" "+%s"`
endSec=`date -d "$endDate" "+%s"`
pre="/data/qiwen/guoqi/output_pingback_"
for((i=$startSec;i<=$endSec;i+=86400))
do
    current_day=`date -d "@$i" "+%Y-%m-%d"`
    statInput=$pre$current_day
    statOutput="/data/qiwen/guoqi/output_TFIDF1_"$current_day
    /usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.TFIDF1 ${statInput} ${statOutput} > tempout1 2>&1
    echo "${current_day}"
done

