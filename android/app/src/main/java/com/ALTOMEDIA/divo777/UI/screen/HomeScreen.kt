package com.altomedia.divo.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.altomedia.divo.R
import com.altomedia.divo.ui.theme.ArcadeGold
import com.altomedia.divo.ui.theme.ArcadePink
import com.altomedia.divo.ui.theme.PanelPurple
import java.text.NumberFormat
import java.util.Locale

@Suppress("UNUSED_PARAMETER")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GameViewModel = viewModel(),
) {
    val state: GameUiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (state.stage) {
        GameStage.Title -> TitleScreen(onPlay = viewModel::startGame)
        GameStage.Game -> GameScreen(state = state, viewModel = viewModel)
    }
}

@Composable
private fun TitleScreen(onPlay: () -> Unit, modifier: Modifier = Modifier) {
    val pulseTransition = rememberInfiniteTransition(label = "title-pulse")
    val pulseScale: Float by pulseTransition.animateFloat(
        initialValue = 0.97f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "title-logo-scale",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF3B0735)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.bgmenu),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x330F0016),
                            Color(0x660F0016),
                            Color(0xAA160017),
                        ),
                    ),
                ),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "THE NIGHT SHIFT",
                color = Color.White.copy(alpha = 0.82f),
                style = MaterialTheme.typography.labelLarge,
                letterSpacing = 3.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "DIVO",
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 360.dp)
                    .scale(pulseScale)
                    .aspectRatio(1.54f),
                contentScale = ContentScale.Fit,
            )
            Text(
                text = "FIVE REELS. ONE LUCKY SPIN.",
                color = ArcadeGold,
                style = MaterialTheme.typography.titleMedium,
                letterSpacing = 2.sp,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = onPlay,
                modifier = Modifier
                    .height(58.dp)
                    .widthIn(min = 190.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ArcadeGold,
                    contentColor = Color(0xFF4A071B),
                ),
                contentPadding = PaddingValues(horizontal = 28.dp),
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "PLAY",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "FREE PLAY · LOCAL CREDITS",
                color = Color.White.copy(alpha = 0.66f),
                style = MaterialTheme.typography.labelSmall,
                letterSpacing = 1.4.sp,
            )
        }
    }
}

