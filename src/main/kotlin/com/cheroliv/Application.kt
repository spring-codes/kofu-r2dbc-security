package com.cheroliv

import org.springframework.fu.kofu.reactiveWebApplication

fun main() {
    reactiveWebApplication {
        configurationProperties<SampleProperties>(prefix = "cheroliv")
        enable(dataConfig)
        enable(webConfig)
        enable(securityConfig)
    }.run()
}
