# dingtalk-spring-boot-starter
dingtalk starter for spring boot

### 组件简介

> 基于钉钉 SDK 的 Spring Boot Starter 实现
> 
> sdasd 

### 使用说明

##### 1、Spring Boot 项目添加 Maven 依赖

``` xml
<dependency>
	<groupId>com.github.hiwepy</groupId>
	<artifactId>dingtalk-spring-boot-starter</artifactId>
	<version>2.0.1.RELEASE</version>
</dependency>
```

##### 2、在`application.yml`文件中增加如下配置

```yaml
#################################################################################################
### dingtalk 配置：
#################################################################################################
dingtalk:
  corp-id: 企业ID
  corp-apps:
  - agent-id: 企业内部开发：程序客户端ID
    app-key: 企业内部开发：应用的唯一标识key
    app-secret: 企业内部开发：应用的密钥
  apps:
  - app-id: 企业内部开发：应用的唯一标识key
    app-secret: 企业内部开发：应用的密钥
  logins:
  - app-id: 移动接入应用-扫码登录应用的appId
    app-secret: 移动接入应用-扫码登录应用的appSecret
  robots:
  - robot-id: robot-
    access-token: xxxx
    secret-token: xxxxx  
```

##### 3、使用示例

```java
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DingTalkApplication_Test {

	@Autowired
	private DingTalkTemplate template;
	
	@PostConstruct
	public void testSendSms() {

		try {
			
			template.opsForAccount().getUserinfoBycode(null, null);
			template.opsForJsapi().createSignature(null, null, null);
			template.opsForRobot().sendLinkMessage(null, null);
			template.opsForSns().getUserinfo(null);
			template.opsForSso().getPersistentCode(null, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DingTalkApplication_Test.class, args);
	}
    
}
```

## Jeebiz 技术社区

Jeebiz 技术社区 **微信公共号**、**小程序**，欢迎关注反馈意见和一起交流，关注公众号回复「Jeebiz」拉你入群。

|公共号|小程序|
|---|---|
| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/qrcode_for_gh_1d965ea2dfd1_344.jpg)| ![](https://raw.githubusercontent.com/hiwepy/static/main/images/gh_09d7d00da63e_344.jpg)|
