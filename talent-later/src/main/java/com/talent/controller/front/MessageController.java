package com.talent.controller.front;

import com.talent.controller.utils.CodeEnum;
import com.talent.controller.utils.Result;
import com.talent.domain.Message;
import com.talent.service.front.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 消息相关控制层
 * @author: luffy
 * @time: 2021/12/16 下午 11:25
 */
@Slf4j
@RestController
@Api(tags = "用户消息相关接口")
@RequestMapping("/messages")
public class MessageController {

    @Resource
    private IMessageService messageService;

    /**
     * 获取当前用户的所有信息
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 11:33 2021/12/16
     **/
    @ApiOperation(value = "获取当前用户所有消息",notes = "无参")
    @RequiresRoles("user")
    @GetMapping("/getAllMessages")
    public Result<List<Message>> getAllMessage() {
        log.info("getAllMessage() called with no parameters");
        String uname = (String) SecurityUtils.getSubject().getPrincipal();
        List<Message> messages = messageService.findAllMessageByUname(uname);
        if (messages == null||messages.isEmpty()) {
            log.info("用户[{}]暂无消息", uname);
            return new Result<>(CodeEnum.FAILURE, "暂无消息");
        }
        return new Result<>(messages);
    }

    /**
     * 根据消息id获取消息
     * @param mid
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 11:34 2021/12/16
     **/
    @ApiOperation(value = "根据消息id获取消息",notes = "参数: 消息id")
    @RequiresRoles("user")
    @GetMapping("/getMessage/{mid}")
    public Result<Message> getMessageByMid(@ApiParam(name = "mid",value = "消息id",required = true,type = "Integer") @PathVariable Integer mid) {
        log.info("getMessageByMid() called with parameters => [mid = {}]", mid);
        Message message = messageService.findMessageByMid(mid);
        if (message == null) {
            log.info("id为[{}]的消息不存在", mid);
            return new Result<>(CodeEnum.FAILURE, "id为" + mid + "的消息不存在");
        }
        return new Result<>(message);
    }

    /**
     * 设置消息为已读
     * @param mid
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 11:39 2021/12/16
     **/
    @ApiOperation(value = "设置消息已读",notes = "参数: 消息id")
    @RequiresRoles("user")
    @PostMapping("/setMessageRead/{mid}")
    public Result setMessageRead(@ApiParam(name = "mid",value = "消息id",required = true,type = "Integer") @PathVariable Integer mid) {
        log.info("setMessageRead() called with parameters => [mid = {}]", mid);
        if (messageService.setMessageRead(mid)) {
            return new Result(CodeEnum.SUCCESS, "id为" + mid + "的消息被设置为已读");
        }
        return new Result(CodeEnum.FAILURE,"操作失败");
    }

    /**
     * 删除消息
     * @param mid
     * @return com.talent.controller.utils.Result
     * @author luffy
     * @date 下午 11:42 2021/12/16
     **/
    @ApiOperation(value = "删除消息",notes = "参数: 消息id")
    @RequiresRoles("user")
    @DeleteMapping("/deleteMessage/{mid}")
    public Result deleteMessage(@ApiParam(name = "mid",value = "消息id",required = true,type = "Integer") @PathVariable Integer mid) {
        log.info("deleteMessage() called with parameters => [mid = {}]",mid);
        if (messageService.delete(mid)) {
            return new Result(CodeEnum.SUCCESS, "id为" + mid + "的消息被删除");
        }
        return new Result(CodeEnum.FAILURE,"操作失败");
    }
}
