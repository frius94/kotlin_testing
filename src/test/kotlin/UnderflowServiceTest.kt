import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UnderflowServiceTest {

    @Nested
    inner class UnderflowService {
        val mockQuestionRepository = mockk<IQuestionRepository>(relaxUnitFun = true)
        val mockUserRepository = mockk<IUserRepository>(relaxUnitFun = true)
        val service = UnderflowService(mockQuestionRepository, mockUserRepository)
        val questionId = 1
        val userId = 1
        val voterId = 20

        @Test
        fun `should be able to initialise service`() {
            assertNotNull(service)
        }

        @Test
        fun `should be able to vote up question`() {
            val user = User(userId, "Alice")
            val question = Question(questionId, user, "title", "question")

            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voterId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
//            every { mockQuestionRepository.update(question) } just Runs
//            every { mockUserRepository.update(user) } just Runs

            val votes = service.voteUpQuestion(questionId, voterId)

            assertEquals(3, votes)
        }

        @Test
        fun `should throw an exception if the question id is invalid`() {
            val user = User(1, "Alice")
            val question = Question(2, user, "title", "question")

            every { mockQuestionRepository.findQuestion(questionId) } throws Exception()

            assertThrows<ServiceException> { service.voteUpQuestion(questionId, voterId) }
        }
    }

    @Nested
    inner class WithAnnotations {

        @RelaxedMockK
        lateinit var mockQuestionRepository: IQuestionRepository

        @RelaxedMockK
        lateinit var mockUserRepository: IUserRepository

        init {
            MockKAnnotations.init(this)
        }

        val service = UnderflowService(mockQuestionRepository, mockUserRepository)
        val questionId = 1
        val userId = 1
        val voterId = 20

        @Test
        fun `should be able to vote up question`() {
            val user = User(userId, "Alice")
            val question = Question(questionId, user, "title", "question")

            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voterId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
//            every { mockQuestionRepository.update(question) } just Runs
//            every { mockUserRepository.update(user) } just Runs

            val votes = service.voteUpQuestion(questionId, voterId)

            assertEquals(3, votes)
        }
    }

    @Nested
    @ExtendWith(MockKExtension::class)
    inner class WithAnnotationsUsingJunit {

        @RelaxedMockK
        lateinit var mockQuestionRepository: IQuestionRepository

        @RelaxedMockK
        lateinit var mockUserRepository: IUserRepository

//        init {
//            MockKAnnotations.init(this)
//        }

//        val service = UnderflowService(mockQuestionRepository, mockUserRepository)

        val questionId = 1
        val userId = 1
        val voterId = 20

        @Test
        fun `should be able to vote up question`() {
            val service = UnderflowService(mockQuestionRepository, mockUserRepository)
            val user = User(userId, "Alice")
            val question = Question(questionId, user, "title", "question")

            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voterId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
//            every { mockQuestionRepository.update(question) } just Runs
//            every { mockUserRepository.update(user) } just Runs

            val votes = service.voteUpQuestion(questionId, voterId)

            assertEquals(3, votes)
        }
    }
}