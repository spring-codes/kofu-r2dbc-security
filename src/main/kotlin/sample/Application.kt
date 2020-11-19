package sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.fu.kofu.configuration

@SpringBootApplication
open class SpringSecurityApplication

fun main(args: Array<String>) {
    runApplication<SpringSecurityApplication>(*args) {
        addInitializers({
            configuration {
                configurationProperties<SampleProperties>(prefix = "sample")
            }
            dataConfig
            webConfig
            securityConfig
        })
    }
}