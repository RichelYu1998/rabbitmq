package cn.tedu;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //连接工厂
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setUsername("admin");
        f.setPassword("admin");
        //建立连接
        Connection c = f.newConnection();
        //建立信道
        Channel ch = c.createChannel();
        //声明队列,如果该队列已经创建过,则不会重复创建
        ch.queueDeclare("helloWorld",false,false,false,null);
        System.out.println("等待接收数据");
        //收到消息后用来处理消息的回调对象
        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                String msg = new String(message.getBody(), "UTF-8");
                System.out.println("收到: "+msg);
            }
        };
        //消费者取消时的回调对象
        CancelCallback cancel = new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        };
        ch.basicConsume("helloWorld", true, callback, cancel);
    }
}