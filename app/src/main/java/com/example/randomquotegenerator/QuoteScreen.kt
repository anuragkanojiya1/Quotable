package com.example.randomquotegenerator

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuoteScreen(quoteViewModel: QuoteViewModel) {
    val context = LocalContext.current
    val quotes by quoteViewModel.quotes.observeAsState(initial = emptyList())
    val errorMessage by quoteViewModel.errorMessage.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { quoteViewModel.fetchRandomQuotes() }) {
            Text("Fetch Quote")
        }

        Spacer(modifier = Modifier.height(16.dp))

        quotes.forEach { quote ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = "\"${quote.content}\" - ${quote.author}")

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { shareQuote(context, "\"${quote.content}\" - ${quote.author}") }) {
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