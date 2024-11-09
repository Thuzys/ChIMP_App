package com.example.chimp.screens.login.viewModel.state

/**
 * Represents the state of the Register screen.
 *
 * The username must have:
 * - At least 3 characters
 * - Only alphanumeric characters
 */
private val userRegex = Regex("^[a-zA-Z0-9]{3,}\$")

/**
 * Represents the state of the Register screen.
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
 * The state representing an idle Register screen.
 *
 * @property username the username
 * @property password the password
 */
internal sealed class Register(
    open val username: String,
    open val password: String,
    open val confirmPassword: String,
    open val invitationCode: String,
) : LoginScreenState {
    abstract fun updateUsername(username: String): Register
    abstract fun updatePassword(password: String): Register
    abstract fun updateConfirmPassword(confirmPassword: String): Register
    abstract fun updateInvitationCode(invitationCode: String): Register
    val isValid: Boolean
        get() = userRegex matches username
                && passwordRegex matches password
                && password == confirmPassword
                && invitationCode.isNotBlank()

    /**
     * The state representing a idle Register screen with the password shown.
     *
     * @property username the username
     * @property password the password
     */
    internal data class RegisterShow(
        override val username: String,
        override val password: String,
        override val confirmPassword: String = "",
        override val invitationCode: String = "",
    ) : Register(
        username,
        password,
        confirmPassword,
        invitationCode
    ),
        Visibility.Show {
        override fun updateUsername(username: String): Register =
            copy(username = username)

        override fun updatePassword(password: String): Register =
            copy(password = password)

        override fun updateConfirmPassword(confirmPassword: String): Register =
            copy(confirmPassword = confirmPassword)

        override fun updateInvitationCode(invitationCode: String): Register =
            copy(invitationCode = invitationCode)

        fun hidePassword(): RegisterHide =
            RegisterHide(
                username,
                password,
                confirmPassword,
                invitationCode
            )
    }

    /**
     * The state representing a idle Register screen with the password hidden.
     *
     * @property username the username
     * @property password the password
     */
    internal data class RegisterHide(
        override val username: String,
        override val password: String,
        override val confirmPassword: String = "",
        override val invitationCode: String = "",
    ) : Register(username, password, confirmPassword, invitationCode),
        Visibility.Hide {
        override fun updateUsername(username: String): Register = copy(username = username)
        override fun updatePassword(password: String): Register =
            copy(password = password)

        override fun updateConfirmPassword(confirmPassword: String):Register =
            copy(confirmPassword = confirmPassword)

        override fun updateInvitationCode(invitationCode: String): Register =
            copy(invitationCode = invitationCode)

        fun showPassword(): RegisterShow =
            RegisterShow(
                username,
                password,
                confirmPassword,
                invitationCode
            )
    }
}