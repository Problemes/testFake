package com.framework.core;

import com.framework.entity.MyPutRet;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import com.qiniu.util.UrlSafeBase64;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by HR on 2017/6/12.
 * Add: https://developer.qiniu.com/kodo/sdk/1239/java
 */
public class QNTest {

    Auth auth;
    String bucket;

    @Before
    public void init(){
        String ACCESS_KEY = "46esTDd-QMIn7bt7X21WFQa4kbIK_jKD62fcBncI";
        String SECRET_KEY = "2_5YrbsVUjNgo2PyFAf2vwLouQDKGxddHxwqVJaV";
        bucket = "test-pic";

        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    }

    //获取简单Token
    @Test
    public void testCreateUpToken() {
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);

        //覆盖上传的凭证,,还需要想进行覆盖的文件名称 key
        String key = "test/yh/";
        String upTokenWithKey = auth.uploadToken(bucket, key);
        System.out.println(upTokenWithKey);
    }


    //带数据处理的Token凭证,returnBody 返回给上传端的JSON参数
    @Test
    public void testHandleDataToken(){

        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 360;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        System.out.println(upToken);
    }

    //获取七牛云回调业务服务器时，带有回调的业务场景的token
    @Test
    public void testCallBackUpToken(){

        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl","http://api.730edu.net/restApi/v1/...");
        putPolicy.put("callbackBody","key=$(key)&hash=$(etag)&bucket=$(bucket)&fsize=$(fsize)");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket,null,expireSeconds,putPolicy);
        System.out.println(upToken);
    }



    public String returnBodyDataToken(){
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"w\":$(imageInfo.width),\"h\":$(imageInfo.height)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        return upToken;
    }

    public String handleDataToken(String key){

        StringMap putPolicy = new StringMap();
        //数据处理指令，支持多个指令
       /* String saveMp4Entry = String.format("%s:avthumb_test_target.mp4", bucket);
        String saveJpgEntry = String.format("%s:vframe_test_target.jpg", bucket);
        String avthumbMp4Fop = String.format("avthumb/mp4|saveas/%s", UrlSafeBase64.encodeToString(saveMp4Entry));
        String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));

        //将多个数据处理指令拼接起来
        String persistentOpfs = StringUtils.join(new String[]{
                avthumbMp4Fop, vframeJpgFop
        }, ";");*/

        String saveJpgEntry = String.format("%s:" + key, bucket);
        String vframeJpgFop = String.format("imageView2/1/w/200/h/200|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));
        String persistentOpfs = StringUtils.join(new String[]{vframeJpgFop}, ";");

        putPolicy.put("persistentOps", persistentOpfs);

        //数据处理队列名称，必填
        putPolicy.put("persistentPipeline", "");
        //数据处理完成结果通知地址
        //putPolicy.put("persistentNotifyUrl", "http://api.730edu.net/restApi/v1/...");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
        System.out.println(upToken);

        return upToken;
    }


    //客户端上传本地的图片到华东区,
    //普通上传返回DefaultPutRet的key，hash
    //用带returnBody的token上传后返回Object类型
    @Test
    public void testServerUpload() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
//        String ACCESS_KEY = "46esTDd-QMIn7bt7X21WFQa4kbIK_jKD62fcBncI";
//        String SECRET_KEY = "2_5YrbsVUjNgo2PyFAf2vwLouQDKGxddHxwqVJaV";
//        String bucket = "test-pic";

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\HR\\Desktop\\testServerObjectStore_Limitwh.jpg";
        //如果是Linux，格式是
        //String localFilePath = "/home/qiniu/test.png";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //String key = "testServerUpload.jpg";
        String key = "testServerObjectStore_Limitwh";
//        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//        String upToken = auth.uploadToken(bucket);

        try {
//            Response response = uploadManager.put(localFilePath, key, this.returnBodyDataToken());
            Response response = uploadManager.put(localFilePath, key, this.handleDataToken(key));
            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//            System.out.println(putRet.bucket);
            MyPutRet myPutRet = response.jsonToObject(MyPutRet.class);
            System.out.println(myPutRet.getKey());
            System.out.println(myPutRet.getHash());
            System.out.println(myPutRet.getBucket());
            System.out.println(myPutRet.getFsize());
            System.out.println(myPutRet.getW());
            System.out.println(myPutRet.getH());
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    //持久化数据处理，上传后处理
    @Test
    public void testHandleData(){
//待处理文件名
        String key = "testServerObjectStore_Limitwh";
//数据处理指令，支持多个指令
        /*String saveMp4Entry = String.format("%s:avthumb_test_target.mp4", bucket);
        String saveJpgEntry = String.format("%s:vframe_test_target.jpg", bucket);
        String avthumbMp4Fop = String.format("avthumb/mp4|saveas/%s", UrlSafeBase64.encodeToString(saveMp4Entry));
        String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));
//将多个数据处理指令拼接起来
        String persistentOpfs = StringUtils.join(new String[]{
                avthumbMp4Fop, vframeJpgFop
        }, ";");*/

        String saveJpgEntry = String.format("%s:" + key, bucket);
        String vframeJpgFop = String.format("imageView2/1/w/200/h/200|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));
        String persistentOpfs = StringUtils.join(new String[]{vframeJpgFop}, ";");

//数据处理队列名称，必须
        String persistentPipeline = "";
//数据处理完成结果通知地址
        String persistentNotifyUrl = "";
//构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
//构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);
        try {
            String persistentId = operationManager.pfop(bucket, key, persistentOpfs, persistentPipeline, persistentNotifyUrl, true);
            //可以根据该 persistentId 查询任务处理进度
            System.out.println("persistentId: " + persistentId);
            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果
            System.out.println(operationStatus.desc);
            System.out.println("operationStatus: " + JSONObject.fromObject(operationStatus));
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }
    }


}
