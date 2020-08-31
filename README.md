# Cordova Plugin Yidun Guardian

易盾反作弊cordova原生插件版，目前仅支持 Android

## Installation

```
cordova plugin add cordova-plugin-yidun-guardian

```

## 反作弊接入

### 初始化
```js
  var $actionEl = document.getElementById('detectAction');  // 显示动作提示的元素
  var instance = new YidunGuardian({
          isCollectApk: false,  // Boolean, 是否允许采集本机已安装的软件，默认 false
          isCollectSensor: false,  // Boolean, 是否允许开始采集传感器实时数据，默认 false
          channel: '',  // String, APP渠道名称，默认空
          productNumber: '从易盾申请的productNumber'
        }, function (ev) {
          if (ev['is_init_success'] === true) { // 初始化反作弊成功
            // TODO: do something ....
            return
          } else if (ev['is_init_success'] === false) { // 初始化反作弊失败
            // TODO: do something ....
            return
          }

          if (ev['is_get_token_success'] === true) { // getToken 成功
            // TODO: 处理token, ev['token']，从 ev['token'] 获取到 token
            // do something ....
            console.log(ev['token'])
            return
          } else if (ev['is_get_token_success'] === false) {
            // TODO: do something ....
            return
          }
        }
      );
```

### getToken 接口
```js
// timeout 最长超时时间，单位毫秒。若没指定，则默认3000毫秒；timeout可设置范围为1000~10000毫秒
  instance.getToken(timeout: number)
```

### setSeniorCollectStatus 接口
```js
  // 该接口用于实时控制传感器数据的采集，可随时启用或关闭采集功能，但是必须在init之后方可调用该接口
  instance.setSeniorCollectStatus(flag: boolean)
```

## 简单示例app

<a href="https://github.com/yidun/cordova-plugin-guardian-demo">cordova-plugin-guardian-demo</a> for a complete working Cordova example for Android platforms.

