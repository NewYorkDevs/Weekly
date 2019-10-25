package com.iqiyi.paopao.rec.mr.job.util.hikv;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iqiyi.hikv.util.FutureUtils;
import com.iqiyi.paopao.rec.mr.job.entity.UserEmbedding;
import com.iqiyi.paopao.rec.mr.job.couchbase.CacheKey;
import com.iqiyi.paopao.rec.mr.job.couchbase.CbConfig;
//import com.iqiyi.paopao.reccommon.entity.*;
import com.iqiyi.paopao.rec.mr.job.proto.UserFeatureProtos;
import com.iqiyi.paopao.reccommon.util.CategoryTagIdUtil;
import com.iqiyi.paopao.reccommon.util.Tools;
//import com.iqiyi.paopao.reccommon.util.hikv.UserFeatureDao;
import com.iqiyi.paopao.reccommon.util.couchbase.CouchbaseService;
import com.iqiyi.paopao.reccommon.util.hbase.HBaseRowKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;  
import java.io.BufferedWriter;  
import java.io.FileWriter;  

import java.io.IOException;
import java.util.*;

//        UserFeatureProtos.UserFeature.Interests shortTermInterests = userFeature.getShortTermInterests();
public class HikvPaopaoPersonaCommand extends EmbeddedCassandraService{

    private static final int HIKV_DATA_TTL = 60 * 24 * 3600;

    private static final int CATG_TAG_LIMIT = 500;

    private static final int CONTENT_TAG_LIMIT = 2000;

    private static final int STAR_VIDEO_CONTENT_TAG_LIMIT = 100;

    public static void createSchema() throws IOException {
        String withUseKVEngine = useKVEngine ? " WITH use_kv_engine = true" : "";
        execute(
                "CREATE KEYSPACE IF NOT EXISTS paopao_persona WITH REPLICATION = { 'class':'SimpleStrategy', 'replication_factor':2 }",
                "DROP TABLE paopao_persona.paopao_persona",
                "CREATE TABLE IF NOT EXISTS paopao_persona.paopao_persona (" +
                        "id blob PRIMARY KEY,"+
                        "device_id ascii,"+
                        "dynamic_features blob,"+
                        "filter_authors blob,"+
                        "filter_circles blob,"+
                        "long_term_circle_interests blob,"+
                        "long_term_interests blob,"+
                        "short_offline_term_interests blob,"+
                        "short_term_circle_interests blob,"+
                        "short_term_interests blob,"+
                        "static_features blob,"+
                        "x01_app_dynamic_features blob,"+
                        "x02_app_star_short_term_interests blob,"+
                        "x03_app_star_long_term_interests blob,"+
                        "x04_app_star_real_short_term_interests blob,"+
                        "x05_user_new_feeds blob,"+
                        "x06_sim_user_icf blob,"+
                        "x07_sim_user_icfvc blob,"+
                        "x08_user_embedding blob,"+
                        "x09_star_video_short_term_interests blob,"+
                        "x10_video_dynamic_features blob,"+
                        "x11_user_new_tvids blob,"+
                        "x12_user_new_video_feeds blob,"+
                        "x13_video_sim_user_cf blob,"+
                        "x14_user_new_video_feeds_with_score blob,"+
                        "x15_video_sim_user_pop blob,"+
                        "x16_user_layer blob,"+
                        "x17_platform blob,"+
                        "x18_user_video_query_words blob)"+
                        withUseKVEngine
        );
    }

    public static void main(String[] args) throws IOException {

        for(int i = 0; i < contactPoints.size(); i++) {
            System.out.println("the contactPoints is "+contactPoints.get(i));
        }
        System.out.println("the port is "+port);
        System.out.println("the keyspace is "+keyspace);
        System.out.println("start createSchema.");
        //createSchema();
        System.out.println("finish createSchema.");

        UserFeatureDao dao = UserFeatureDao.fromConfig("hikv.paopao_persona");
        System.out.println("finish fromConfig.");
        //String id = "999999";
        System.out.println("args.length is "+args.length);
        /*
        if(args.length >= 1)
        {
            id = args[0];
        }*/
        String[] idset={"20E9E9F0-3EB8-4B9A-A2AF-19240831AC5C","862788034828431","865741044956699","869310029476390","863729044328334","FD11973C-E95F-49CD-9AC0-E34280EBECC5","869810037462415","854DCD4C-B304-406E-8849-9FF40F4F94C6","862788037036826","9C86AADC-1715-4516-AEBA-4B6B07FEBAEF","867215040448272","12AA78E0-8404-4888-9FA8-219AC9A8F379","868663030484337","12AA78E0-8404-4888-9FA8-219AC9A8F379","DFAFF09C-BF5F-4198-8115-2BC823751D83","383E36CB-853C-4B50-AC52-EA0B437A353A","033638EC-3099-43F1-83B0-162C00220A19","869902043625935","BD148EC0-87BE-4B38-8954-37E3CF57572F","F11D5766-02FC-49CF-8ABD-333714E3DCF1","D73CE024-F62C-4CD5-A3B1-B8A9813BD1CA","869494039943795","1B2725D8-6679-4285-A88D-C0AFCA533E51","353460083351956","D8695C18-1C5B-453B-8C87-FA5EA750B7ED","DEB6BE0A-C87E-4EC6-8D7F-21C41CFBDE22","72BFDC09-0182-4604-AC03-ED11F19FB339","7EB7A79A-B582-493C-B840-644806CEF799","CC814605-F7C6-499C-9A85-82A98969B6EA","81C92F31-76C4-4209-92CF-269C362CCCE1","247B3085-B3E8-4A3C-9B38-7EF8AEF04FE2","B1DBF94B-1ED3-46B3-BA68-E7D87AC0428F","04A45291-7066-445C-93DC-8E334597A608","5D921420-89D9-4AD6-9F15-8C79FA016AB1"};
        
        for (String id : idset){



        UserFeatureProtos.UserFeature userFeature = FutureUtils.getUninterruptibly(dao.get(id).toCompletableFuture());
        //System.out.println("the user feature is :"+userFeature.toString());
        /*System.out.println(userFeature.getShortTermInterests());
        System.out.println(userFeature.getShortOfflineTermInterests());
        System.out.println(userFeature.getLongTermInterests());*/
        System.out.println("********************************************************");
        if (userFeature!=null){
        File writename = new File(id+".txt");
        writename.createNewFile(); // 创建新文件  
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
        out.write(userFeature.getShortTermInterests().toString()+"|");
        out.write(userFeature.getShortOfflineTermInterests().toString()+"|");
        out.write(userFeature.getLongTermInterests().toString()+"|");

        out.flush(); // 把缓存区内容压入文件  
        out.close(); // 最后记得关闭文件  
        }else{
        System.out.println(id);
        }
        }
    }
}
