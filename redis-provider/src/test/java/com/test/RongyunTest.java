package com.test;

import io.rong.RongCloud;
import io.rong.messages.BaseMessage;
import io.rong.messages.ImgTextMessage;
import io.rong.messages.TxtMessage;
import io.rong.messages.VoiceMessage;
import io.rong.methods.message.Message;
import io.rong.methods.message._private.Private;
import io.rong.methods.user.User;
import io.rong.models.Result;
import io.rong.models.message.PrivateMessage;
import io.rong.models.response.ResponseResult;
import io.rong.models.user.UserModel;

/**
 * @Auther: 晓枫
 * @Date: 2019/4/6 13:58
 * @Description:
 */
public class RongyunTest {

    private static String appKey = "8luwapkv8jotl";
    private static String appSecret = "CLreS4FCxQ8s";

    public static void main(String[] args) throws Exception {
//        init();
        sendMessage();
    }

    public static void init() throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        User user = rongCloud.user;

        /**
         * API 文档: http://www.rongcloud.cn/docs/server_sdk_api/user/user.html#register
         *
         * 注册用户，生成用户在融云的唯一身份标识 Token
         */
        UserModel userModel = new UserModel()
                .setId("1")
                .setName("xiaofeng")
                .setPortrait("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540998751506&di=16c4a38aefa53d968fe4f3d73afcf537&imgtype=0&src=http%3A%2F%2Fimage.yy.com%2Fyywebalbumbs2bucket%2Fe0df6e5c270440ca9696294bf5fa6b52_1514659221515.jpg");
        Result result = user.update(userModel);
        System.out.println("getToken:  " + result.toString());
    }
    private static final TxtMessage txtMessage = new TxtMessage("你好啊", "helloExtra");
//    private static final VoiceMessage voiceMessage = new VoiceMessage("你好啊", "helloExtra", 20L);

    public static void sendMessage() throws Exception {
        RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret);
        //自定义 api 地址方式
        //RongCloud rongCloud = RongCloud.getInstance(appKey, appSecret,api);
        Private msgPrivate = rongCloud.message.msgPrivate;
        String[] targetIds = {"2"};
        PrivateMessage privateMessage = new PrivateMessage()
                .setSenderId("1")
                .setTargetId(targetIds)
                .setObjectName(txtMessage.getType())
                .setContent(txtMessage)
                .setPushContent("")
                .setPushData("{\"pushData\":\"hello\"}")
                .setCount("4")
                .setVerifyBlacklist(0)
                .setIsPersisted(0)
                .setIsCounted(0)
                .setIsIncludeSender(0);
        ResponseResult privateResult = msgPrivate.send(privateMessage);
        System.out.println(privateResult);
    }
}
