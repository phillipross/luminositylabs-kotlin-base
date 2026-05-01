package co.luminositylabs.oss.examples.jackson

import com.fasterxml.jackson.dataformat.toml.TomlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.util.UUID
import kotlin.test.Test

class TomlTest {
    @Test
    fun `Test data class to toml serialization`() {
        logger.trace { "testing data class to toml serialization" }
        val mapper = TomlMapper().registerKotlinModule()
        val permissions =
            mutableListOf(
                SecurityPermission(name = "perm1", description = "first permission"),
                SecurityPermission(name = "perm2", description = "second permission"),
                SecurityPermission(name = "perm3", description = "third permission"),
            )
        val realms =
            mutableListOf(
                SecurityRealm(name = "realm1", description = "first realm"),
                SecurityRealm(name = "realm2", description = "second realm"),
            )
        val roles =
            mutableListOf(
                SecurityRole(name = "role1", description = "1st role", securityPermissions = permissions.subList(0, 1).toSet()),
                SecurityRole(name = "role2", description = "2nd role"),
                SecurityRole(name = "role3", description = "3rd role"),
            )
        val appUsers =
            mutableListOf(
                AppUser(
                    username = "appuser1",
                    password = "testpassword1",
                    previousPasswords = mutableListOf("oldpwA", "oldpwB", "oldpwC"),
                    emailAddress = "appuser1@everywhere.us",
                    securityPermissions = permissions.subList(0, 2).toSet(),
                ),
                AppUser(
                    username = "appuser2",
                    password = "testpassword2",
                    emailAddress = "appuser2@everywhere.us",
                    securityPermissions = permissions.subList(3, permissions.size).toSet(),
                ),
            )
        logger.debug { "first toml rule:\n${mapper.writeValueAsString(appUsers[0])}" }
    }
}

data class AppUser(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val password: String,
    val previousPasswords: List<String> = mutableListOf(),
    val emailAddress: String,
    val active: Boolean = false,
    val securityPermissions: Set<SecurityPermission> = mutableSetOf(),
    val securityRoles: Set<SecurityRole> = mutableSetOf(),
    val securityRealms: Set<SecurityRealm> = mutableSetOf(),
)

data class SecurityPermission(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
)

data class SecurityRole(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val securityPermissions: Set<SecurityPermission> = mutableSetOf(),
)

data class SecurityRealm(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
)
