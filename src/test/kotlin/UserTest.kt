import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserTest {

    val user = User(1, "Alice")

    @Test
    fun `should be able to increase reputation`() {
        user.changeReputation(10)
        assertEquals(10, user.reputation)
    }

    @Test
    fun `should be able to decrease reputation`() {
        user.changeReputation(10)
        user.changeReputation(-5)
        assertEquals(5, user.reputation)
    }
}