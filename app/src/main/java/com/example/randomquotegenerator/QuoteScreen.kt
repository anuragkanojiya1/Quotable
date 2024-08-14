package com.example.randomquotegenerator

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(navController: NavController, quoteViewModel: QuoteViewModel) {

    val context = LocalContext.current
    val quotes by quoteViewModel.quotes.observeAsState(initial = emptyList())
    val errorMessage by quoteViewModel.errorMessage.observeAsState()

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF181818), Color(0xFF2E2E2E)
        )
    )
    val gradient2 = Brush.horizontalGradient(
        colors = listOf(Color(0xFF00BCD4), Color(0xFF1E88E5))
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(brush = gradient)

    ) {
        TopAppBar(title = {
            Text(text = "Quotable",
                fontWeight = FontWeight.W500,
                fontSize = 24.sp)
        })

        Text(text = "An Android App for Generating Random Quotes...",
            modifier = Modifier.padding(16.dp)
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Light,
            color = Color.White,
            fontSize = 28.sp)

        ExtendedFloatingActionButton(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(24.dp)
            .background(gradient2, shape = RoundedCornerShape(20.dp)),
            onClick = { quoteViewModel.fetchRandomQuotes() }) {
            Text("Fetch Quote")
        }

        Spacer(modifier = Modifier.height(16.dp))

        quotes.forEach { quote ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(modifier = Modifier
                    .padding(16.dp),
                    text = "\"${quote.content}\"\n - ${quote.author}",
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExtendedFloatingActionButton(modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(gradient2, shape = RoundedCornerShape(20.dp)),
                    onClick = { shareQuote(context, "\"${quote.content}\" - ${quote.author}") }) {
                    Text("Share Quote")
                }
            }
        }

        // Show error message as a Toast
        errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

fun shareQuote(context: Context, quote: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, quote)
        type = "text/plain"
    }
    val chooser = Intent.createChooser(intent, "Share Quote via")
    context.startActivity(chooser)
}