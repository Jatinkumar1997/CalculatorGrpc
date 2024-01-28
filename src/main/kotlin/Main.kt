package org.example

import io.grpc.ServerBuilder

fun main() {
    helloServer()
    CalculatorClient().calClientMethods()
}

fun helloServer() {
    val helloService = CalculatorService()
    val server = ServerBuilder.forPort(15001).addService(helloService).build()

    Runtime.getRuntime().addShutdownHook(Thread {
        server.shutdown()
        server.awaitTermination()
    })

    server.start()
    println("server started")
}