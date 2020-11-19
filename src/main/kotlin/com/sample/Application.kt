package com.sample

import org.springframework.fu.kofu.reactiveWebApplication

fun main() {
    reactiveWebApplication {
        configurationProperties<SampleProperties>(prefix = "sample")
        enable(dataConfig)
        enable(webConfig)
        enable(securityConfig)
    }.run()
}
