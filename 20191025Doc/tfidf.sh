#!/bin/bash
export HADOOP_CLASSPATH=`hbase classpath`

sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

#year=$(date -d "today" "+%Y")
#month=$(date -d "today" +"%m")
#day=$(date -d "today" +"%d")
#daytag=${year}-${month}-${day}

statInput="/hive/warehouse/qiwen.db/paopao_qiwen_user_with_feedback_seq/dt="
statOutput="/data/qiwen/guoqi"
queueName="root.qiwen.xrec"
#
reduceJobNum="200"
days="1"


startDate=20190703
endDate=20190826
startSec=`date -d "$startDate" "+%s"`
endSec=`date -d "$endDate" "+%s"`
for((i=$startSec;i<=$endSec;i+=86400))
do
    current_day=`date -d "@$i" "+%Y-%m-%d"`
    echo "${current_day}"+"begin"
    /usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.UserSeqLogTest  ${reduceJobNum} ${days} ${queueName} ${current_day} > tempout 2>&1
    echo "${current_day}"
done

#/usr/bin/hadoop jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.UserSeqLogTest $1 $2  ${reduceJobNum} ${days} ${queueName} ${startDate} > 2>&1
#/usr/bin/hadoop  jar $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.seqlog.UserSeqLogTest  ${reduceJobNum} ${days} ${queueName} ${startDate} > tempout 2>&1

echo "seq_log_test finished"

