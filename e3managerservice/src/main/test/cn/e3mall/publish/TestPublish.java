package cn.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Date 2019/8/11 11:11
 */
public class TestPublish {
    //启动tomcat只是为了加载spring容器 所以现在不使用tomcat来初始化spring的配置文件
    @Test
    public void publishService()throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        while (true){
            Thread.sleep(1000);
        }
    }
}
