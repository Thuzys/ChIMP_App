package com.example.chimp.screens.login.viewModel.state

/**
 * Represents the state of the Login screen.
 *
 * The username must have:
 * - At least 3 characters
 * - Only alphanumeric characters
 */
private val userRegex = Regex("^[a-zA-Z0-9]{3,}\$")

/**
 * Represents the state of the Login screen.
 *
 * The password must have:
 * - At least 1 uppercase letter
 * - At least 1 digit
 * - At least 1 special character
 * - At least 8 characters
 */
private val passwordRegex = Regex(
    "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}\$"
)
/**
 * The state representing an idle Login screen.
 *
 * @property username the username
 * @property password the password
 */
internal sealed class Login(
    open val username: String,
    open val password: String,
) : LoginScreenState {
    abstract fun updateUsername(username: String): Login
    abstract fun updatePassword(password: String): Login
    val isValid: Boolean
        get() = userRegex matches username && passwordRegex matches password

    /**
     * The state representing an idle Login screen with the password shown.
     *
     * @property username the username
     * @property password the password
     */
    internal data class LoginShow(
        override val username: String,
        override val password: String,
    ) : Login(username, password), Visibility.Show {
        override fun updateUsername(username: String): Login = copy(username = username)
        override fun updatePassword(password: String): Login = copy(password = password)
        fun hidePassword(): LoginHide = LoginHide(username, password)
    }

    /**
     * The state representing an idle Login screen with the password hidden.
     *
     * @property username the username
     * @property password the password
     */
    internal data class LoginHide(
        override val username: String,
        override val password: String,
    ) : Login(username, password), Visibility.Hide {
        override fun updateUsername(username: String): Login = copy(username = username)
        override fun updatePassword(password: String): Login = copy(password = password)
        fun showPassword(): LoginShow = LoginShow(username, password)
    }
}