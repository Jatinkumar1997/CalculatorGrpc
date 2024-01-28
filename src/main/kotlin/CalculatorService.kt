package org.example

import org.example.CalculatorServiceOuterClass.CalculatorReply

class CalculatorService : CalculatorServiceGrpcKt.CalculatorServiceCoroutineImplBase() {
    override suspend fun addition(request: CalculatorServiceOuterClass.CalculatorRequest): CalculatorServiceOuterClass.CalculatorReply {
        return CalculatorReply.newBuilder().setResult(request.number1 + request.number2).build()
    }

    override suspend fun subtraction(request: CalculatorServiceOuterClass.CalculatorRequest): CalculatorServiceOuterClass.CalculatorReply {
        return CalculatorReply.newBuilder().setResult(request.number1 - request.number2).build()
    }

    override suspend fun multiplication(request: CalculatorServiceOuterClass.CalculatorRequest): CalculatorServiceOuterClass.CalculatorReply {
        return CalculatorReply.newBuilder().setResult(request.number1 * request.number2).build()
    }

    override suspend fun division(request: CalculatorServiceOuterClass.CalculatorRequest): CalculatorServiceOuterClass.CalculatorReply {
        return CalculatorReply.newBuilder().setResult(request.number1 / request.number2).build()
    }
}