package uvg.edu.myapp.mainapp.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uvg.edu.myapp.authentication.domain.AuthRepository

class MainViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _appState = MutableStateFlow(AppState())
    val appState = _appState.asStateFlow()

    fun addCounter() {
        _appState.value = _appState.value.copy(
            counter = _appState.value.counter + 1
        )
    }

    fun logUserOut() {
        authRepository.logoutUser()
    }
}