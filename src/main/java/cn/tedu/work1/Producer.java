package cn.tedu.work1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.连接
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Channel c = f.newConnection().createChannel();
        //2.定义队列
        //指定用来发送消息的队列，如果队列不存在，服务器自动创建队列
        //如果已存在，直接使用队列
        c.queueDeclare("helloWorld",
                false,
                false,
                false,
                null);
        //3.发送信息
        c.basicPublish("", "helloWorld", null, "Hello world!".getBytes());
        System.out.println("消息已发送");
        c.close();
    }
}
