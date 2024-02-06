import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.example.CalculatorService
import org.example.CalculatorServiceOuterClass
import org.example.CalculatorServiceOuterClass.CalculatorRequest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CalculatorServiceTest {
    @Test
    fun addition() = runTest {
        val res = CalculatorService().addition(CalculatorRequest.newBuilder().setNumber1(1.0).setNumber2(2.0).build())
        assertEquals(3.0, res.result)
    }

    @Test
    fun subtraction() = runTest {
        val res =
            CalculatorService().subtraction(CalculatorRequest.newBuilder().setNumber1(2.0).setNumber2(1.0).build())
        assertEquals(1.0, res.result)
    }

    @Test
    fun multiplication() = runTest {
        val res =
            CalculatorService().multiplication(CalculatorRequest.newBuilder().setNumber1(2.0).setNumber2(1.0).build())
        assertEquals(2.0, res.result)
    }

    @Test
    fun division() = runTest {
        val res = CalculatorService().division(CalculatorRequest.newBuilder().setNumber1(2.0).setNumber2(1.0).build())
        assertEquals(2.0, res.result)
    }

    @Test
    fun getPrimeNumber() = runTest {
        val res =
            CalculatorService().getPrimeNumber(CalculatorServiceOuterClass.Number.newBuilder().setNumber(10).build())
        val primeNos = res.map { n -> n.number }.toList()
        assertEquals(listOf(2, 3, 5, 7), primeNos)
    }

    @Test
    fun calculateAverage() = runTest {
        val requests = flow<CalculatorServiceOuterClass.Number> {
            emit(CalculatorServiceOuterClass.Number.newBuilder().setNumber(2).build())
            emit(CalculatorServiceOuterClass.Number.newBuilder().setNumber(4).build())
        }
        val res = CalculatorService().calculateAverage(requests)
        assertEquals(3, res.number)
    }

    @Test
    fun calculateSquareOfNumbers() = runTest {
        val requests = flow<CalculatorServiceOuterClass.Number> {
            emit(CalculatorServiceOuterClass.Number.newBuilder().setNumber(2).build())
            emit(CalculatorServiceOuterClass.Number.newBuilder().setNumber(4).build())
        }
        val res = CalculatorService().calculateSquareOfNumbers(requests)
        assertEquals(listOf(4, 16), res.map { n -> n.number }.toList())
    }


}