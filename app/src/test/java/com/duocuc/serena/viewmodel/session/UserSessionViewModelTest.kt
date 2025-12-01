package com.duocuc.serena.viewmodel.session

import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.repository.UserRepository
import com.duocuc.serena.util.MainDispatcherExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherExtension::class)
class UserSessionViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: UserSessionViewModel

    @BeforeEach
    fun setUp() {
        // Creamos un mock para el repositorio. `relaxed = true` para no tener que definir
        // el comportamiento de todas sus funciones, solo las que nos interesan.
        userRepository = mockk(relaxed = true)
    }

    @Test
    fun `activeUser deberia exponer el usuario del repositorio`() = runTest {
        // Dado: El repositorio tiene un flujo con un usuario de prueba
        val testUser = UserData(id = 1, userName = "Test User", userEmail = "test@test.com", userPassword = "123")
        coEvery { userRepository.activeUser } returns flowOf(testUser)

        // Cuando: Se inicializa el ViewModel
        viewModel = UserSessionViewModel(userRepository)

        // Entonces: El primer valor emitido por activeUser en el ViewModel debe ser el usuario de prueba
        val emittedUser = viewModel.activeUser.first()
        assertEquals(testUser, emittedUser)
    }

    @Test
    fun `logout deberia llamar al metodo logout del repositorio`() = runTest {
        // Dado: El ViewModel está inicializado (no es necesario un usuario activo para este test)
        coEvery { userRepository.activeUser } returns flowOf(null)
        viewModel = UserSessionViewModel(userRepository)

        // Cuando: Se llama a la función de logout
        viewModel.logout()

        // Entonces: Se debe verificar que la función logout del repositorio fue llamada exactamente una vez
        coVerify(exactly = 1) { userRepository.logout() }
    }
}