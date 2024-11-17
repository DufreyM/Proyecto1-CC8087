package com.example.waterreminder.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

class ProgressViewModel : ViewModel() {

    // Progreso y total diario
    private val _dayProgress = MutableStateFlow(0) // Progreso actual del día (ahora es Int)
    private val _dayTotal = MutableStateFlow(3000f) // Total diario (meta) sigue siendo Float

    val dayProgress = _dayProgress.asStateFlow() // Exposición del progreso del día
    val dayTotal = _dayTotal.asStateFlow() // Exposición del total del día

    // Progreso y total mensual
    private val _monthProgress = MutableStateFlow(0) // Progreso acumulado del mes (ahora es Int)
    private val _monthTotal = MutableStateFlow(90000f) // Total mensual (meta) 30 * 3000 ml

    val monthProgress = _monthProgress.asStateFlow() // Exposición del progreso del mes
    val monthTotal = _monthTotal.asStateFlow() // Exposición del total del mes

    // Progresos normalizados (entre 0 y 1)
    val normalizedDayProgress = combine(dayProgress, dayTotal) { progress, total ->
        if (total > 0) progress / total else 0f
    }

    val normalizedMonthProgress = combine(monthProgress, monthTotal) { progress, total ->
        if (total > 0) progress / total else 0f
    }

    // Métodos para actualizar valores
    fun actualizarProgresoDia(valor: Int) {
        // Asegurarse de que el progreso no sea mayor que el total diario
        _dayProgress.value = valor.coerceIn(0, _dayTotal.value.toInt())
    }

    fun setTotalDia(valor: Float) {
        _dayTotal.value = valor // La meta diaria sigue siendo Float
    }

    fun actualizarProgresoMes(valor: Int) {
        // Asegurarse de que el progreso no sea mayor que el total mensual
        _monthProgress.value = valor.coerceIn(0, _monthTotal.value.toInt())
    }

    fun setTotalMes(dias: Int, metaDiaria: Float) {
        _monthTotal.value = dias * metaDiaria // Total mensual basado en 30 días
    }
}
