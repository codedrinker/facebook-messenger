## 接入 Facebook Messenger Platform 机器人
## Facebook Messenger Platform 介绍
`Facebook` 仿照 `Wechat` 公共微信号，使用 `Messenger` 平台提供了一套核心的 `API`、网页插件和一个全屏网页视图，让您可以轻松构建精彩的 Messenger 体验。对话除了简单的文字消息，还包含很多组成要素。音频、视频、图片、文件、结构化消息（和文本！）都是对话体验的组成部分。我们可以轻松的使用其 `API` 接入 Facebook Messenger Platform(下文简称FMP)。详情参照 [官方文档](https://developers.facebook.com/docs/messenger-platform/introduction)。说直白一点，就是支持想微信公共号一样，群发消息和根据用户的操作做出相应的反应。

## 快速入门
### 创建 Page 和 App
#### 创建 Page
FMP 的原理比较简单，首先你需要拥有一个 Facebook Page，用来关联你的 Facebook Messenger的，不然没有入口。直接去 https://www.facebook.com/pages/create/?ref_type=bookmark 地址创建即可。

#### 创建 App
其次需要去 [Facebook Developer](https://developers.facebook.com) 控制台创建一个新 App。

#### Setup Messenger
创建成功 App 以后可以在控制台看到 Products列表，直接创建一个Messenger。会提示你关联一个 page，关联好了以后会或者一个 token，保存下来备用，因为刷新页面会消失。

#### Setup Webhook
创建 Messenger 以后会提示你Setup Webhook，直接点击setup，需要填写三个内容。第一个是callback url，这个就是你用来接收和发送消息的服务器地址，下文会详细讲述。第二个是verify token，就是刚才你生成的token，这个token还需要继续保留，后面还需要使用。第三个是就是你需要的权限，根据你当前的 messenger 需要做什么工作勾选即可。这个时候配置已经不能继续前行了，我们需要创建好项目，然后再继续配置这个步骤剩下的环节。

### 构建项目
#### 创建项目并引入jar
使用idea创建 spring-boot项目，勾选web选项，等待项目加载成功。在pom里面加入如下依赖。
```xml
<dependency>
  <groupId>com.github.codedrinker</groupId>
  <artifactId>facebook-messenger</artifactId>
  <version>1.0.0</version>
</dependency>
```
#### 添加webhook接口
webhook需要两个接口，一个get一个post，需要地址一样。get是用来第一次绑定的时候使用，post是用来接收webhook的消息使用。我们这里直接添加一个controller，然后写一个post方法和get方法即可，需要注意的是， facebook-messenger 提供了解析和校验的逻辑，具体代码如下。里面需要的secret是我们刚刚创建app的secret，token是我们刚才创建messenger时候生成的token。
```java
public class WebhookController {
    private static boolean isFirstHook = true;

    @RequestMapping(value = "/webhook", method = RequestMethod.GET)
    @ResponseBody
    public void getWebhook(HttpServletRequest request, HttpServletResponse response) {
        String mode = request.getParameter("hub.mode");
        String token = request.getParameter("hub.verify_token");
        if (StringUtils.equals("subscribe", mode) && StringUtils.equals("EAACTECZA84coBAAN8wpjYEa46ZAecuZC5ec1KF8uNy0kLZBo6oVyIYReZAdiQVNxPp1UgX9NsRmCRRWe", token)) {
            String parameter = request.getParameter("hub.challenge");
            try {
                response.getWriter().println(parameter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("failed");
            response.setStatus(403);
        }
    }
    
    @RequestMapping(value = "/webhook", method = RequestMethod.POST)
    @ResponseBody
    public void postWebhook(HttpServletRequest request, HttpServletResponse response) {
        try {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String payload = buffer.toString();

            String xHubSignature = request.getHeader("X-Hub-Signature");
            FMClient fmClient = FMClient
                    .getInstance()
                    .withAccessToken("EAACTECZA84coBAAN8wpjYEa46ZAecuZC5ec1KF8uNy0kLZBo6oVyIYReZAdiQVNxPp1UgX9NsRmCRRWe")
                    .withAccessSecret("d15872fbd9ec0c3f6c32b")
                    .withFmMessageHandler(new FMMessageHandler() {
                        @Override
                        public void handle(FMReceiveMessage.Messaging messaging) {
                            System.out.println(messaging);
                        }
                    });
            boolean signature = fmClient.signature(payload, xHubSignature);
            if (signature) {
                if (isFirstHook) {
                    fmClient.sendSetting(FMSettingMessageBuilder
                            .defaultBuilder()
                            .withGreeting("Welcome to our messenger.")
                            .withSettingType(FMSettingMessage.SettingType.greeting)
                            .build());
                    isFirstHook = false;
                }
                fmClient.dispatch(payload);
            } else {
                response.setStatus(403);
            }
        } catch (IOException e) {
        }
    }
}
```
> FMClient 是用来接受消息和发送消息的对象。  
> isFirstHook 用来标记，第一次访问的时候才发送的消息。  
> fmClient.sendSetting 是用来设置刚点击messenger get started 时候的反馈。这里设置的是" Welcome to our messenger."。  
> fmClient.signature是用来验证是否为facebook发送来的消息  
> withFmMessageHandler是配置最简单的接受messenger的handler，下文会有更详细的介绍。    

这样以后简单的webhook 服务器就搭建好了，我们需要部署到公网上面去，这样才能剩下的 Setup Webhook的工作。

#### 部署项目
进入目录，使用如下命令运行
> mvn clean compile spring-boot:run  
如果是在EC2上面部署的话，记得配置 Security Group。同时facebook只支持https，所以你需要准备一个https的地址。

### 完成 Webhook 配置
接下来我们得到了一个webhook 的地址如下
https://youdomain.com/webhook，那么我们直接重新进入webhook配置。
为了测试消息，我们需要勾选messages，和messaging-postbacks等。
直接点击verify and save 等待校验成功就可以了，配置成功以后，下面让你选择 page events，选择你需要准备好的 page 页面， subscirbe就完成了所有的webhook配置流程。接下来我们进行更详细的消息的介绍。

## 配置
### 设置问候语
问候语就是在手机进入 Page 主页的时候，显示的内容。直接在首次登陆的时候调用如下方法即可。
```java
fmClient.sendSetting(FMSettingMessageBuilder
    .defaultBuilder()
    .withGreeting("Welcome to our messenger, you can buy anything.")
    .withSettingType(FMSettingMessage.SettingType.greeting)
    .build());
```

### 设置Get Started
get started 是进入page 主页点击 get started 按钮，出发的消息。这个可以试试纯文本，但是这里设置了一个按钮，在文本下方会展示一个按钮，点击回来以后会出发一个相应。
```java
fmClient.sendSetting(FMSettingMessageBuilder
    .defaultBuilder()
    .withSettingType(FMSettingMessage.SettingType.call_to_actions)
    .withThreadState(FMSettingMessage.ThreadState.new_thread)
    .withCallToAction(FMCallToActionBuilder
            .defaultBuilder()
            .withPayload("GETSTARTED")
            .withType(FMSettingMessage.CallActionType.postback)
            .build())
    .build());
```
上面类型是 postBack类型，意思是一个可以相应的回复按钮，payload意思是点击这个postback以后会传递一个 postback事件到服务器，会直接被我们定义的PostBackHandler接收，下文会做详细介绍。
根据当前example项目为例，当你点击getstarted的时候，服务器会收到如下信息，这样你就可以根据自己的服务器逻辑响应了。
```json
{
	"postback":{
		"payload":"GETSTARTED"
	},
	"recipient":{
		"id":"1343135295736768"
	},
	"sender":{
		"id":"1201783453235386"
	},
	"timestamp":1517989743978
}
```

### 设置菜单
菜单是在没有收到消息菜单时候保留在下面的菜单栏，类似于微信公共号下面的那些按钮。下面设置了两个菜单，分别定义了两个内容，点击的时候传回服务器Postback事件。
```java
fmClient.sendSetting(FMSettingMessageBuilder.defaultBuilder()
    .withSettingType(FMSettingMessage.SettingType.call_to_actions)
    .withThreadState(FMSettingMessage.ThreadState.existing_thread)
    .withCallToAction(FMCallToActionBuilder
                    .defaultBuilder()
                    .withPayload("DAILYFEATURED")
                    .withTitle("Daily Featured")
                    .withType(FMSettingMessage.CallActionType.postback)
                    .build(),
            FMCallToActionBuilder
                    .defaultBuilder()
                    .withPayload("POPULARTOPICS")
                    .withTitle("Popular Topics")
                    .withType(FMSettingMessage.CallActionType.postback)
                    .build())
    .build());
```
以当前example为例，当你点击Daily Featured的时候会收到如下信息
```json
{
	"postback":{
		"payload":"POPULARTOPICS"
	},
	"recipient":{
		"id":"1343135295736768"
	},
	"sender":{
		"id":"1201783453235386"
	},
	"timestamp":1517989838480
}
```
## 消息
### 发送消息
上面讲解怎么设置菜单，但是收到菜单以后，给用户响应，发送消息啊。下面就讲解具体的发送消息类型。这样为了讲解怎么样处理用户的文本，我们定义一个消息解析器，用户输入不同的内容可以返回不会的消息。我们暂定，
用户输入1返回普通文本，  
用户输入2返回普通文本带按钮，  
用户输入3反馈卡片类型，  
用户输入4反馈卡片类型并携带快速回复(Postback)。具体代码如下，我们需要注册一个MessengeHandler。
```java
package com.jiandaola.controller;

import com.alibaba.fastjson.JSON;
import com.github.codedrinker.fm.builder.*;
import com.github.codedrinker.fm.entity.FMReceiveMessage;
import com.github.codedrinker.fm.entity.FMReplyMessage;
import com.github.codedrinker.fm.handler.FMMessageHandler;
import com.github.codedrinker.fm.provider.FMProvider;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by codedrinker on 07/02/2018.
 */
public class MessengerHandler extends FMMessageHandler {
    @Override
    public void handle(FMReceiveMessage.Messaging messaging) {
        FMReceiveMessage.Messaging.Message message = messaging.getMessage();
        if (message.getQuick_reply() != null && StringUtils.isNotBlank(message.getQuick_reply().getPayload())) {
            //因为快速回复和按钮不一样，不能产生Postback，所以需要在这里处理quick reply的事件
            long time = System.currentTimeMillis();
            System.out.println(JSON.toJSONString(message, true));
        } else {
            //如果不是 quick reply，通过文本判断内容
            String text = message.getText();
            switch (text) {
                case "1":
                    FMProvider.sendMessage(FMReplyMessageBuilder
                            .textBuilder(messaging.getSender().getId(), "Text Message.")
                            .build());
                    break;
                case "2":
                    FMProvider.sendMessage(FMReplyMessageBuilder
                            .defaultBuilder()
                            .withRecipient(messaging.getSender().getId())
                            .withMessage(FMMessageBuilder
                                    .defaultBuilder()
                                    .withAttachment(
                                            FMAttachmentBuilder
                                                    .defaultBuilder()
                                                    .withText("Text messagen with btns, pls Click Me")
                                                    .withType(FMReplyMessage.TemplateType.button)
                                                    .withButtons(FMButtonBuilder
                                                            .postbackBuilder()
                                                            .withPayload("CLICKME")
                                                            .withTitle("CLICK ME")
                                                            .build())
                                                    .build())
                                    .build())
                            .build());
                    break;
                case "3":
                    FMProvider.sendMessage(FMReplyMessageBuilder
                            .defaultBuilder()
                            .withRecipient(messaging.getSender().getId())
                            .withMessage(FMMessageBuilder
                                    .defaultBuilder()
                                    .withAttachment(FMAttachmentBuilder.defaultBuilder().withElements(FMElementBuilder
                                            .defaultBuilder()
                                            .withImageUrl("https://scontent-nrt1-1.xx.fbcdn.net/v/t1.0-1/p480x480/15355831_1416125901771040_9115209388184344039_n.jpg?oh=cb178c86ea238d85456dcf8278c805f4&oe=5B24DA25")
                                            .withTitle("Card Title")
                                            .withSubtitle("Card Subtitle")
                                            .withButtons(
                                                    FMButtonBuilder
                                                            .postbackBuilder()
                                                            .withPayload("Share")
                                                            .withTitle("Share")
                                                            .build())
                                            .build()).build())
                                    .build())
                            .build());
                    break;
                case "4":
                    FMProvider.sendMessage(FMReplyMessageBuilder
                            .textBuilder(messaging.getSender().getId(), "Quick Replies")
                            .withQuickReplies(
                                    FMQuickReplyBuilder
                                            .defaultBuilder()
                                            .withTitle("1")
                                            .withContentType(FMReplyMessage.QuickReplyType.text)
                                            .withPayload("1")
                                            .build(),
                                    FMQuickReplyBuilder
                                            .defaultBuilder()
                                            .withTitle("2")
                                            .withContentType(FMReplyMessage.QuickReplyType.text)
                                            .withPayload("2")
                                            .build())
                            .build());
                    break;
            }
        }
    }
}
```
写完以后需要注册到 client
```java
client.withFmMessageHandler(new MessengerHandler())
```
#### 普通文本
所有的发送文本都是用Builder创建，发送普通文本
```java
 FMProvider.sendMessage(FMReplyMessageBuilder
        .textBuilder(messaging.getSender().getId(), "Text Message.")
        .build());
```

#### 携带按钮
```java
FMProvider.sendMessage(FMReplyMessageBuilder
    .defaultBuilder()
    .withRecipient(messaging.getSender().getId())
    .withMessage(FMMessageBuilder
            .defaultBuilder()
            .withAttachment(
                    FMAttachmentBuilder
                            .defaultBuilder()
                            .withText("Text messagen with btns, pls Click Me")
                            .withType(FMReplyMessage.TemplateType.button)
                            .withButtons(FMButtonBuilder
                                    .postbackBuilder()
                                    .withPayload("CLICKME")
                                    .withTitle("CLICK ME")
                                    .build())
                            .build())
            .build())
    .build());
```

#### 卡片类型
```java
FMProvider.sendMessage(FMReplyMessageBuilder
    .defaultBuilder()
    .withRecipient(messaging.getSender().getId())
    .withMessage(FMMessageBuilder
            .defaultBuilder()
            .withAttachment(FMAttachmentBuilder.defaultBuilder().withElements(FMElementBuilder
                    .defaultBuilder()
                    .withImageUrl("https://scontent-nrt1-1.xx.fbcdn.net/v/t1.0-1/p480x480/15355831_1416125901771040_9115209388184344039_n.jpg?oh=cb178c86ea238d85456dcf8278c805f4&oe=5B24DA25")
                    .withTitle("Card Title")
                    .withSubtitle("Card Subtitle")
                    .withButtons(
                            FMButtonBuilder
                                    .postbackBuilder()
                                    .withPayload("Share")
                                    .withTitle("Share")
                                    .build())
                    .build()).build())
            .build())
    .build());
```
#### 快速回复
快速回复是收到消息以后下面显示的快速回复按钮
```java
FMProvider.sendMessage(FMReplyMessageBuilder
    .textBuilder(messaging.getSender().getId(), "Quick Replies")
    .withQuickReplies(
            FMQuickReplyBuilder
                    .defaultBuilder()
                    .withTitle("1")
                    .withContentType(FMReplyMessage.QuickReplyType.text)
                    .withPayload("1")
                    .build(),
            FMQuickReplyBuilder
                    .defaultBuilder()
                    .withTitle("2")
                    .withContentType(FMReplyMessage.QuickReplyType.text)
                    .withPayload("2")
                    .build())
    .build());
```

### 接受消息
接受消息就比较简单了，facebook-messenger已经定义好了多个Hanler，需要继承对应的Hanler复写方法即可
#### 普通文本
继承FMMessageHandler类，复写方法。
#### 快速回复
快速回复，又名QuickReply，这个消息直接发送到了FMMessageHandler里面，需要在里面判断，上文的例子里面有代码逻辑。
#### 消息阅读
有的时候需要统计一下，多少用户阅读了消息，那么可以继承FMMessageReadHandler，然后通过senderid判断哪个用户已经阅读了消息
#### 回执消息
回执消息就是postback，最常见的一种类型，直接继承FMMessagePostBackHandler即可。


## 异常处理
在调用的时候发现不正常，可以添加错误拦截器打印错误信息。
```java
fmClient.withFmResultAspect(new FMResultAspect() {
        @Override
        public void handle(FMResult fmResult) {
            System.out.println(JSON.toJSONString(fmClient, true));
        }
    });
```

## 依赖问题
如果出现了依赖有问题，可以查看源码或提交修复，[地址](https://github.com/codedrinker/facebook-messenger)。
