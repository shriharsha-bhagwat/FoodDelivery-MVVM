package com.kopa.me.driver.domain.user

/**
 * Used to define map: which [com.kopa.me.driver.data.datasource.remote.user.model.NetworkUserModel]
 * corresponds to a given [UserType]
 */

enum class UserType(val code: Int) {
    OWNER(1), ADMIN(2), DRIVER(3), CLIENT(4)
}