package com.duocuc.serena.viewmodel.auth

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
class RegisterViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: RegisterViewModel

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        viewModel = RegisterViewModel(userRepository)
    }

    @Test
    fun `onUserNameChange cuando el nombre es menor a 3 caracteres deberia mostrar error`() {
        // Dado
        val nuevoNombre = "ab"

        // Cuando
        viewModel.onUserNameChange(nuevoNombre)

        // Entonces
        val uiState = viewModel.uiState.value
        assertEquals(nuevoNombre, uiState.userName)
        assertEquals("El nombre debe tener al menos 3 caracteres", uiState.userNameError)
    }

    @Test
    fun `onUserNameChange cuando el nombre es valido no deberia mostrar error`() {
        // Dado
        val nuevoNombre = "UsuarioValido"

        // Cuando
        viewModel.onUserNameChange(nuevoNombre)

        // Entonces
        val uiState = viewModel.uiState.value
        assertEquals(nuevoNombre, uiState.userName)
        assertNull(uiState.userNameError)
    }

    @Test
    fun `onUserEmailChange con email invalido deberia mostrar error`() {
        // Dado
        val emailInvalido = "test@invalido"

        // Cuando
        viewModel.onUserEmailChange(emailInvalido)

        // Entonces
        val uiState = viewModel.uiState.value
        assertEquals(emailInvalido, uiState.userEmail)
        assertEquals("Formato de email no válido", uiState.userEmailError)
    }
    
    @Test
    fun `onUserEmailChange con email valido no deberia mostrar error`() {
        // Dado
        val emailValido = "test@valido.com"

        // Cuando
        viewModel.onUserEmailChange(emailValido)

        // Entonces
        val uiState = viewModel.uiState.value
        assertEquals(emailValido, uiState.userEmail)
        assertNull(uiState.userEmailError)
    }

    @Test
    fun `onUserPasswordChange con clave corta deberia mostrar error`() {
        // Dado
        val claveCorta = "12345"

        // Cuando
        viewModel.onUserPasswordChange(claveCorta)

        // Entonces
        val uiState = viewModel.uiState.value
        assertEquals(claveCorta, uiState.userPassword)
        assertEquals("La contraseña debe tener al menos 8 caracteres", uiState.userPasswordError)
    }

    @Test
    fun `register cuando el formulario es valido y el repositorio es exitoso deberia ser exitoso`() = runTest {
        // Dado: El repositorio devolverá un Result exitoso con el valor true.
        coEvery { userRepository.registerUser(any(), any(), any()) } returns Result.success(true)
        
        // Cuando: Todos los campos se llenan correctamente
        viewModel.onUserNameChange("Usuario Valido")
        viewModel.onUserEmailChange("valid@email.com")
        viewModel.onUserPasswordChange("password123")
        viewModel.onUserRepeatPasswordChange("password123")
        viewModel.onUserAcceptConditionsChange(true)
        
        viewModel.register()
        advanceUntilIdle() // Permitir que la corrutina finalice

        // Entonces: El estado debe reflejar un registro exitoso
        val uiState = viewModel.uiState.value
        assertTrue(uiState.isRegistrationSuccessful)
        assertFalse(uiState.isLoading)
        assertNull(uiState.registrationError)
    }

    @Test
    fun `register cuando el repositorio falla deberia mostrar error`() = runTest {
        // Dado: El repositorio devolverá un Result de fallo con un mensaje de error específico.
        val mensajeError = "El email ya está en uso"
        coEvery { userRepository.registerUser(any(), any(), any()) } returns Result.failure(Exception(mensajeError))

        // Cuando: Todos los campos se llenan correctamente
        viewModel.onUserNameChange("Usuario Valido")
        viewModel.onUserEmailChange("valid@email.com")
        viewModel.onUserPasswordChange("password123")
        viewModel.onUserRepeatPasswordChange("password123")
        viewModel.onUserAcceptConditionsChange(true)

        viewModel.register()
        advanceUntilIdle()

        // Entonces: El estado debe reflejar el error de la excepción
        val uiState = viewModel.uiState.value
        assertFalse(uiState.isRegistrationSuccessful)
        assertFalse(uiState.isLoading)
        assertEquals(mensajeError, uiState.registrationError)
    }
}