package com.duocuc.serena.ui.screens.auth

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import com.duocuc.serena.repository.UserRepository
import com.duocuc.serena.viewmodel.auth.LoginViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ViewModel y dependencias falsas para tener control total en el test
    private lateinit var fakeViewModel: LoginViewModel
    private val fakeUserRepository: UserRepository = mockk(relaxed = true)

    @Test
    fun cuandoElFormularioSeLlenaCorrectamente_elBotonDeLoginDeberiaEstarHabilitado() {
        // Dado: Inicializamos nuestro ViewModel falso
        fakeViewModel = LoginViewModel(fakeUserRepository)

        // Cuando: Lanzamos la pantalla de login, INYECTANDO el ViewModel falso
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = {},
                onNavigateToRegister = {},
                loginViewModel = fakeViewModel
            )
        }

        // Entonces: Simulamos que el usuario escribe en todos los campos
        composeTestRule.onNodeWithTag("LoginEmailField").performTextInput("test@valido.com")
        composeTestRule.onNodeWithTag("LoginPasswordField").performTextInput("password123")

        // Y: Verificamos que el botón de inicio de sesión está habilitado
        composeTestRule.onNodeWithTag("LoginButton").assertIsEnabled()
    }
}