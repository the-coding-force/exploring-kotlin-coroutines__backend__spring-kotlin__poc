package the.coding.force.exploring_kotlin_coroutines

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import the.coding.force.exploring_kotlin_coroutines.repository.DataRepository

@AutoConfigureMockMvc
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = [ExploringKotlinCoroutinesApplication::class]
)
@ActiveProfiles("test")
class IntegrationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var repository: DataRepository

    @BeforeEach
    fun beforeEach() {
        repository.deleteAll()
    }
}
