package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**图片上传处理Controller
 * @Date 2019/8/9 20:43
 */
@Controller
public class PictureController {
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;
    //这个路径是由‘富文本编译器提供的地址’
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody               //uploadFile 是富文本定义的属性 传过来的值
    public String uploadFile(MultipartFile uploadFile){ //将返回值类型改为String的话是为了更好的和其他浏览器兼容，加上requestMapping里面的produces
        //TODO      服务器图片上传  了解了解 就是服务器难配置
        try {
            //把图片上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //得到一个图片的地址和文件名
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //补充为完整的url
            url = IMAGE_SERVER_URL + url;
            //封装到map中返回
            Map<String,Object> result = new HashMap<>();
            result.put("error",0);
            result.put("url",url);
            return JsonUtils.objectToJson(result) ;
        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> result = new HashMap<>();
            result.put("error",0);
            result.put("message","图片上传失败");
            return JsonUtils.objectToJson(result) ;
        }
    }
}
