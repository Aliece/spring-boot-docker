package org.aliece.docker;

import org.aliece.docker.Utils.EventTemplate;
import org.aliece.docker.mq.rabbitmq.DefaultEventController;
import org.aliece.docker.mq.rabbitmq.EventProcesser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RabbitMqTest{

    private EventTemplate eventTemplate;

    @Value("${rabbitmq.storeQueue}")
    String defaultQueue;

    @Value("${rabbitmq.storeExchange}")
    String defaultExchange;

    @Autowired
    DefaultEventController eventController;

    @Before
    public void init() throws IOException {
        eventTemplate = eventController.getEopEventTemplate();
        eventController.add(defaultQueue, defaultExchange, new ApiProcessEventProcessor());
        eventController.start();
    }

    @Test
    public void sendString() throws Exception{
        eventTemplate.send(defaultQueue, defaultExchange, "hello world");
    }

    @Test
    public void sendObject() throws Exception{
        eventTemplate.send(defaultQueue, defaultExchange, mockObj());
    }

    @Test
    public void sendTemp() throws Exception, InterruptedException{
        String tempExchange = "EXCHANGE_DIRECT_TEST_TEMP";//以前未声明的exchange
        String tempQueue = "QUEUE_TEST_TEMP";//以前未声明的queue
        eventTemplate.send(tempQueue, tempExchange, mockObj());
        //发送成功后此时不会接受到消息，还需要绑定对应的消费程序
        eventController.add(tempQueue, tempExchange, new ApiProcessEventProcessor());
    }

    @After
    public void end() throws InterruptedException{
        Thread.sleep(2000);
    }

    private People mockObj(){
        People jack = new People();
        jack.setId(1);
        jack.setName("JACK");
        jack.setMale(true);

        List<People> friends = new ArrayList<>();
        friends.add(jack);
        People hanMeiMei = new People();
        hanMeiMei.setId(1);
        hanMeiMei.setName("韩梅梅");
        hanMeiMei.setMale(false);
        hanMeiMei.setFriends(friends);

        People liLei = new People();
        liLei.setId(2);
        liLei.setName("李雷");
        liLei.setMale(true);
        liLei.setFriends(friends);
        liLei.setSpouse(hanMeiMei);
        hanMeiMei.setSpouse(liLei);
        return hanMeiMei;
    }

    class ApiProcessEventProcessor implements EventProcesser {
        @Override
        public void process(Object e) {//消费程序这里只是打印信息
            Assert.assertNotNull(e);
            System.out.println(e);
            if(e instanceof People){
                People people = (People)e;
                System.out.println(people.getSpouse());
                System.out.println(people.getFriends());
            }
        }
    }

    class People implements Serializable {
        private int id;
        private String name;
        private boolean male;
        private People spouse;
        private List<People> friends;
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public boolean isMale() {
            return male;
        }
        public void setMale(boolean male) {
            this.male = male;
        }
        public People getSpouse() {
            return spouse;
        }
        public void setSpouse(People spouse) {
            this.spouse = spouse;
        }
        public List<People> getFriends() {
            return friends;
        }
        public void setFriends(List<People> friends) {
            this.friends = friends;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "People[id="+id+",name="+name+",male="+male+"]";
        }
    }
}
