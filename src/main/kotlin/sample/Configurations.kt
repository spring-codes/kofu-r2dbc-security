package sample

import org.springframework.core.io.ClassPathResource
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.templating.mustache
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.provisioning.InMemoryUserDetailsManager

val dataConfig = configuration {
    beans {
        bean<UserRepository>()
        bean {
            ConnectionFactoryInitializer().apply {
                setConnectionFactory(ref())
                setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("db/tables.sql")))
            }
        }
    }
    r2dbc {
        url = "r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
    }
}

val webConfig = configuration {
    beans {
        bean<UserHandler>()
        bean(::routes)
    }
    webFlux {
        port = if (profiles.contains("test")) 8181 else 8080
        mustache()
        codecs {
            string()
            jackson()
        }
    }
}

@EnableWebSecurity
open class KotlinSecurityConfiguration : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic {}
            authorizeRequests {
                authorize("/greetings/**", hasAuthority("ROLE_ADMIN"))
                authorize("/**", permitAll)
            }
        }
    }
}

val securityConfig = configuration {
    beans {
        bean {
            fun user(user: String, pw: String, vararg roles: String): UserDetails =
                    User.withDefaultPasswordEncoder()
                            .username(user)
                            .password(pw)
                            .roles(*roles)
                            .build()
            InMemoryUserDetailsManager(
                    user("lmonkey", "pw", "USER", "ADMIN"),
                    user("svinsmoke", "pw", "USER"),
                    user("rzoro", "pw1", "USER"),
                    user("rnico", "pw1", "USER"),
                    user("ctonytony", "pw1", "USER"),
                    user("tsunny", "pw1", "USER"),
                    user("mvogue", "pw1", "USER"),
                    user("ftetsujin", "pw1", "USER"),
                    user("jlong", "pw", "USER"),
                    user("rwinch", "pw1", "USER"))
        }
    }
}

