package com.hason.learn.rocketmq.chapter2;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * （长轮询）消费者
 *
 * @author Huanghs
 * @since 2.0
 * @date 2018/10/11
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        // 创建长轮询的消费者实例
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("chapter1_consumer");
        // 指定 name server 地址
        consumer.setNamesrvAddr("192.168.137.100:9876");
        // 当指定的 Consumer Group 是新的时，表示从哪里开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.setMessageModel(MessageModel.BROADCASTING);

        // 订阅 Topic
        consumer.subscribe("TopicTest", "*");

        // 注册回调函数，在消息到达时执行
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 启动实例
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

}
