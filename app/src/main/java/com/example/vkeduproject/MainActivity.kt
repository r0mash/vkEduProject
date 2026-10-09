package com.example.vkeduproject

import android.content.Intent
//import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkeduproject.ui.theme.VkEduProjectTheme
import kotlin.jvm.java
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEduProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier.padding(16.dp)) {
        // 3. Поле ввода (EditText в старом Android)
        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Введите текст или телефон") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        //Кнопки
        //Первая
        Button(onClick = {
            if (text.isNotBlank()) {
                val intent = Intent(context, MainActivity2::class.java)
                intent.putExtra("EXTRA_TEXT", text)
                context.startActivity(intent)
            }
        }) {
            Text("Открыть вторую Activity")
        }
        //Вторая кнопка
        Button(onClick = {
            val phone = text.trim()
            if (phone.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL).apply{data = "tel:$phone".toUri()}
                context.startActivity(intent)
            }
        }) {
            Text("Позвонить другу")
        }
        //Кнопка 3 (системный)
        Button(onClick = {
            if (text.isNotBlank()) {
                // ACTION_SEND говорит системе, что мы хотим что-то отправить
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain" // Указываем, что отправляем обычный текст
                }

                // createChooser принудительно вызывает красивое нижнее меню выбора приложений
                val shareIntent = Intent.createChooser(sendIntent, "Куда отправить?")
                context.startActivity(shareIntent)
            }
        }) {
            Text("Поделиться текстом")
        }
    }
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    //Text(
    //    text = "Hello $name!",
    //    modifier = modifier
    //)
    VkEduProjectTheme {
        Greeting("Android")
    }

}