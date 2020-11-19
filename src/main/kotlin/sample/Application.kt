package sample

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {

    configurationProperties<SampleProperties>(prefix = "sample")
    enable(dataConfig)
    enable(webConfig)
    enable(securityConfig)
}

//fun main() = app.run()
fun main() {
    app.run()
}


