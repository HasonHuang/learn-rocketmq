package com.hason.learn.rocketmq.chapter2;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 以 Sync 方式发送消息
 *
 * @author Huanghs
 * @since 2.0
 * @date 2018/10/10
 */
public class SyncProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 创建生产者实例
        DefaultMQProducer producer = new DefaultMQProducer("chapter1_producer");
        // 指定 name server 地址
        producer.setNamesrvAddr("192.168.137.100:9876");
        // 启动实例
        producer.start();

        String msgTemplate=  "Hello RocketMQ ";
        for (int i = 0; i < 100; i++) {
            // 创建消息实例，指定 Topic、Tag和消息体
            Message message = new Message("TopicTest", "TagA", (msgTemplate + i).getBytes());
            // 向其中一个 broker 发送消息
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }

        // 生产者不再使用后，关闭实例和释放相关资源
        producer.shutdown();
    }

}
