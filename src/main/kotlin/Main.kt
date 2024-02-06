package org.example

import io.grpc.ServerBuilder
import org.example.interceptor.LogServerInterceptor

fun main() {
    helloServer()
    CalculatorClient().callClientMethods()
}

fun helloServer() {
    val calculatorService = CalculatorService()
    val server = ServerBuilder.forPort(15001).addService(calculatorService).intercept(LogServerInterceptor()).build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
        server.awaitTermination()
    })

    server.start()
    println("server started")
}