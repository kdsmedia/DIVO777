package com.altomedia.divo.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.random.Random

enum class GameStage {
    Title,
    Game,
}

enum class GameModal {
    AutoSpin,
    PayTable,
    PayLines,
    Credits,
}

data class GameUiState(
    val stage: GameStage = GameStage.Title,
    val balance: Int = 100_000,
    val win: Int = 0,
    val coin: Int = 10,
    val lines: Int = 1,
    val reels: List<List<Int>> = initialReels(),
    val isSpinning: Boolean = false,
    val autoSpinsRemaining: Int = 0,
    val autoSpinAmount: Int = 5,
    val musicEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val activeModal: GameModal? = null,
    val resultMessage: String? = null,
)

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = application.getSharedPreferences(PREFERENCES_NAME, 0)
    private val _uiState: MutableStateFlow<GameUiState> = MutableStateFlow(loadState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun startGame() {
        _uiState.update { it.copy(stage = GameStage.Game, resultMessage = null) }
    }

    fun cycleCoin() {
        if (isBusy()) return
        val nextCoin: Int = if (_uiState.value.coin >= 50) 10 else _uiState.value.coin + 10
        _uiState.update { it.copy(coin = nextCoin, resultMessage = null) }
        savePreferences()
    }

    fun cycleLines() {
        if (isBusy()) return
        val nextLines: Int = if (_uiState.value.lines >= 20) 1 else _uiState.value.lines + 1
        _uiState.update { it.copy(lines = nextLines, resultMessage = null) }
        savePreferences()
    }

    fun maxBet() {
        if (isBusy()) return
        _uiState.update { it.copy(coin = 50, lines = 20, resultMessage = null) }
        savePreferences()
    }

    fun toggleMusic() {
        _uiState.update { it.copy(musicEnabled = !it.musicEnabled) }
        savePreferences()
    }

    fun toggleSound() {
        _uiState.update { it.copy(soundEnabled = !it.soundEnabled) }
        savePreferences()
    }

    fun openModal(modal: GameModal) {
        if (_uiState.value.isSpinning) return
        _uiState.update { it.copy(activeModal = modal) }
    }

    fun closeModal() {
        _uiState.update { it.copy(activeModal = null) }
    }

    fun setAutoSpinAmount(amount: Int) {
        val nextAmount: Int = amount.coerceIn(5, 100)
        _uiState.update { it.copy(autoSpinAmount = nextAmount) }
    }

    fun startAutoSpin(amount: Int) {
        if (isBusy()) return
        val cost: Int = currentBet()
        if (_uiState.value.balance < cost) {
            _uiState.update { it.copy(activeModal = null, resultMessage = "Need more credits") }
            return
        }
        _uiState.update {
            it.copy(
                activeModal = null,
                autoSpinsRemaining = amount.coerceIn(5, 100),
                resultMessage = null,
            )
        }
        spin()
    }

    fun stopAutoSpin() {
        _uiState.update { it.copy(autoSpinsRemaining = 0, resultMessage = "Auto stopped") }
    }

    fun spin() {
        val state: GameUiState = _uiState.value
        if (state.isSpinning) return
        val cost: Int = currentBet()
        if (state.balance < cost) {
            _uiState.update { it.copy(resultMessage = "Need more credits") }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    balance = it.balance - cost,
                    win = 0,
                    isSpinning = true,
                    resultMessage = null,
                )
            }

            repeat(8) {
                delay(82L)
                _uiState.update { it.copy(reels = randomReels()) }
            }

            val finalReels: List<List<Int>> = randomReels()
            val payout: Int = calculatePayout(finalReels, _uiState.value.lines, _uiState.value.coin)
            val remainingAutoSpins: Int = (_uiState.value.autoSpinsRemaining - 1).coerceAtLeast(0)
            delay(220L)
            _uiState.update {
                it.copy(
                    reels = finalReels,
                    balance = it.balance + payout,
                    win = payout,
                    isSpinning = false,
                    autoSpinsRemaining = remainingAutoSpins,
                    resultMessage = if (payout > 0) "WIN +${payout.toCommaString()}" else "Good luck",
                )
            }
            savePreferences()

            if (remainingAutoSpins > 0) {
                delay(820L)
                if (_uiState.value.autoSpinsRemaining > 0) {
                    spin()
                }
            }
        }
    }

    private fun isBusy(): Boolean = _uiState.value.isSpinning || _uiState.value.autoSpinsRemaining > 0

    private fun currentBet(): Int = _uiState.value.coin * _uiState.value.lines

    private fun calculatePayout(reels: List<List<Int>>, lines: Int, coin: Int): Int {
        val rowsToCheck: Int = when {
            lines >= 15 -> 3
            lines >= 5 -> 2
            else -> 1
        }
        val payoutValues: List<Int> = listOf(100, 50, 25, 25, 15, 10, 10, 10, 5, 3)
        var total: Int = 0
        for (row in 0 until min(rowsToCheck, 3)) {
            val symbol: Int = reels.first()[row]
            var streak: Int = 1
            for (column in 1 until reels.size) {
                if (reels[column][row] == symbol) {
                    streak += 1
                } else {
                    break
                }
            }
            if (streak >= 3) {
                total += payoutValues[symbol] * (streak - 2) * coin
            }
        }
        return total
    }

    private fun loadState(): GameUiState {
        return GameUiState(
            balance = preferences.getInt(KEY_BALANCE, 100_000),
            coin = preferences.getInt(KEY_COIN, 10),
            lines = preferences.getInt(KEY_LINES, 1),
            musicEnabled = preferences.getBoolean(KEY_MUSIC, true),
            soundEnabled = preferences.getBoolean(KEY_SOUND, true),
        )
    }

    private fun savePreferences() {
        val state: GameUiState = _uiState.value
        preferences.edit()
            .putInt(KEY_BALANCE, state.balance)
            .putInt(KEY_COIN, state.coin)
            .putInt(KEY_LINES, state.lines)
            .putBoolean(KEY_MUSIC, state.musicEnabled)
            .putBoolean(KEY_SOUND, state.soundEnabled)
            .apply()
    }

    private companion object {
        const val PREFERENCES_NAME: String = "fruit_fortune_preferences"
        const val KEY_BALANCE: String = "balance"
        const val KEY_COIN: String = "coin"
        const val KEY_LINES: String = "lines"
        const val KEY_MUSIC: String = "music"
        const val KEY_SOUND: String = "sound"
    }
}

private fun initialReels(): List<List<Int>> = listOf(
    listOf(0, 4, 7),
    listOf(3, 1, 8),
    listOf(9, 2, 5),
    listOf(6, 0, 4),
    listOf(7, 3, 1),
)

private fun randomReels(): List<List<Int>> = List(5) {
    List(3) { Random.nextInt(0, 10) }
}

private fun Int.toCommaString(): String = "%,d".format(this)
