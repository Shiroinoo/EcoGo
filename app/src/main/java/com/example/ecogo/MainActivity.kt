package com.example.ecogo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

// ---------- Colores ----------
val AzulFondo = Color(0xFF4F7A9C)
val AzulOscuro = Color(0xFF2D5577)
val AzulLogo = Color(0xFF0B2A4A)
val Amarillo = Color(0xFFD8B03A)
val GrisClaro = Color(0xFFB0C4D2)
val GrisOscuro = Color(0xFF2B2B2B)

// ---------- Rutas ----------
object Rutas {
    const val INICIO = "inicio"
    const val SELECCION = "seleccion"
    const val PILAS = "pilas"
    const val PAPEL = "papel"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { EcoGoApp() }
        }
    }
}

@Composable
fun EcoGoApp() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Rutas.INICIO) {
        composable(Rutas.INICIO) {
            PantallaInicio(onEntrar = { nav.navigate(Rutas.SELECCION) })
        }
        composable(Rutas.SELECCION) {
            PantallaSeleccion(
                onPilas = { nav.navigate(Rutas.PILAS) },
                onPapel = { nav.navigate(Rutas.PAPEL) }
            )
        }
        composable(Rutas.PILAS) { PantallaPilas() }
        composable(Rutas.PAPEL) { PantallaPapel() }
    }
}

// ---------- Componentes reutilizables ----------
@Composable
fun Logo(ancho: Dp = 140.dp) {
    Image(
        painter = painterResource(id = R.drawable.logo_ecogo),
        contentDescription = "Logo EcoGo",
        modifier = Modifier.width(ancho)
    )
}
@Composable
fun Encabezado() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(AzulOscuro)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) { Logo() }
}

@Composable
fun Etiqueta(texto: String, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    Text(
        text = texto,
        color = Color.White,
        fontSize = 12.sp,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(GrisOscuro)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

// ---------- Pantalla 1: Inicio ----------
@Composable
fun PantallaInicio(onEntrar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulFondo)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(AzulOscuro),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Logo()
                Spacer(Modifier.height(8.dp))
                Text("GUÍA DE RECICLAJE", color = Color.White, fontSize = 16.sp)
                Text("Bienvenido", color = Color.White, fontSize = 16.sp)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            // Botón "Entrar →"
            TextButton(onClick = onEntrar) {
                Text("Entrar", color = Color.White, fontSize = 16.sp)
                Spacer(Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Entrar",
                    tint = Color.White
                )
            }
        }
    }
}

// ---------- Pantalla 2: Selección de material ----------
@Composable
fun PantallaSeleccion(onPilas: () -> Unit, onPapel: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulFondo)
    ) {
        Encabezado()

        Spacer(Modifier.height(48.dp))
        Text(
            text = "Selecciona el tipo de material",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GrisOscuro)
                .padding(12.dp)
        )

        Spacer(Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            BotonMaterial("PILAS", Amarillo, onPilas)
            BotonMaterial("PAPEL", GrisClaro, onPapel)
        }
    }
}

@Composable
fun BotonMaterial(texto: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color.Black),
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .height(72.dp)
    ) {
        Text(texto, fontSize = 13.sp)
    }
}

// ---------- Pantalla 3: Pilas ----------
@Composable
fun PantallaPilas() {
    val context = LocalContext.current
    val tipos = listOf(
        "Pilas Alcalinas y recargables\n(AA, AAA, 9V, etc)",
        "Baterías de plomo-ácido\n(Automóviles y motos)",
        "Baterías de iones de litio\n(electrónica portátil)",
        "Baterías industriales y\nespeciales"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulFondo)
    ) {
        Encabezado()

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "¿Qué tipo de pilas deben reciclarse?",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Amarillo)
                    .padding(14.dp)
            )

            tipos.forEach { Etiqueta(it) }

            Spacer(Modifier.height(8.dp))

            Etiqueta(
                "Acude a las instalaciones de la Secretaría de Ecología y Gestión " +
                        "Ambiental, Ubicadas en Valentín Gama#865 Col. Las Águilas 3ra " +
                        "Sección, C.P 78260",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            // Al tocar, abre el marcador del teléfono
            Etiqueta("Más info: 444 151 0609") {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:4441510609"))
                context.startActivity(intent)
            }
        }
    }
}

// ---------- Pantalla Papel (pendiente de diseñar) ----------
@Composable
fun PantallaPapel() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulFondo)
    ) {
        Encabezado()
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Información de papel próximamente",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}