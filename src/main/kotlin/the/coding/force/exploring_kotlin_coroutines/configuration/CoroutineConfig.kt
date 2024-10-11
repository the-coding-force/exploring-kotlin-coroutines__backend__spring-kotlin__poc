package the.coding.force.exploring_kotlin_coroutines.configuration

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.reactor.asCoroutineDispatcher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.scheduler.Schedulers

@Configuration
class CoroutineConfig {

    @Bean
    fun ioDispatcher(): CoroutineDispatcher = Schedulers.boundedElastic().asCoroutineDispatcher()
}
