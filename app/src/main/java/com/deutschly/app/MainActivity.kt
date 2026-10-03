package com.deutschly.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Sage = Color(0xFF526D59)
private val Cream = Color(0xFFFFFBF4)
private val Yellow = Color(0xFFFFE7A4)
private val Sky = Color(0xFFD9ECF4)
private val Red = Color(0xFFB7574A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { DeutschlyApp() }
    }
}

@Composable
private fun DeutschlyApp() {
    val scheme = if (isSystemInDarkTheme()) darkColorScheme(
        primary = Color(0xFFB5D0B7), secondary = Yellow, tertiary = Sky,
        background = Color(0xFF1A1C1A), surface = Color(0xFF222522), onPrimary = Color(0xFF1E3524)
    ) else lightColorScheme(
        primary = Sage, secondary = Color(0xFF816B26), tertiary = Color(0xFF427180),
        background = Cream, surface = Color(0xFFFFFDF9)
    )
    MaterialTheme(colorScheme = scheme, typography = Typography()) {
        var destination by rememberSaveable { mutableIntStateOf(0) }
        Scaffold(
            containerColor = Cream,
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    val items = listOf("Home" to Icons.Filled.Home, "Lernen" to Icons.Filled.MenuBook, "Üben" to Icons.Filled.Edit, "Wiederholen" to Icons.Filled.Refresh, "Profil" to Icons.Filled.Person)
                    items.forEachIndexed { index, (label, icon) ->
                        NavigationBarItem(selected = destination == index, onClick = { destination = index }, icon = { Icon(icon, null) }, label = { Text(label) })
                    }
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding)) {
                when (destination) {
                    0 -> Home(onStart = { destination = 1 })
                    1 -> Learn()
                    2 -> Practice()
                    3 -> Review()
                    else -> Profile()
                }
            }
        }
    }
}

@Composable private fun Home(onStart: () -> Unit) = LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
    item { Row(verticalAlignment = Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text("Hallo, Duy! 👋", fontSize = 28.sp, fontWeight = FontWeight.Bold); Text("Sẵn sàng học tiếng Đức hôm nay?", color = MaterialTheme.colorScheme.onSurfaceVariant) }; AssistChip(onClick = {}, label = { Text("🔥 12 Tage") }, leadingIcon = { Icon(Icons.Default.LocalFireDepartment, null, tint = Red) }) } }
    item { DailyGoal() }
    item { SectionTitle("Weiterlernen") }
    item { ContinueCard(onStart) }
    item { SectionTitle("Deutsch des Tages 🇩🇪") }
    item { WordOfDay() }
    item { SectionTitle("Für dich empfohlen") }
    items(listOf("Hören · Im Café bestellen" to "8 Minuten", "Grammatik · Wechselpräpositionen" to "12 Minuten", "Sprechen · Deine Meinung sagen" to "10 Minuten")) { (title, duration) -> ListItem(headlineContent = { Text(title, fontWeight = FontWeight.SemiBold) }, supportingContent = { Text(duration) }, leadingContent = { Surface(shape = RoundedCornerShape(14.dp), color = Sky, modifier = Modifier.size(48.dp)) { Icon(Icons.Default.PlayArrow, null, modifier = Modifier.padding(12.dp), tint = Sage) } }, modifier = Modifier.clip(RoundedCornerShape(18.dp)).background(MaterialTheme.colorScheme.surface)) }
}

@Composable private fun DailyGoal() = Card(colors = CardDefaults.cardColors(containerColor = Yellow), shape = RoundedCornerShape(24.dp)) { Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) { Box(contentAlignment = Alignment.Center, modifier = Modifier.size(76.dp)) { CircularProgressIndicator(progress = { .8f }, modifier = Modifier.fillMaxSize(), color = Sage, trackColor = Color.White.copy(.55f), strokeWidth = 7.dp); Text("80%", fontWeight = FontWeight.Bold) }; Spacer(Modifier.width(18.dp)); Column(Modifier.weight(1f)) { Text("Heutiges Ziel", fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("15 Minuten Deutsch"); Text("4 / 5 Aufgaben abgeschlossen", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant) }; Icon(Icons.Default.LocalFireDepartment, null, tint = Red) } }

