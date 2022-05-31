import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

class QuestionTest {

    val user = User(1, "Alice")

    @Nested
    inner class Constructor {

        @Test
        fun `should throw an exception if the title is empty`() {

            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, "", "question")
            }
        }

        @Test
        fun `should throw an exception if the body is empty`() {

            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, "title", "")
            }
        }

        @Test
        fun `should not throw an exception if the question is valid`() {

            Assertions.assertDoesNotThrow() {
                Question(1, user, "title", "question")
            }
        }

        @ParameterizedTest
        @CsvSource("' ', question",
        "'', question",
        "title, ' '",
        "title, ''"
        )
        fun `should throw an exception if title or question is invalid`(title: String, body: String) {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, title, body)
            }
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    inner class `Constructor should with method source` {

        fun titlesAndQuestions() = listOf(
            Arguments.of("", "question"),
            Arguments.of(" ", "question"),
            Arguments.of("title", ""),
            Arguments.of("title", " "),
        )

        @ParameterizedTest
        @MethodSource("titlesAndQuestions")
        fun `throw an exception if title or question is invalid`(title: String, question: String) {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, title, question)
            }
        }

    }
}