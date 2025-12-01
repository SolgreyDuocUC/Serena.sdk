package com.duocuc.serena.viewmodel.emotionData

import com.duocuc.serena.data.dataModel.EmotionalRegisterData
import com.duocuc.serena.repository.EmotionalRegisterRepository
import com.duocuc.serena.util.MainDispatcherExtension
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherExtension::class)
class EmotionalRegisterViewModelTest {

    private lateinit var repository: EmotionalRegisterRepository
    private lateinit var viewModel: EmotionalRegisterViewModel

    @BeforeEach
    fun setUp() {
        repository = mockk()
        viewModel = EmotionalRegisterViewModel(repository)
    }

    @Test
    fun `loadRegisters cuando el repositorio es exitoso deberia actualizar la lista de registros`() = runTest {
        // Dado: El repositorio devolverá una lista de registros de prueba con la estructura correcta
        val fakeRegisters = listOf(
            EmotionalRegisterData(
                id = 1,
                idEmocion = 1,
                fecha = LocalDate.now(),
                emocionNombre = "Feliz",
                emocionEmoji = "😊",
                descripcion = "Un gran día"
            ),
            EmotionalRegisterData(
                id = 2,
                idEmocion = 5,
                fecha = LocalDate.now().minusDays(1),
                emocionNombre = "Triste",
                emocionEmoji = "😢",
                descripcion = "Día para olvidar"
            )
        )
        coEvery { repository.getAllRegisters() } returns Result.success(fakeRegisters)

        // Cuando
        viewModel.loadRegisters()
        advanceUntilIdle()

        // Entonces: El estado de los registros debe ser igual a la lista de prueba
        assertEquals(fakeRegisters, viewModel.registers.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun `registerEmotion cuando el repositorio es exitoso deberia recargar los registros`() = runTest {
        // Dado: El repositorio devolverá un Result<Boolean> exitoso
        coEvery { repository.registerEmotion(any(), any(), any()) } returns Result.success(true)
        coEvery { repository.getAllRegisters() } returns Result.success(emptyList()) // Simula la recarga

        // Cuando
        viewModel.registerEmotion(1, "Día productivo", LocalDate.now())
        advanceUntilIdle()

        // Entonces: Se debe haber llamado a getAllRegisters después del registro
        coVerify(exactly = 1) { repository.registerEmotion(1, "Día productivo", LocalDate.now()) }
        coVerify(exactly = 1) { repository.getAllRegisters() }
    }

    @Test
    fun `deleteEmotion cuando el repositorio falla deberia mostrar error`() = runTest {
        // Dado: El repositorio devolverá un error al intentar eliminar
        val errorMessage = "Error al eliminar el registro"
        coEvery { repository.deleteEmotion(any()) } returns Result.failure(Exception(errorMessage))

        // Cuando
        viewModel.deleteEmotion(123)
        advanceUntilIdle()

        // Entonces: El estado de error debe contener el mensaje del repositorio
        assertEquals(errorMessage, viewModel.error.value)

        // Y: No se debería intentar recargar la lista si la operación principal falló
        coVerify(exactly = 0) { repository.getAllRegisters() }
    }

    @Test
    fun `updateEmotion cuando el repositorio es exitoso deberia recargar los registros`() = runTest {
        // Dado: El repositorio devolverá un Result<Boolean> exitoso
        coEvery { repository.updateEmotion(any(), any(), any(), any()) } returns Result.success(true)
        coEvery { repository.getAllRegisters() } returns Result.success(emptyList())

        // Cuando
        viewModel.updateEmotion(1, 2, "Descripción actualizada", LocalDate.now())
        advanceUntilIdle()

        // Entonces: Se debe haber llamado a las funciones del repositorio
        coVerify(exactly = 1) { repository.updateEmotion(1, 2, "Descripción actualizada", LocalDate.now()) }
        coVerify(exactly = 1) { repository.getAllRegisters() }
    }
}