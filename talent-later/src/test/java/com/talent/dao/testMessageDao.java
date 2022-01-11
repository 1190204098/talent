package com.talent.dao;

import com.talent.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @author: luffy
 * @time: 2021/12/16 下午 11:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class testMessageDao {

    @Autowired
    public IMessageDao messageDao;

    @Test
    public void testFindAllMessageByUid(){
        List<Message> messages = messageDao.findAllMessageByUid(10);
        for (Message message : messages) {
            System.out.println("message = " + message);
        }
    }

    @Test
    public void testAddMessage(){
        Message message = new Message();
        message.setUid(7);
        message.setMname("test");
        message.setContext("test");
        message.setTime(new Date());
        messageDao.addMessage(message);
    }

    @Test
    public void testFindMessageByMid(){
        Message message = messageDao.findMessageByMid(4);
        System.out.println(message);
    }

    @Test
    public void testUpdate(){
        messageDao.update(4);
    }

    @Test
    public void testDelete(){
        messageDao.delete(8);
    }
}
