package wechat;

import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.api.constant.Config;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author sunpengwei
 * @description 微信机器人
 * @date 2019/8/21 17:40
 */

@Data
public class HelloBot extends WeChatBot {

    public HelloBot(Config config) {
        super(config);
    }

    @Bind(msgType = MsgType.TEXT)
    public void handleText(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName())) {
            System.out.println("接收到的消息: "+ message.getName()+":"+ message.getText());
            this.sendMsg(message.getFromUserName(), "自动回复: " + message.getText());
        }
    }

    public static void main(String[] args) {
        new HelloBot(Config.me().autoLogin(true).showTerminal(true)).start();
    }

}
