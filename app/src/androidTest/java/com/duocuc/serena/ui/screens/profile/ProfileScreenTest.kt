package com.duocuc.serena.ui.screens.profile

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.repository.UserRepository
import com.duocuc.serena.viewmodel.profile.ProfileViewModel
import com.duocuc.serena.viewmodel.profile.SessionViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Dependencias falsas
    private val fakeUserRepository: UserRepository = mockk(relaxed = true)
    private val fakeSessionViewModel: SessionViewModel = mockk(relaxed = true)

    // Usuario de prueba que simularemos está en la sesión activa
    private val testUser = UserData(
        id = 1,
        userName = "Usuario de Prueba",
        userEmail = "test@test.com",
        userPassword = "password123"
    )

    @Test
    fun cuandoLaPantallaCarga_losDatosDelUsuarioDeberianMostrarse() {
        // Dado: El SessionViewModel falso devolverá nuestro usuario de prueba
        coEvery { fakeSessionViewModel.activeUser } returns MutableStateFlow(testUser)

        // Y: Inicializamos el ProfileViewModel con las dependencias falsas
        val fakeProfileViewModel = ProfileViewModel(fakeUserRepository, fakeSessionViewModel)

        // Cuando: Lanzamos la pantalla de perfil, inyectando el ViewModel falso
        composeTestRule.setContent {
            ProfileScreen(
                onNavigateBack = {},
                profileViewModel = fakeProfileViewModel
            )
        }

        // Entonces: Verificamos que los campos de texto contienen los datos del usuario
        composeTestRule.onNodeWithTag("ProfileNameField").assertTextContains("Usuario de Prueba")
        composeTestRule.onNodeWithTag("ProfileEmailField").assertTextContains("test@test.com")
    }
}