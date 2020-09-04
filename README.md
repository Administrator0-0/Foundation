# Foundation

​	Foundation旨在提高构建MVVM项目和项目编写的速度。

​	**提示**: Foundation 需要至少 Java 8+ 和 Android API 16+。

## Usage

### 1. HttpComponent

​		HttpComponent可以简化使用Retrofit。

​		getJsonRequestBody()可以获取RESTFUL规范的请求体，getGsonService()接收您自定义的Retrofit接口并返回RESTFUL规范的Retrofit Service。

​		nestedRequest()和flatMap()可以帮助您更加清晰地调用嵌套的网络请求，类似于RxJava，您需要提供从上一个请求的结果转换到这个请求的Call的接口。

​		对于每个请求您可以调用addCallback()添加请求回调。

​		当所有链式调用完毕后，您可以调用execute()开始网络请求。

```kotlin
        val loginSend = LoginSend()
        loginSend.setEmail("233@233.com")
        loginSend.setPassword("233233")
        val requestBody = Foundation.getHttp().getJsonRequestBody(loginSend)
        val service = Foundation.getHttp().getGsonService("http://123:8000/", RetrofitService::class)
        val call = service.login(requestBody)
        Foundation.getHttp().nestedRequest(call)
            .flatMap<CommonReturn>(object: HttpMapFun<CommonReturn, CommonReturn> {
                override fun map(result: HttpResult<CommonReturn>?): Call<CommonReturn> {
                    val loginSend2 = LoginSend()
                    loginSend2.setEmail("233@233.com")
                    loginSend2.setPassword("233233")
                    val requestBody2 = Foundation.getHttp().getJsonRequestBody(loginSend2)
                    val service2 = Foundation.getHttp().getGsonService("http://123:8000/", RetrofitService::class)
                    return service2.login(requestBody2)
                }
            })
            .flatMap<CommonReturn>(object: HttpMapFun<CommonReturn, CommonReturn> {
                override fun map(result: HttpResult<CommonReturn>?): Call<CommonReturn> {
                    val loginSend2 = LoginSend()
                    loginSend2.setEmail("233@233.com")
                    loginSend2.setPassword("233233")
                    val requestBody2 = Foundation.getHttp().getJsonRequestBody(loginSend2)
                    val service2 = Foundation.getHttp().getGsonService("http://123:8000/", RetrofitService::class)
                    return service2.login(requestBody2)
                }
            })
            .addCallback(object : HttpCallback<CommonReturn> {
                override fun onSuccess(result: HttpResult<CommonReturn>?) {
                }
                override fun onFail(result: ResponseBody) {
                }
                override fun onFail() {
                }
                override fun onFail(errorMessage: String?) {
                }

            })
            .execute()
```

​		您也可以直接使用requestHttp()发起一个网络请求。

```kotlin
	val loginSend = LoginSend()
        loginSend.setEmail("233@233.com")
        loginSend.setPassword("233233")
        val requestBody = Foundation.getHttp().getJsonRequestBody(loginSend)
        val service = Foundation.getHttp().getGsonService("http://123:8000/", RetrofitService::class)
        val call = service.login(requestBody)
        Foundation.getHttp().requestHttp(call, object : HttpCallback<CommonReturn> {
            override fun onSuccess(result: HttpResult<CommonReturn>?) {
      
            }

            override fun onFail(result: ResponseBody) {

            }

            override fun onFail() {
       
            }

            override fun onFail(errorMessage: String?) {
        
            }

        })
```

### 2. PermissionComponent

​			Foundation集成了PermissionX，您可以很简单地请求权限。

```kotlin
Foundation.getPermission().requestPermission(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE)
```

### 3. EventComponent

​			Foundation集成了EventBus。

​			您可以调用 Foundation.fullBind() 或者 Foundation.getEvent().register() 去注册EventBus。

​			Foundation提供了携带消息的载体EventMsg，您可以继承它，实现自己的消息载体。

```kotlin
Foundation.fullBind(this)
// or
Foundation.getEvent().register(this)

Foundation.getEvent().postEvent("test","hello")
```

### 4. ViewModelComponent

​		Foundation提供了简化使用MVVM架构的方法。

​		ObserveFun 是一个应用于方法的注解，您需要提供liveData的获取方式，Foundation便可以为您快捷的将liveData与该方法做订阅。

​		您需要在Activity或者Fragment中调用Foundation.bind()或者Foundation.fullBind()方法使得订阅生效，其中fullBind会注册EventBus。

```kotlin
    @ObserveFun("viewModel.getTest()")
    fun test() {
    }
    @ObserveFun("viewModel.getTest()")
    fun test2() {
    }
```

### 5. LogComponent

​		您可以使用LogComponent去打印出类似logger的日志，默认TAG为Foundation，支持自定义TAG，打印异常。您可以使用Foundation.getLog().init()方法自定义是否打印线程信息、位置、消息。

​		LogComponent支持格式化打印Json字符串与Map的键值对。

```kotlin
Foundation.getLog().init(threadEnable = false, locationEnable = true, messageEnable = true)
Foundation.getLog().d("xxx")
Foundation.getLog().j(json)
Foundation.getLog().m(map)
```

​		LogComponent提供自动捕获crash信息，并上传至服务器或者存储至本地的方法。您需要调用Foundation.getLog().openAutoUpload()方法去开启是否自动上传或者保存至本地。您可以调用Foundation.getLog().setStorePath()去设置保存的路径，您也可以使用Foundation.getLog().closeAutoUpload()去关闭LogComponent抓捕crash信息。

### 6.Utils

​		Foundation提供封装好的util供您使用。

  1. DateUtil

     ​	提供常见的日期处理方法。

  2. EncryptUtil

     ​	提供加密字符串的方法。

  3. SPUtil

     ​	提供便捷使用SharePerference的方法。

### 7.Extra

​	Foundation集成了AutoSize，其可以便捷地支持屏幕适配，必须在项目中的Manifest文件中添加meta-data，提供宽高参考。

## Releases

​	您需要添加jitpack仓库。

```groovy
maven { url 'https://jitpack.io' }
```

​	添加如下依赖使用Foundation。

```groovy
implementation 'com.github.Administrator0-0.Foundation:foundation-lib:1.0.0'
annotationProcessor 'com.github.Administrator0-0.Foundation:foundation-processor:1.0.0'
```

## License

```
MIT License

Copyright (c) 2020 Almoon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

