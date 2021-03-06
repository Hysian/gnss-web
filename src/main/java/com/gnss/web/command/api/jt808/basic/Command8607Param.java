package com.gnss.web.command.api.jt808.basic;

import com.gnss.core.annotations.DownCommand;
import com.gnss.core.proto.TerminalProto;
import com.gnss.core.service.IDownCommandMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * <p>Description: JT808 0x8606指令参数</p>
 * <p>Company: www.gps-pro.cn</p>
 *
 * @author huangguangbin
 * @version 1.0.1
 * @date 2020/5/20
 */
@ApiModel("0x8607删除路线")
@Getter
@Setter
@DownCommand(messageId = 0x8607, respMessageId = 0x0001, desc = "删除路线")
public class Command8607Param implements IDownCommandMessage {

    @ApiModelProperty(value = "路线ID列表", required = true, position = 1)
    @NotEmpty(message = "路线ID列表不能为空")
    private LinkedHashSet<Integer> lineIdList;

    @ApiModelProperty(value = "路线数", hidden = true)
    private int lineIdCount;

    @Override
    public byte[] buildMessageBody(TerminalProto terminalInfo) throws Exception {
        lineIdCount = lineIdList.size();
        ByteBuf msgBody = Unpooled.buffer(4 * lineIdCount + 1);
        //设置路线数
        msgBody.writeByte(lineIdCount);
        //循环设置路线ID
        lineIdList.forEach(msgBody::writeInt);
        byte[] msgBodyArr = msgBody.array();
        ReferenceCountUtil.release(msgBody);
        return msgBodyArr;
    }

    @Override
    public String toString() {
        Map<String, Object> msgBodyItems = new LinkedHashMap<>();
        msgBodyItems.put("路线数", lineIdCount);
        msgBodyItems.put("路线ID列表", lineIdList);
        return msgBodyItems.toString();
    }
}
