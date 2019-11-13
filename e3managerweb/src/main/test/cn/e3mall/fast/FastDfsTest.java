package cn.e3mall.fast;

import cn.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**图片服务器上传测试
 * @Date 2019/8/9 16:09
 */
public class FastDfsTest {
    @Test
    public void testUpload() throws Exception{
        //创建一个配置文件。文件名任意。内容就是tracker服务器的地址。
        //使用全局对象加载配置文件
        ClientGlobal.init("E:/emall/e3managerweb/src/main/resources/conf/client.conf");
        //创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //创建一个StorageServer的引用，可以是null
        StorageServer strorageServer = null;
        //创建一个StrorageClient，参数需要TrackerServer和StrorageServer
        StorageClient storageClient = new StorageClient(trackerServer,strorageServer);
        //使用StrorageClient上传文件
        String[] strings = storageClient.upload_appender_file("F:/price/a.png", "jpg", null);
        for (String s : strings) {
            System.out.println(s);
        }
    }
    @Test
    public void testFastDfsClient() throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("E:/emall/e3managerweb/src/main/resources/conf/client.conf");
        String uploadFile = fastDFSClient.uploadFile("F:/price/b.png");
        System.out.println(uploadFile);
    }
}
