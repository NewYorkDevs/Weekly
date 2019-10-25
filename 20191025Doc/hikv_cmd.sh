#!/bin/bash
sh_dir="/data/qiwen/guoqi/personas/temp_result/paopao-personas/rec-data/rec-mr-job/target"

year=$(date -d "today" "+%Y")
month=$(date -d "today" +"%m")
day=$(date -d "today" +"%d")
daytag=${year}-${month}-${day}

statOutput="/data/qiwen/guoqi/personas/short"
queueName="root.qiwen.deeptext"
startDate="2019-06-24"



#java  -cp $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.util.hikv.UserFeatureDaoService >  tempout 2>&1
#java  -cp $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.util.hikv.HikvCommand $1 >  tempout 2>&1
java  -cp $sh_dir/rec-mr-job.jar com.iqiyi.paopao.rec.mr.job.util.hikv.HikvTFIDFTest  >  tempout 2>&1

