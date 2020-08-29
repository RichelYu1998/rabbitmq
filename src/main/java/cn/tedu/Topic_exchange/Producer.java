package cn.tedu.Topic_exchange;

import com.rabbitmq.client.*;

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
        //参数1: 交换机名
        //参数2: 交换机类型
        ch.exchangeDeclare("topic_logs", BuiltinExchangeType.TOPIC);
        while (true) {
            System.out.print("输入消息: ");
            String msg = new Scanner(System.in).nextLine();
            if ("exit".contentEquals(msg)) {
                break;
            }
            System.out.print("输入routingKey: ");
            String routingKey = new Scanner(System.in).nextLine();
            //参数1: 交换机名
            //参数2: routingKey, 路由键,这里我们用日志级别,如"error","info","warning"
            //参数3: 其他配置属性
            //参数4: 发布的消息数据
            ch.basicPublish("topic_logs", routingKey, null, msg.getBytes());
            System.out.println("消息已发送: "+routingKey+" - "+msg);
        }
        c.close();
    }
}