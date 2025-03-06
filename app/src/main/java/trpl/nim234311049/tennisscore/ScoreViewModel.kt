package trpl.nim234311049.tennisscore

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class ScoreViewModel : ViewModel() {
    var teamAScore by mutableStateOf(0) // Skor untuk Team A
    var teamBScore by mutableStateOf(0) // Skor untuk Team B
    var gameA by mutableStateOf(0) // Game untuk Team A
    var gameB by mutableStateOf(0) // Game untuk Team B
    var setA by mutableStateOf(0) // Set untuk Team A
    var setB by mutableStateOf(0) // Set untuk Team B
    var isTiebreak by mutableStateOf(false) // Menandakan apakah sedang dalam tiebreak
    var tiebreakA by mutableStateOf(0) // Skor tiebreak untuk Team A
    var tiebreakB by mutableStateOf(0) // Skor tiebreak untuk Team B

    var winner: String? by mutableStateOf(null) // Menyimpan pemenang

    private val scoreValues = listOf(0, 15, 30, 40, 45)

    // Menetapkan skor untuk tim A
    fun setTeamAScore() {
        if (winner != null) return  // Jika sudah ada pemenang, jangan proses lagi

        if (isTiebreak) {
            // Logika tiebreak
            if (tiebreakA < 7 && tiebreakB < 7) {
                tiebreakA += 1
            }
            if (tiebreakA >= 7 && tiebreakA - tiebreakB >= 2) {
                // Team A menang tiebreak dan set
                setA += 1
                checkSetWinner() // Cek pemenang set
                resetGameAfterTiebreak() // Reset game setelah tiebreak
            }
        } else {
            // Logika biasa untuk skor game
            when (teamAScore) {
                40 -> {
                    if (teamBScore == 40) {
                        teamAScore = 45
                    } else if (teamBScore == 45) {
                        teamAScore = 40
                        teamBScore = 40
                    } else {
                        gameA += 1
                        checkSetWinner() // Cek pemenang set
                        resetScore()
                    }
                }
                45 -> {
                    gameA += 1
                    checkSetWinner() // Cek pemenang set
                    resetScore()
                }
                else -> {
                    val currentScoreIndex = scoreValues.indexOf(teamAScore)
                    if (currentScoreIndex < scoreValues.size - 1) {
                        teamAScore = scoreValues[currentScoreIndex + 1]
                    }
                }
            }
        }
    }

    // Menetapkan skor untuk tim B
    fun setTeamBScore() {
        if (winner != null) return  // Jika sudah ada pemenang, jangan proses lagi

        if (isTiebreak) {
            // Logika tiebreak
            if (tiebreakB < 7 && tiebreakA < 7) {
                tiebreakB += 1
            }
            if (tiebreakB >= 7 && tiebreakB - tiebreakA >= 2) {
                // Team B menang tiebreak dan set
                setB += 1
                checkSetWinner() // Cek pemenang set
                resetGameAfterTiebreak() // Reset game setelah tiebreak
            }
        } else {
            // Logika biasa untuk skor game
            when (teamBScore) {
                40 -> {
                    if (teamAScore == 40) {
                        teamBScore = 45
                    } else if (teamAScore == 45) {
                        teamAScore = 40
                        teamBScore = 40
                    } else {
                        gameB += 1
                        checkSetWinner() // Cek pemenang set
                        resetScore()
                    }
                }
                45 -> {
                    gameB += 1
                    checkSetWinner() // Cek pemenang set
                    resetScore()
                }
                else -> {
                    val currentScoreIndex = scoreValues.indexOf(teamBScore)
                    if (currentScoreIndex < scoreValues.size - 1) {
                        teamBScore = scoreValues[currentScoreIndex + 1]
                    }
                }
            }
        }
    }

    // Memeriksa apakah ada pemenang set
    private fun checkSetWinner() {
        // Menentukan pemenang set
        if (gameA == 6 && gameB <= 4) {
            setA += 1
            checkMatchWinner() // Cek pemenang pertandingan
            resetGame()
        } else if (gameB == 6 && gameA <= 4) {
            setB += 1
            checkMatchWinner() // Cek pemenang pertandingan
            resetGame()
        } else if (gameA == 7 && gameB == 5) {
            setA += 1
            checkMatchWinner() // Cek pemenang pertandingan
            resetGame()
        } else if (gameB == 7 && gameA == 5) {
            setB += 1
            checkMatchWinner() // Cek pemenang pertandingan
            resetGame()
        } else if (gameA == 6 && gameB == 6) {
            // Masuk ke tiebreak jika skor sama 6-6
            isTiebreak = true
        }
    }

    // Memeriksa apakah ada pemenang pertandingan
    private fun checkMatchWinner() {
        // Jika salah satu tim sudah menang 2 set, maka pertandingan selesai
        if (setA == 2) {
            winner = "Team A" // Team A menang
        } else if (setB == 2) {
            winner = "Team B" // Team B menang
        }
    }

    // Mereset pertandingan setelah selesai
    fun resetMatch() {
        gameA = 0
        gameB = 0
        setA = 0
        setB = 0
        teamAScore = 0
        teamBScore = 0
        winner = null // Reset pemenang
    }

    // Mereset skor (tanpa mereset game)
    fun resetScore() {
        teamAScore = 0
        teamBScore = 0
    }

    // Mereset game setelah tiebreak
    private fun resetGameAfterTiebreak() {
        gameA = 0
        gameB = 0
        resetScore() // Reset skor setelah tiebreak selesai
        isTiebreak = false // Matikan tiebreak setelah selesai
    }

    // Mereset game
    fun resetGame() {
        gameA = 0
        gameB = 0
        resetScore() // Reset skor setiap kali reset game
    }

    // Mereset set
    fun resetSet() {
        setA = 0
        setB = 0
        resetGame()
    }

    // Mereset tiebreak
    private fun resetTiebreak() {
        tiebreakA = 0
        tiebreakB = 0
        isTiebreak = false
    }
}
