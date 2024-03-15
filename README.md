# 概述

随着云视频会议的越来越普及，企业沟通中随时拉会的场景越来越多，云舟会议赋能企业内部各种应用随时拉起视频通话或者视频会议，让您的应用轻松插上即时通话和会议的翅膀。

云舟会议SDK是自带UI功能的音视频通话和音视频会议SDK，具有如下特点：
1. 集成简单：用户只需简单几行代码就可以拥有音视频通话和音视频会议的功能；
2. 功能全面：具备了会议创建，会议加入，呼叫，被叫，共享桌面，录制，会控等音视频会议的常用功能；
3. 平台能力稳定：音视频会议基于稳定的RTC平台构建，具备网络切换，断网重连，弱网重传等功能。支持H264和AV1等先进的编码方式，保证了低码率和画质。
4. 私有化部署：满足企业安全需求，方便企业资产结算。


# 集成必读

在集成SDK之前，需要做如下提前准备：
1. 需要部署好音视频服务端，确认好会议服务器的地址和端口;
2. 集成本SDK的APP需要有用户体系，调用SDK时需要指定用户名，用户ID，用户头像地址这些用户信息；
3. 集成会议功能时，集成本SDK的APP需要提供创建会议或者加入会议的入口交互能力，如果是加入会议则需要提供会议号;
4. 集成通话功能时，集成本SDK的APP的系统需要具备推送机制，APP需要具备呼叫入口交互能力，接收呼叫推送后调起本SDK的被叫接口。


# 快速开始

# Android集成

## 集成准备

### 开发环境要求

JDK版本：11
Gradle版本：6.1.1
Gradle插件版本：4.0.1

以上为推荐配置，若项目本身构建时依赖的JDK版本为11以下，请先阅读常见问题说明，再进行编译。
注意，**编译运行Demo只需看获取node_modules内容即可**。

### 获取node_modules


打开示例工程，从https://github.com/yunzhoucomm/yzmeeting-sdk-android 下载package.json, react-native.config.js文件并拷贝到主工程的根目录下。（和`settings.gradle`平级）

打开命令行，cd到根目录下，执行`yarn install`， 等执行完毕则会在根目录下生成node_modules文件夹。

#### Mac环境

使用HomeBrew安装yarn命令：

```java
brew install yarn
```

没有HomeBrew的话通过以下命令先安装

```java
/bin/zsh -c "$(curl -fsSL https://gitee.com/cunkai/HomebrewCN/raw/master/Homebrew.sh)"
```

#### Windows环境

先安装Node.js，从[官网](https://nodejs.org/en)下载最新的LTS版本。输入命令`node -v`，`npm -v`检查是否安装成功。

再安装yarn，输入命令`yarn -v`检查是否安装成功。

```
npm install -g yarn
```

如果因下载较慢或其他原因，也可以先联系音视频管理员获取当前版本的node_modules文件夹。

### 获取会议服务器地址
如果自己部署会议服务器，会议服务器地址为自己部署的会议服务器地址
如果需要使用云舟会议服务器，请联系支持人员

## 手动集成步骤

### 导入aar包和所需的so文件

将示例工程内的aar文件和so文件（`/yzmeeting-demo-android/app/libs/`）拷入到主工程的libs目录下。

```
├── libs
│   ├── YunZhouMeeting.aar
│   └── arm64-v8a
│       └── libjsc.so
```


### 添加依赖

打开setting.gradle文件，添加以下代码

```
apply from: file("node_modules/@react-native-community/cli-platform-android/native_modules.gradle"); applyNativeModulesSettingsGradle(settings)
```


参考示例程序里/yzmeeting-demo-android/app的build.gradle，添加aar依赖

```java
dependencies {
    implementation(name: 'YunZhouMeeting', ext: 'aar')
}
```

除了依赖以上aar文件，请在示例工程里搜索`USE_DEMO`，找到所有相关依赖项并添加到对应位置。

```
dependencies {

  if(USE_DEMO.toBoolean()){
		 // …
	}

}
```


在`packagingOptions`里排除以下内容
```java
packagingOptions {
     exclude 'META-INF/minyu_livekit-react-native_debug.kotlin_module'
     exclude 'META-INF/minyu_livekit-react-native_release.kotlin_module'
     exclude 'META-INF/livekitreactnative_debug.kotlin_module'
     exclude 'META-INF/livekitreactnative_release.kotlin_module'
     exclude 'META-INF/audioswitch_release.kotlin_module'
}
```

### 混淆设置

为避免SDK被错误混淆而导致不可用，请在混淆文件中添加以下配置：

```
-keep class org.webrtc.** {*;}
-dontwarn com.facebook.react.**
-keep class com.facebook.react.** {*;}
-keep public class com.horcrux.svg.** {*;}
```

## 接口说明

### 全局初始化

仅需调用以下方法完成初始化
```java
YunZhouMeetingAPI.getInstance().init(this);
```
### 启动会议
```java
YunZhouMeetingAPI.getInstance().start(this, options);
```
| 参数 | 含义 |
| :--- | ---- |
| context    | 当前Activity的上下文 |
| options    | MeetingOptions对象，包装会议信息的参数 |

示例：
```java
MeetingOptions options = new MeetingOptions.Builder()
      .entryType(Constant.MEET_CONFIG_PAGE_ENTRY_VALUE_CREATE)
      .userInfo(new User("","",""))
      .serverUrl("")
      .build();
```
MeetingOptions参数说明：

| 参数           | 含义                                                    |
| :------------- | ------------------------------------------------------- |
| entryType       | 标识创建或加入会议，创建会议传create， 加入会议传join   |
| userInfo        | 设置参会人信息，构造参数依次为用户id， 用户名，用户头像(头像静态资源地址，不填只能显示默认头像) |
| serverUrl       | 设置会议服务器地址                                      |

entryType参数传值说明：
```
// 创建会议
public static final String MEET_CONFIG_PAGE_ENTRY_VALUE_CREATE = "create"; 
// 加入会议
public static final String MEET_CONFIG_PAGE_ENTRY_VALUE_JOIN = "join";

```

# 常见问题
## Android

1、Mac编译报错

```
> Cannot run program “node”: error=2, No such file or directory
```

常见为项目使用了JDK8等低于11的版本构建的，可以使用命令行打开Android Studio，`open '/Applications/Android Studio Fox.app’`。
若不生效，可能是由于Android Studio版本的兼容性问题，请下载Arctic Fox版本。

