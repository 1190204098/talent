package com.talent.service.front.impl;

import com.talent.dao.IMessageDao;
import com.talent.dao.IUserDao;
import com.talent.domain.Message;
import com.talent.domain.User;
import com.talent.service.front.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息的业务层
 * @author: luffy
 * @time: 2021/12/16 上午 12:57
 */
@Slf4j
@Service
public class MessageServiceImpl implements IMessageService {

    @Resource
    private IUserDao userDao;
    @Resource
    private IMessageDao messageDao;

    @Override
    public List<Message> findAllMessageByUname(String uName) {
        log.info("findAllMessageByUname() called with parameters => [uName = {}]",uName);
        User user = userDao.findUserByName(uName);
        return messageDao.findAllMessageByUid(user.getUid());
    }

    @Override
    public Message findMessageByMid(Integer mid) {
        log.info("findMessageByMid() called with parameters => [mid = {}]",mid);
        return messageDao.findMessageByMid(mid);
    }

    @Override
    public void addMessage(Message message) {
        log.info("addMessage() called with parameters => [message = {}]",message);
        messageDao.addMessage(message);
    }

    @Override
    public Boolean setMessageRead(Integer mid) {
        log.info("setMessageRead() called with parameters => [mid = {}]",mid);
        Message message = messageDao.findMessageByMid(mid);
        message.setFlag(1);
        messageDao.update(mid);
        log.info("消息id为[{}]已设置为已读",mid);
        return true;
    }

    @Override
    public Boolean delete(Integer mid) {
        log.info("delete() called with parameters => [mid = {}]",mid);
        messageDao.delete(mid);
        log.info("消息id为[{}]已删除",mid);
        return true;
    }
}