@Composable
private fun GameScreen(
    state: GameUiState,
    viewModel: GameViewModel,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF431706)),
    ) {
        Image(
            painter = painterResource(R.drawable.machine_art),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 12.dp, top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            StatusPill(label = "CREDITS", value = state.balance.toCommaString())
            StatusPill(label = "WIN", value = state.win.toCommaString(), accent = true)
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 10.dp, top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            CompactIconButton(
                icon = if (state.musicEnabled) Icons.Default.MusicNote else Icons.Default.MusicOff,
                label = "Music",
                selected = state.musicEnabled,
                onClick = viewModel::toggleMusic,
            )
            CompactIconButton(
                icon = if (state.soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                label = "Sound",
                selected = state.soundEnabled,
                onClick = viewModel::toggleSound,
            )
        }

        ReelBoard(
            reels = state.reels,
            isSpinning = state.isSpinning,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = maxHeight * 0.13f)
                .width(maxWidth * 0.50f)
                .height(maxHeight * 0.66f),
        )

        AnimatedVisibility(
            visible = state.resultMessage != null,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = maxHeight * 0.19f),
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Surface(
                color = if (state.win > 0) Color(0xE62DAE62) else Color(0xCC260616),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp,
            ) {
                Text(
                    text = state.resultMessage.orEmpty(),
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        if (state.autoSpinsRemaining > 0) {
            Surface(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp),
                color = Color(0xDD5E0A4F),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "AUTO ${state.autoSpinsRemaining}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = ArcadeGold,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            ControlButton(
                label = "COIN",
                value = state.coin.toString(),
                icon = Icons.Default.LocalOffer,
                onClick = viewModel::cycleCoin,
                modifier = Modifier.weight(1f),
            )
            ControlButton(
                label = "LINES",
                value = state.lines.toString(),
                icon = Icons.Default.List,
                onClick = viewModel::cycleLines,
                modifier = Modifier.weight(1f),
            )
            ControlButton(
                label = "MAXBET",
                value = (state.coin * state.lines).toCommaString(),
                icon = Icons.Default.Bolt,
                onClick = viewModel::maxBet,
                modifier = Modifier.weight(1.18f),
            )
            ControlButton(
                label = "AUTO",
                value = if (state.autoSpinsRemaining > 0) "STOP" else "5",
                icon = Icons.Default.AutoMode,
                onClick = {
                    if (state.autoSpinsRemaining > 0) viewModel.stopAutoSpin()
                    else viewModel.openModal(GameModal.AutoSpin)
                },
                modifier = Modifier.weight(1f),
            )
            ControlButton(
                label = "INFO",
                value = "PAY",
                icon = Icons.Default.Info,
                onClick = { viewModel.openModal(GameModal.PayTable) },
                modifier = Modifier.weight(1f),
            )
            Button(
                onClick = viewModel::spin,
                modifier = Modifier
                    .weight(1.32f)
                    .height(58.dp),
                enabled = !state.isSpinning,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE80C28),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF8B2733),
                ),
                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 3.dp),
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Spin", modifier = Modifier.size(22.dp))
                    Text("SPIN", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black)
                }
            }
        }
    }

    when (state.activeModal) {
        GameModal.AutoSpin -> AutoSpinDialog(state, viewModel)
        GameModal.PayTable -> ArtDialog(
            title = "PAY TABLE",
            imageRes = R.drawable.paytable_art,
            onClose = viewModel::closeModal,
            secondaryAction = { viewModel.openModal(GameModal.PayLines) },
            secondaryLabel = "VIEW PAYLINES",
        )
        GameModal.PayLines -> ArtDialog(
            title = "PAY LINES",
            imageRes = R.drawable.paylines_art,
            onClose = viewModel::closeModal,
            secondaryAction = { viewModel.openModal(GameModal.Credits) },
            secondaryLabel = "CREDITS",
        )
        GameModal.Credits -> CreditsDialog(onClose = viewModel::closeModal)
        null -> Unit
    }
}

@Composable
private fun ReelBoard(
    reels: List<List<Int>>,
    isSpinning: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clipToBounds(),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        reels.forEachIndexed { index: Int, symbols: List<Int> ->
            ReelColumn(
                symbols = symbols,
                isSpinning = isSpinning,
                modifier = Modifier.weight(1f),
                delayMillis = index * 70,
            )
        }
    }
}

@Composable
private fun ReelColumn(
    symbols: List<Int>,
    isSpinning: Boolean,
    delayMillis: Int,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "reel-$delayMillis")
    val phase: Float by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(180, delayMillis = delayMillis),
            repeatMode = RepeatMode.Restart,
        ),
        label = "reel-phase-$delayMillis",
    )
    AnimatedContent(
        targetState = symbols,
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Black.copy(alpha = 0.92f))
            .border(1.dp, Color(0xFF2B0C04), RoundedCornerShape(4.dp))
            .graphicsLayer {
                translationY = if (isSpinning) (phase - 0.5f) * 22f else 0f
            },
        transitionSpec = {
            (slideInVertically { height: Int -> -height } + fadeIn()) togetherWith
                (slideOutVertically { height: Int -> height } + fadeOut())
        },
        label = "reel-symbols",
    ) { reelSymbols: List<Int> ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            reelSymbols.forEach { symbol: Int ->
                Image(
                    painter = painterResource(symbolResource(symbol)),
                    contentDescription = "Slot symbol",
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 3.dp, vertical = 2.dp),
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}

@Composable
private fun StatusPill(
    label: String,
    value: String,
    accent: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = Color(0xDD3A1208),
        shape = RoundedCornerShape(9.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (accent) Color(0xFF61D56A) else Color(0xFFFF8D1A)),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, color = Color.White.copy(alpha = 0.72f), style = MaterialTheme.typography.labelSmall)
            Text(
                value,
                color = if (accent) Color(0xFF7CFF84) else ArcadeGold,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun CompactIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(40.dp),
        color = if (selected) Color(0xCC431706) else Color(0xAA260616),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (selected) ArcadeGold else Color.White.copy(alpha = 0.25f)),
    ) {
        IconButton(onClick = onClick, modifier = Modifier.fillMaxSize()) {
            Icon(icon, contentDescription = label, tint = if (selected) ArcadeGold else Color.White.copy(alpha = 0.62f))
        }
    }
}

