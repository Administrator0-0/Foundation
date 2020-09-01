# Foundation

​	For easily buildng a MVVM project & promoting the speed of coding.

​	**Attention**: Foundation requires at minimum Java 8+ & Android API 16+.

## Fast Step

### 1. HttpComponent

​		This program will be more clear for calling nested http requests by retrofit

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
                    Log.d("aaa", "1"+ result!!.getData()!!.getMessage())
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
                    Log.d("aaa", "1"+ result!!.getData()!!.getMessage())
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
                    Log.d("aaa", "2"+ result!!.getData()!!.getMessage())
                }

                override fun onFail(result: ResponseBody) {
                    TODO("Not yet implemented")
                }

                override fun onFail() {
                    TODO("Not yet implemented")
                }

                override fun onFail(errorMessage: String?) {
                    TODO("Not yet implemented")
                }

            })
            .execute()
```

​		This program will help you easily call an http request with a callback

```kotlin
	val loginSend = LoginSend()
        loginSend.setEmail("233@233.com")
        loginSend.setPassword("233233")
        val requestBody = Foundation.getHttp().getJsonRequestBody(loginSend)
        val service = Foundation.getHttp().getGsonService("http://123:8000/", RetrofitService::class)
        val call = service.login(requestBody)
        Foundation.getHttp().requestHttp(call, object : HttpCallback<CommonReturn> {
            override fun onSuccess(result: HttpResult<CommonReturn>?) {
                TODO("Not yet implemented")
            }

            override fun onFail(result: ResponseBody) {
                TODO("Not yet implemented")
            }

            override fun onFail() {
                TODO("Not yet implemented")
            }

            override fun onFail(errorMessage: String?) {
                TODO("Not yet implemented")
            }

        })
```

### 2. PermissionComponent

​			This program will help you easily request permissions by permissionX

```kotlin
Foundation.getPermission().requestPermission(this, 1, Manifest.permission.WRITE_EXTERNAL_STORAGE)
```

### 3. EventComponent

​			This program will help you easily use enventBus

​			You can invoke Foundation.bind() or Foundation.getEvent().register() to register your object

```kotlin
Foundation.bind(this)
// or
Foundation.getEvent().register(this)

Foundation.getEvent().postEvent("test","hello")
```

### 4. ViewModelComponent

​		You can use ObserveFun(String) to fast make liveData to observe your methods

​		ObserveFun needs a string param , getting your liveData 

```kotlin
    @ObserveFun("viewModel.getTest()")
    fun test() {
        Log.d(TAG,"Yes")
    }
    @ObserveFun("viewModel.getTest()")
    fun test2() {
        Log.d(TAG,"No")
    }
```

### 5.Utils

​		There are 3 utils and you can extend them & replace them to use your utils

  1. DateUtil

     ​	It provides some methods to easily proceed some date

  2. EncryptUtil

     ​	It provides some methods to easily encrypt your string

  3. SPUtil

     ​	It provides some methods to easily use SharePerference

## Releases

​	You should add repository of jitpack

```groovy
maven { url 'https://jitpack.io' }
```

​	Add dependencies to use Foundation

```groovy
implementation 'com.github.Administrator0-0.Foundation:foundation-lib:0.01'
annotationProcessor 'com.github.Administrator0-0.Foundation:foundation-processor:0.01'
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

