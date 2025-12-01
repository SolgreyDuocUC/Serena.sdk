package com.duocuc.serena.viewmodel.profile

import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.repository.UserRepository
import com.duocuc.serena.util.MainDispatcherExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherExtension::class)
class ProfileViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var sessionViewModel: SessionViewModel
    private lateinit var viewModel: ProfileViewModel

    // Creamos un usuario de prueba que el sessionViewModel devolverá
    private val testUser = UserData(
        id = 1,
        userName = "Usuario de Prueba",
        userEmail = "test@test.com",
        userPassword = "password123"
    )

    @BeforeEach
    fun setUp() {
        // Creamos los mocks
        userRepository = mockk()
        sessionViewModel = mockk()

        // Por defecto, simulamos que el sessionViewModel ya tiene un usuario activo
        coEvery { sessionViewModel.activeUser } returns MutableStateFlow(testUser)
        
        // Inicializamos el ViewModel con los mocks
        viewModel = ProfileViewModel(userRepository, sessionViewModel)
    }

    @Test
    fun `saveChanges cuando la contraseña es correcta y el repositorio funciona deberia guardar los cambios`() = runTest {
        // Dado: La contraseña antigua introducida es correcta
        viewModel.onOldPasswordChange("password123")
        viewModel.onNewPasswordChange("newPassword456")
        viewModel.onNameChange("Nuevo Nombre")

        // Y: La llamada al repositorio no lanzará excepciones (comportamiento por defecto de mockk)
        coEvery { userRepository.updateUser(any()) } returns Unit

        // Cuando
        viewModel.saveChanges()
        advanceUntilIdle() // Ejecutar las corrutinas pendientes

        // Entonces: El estado de la UI debe reflejar éxito
        val uiState = viewModel.uiState.value
        assertTrue(uiState.saveSuccess)
        assertEquals("Cambios guardados correctamente", uiState.snackbarMessage)

        // Y: Se debe haber llamado al repositorio para actualizar el usuario
        coVerify { userRepository.updateUser(any()) }
    }

    @Test
    fun `saveChanges cuando la contraseña antigua es incorrecta deberia mostrar error`() = runTest {
        // Dado: La contraseña antigua introducida es incorrecta
        viewModel.onOldPasswordChange("contraseña-incorrecta")

        // Cuando
        viewModel.saveChanges()
        advanceUntilIdle()

        // Entonces: El estado de la UI debe reflejar el error de contraseña
        val uiState = viewModel.uiState.value
        assertFalse(uiState.saveSuccess)
        assertEquals("La contraseña actual es incorrecta", uiState.errorMessage)

        // Y: No se debe llamar al repositorio
        coVerify(exactly = 0) { userRepository.updateUser(any()) }
    }
}