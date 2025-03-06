package trpl.nim234311049.tennisscore

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.res.colorResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TennisScoreApp()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TennisScoreApp(scoreViewModel: ScoreViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Tennis Score",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = colorResource(id = R.color.purple))
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Menampilkan skor game
                Text(text = "Game: ${scoreViewModel.gameA} - ${scoreViewModel.gameB}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))

                // Menampilkan set
                Text(text = "Set: ${scoreViewModel.setA} - ${scoreViewModel.setB}", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(24.dp))

                // Menampilkan tiebreak jika ada
                if (scoreViewModel.isTiebreak) {
                    Text(
                        text = "Tiebreak: ${scoreViewModel.tiebreakA} - ${scoreViewModel.tiebreakB}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Red
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Menampilkan pemenang pertandingan jika ada
                if (scoreViewModel.winner != null) {
                    Text(text = "\uD83C\uDFC6Pemenang: ${scoreViewModel.winner}!", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(onClick = { scoreViewModel.resetMatch() }) {
                        Text(text = "Reset Match")
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Menampilkan skor Team A
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Team A", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = when (scoreViewModel.teamAScore) {
                                0 -> "0"
                                15 -> "15"
                                30 -> "30"
                                40 -> "40"
                                45 -> "A"
                                else -> "A"
                            },
                            style = MaterialTheme.typography.displayLarge
                        )
                        Row {
                            Button(onClick = { scoreViewModel.setTeamAScore() }) {
                                Text(text = "Poin A")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(48.dp))

                    // Menampilkan skor Team B
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Team B", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = when (scoreViewModel.teamBScore) {
                                0 -> "0"
                                15 -> "15"
                                30 -> "30"
                                40 -> "40"
                                45 -> "A"
                                else -> "A"
                            },
                            style = MaterialTheme.typography.displayLarge
                        )
                        Row {
                            Button(onClick = { scoreViewModel.setTeamBScore() }) {
                                Text(text = "Poin B")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tombol reset skor dan game
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { scoreViewModel.resetScore() }) {
                        Text(text = "Reset Scores")
                    }
                    Button(onClick = { scoreViewModel.resetSet() }) {
                        Text(text = "Reset Set")
                    }
                }

                Box(
                ) {
                    Button(onClick = { scoreViewModel.resetGame() }) {
                        Text(text = "Reset Game")
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TennisScoreApp()
}