@Composable private fun ContinueCard(onStart: () -> Unit) = Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Sage)) { Column(Modifier.padding(22.dp)) { Text("DEUTSCH A2", color = Color.White.copy(.75f), style = MaterialTheme.typography.labelLarge); Spacer(Modifier.height(5.dp)); Text("Lektion 12", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold); Text("Im Alltag", color = Color.White.copy(.85f)); Spacer(Modifier.height(18.dp)); LinearProgressIndicator(progress = { .72f }, color = Yellow, trackColor = Color.White.copy(.25f), modifier = Modifier.fillMaxWidth().height(7.dp).clip(RoundedCornerShape(9.dp))); Spacer(Modifier.height(8.dp)); Text("72% · noch 12 Minuten", color = Color.White.copy(.8f), style = MaterialTheme.typography.labelMedium); Spacer(Modifier.height(14.dp)); Button(onClick = onStart, colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Sage)) { Text("Weiterlernen  →", fontWeight = FontWeight.Bold) } } }

@Composable private fun WordOfDay() = Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Sky)) { Column(Modifier.padding(22.dp)) { Row(verticalAlignment = Alignment.CenterVertically) { Column(Modifier.weight(1f)) { Text("gemütlich", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold); Text("[ɡəˈmyːtlɪç]  ·  Adjektiv · B1", color = MaterialTheme.colorScheme.onSurfaceVariant) }; IconButton(onClick = {}) { Icon(Icons.Default.VolumeUp, "Anhören") } }; Spacer(Modifier.height(10.dp)); Text("ấm cúng, dễ chịu", fontWeight = FontWeight.Medium, fontSize = 17.sp); Spacer(Modifier.height(12.dp)); Text("“Das Café ist sehr gemütlich.”", fontStyle = androidx.compose.ui.text.font.FontStyle.Italic); Text("Quán cà phê rất ấm cúng.", color = MaterialTheme.colorScheme.onSurfaceVariant); Spacer(Modifier.height(16.dp)); Row { OutlinedButton(onClick = {}) { Icon(Icons.Default.FavoriteBorder, null); Spacer(Modifier.width(7.dp)); Text("Lưu") }; Spacer(Modifier.width(10.dp)); TextButton(onClick = {}) { Text("Mehr lernen") } } } }
@Composable private fun SectionTitle(text: String) = Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))

