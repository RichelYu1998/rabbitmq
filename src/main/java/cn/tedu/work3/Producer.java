package cn.tedu.work3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory f = new ConnectionFactory();
        f.setHost("192.168.64.140");
        f.setPort(5672);
        f.setUsername("admin");
        f.setPassword("admin");
        Connection c = f.newConnection();
        Channel ch = c.createChannel();
        ch.queueDeclare("task_queue", true,false,false,null);
        while (true){
            System.out.println("输入消息:");
            String msg = new Scanner(System.in).nextLine();
            if("exit".equals(msg)){
                break;
            }
            ch.basicPublish("", "task_queue", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
            System.out.println("消息已发送: "+msg);
        }
        c.close();
    }
}
