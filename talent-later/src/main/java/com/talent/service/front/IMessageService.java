package com.talent.service.front;

import com.talent.domain.Message;

import java.util.List;

/**
 * @author: luffy
 * @time: 2021/12/16 上午 12:55
 */
public interface IMessageService {

    /**
     * 根据用户id查询所有消息
     * @author luffy
     * @date 上午 12:48 2021/12/16
     * @param uName
     * @return java.util.List<com.talent.domain.Message>
     **/
    List<Message> findAllMessageByUname(String uName);

    /**
     * 根据mid查询消息
     * @author luffy
     * @date 上午 01:02 2021/12/16
     * @param mid
     * @return com.talent.domain.Message
     **/
    Message findMessageByMid(Integer mid);

    /**
     * 新增消息
     * @author luffy
     * @date 上午 12:48 2021/12/16
     * @param message
     * @return void
     **/
    void addMessage(Message message);

    /**
     * 更新消息，标记为已读
     * @author luffy
     * @date 上午 12:51 2021/12/16
     * @param mid
     * @return void
     **/
    Boolean setMessageRead(Integer mid);

    /**
     * 删除文件
     * @author luffy
     * @date 上午 12:53 2021/12/16
     * @param mid
     * @return void
     **/
    Boolean delete(Integer mid);
}
