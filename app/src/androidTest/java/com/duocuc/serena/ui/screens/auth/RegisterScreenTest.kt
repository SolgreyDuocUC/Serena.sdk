package com.duocuc.serena.ui.screens.auth

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput

import com.duocuc.serena.repository.UserRepository
import com.duocuc.serena.viewmodel.auth.RegisterViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ViewModel y dependencias falsas para tener control total en el test
    private lateinit var fakeViewModel: RegisterViewModel
    private val fakeUserRepository: UserRepository = mockk(relaxed = true)

    @Test
    fun cuandoElFormularioSeLlenaCorrectamente_elBotonDeRegistroDeberiaEstarHabilitado() {
        // Dado: Inicializamos nuestro ViewModel falso
        fakeViewModel = RegisterViewModel(fakeUserRepository)

        // Cuando: Lanzamos la pantalla de registro, INYECTANDO el ViewModel falso
        composeTestRule.setContent {
            RegisterScreen(
                onRegisterSuccess = {},
                onNavigateToLogin = {},
                registerViewModel = fakeViewModel // <-- ¡Esta es la corrección clave!
            )
        }

        // Entonces: Simulamos que el usuario escribe en todos los campos
        composeTestRule.onNodeWithTag("RegisterUsernameField").performTextInput("Usuario Valido")
        composeTestRule.onNodeWithTag("RegisterEmailField").performTextInput("test@valido.com")
        composeTestRule.onNodeWithTag("RegisterPasswordField").performTextInput("password123")
        composeTestRule.onNodeWithTag("RegisterConfirmPasswordField").performTextInput("password123")
        composeTestRule.onNodeWithTag("RegisterTermsCheckbox").performClick()

        // Y: Verificamos que el botón de registro está habilitado
        composeTestRule.onNodeWithTag("RegisterButton").assertIsEnabled()
    }
}