package com.duocuc.serena.viewmodel.auth

import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.repository.UserRepository
import com.duocuc.serena.util.MainDispatcherExtension
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherExtension::class)
class LoginViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: LoginViewModel

    // Creamos un usuario de prueba que el repositorio devolverá en caso de éxito
    private val testUser = UserData(
        id = 1,
        userName = "Usuario de Prueba",
        userEmail = "test@test.com",
        userPassword = "password123"
    )

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        viewModel = LoginViewModel(userRepository)
    }

    @Test
    fun `login cuando las credenciales son validas y el repositorio es exitoso deberia ser exitoso`() = runTest {
        // Dado: El repositorio devolverá un Result exitoso con el usuario de prueba
        coEvery { userRepository.loginUser(any(), any()) } returns Result.success(testUser)

        // Cuando: Se introducen las credenciales correctas
        viewModel.onUserEmailChange("test@test.com")
        viewModel.onUserPasswordChange("password123")
        
        viewModel.login()
        advanceUntilIdle() // Permitir que la corrutina finalice

        // Entonces: El estado debe reflejar un login exitoso
        val uiState = viewModel.uiState.value
        assertTrue(uiState.isLoginSuccessful)
        assertFalse(uiState.isLoading)
        assertNull(uiState.loginError)
    }

    @Test
    fun `login cuando las credenciales son incorrectas y el repositorio falla deberia mostrar error`() = runTest {
        // Dado: El repositorio devolverá un Result de fallo
        val errorMessage = "Credenciales incorrectas"
        coEvery { userRepository.loginUser(any(), any()) } returns Result.failure(Exception(errorMessage))

        // Cuando: Se introducen credenciales incorrectas
        viewModel.onUserEmailChange("test@test.com")
        viewModel.onUserPasswordChange("wrongpassword")

        viewModel.login()
        advanceUntilIdle()

        // Entonces: El estado debe reflejar el error de login
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoginSuccessful)
        assertFalse(uiState.isLoading)
        assertEquals(errorMessage, uiState.loginError)
    }

    @Test
    fun `login cuando el email esta vacio no deberia llamar al repositorio`() = runTest {
        // Cuando: El email está vacío
        viewModel.onUserEmailChange("")
        viewModel.onUserPasswordChange("password123")

        viewModel.login()
        advanceUntilIdle()

        // Entonces: El estado no debe ser exitoso y no se debe haber llamado al repositorio
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isLoginSuccessful)
        io.mockk.coVerify(exactly = 0) { userRepository.loginUser(any(), any()) }
    }
}