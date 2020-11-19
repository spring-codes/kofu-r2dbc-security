package com.sample

import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok
//import org.springframework.web.servlet.function.ServerResponse as ServerResponseServlet
//import org.springframework.web.reactive.function.server.ServerResponse as ServerResponseReactive
import org.springframework.web.reactive.function.server.ServerResponse


@Suppress("UNUSED_PARAMETER")
class UserHandler(
        private val repository: UserRepository,
        private val configuration: SampleProperties) {

    suspend fun listApi(request: ServerRequest) =
            ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait<User>(repository.findAll())

    suspend fun userApi(request: ServerRequest) =
            ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyValueAndAwait(repository.findOne(request.pathVariable("login")))

    suspend fun listView(request: ServerRequest) =
            ok().renderAndAwait("users", mapOf("users" to repository.findAll()))

    suspend fun conf(request: ServerRequest) =
            ok().bodyValueAndAwait(configuration.message)


    suspend fun greet(request: ServerRequest): ServerResponse {
        var res=request.principal().awaitSingleOrNull()
        return ok().bodyValueAndAwait(mapOf("greeting" to "Hello, ${
            if (res === null) "anonymous" else res
        }"))
    }
}

// fun ano(res: String): String = if (res === "null") "anonymous" else res
//principal().map { it.name }.map { ServerResponse.ok().body(mapOf("greeting" to "Hello, $it")) }.orElseGet { ServerResponse.badRequest().build() }
