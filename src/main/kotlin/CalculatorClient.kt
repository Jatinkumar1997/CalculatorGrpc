package org.example

import io.grpc.ManagedChannelBuilder

class CalculatorClient {
    fun calClientMethods() {
        val channel = ManagedChannelBuilder.forAddress("localhost", 15001).usePlaintext().build()
        val stub = CalculatorServiceGrpc.newBlockingStub(channel)
        val responseAddition = stub.addition(
            CalculatorServiceOuterClass.CalculatorRequest.newBuilder().setNumber1(1.0).setNumber2(2.0).build()
        )
        val responseSubtraction = stub.subtraction(
            CalculatorServiceOuterClass.CalculatorRequest.newBuilder().setNumber1(1.0).setNumber2(2.0).build()
        )
        val responseMultiplication = stub.multiplication(
            CalculatorServiceOuterClass.CalculatorRequest.newBuilder().setNumber1(1.0).setNumber2(2.0).build()
        )
        val responseDivision = stub.division(
            CalculatorServiceOuterClass.CalculatorRequest.newBuilder().setNumber1(1.0).setNumber2(0.0).build()
        )

        println("Addition response: $responseAddition")
        println("Subtraction response: $responseSubtraction")
        println("Multiplication response: $responseMultiplication")
        println("Division response: $responseDivision")
    }
}