@Composable
private fun ControlButton(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(58.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xE6591907),
            contentColor = Color.White,
        ),
        contentPadding = PaddingValues(horizontal = 3.dp, vertical = 3.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = ArcadeGold)
            Text(label, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(value, style = MaterialTheme.typography.labelSmall, color = ArcadeGold, maxLines = 1)
        }
    }
}

@Composable
private fun AutoSpinDialog(state: GameUiState, viewModel: GameViewModel) {
    Dialog(onDismissRequest = viewModel::closeModal, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .wrapContentHeight(),
            color = PanelPurple,
            shape = RoundedCornerShape(26.dp),
            border = androidx.compose.foundation.BorderStroke(2.dp, ArcadePink),
            shadowElevation = 18.dp,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DialogHeader(title = "AUTO SPIN", onClose = viewModel::closeModal)
                Text(
                    text = "Let the reels roll while you watch the lights.",
                    color = Color.White.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                    Button(
                        onClick = { viewModel.setAutoSpinAmount(state.autoSpinAmount - 5) },
                        modifier = Modifier.size(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF47104C)),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease auto spins")
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.autoSpinAmount.toString(),
                            color = ArcadeGold,
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Black,
                        )
                        Text("SPINS", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.labelSmall)
                    }
                    Button(
                        onClick = { viewModel.setAutoSpinAmount(state.autoSpinAmount + 5) },
                        modifier = Modifier.size(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF47104C)),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Increase auto spins")
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = { viewModel.startAutoSpin(state.autoSpinAmount) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ArcadeGold, contentColor = Color(0xFF4A071B)),
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("START AUTO", fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
private fun ArtDialog(
    title: String,
    imageRes: Int,
    onClose: () -> Unit,
    secondaryAction: () -> Unit,
    secondaryLabel: String,
) {
    Dialog(onDismissRequest = onClose, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.94f).fillMaxHeight(0.86f),
            color = Color(0xFF4B0870),
            shape = RoundedCornerShape(22.dp),
            border = androidx.compose.foundation.BorderStroke(2.dp, ArcadePink),
        ) {
            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                DialogHeader(title = title, onClose = onClose)
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = title,
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                )
                Button(
                    onClick = secondaryAction,
                    modifier = Modifier.fillMaxWidth(0.64f).height(44.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ArcadeGold, contentColor = Color(0xFF4A071B)),
                ) {
                    Text(secondaryLabel, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun CreditsDialog(onClose: () -> Unit) {
    Dialog(onDismissRequest = onClose, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.82f),
            color = PanelPurple,
            shape = RoundedCornerShape(24.dp),
            border = androidx.compose.foundation.BorderStroke(2.dp, ArcadePink),
        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DialogHeader(title = "CREDITS", onClose = onClose)
                Text("DIVO", color = ArcadeGold, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "A bright little slot-machine tribute built for free play.\n\nNo real money. Just reels, fruit, and a little luck.\n\nDeveloper: ALTOMEDIA\nContact: altomediaindonesia@gmail.com",
                    textAlign = TextAlign.Center,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = onClose,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ArcadeGold, contentColor = Color(0xFF4A071B)),
                ) {
                    Text("BACK TO THE REELS", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DialogHeader(title: String, onClose: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, letterSpacing = 1.4.sp)
        IconButton(onClick = onClose) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }
    }
}

private fun symbolResource(symbol: Int): Int = when (symbol) {
    0 -> R.drawable.symbol_0
    1 -> R.drawable.symbol_1
    2 -> R.drawable.symbol_2
    3 -> R.drawable.symbol_3
    4 -> R.drawable.symbol_4
    5 -> R.drawable.symbol_5
    6 -> R.drawable.symbol_6
    7 -> R.drawable.symbol_7
    8 -> R.drawable.symbol_8
    else -> R.drawable.symbol_9
}

private fun Int.toCommaString(): String = NumberFormat.getIntegerInstance(Locale.US).format(this)