@Composable private fun Learn() = LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
    item { Text("Lernen", fontSize = 30.sp, fontWeight = FontWeight.Bold); Text("Kleine Schritte, große Fortschritte.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
    item { LearningHero() }
    item { SectionTitle("Deine Lernbereiche") }
    items(listOf(Triple("Wortschatz", "326 Wörter gelernt", Icons.Default.Style), Triple("Grammatik", "B1 · 8 Kapitel", Icons.Default.AutoStories), Triple("Hören", "18 Übungen", Icons.Default.Headphones), Triple("Sprechen", "9 Dialoge", Icons.Default.Mic))) { (title, sub, icon) -> ElevatedCard(shape = RoundedCornerShape(20.dp)) { ListItem(headlineContent = { Text(title, fontWeight = FontWeight.Bold) }, supportingContent = { Text(sub) }, leadingContent = { Icon(icon, null, tint = Sage, modifier = Modifier.size(30.dp)) }, trailingContent = { Icon(Icons.Default.ChevronRight, null) }) } }
}
@Composable private fun LearningHero() = Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFE9E7D7)), shape = RoundedCornerShape(24.dp)) { Column(Modifier.padding(20.dp)) { Text("Heute neu", color = Sage, fontWeight = FontWeight.Bold); Text("Wortschatz: Unterwegs", fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("30 Karten · A2", color = MaterialTheme.colorScheme.onSurfaceVariant); Spacer(Modifier.height(14.dp)); Button(onClick = {}) { Icon(Icons.Default.PlayArrow, null); Spacer(Modifier.width(7.dp)); Text("Lernmodus starten") } } }

@Composable private fun Practice() = LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
    item { Text("Üben", fontSize = 30.sp, fontWeight = FontWeight.Bold); Text("Festige dein Deutsch mit kurzen Übungen.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
    items(listOf(Triple("Hören", "Verstehe echtes Deutsch", Icons.Default.Headphones), Triple("Sprechen", "Sprich mit Selbstvertrauen", Icons.Default.RecordVoiceOver), Triple("Schreiben", "Sätze intelligent aufbauen", Icons.Default.Edit), Triple("Prüfungstraining", "Goethe & telc Vorbereitung", Icons.Default.Assignment))) { (title, sub, icon) -> Card(shape = RoundedCornerShape(22.dp)) { ListItem(headlineContent = { Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp) }, supportingContent = { Text(sub) }, leadingContent = { Surface(shape = RoundedCornerShape(14.dp), color = Yellow, modifier = Modifier.size(52.dp)) { Icon(icon, null, modifier = Modifier.padding(13.dp), tint = Sage) } }, trailingContent = { Icon(Icons.Default.ArrowForward, null) }) } }
}

@Composable private fun Review() = LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
    item { Text("Wiederholen", fontSize = 30.sp, fontWeight = FontWeight.Bold); Text("Deine Wiederholung ist bereit.", color = MaterialTheme.colorScheme.onSurfaceVariant) }
    item { Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF7DFDE)), shape = RoundedCornerShape(24.dp)) { Column(Modifier.padding(22.dp)) { Text("SPACED REPETITION", color = Red, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge); Text("24 Wörter warten auf dich", fontSize = 24.sp, fontWeight = FontWeight.Bold); Text("Nur etwa 8 Minuten für deinen Fortschritt."); Spacer(Modifier.height(16.dp)); Button(onClick = {}) { Icon(Icons.Default.Refresh, null); Spacer(Modifier.width(8.dp)); Text("Jetzt wiederholen") } } } }
    item { SectionTitle("Deine schwierigen Wörter") }
    items(listOf("die Gelegenheit · cơ hội", "vermeiden · tránh", "zuverlässig · đáng tin cậy")) { text -> ListItem(headlineContent = { Text(text, fontWeight = FontWeight.SemiBold) }, trailingContent = { Icon(Icons.Default.VolumeUp, null) }, modifier = Modifier.background(MaterialTheme.colorScheme.surface)) }
}

@Composable private fun Profile() = LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
    item { Row(verticalAlignment = Alignment.CenterVertically) { Surface(color = Sage, shape = RoundedCornerShape(50), modifier = Modifier.size(64.dp)) { Box(contentAlignment = Alignment.Center) { Text("D", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Bold) } }; Spacer(Modifier.width(14.dp)); Column { Text("Duy Nguyen", fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("B1 · 4.850 XP") } } }
    item { Card(shape = RoundedCornerShape(22.dp)) { Row(Modifier.padding(20.dp), horizontalArrangement = Arrangement.SpaceAround) { Stat("12", "Tage Streak"); Stat("326", "Wörter"); Stat("18", "Lektionen") } } }
    items(listOf("Lernfortschritt", "Tägliches Ziel · 15 Minuten", "Benachrichtigungen", "Einstellungen")) { setting -> ListItem(headlineContent = { Text(setting, fontWeight = FontWeight.Medium) }, trailingContent = { Icon(Icons.Default.ChevronRight, null) }, modifier = Modifier.clip(RoundedCornerShape(14.dp)).clickable { }.background(MaterialTheme.colorScheme.surface)) }
}
@Composable private fun RowScope.Stat(number: String, label: String) = Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) { Text(number, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Sage); Text(label, style = MaterialTheme.typography.labelSmall, maxLines = 1, overflow = TextOverflow.Ellipsis) }
