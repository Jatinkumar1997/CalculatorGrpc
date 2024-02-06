package org.example

import io.grpc.ManagedChannelBuilder
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.example.CalculatorServiceOuterClass.Number
import org.example.interceptor.LogClientInterceptor

class CalculatorClient {
    fun callClientMethods() {
        val channel =
            ManagedChannelBuilder.forAddress("localhost", 15001).usePlaintext().intercept(LogClientInterceptor())
                .build()
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
        try {
            val responseDivision = stub.division(
                CalculatorServiceOuterClass.CalculatorRequest.newBuilder().setNumber1(1.0).setNumber2(0.0).build()
            )
            println("Division response: $responseDivision")
        } catch (e: StatusRuntimeException) {
            println("Error occurred while division: $e")
        }

        println("Addition response: $responseAddition")
        println("Subtraction response: $responseSubtraction")
        println("Multiplication response: $responseMultiplication")

        val resPrimeNos = stub.getPrimeNumber(Number.newBuilder().setNumber(10).build())

        resPrimeNos.forEach { prime ->
            println("prime number response : $prime")
        }

        val coroutineStub = CalculatorServiceGrpc.newStub(channel)
        val numbers = listOf(
            Number.newBuilder().setNumber(4).build(),
            Number.newBuilder().setNumber(2).build(),
            Number.newBuilder().setNumber(3).build(),
            Number.newBuilder().setNumber(5).build(),
            Number.newBuilder().setNumber(1).build()
        )
        val observer = object : StreamObserver<Number> {
            override fun onNext(p0: Number?) {
                if (p0 != null) {
                    println("Response : ${p0.number}")
                } else println("null response")
            }

            override fun onError(p0: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onCompleted() {
                println("Finished responding...")
            }
        }

        val requestObsAvg = coroutineStub.calculateAverage(observer)
        val reqObserverSquare = coroutineStub.calculateSquareOfNumbers(observer)
        for (num in numbers) {
            println("Request : ${num.number}")
            requestObsAvg.onNext(num)
            reqObserverSquare.onNext(num)
        }
        requestObsAvg.onCompleted()
        reqObserverSquare.onCompleted()

    }
}