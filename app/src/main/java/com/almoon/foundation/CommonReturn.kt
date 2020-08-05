package com.almoon.foundation

/**
 * 一般情况的返回数据格式
 */
class CommonReturn {
    /**
     * code : 200
     * data : {"token":"eyJhbGciOiJIUzUxMiIsImlhdCI6MTU5MjA1OTc2MywiZXhwIjoxNTkyMzE4OTYzfQ.eyJ1aWQiOjJ9.pw_mxp3maiD1Ukol9DwuDAVVZ6ovaFYvaGQMw0Tr5tiyAKtHbOQynreKcxD4XTa-AyJ-9dBbYhy073JfHVb5sA","username":"HouTaiQiang"}
     * message : 登录成功
     */
    private var code = 0
    private var data: DataBean? = null
    private var message: String? = null

    fun getCode(): Int {
        return code
    }

    fun setCode(code: Int) {
        this.code = code
    }

    fun getData(): DataBean? {
        return data
    }

    fun setData(data: DataBean?) {
        this.data = data
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    class DataBean {
        /**
         * token : eyJhbGciOiJIUzUxMiIsImlhdCI6MTU5MjA1OTc2MywiZXhwIjoxNTkyMzE4OTYzfQ.eyJ1aWQiOjJ9.pw_mxp3maiD1Ukol9DwuDAVVZ6ovaFYvaGQMw0Tr5tiyAKtHbOQynreKcxD4XTa-AyJ-9dBbYhy073JfHVb5sA
         * username : HouTaiQiang
         */
        var token: String? = null
        var username: String? = null

    }

}