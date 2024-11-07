package com.example.chimp.login.viewModel.state

/**
 * The state representing an idle Login screen.
 *
 * @property username the username
 * @property password the password
 */
sealed class Login(
    open val username: String,
    open val password: String,
) : LoginScreenState {
    abstract fun updateUsername(username: String): Login
    abstract fun updatePassword(password: String): Login
    val isValid: Boolean
        get() = username.isNotBlank() && password.isNotBlank()

    /**
     * The state representing an idle Login screen with the password shown.
     *
     * @property username the username
     * @property password the password
     */
    data class LoginShow(
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
    data class LoginHide(
        override val username: String,
        override val password: String,
    ) : Login(username, password), Visibility.Hide {
        override fun updateUsername(username: String): Login = copy(username = username)
        override fun updatePassword(password: String): Login = copy(password = password)
        fun showPassword(): LoginShow = LoginShow(username, password)
    }
}