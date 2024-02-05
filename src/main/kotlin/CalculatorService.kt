package org.example

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
        if (num <= 1) return false
        if (num <= 3) return true
        if (num % 2 == 0 || num % 3 == 0) return false
        var n = num
        for (i in 5 until n step 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false
            n = i * i
        }
        return true
    }
}