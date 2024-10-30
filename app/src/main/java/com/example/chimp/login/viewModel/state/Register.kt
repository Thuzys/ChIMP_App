package com.example.chimp.login.viewModel.state

/**
 * The state representing an idle Register screen.
 *
 * @property username the username
 * @property password the password
 */
sealed class Register(
    open val username: String,
    open val password: String,
    open val confirmPassword: String,
) : LoginScreenState {
    abstract fun updateUsername(username: String): Register
    abstract fun updatePassword(password: String): Register
    abstract fun updateConfirmPassword(confirmPassword: String): Register
    val isValid: Boolean
        get() = username.isNotBlank() && password.isNotBlank() && password == confirmPassword

    /**
     * The state representing a idle Register screen with the password shown.
     *
     * @property username the username
     * @property password the password
     */
    data class RegisterShow(
        override val username: String,
        override val password: String,
        override val confirmPassword: String = "",
    ) : Register(username, password, confirmPassword), Visibility.Show {
        override fun updateUsername(username: String): Register = copy(username = username)
        override fun updatePassword(password: String): Register = copy(password = password)
        override fun updateConfirmPassword(confirmPassword: String): Register =
            copy(confirmPassword = confirmPassword)

        fun hidePassword(): RegisterHide = RegisterHide(username, password)
    }

    /**
     * The state representing a idle Register screen with the password hidden.
     *
     * @property username the username
     * @property password the password
     */
    data class RegisterHide(
        override val username: String,
        override val password: String,
        override val confirmPassword: String = "",
    ) : Register(username, password, confirmPassword), Visibility.Hide {
        override fun updateUsername(username: String): Register = copy(username = username)
        override fun updatePassword(password: String): Register = copy(password = password)
        override fun updateConfirmPassword(confirmPassword: String): Register =
            copy(confirmPassword = confirmPassword)

        fun showPassword(): RegisterShow = RegisterShow(username, password)
    }
}