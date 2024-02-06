package org.example.interceptor

import io.grpc.*

class LogClientInterceptor : ClientInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        p0: MethodDescriptor<ReqT, RespT>, p1: CallOptions, p2: Channel
    ): ClientCall<ReqT, RespT> {
        println("Outgoing request from ${p0.fullMethodName}")
        return p2.newCall(p0, p1)
    }
}