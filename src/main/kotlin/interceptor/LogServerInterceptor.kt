package org.example.interceptor

import io.grpc.*

class LogServerInterceptor : ServerInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        p0: ServerCall<ReqT, RespT>, p1: Metadata, p2: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        println("Incoming request to ${p0.methodDescriptor.fullMethodName}")
        return p2.startCall(p0, p1)
    }

}