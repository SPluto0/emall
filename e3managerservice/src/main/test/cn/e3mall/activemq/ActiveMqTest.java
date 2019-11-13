package cn.e3mall.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * @Date 2019/8/22 17:28
 */
public class ActiveMqTest {
    /**
     * 点到点形式发送信息
     * @throws Exception
     */
    @Test
    public void testQueueProducer()throws Exception{
        //1、创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.251:61616");
        //2、使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接，调用Connection对象的start方法
        connection.start();
        //4、创建一个Session对象
        //第一个参数：是否开启事务（和mysql事务不一样），如果开启事务，第二个参数无意义。一般不开启事务。
        //第二个参数：应答模式。自动应答或者手动应答。一般自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用Session对象创建一个Destination对象。两种queue、topic,现在应该使用queue
        Queue queue = session.createQueue("test-queue");//名字
        //6、使用Session对象创建一个Producer对象    生产者
        MessageProducer producer = session.createProducer(queue);
        //7、创建一个Message对象，可以使用TextMessage
       /*TextMessage textmessage = new ActiveMQTextMessage();
       textmessage.setText("hello Activemq");*/
        TextMessage textMessage = session.createTextMessage("hello Activemq");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }
    @Test
    public void testQueueConsumer()throws Exception{
        //创建一个ConnectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.251:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象连接一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象，queue对象
        Queue queue = session.createQueue("test-queue");
        //使用Sesstion对象创建一个消费者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //接受消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待接收消息
        System.in.read();
        // 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }


    @Test
    public void testTopicProducer()throws Exception{
        //1、创建一个连接工厂对象，需要指定服务的ip及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.251:61616");
        //2、使用工厂对象创建一个Connection对象
        Connection connection = connectionFactory.createConnection();
        //3、开启连接，调用Connection对象的start方法
        connection.start();
        //4、创建一个Session对象
        //第一个参数：是否开启事务（和mysql事务不一样），如果开启事务，第二个参数无意义。一般不开启事务。
        //第二个参数：应答模式。自动应答或者手动应答。一般自动应答。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5、使用Session对象创建一个Destination对象。两种queue、topic,现在应该使用topic
        Topic topic = session.createTopic("test-topic");//名字
        //6、使用Session对象创建一个Producer对象    生产者
        MessageProducer producer = session.createProducer(topic);
        //7、创建一个Message对象，可以使用TextMessage
       /*TextMessage textmessage = new ActiveMQTextMessage();
       textmessage.setText("hello Activemq");*/
        TextMessage textMessage = session.createTextMessage("topic message");
        //8、发送消息
        producer.send(textMessage);
        //9、关闭资源
        producer.close();
        session.close();
        connection.close();
    }


    @Test
    public void testTopiceConsumer()throws Exception{
        //创建一个ConnectionFactory对象连接MQ服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.251:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象连接一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象，topic对象
        Topic topic = session.createTopic("test-topic");
        //使用Sesstion对象创建一个消费者对象
        MessageConsumer consumer = session.createConsumer(topic);
        //接受消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印结果
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("topic消费者1已经启动。。。。");
        //等待接收消息
        System.in.read();
        // 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
