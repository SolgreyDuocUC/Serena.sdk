package com.duocuc.serena.ui.screens.emotionData

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.duocuc.serena.data.dataModel.EmotionalRegisterData
import com.duocuc.serena.repository.EmotionalRegisterRepository
import com.duocuc.serena.viewmodel.emotionData.EmotionalRegisterViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class EmotionalRegisteredScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Dependencias falsas
    private val fakeRepository: EmotionalRegisterRepository = mockk(relaxed = true)
    private lateinit var fakeViewModel: EmotionalRegisterViewModel

    @Test
    fun cuandoHayRegistros_deberiaMostrarseEmotionCard() {
        // Dado: El ViewModel tendrá un registro para la fecha de hoy
        val today = LocalDate.now()
        val fakeRegister = EmotionalRegisterData(
            id = 1,
            idEmocion = 1,
            fecha = today,
            emocionNombre = "Feliz",
            descripcion = "Test de UI"
        )
        // Simulamos la respuesta del repositorio que el ViewModel usará
        coEvery { fakeRepository.getAllRegisters() } returns Result.success(listOf(fakeRegister))
        
        fakeViewModel = EmotionalRegisterViewModel(fakeRepository)

        // Cuando: Lanzamos la pantalla
        composeTestRule.setContent {
            EmotionalRegisteredScreen(
                navController = rememberNavController(),
                date = today,
                viewModel = fakeViewModel
            )
        }

        // Entonces: La EmotionCard con el testTag específico debe estar visible
        composeTestRule.onNodeWithTag("EmotionCard_1").assertIsDisplayed()
    }
}