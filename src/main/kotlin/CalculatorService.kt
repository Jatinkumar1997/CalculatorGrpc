package org.example

import io.grpc.Status.INVALID_ARGUMENT
import io.grpc.StatusRuntimeException
import kotlinx.coroutines.flow.*
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
        if (request.number2 == 0.0) {
            throw StatusRuntimeException(INVALID_ARGUMENT.withDescription("Division by 0 not valid"))
        }
        return CalculatorReply.newBuilder().setResult(request.number1 / request.number2).build()
    }

    override fun getPrimeNumber(request: CalculatorServiceOuterClass.Number): Flow<CalculatorServiceOuterClass.Number> {
        val res = mutableListOf<CalculatorServiceOuterClass.Number>()
        for (i in 2 until request.number) {
            if (isPrime(i)) {
                res.add(CalculatorServiceOuterClass.Number.newBuilder().setNumber(i).build())
            }
        }
        return res.asFlow()
    }

    override suspend fun calculateAverage(requests: Flow<CalculatorServiceOuterClass.Number>): CalculatorServiceOuterClass.Number {
        val numbers = requests.toList().map { req -> req.number }
        return CalculatorServiceOuterClass.Number.newBuilder().setNumber(numbers.average().toInt()).build()
    }

    override fun calculateSquareOfNumbers(requests: Flow<CalculatorServiceOuterClass.Number>): Flow<CalculatorServiceOuterClass.Number> {
        return flow {
            val numbers = requests.map { num -> num.number }.toList()
            numbers.forEach { number ->
                emit(CalculatorServiceOuterClass.Number.newBuilder().setNumber(number * number).build())
            }
        }
    }

    private fun isPrime(num: Int): Boolean {
        for (i in 2..num / 2) {
            if (num % i == 0) {
                return false
            }
        }
        return true
    }
}