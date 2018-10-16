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
        // 指定 name server 地址，可以填写多个，用分号隔开，例如："ip1:port;ip2:port"
        consumer.setNamesrvAddr("192.168.137.100:9876;192.168.137.101:9876");
        // 当指定的 Consumer Group 是新的时，表示从哪里开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 消息模式：
        //   Clustering: 同一个 ConsumerGroup（GroupName 相同）里的每个 Consumer 只
        //               消费所订阅消息的一部分内容，同一个 ConsumerGroup 里所有的 Consumer
        //               消费的内容合起来才是所订阅 Topic 内容的整体，从而达到负载均衡的目的。
        //   BROADCASTING: 同一个 ConsumerGroup 里的每个 Consumer 都能消费到所订阅 Topic
        //               的全部消息，也就是一个消息会被多次分发，被多个 Consumer 消费。
//        consumer.setMessageModel(MessageModel.BROADCASTING);

        // 订阅 Topic，用 null 或 * 表示要消费 Topic 的所有消息
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
