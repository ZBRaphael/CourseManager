package com.example.startrace.base

/**
 * ClassName:Session
 * Created bu ZhangBo at 2020/6/26
 * Describe:
 **/
public class Session {

    companion object {
        lateinit var instance: Session
    }

    init {
        instance = this
    }
    var sessionId: String? = null
        get() = field
        set(value) {
            field = value
        }
    var userId: String? = null
        get() = field
        set(value) {
            field = value
        }
}