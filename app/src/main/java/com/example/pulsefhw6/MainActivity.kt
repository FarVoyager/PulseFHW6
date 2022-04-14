package com.example.pulsefhw6

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pulsefhw6.ui.theme.PulseFHW6Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.getViewModel
import java.util.*

class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this.applicationContext

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            PulseFHW6Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main(context)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Main(context: Context) {
    val viewModel = getViewModel<MainViewModel>()
    val state by viewModel.viewState.collectAsState()

    Column {

        Column(
            Modifier
                .padding(top = 20.dp, bottom = 5.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(text = "Введите новые данные", Modifier.padding(top = 20.dp))
            TextField(
                value = state.stField, onValueChange = {
                    viewModel.changeFirstValue(it.filter { it.isDigit() })
                },
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                placeholder = { Text(text = "Пульс")}
            )
            TextField(
                value = state.ndField, onValueChange = {
                    viewModel.changeSecondValue(it.filter { it.isDigit() })
                },
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                placeholder = { Text(text = "Давление")}

            )
        }

        LazyColumn {
            items(state.list) {
                PulseItem(pulse = it.pulse, pressure = it.pressure)
            }
        }
    }

    FloatingActionButton(
        modifier =
        Modifier
            .wrapContentHeight(Alignment.Bottom)
            .wrapContentWidth(Alignment.End)
            .padding(10.dp)
            .height(80.dp)
            .width(80.dp),
        onClick = {
            if (state.stField.isEmpty() || state.ndField.isEmpty()) {
                Toast.makeText(context, "Поля пусты", Toast.LENGTH_SHORT).show()
            } else viewModel.addItemToList(PulseInfo(state.stField, state.ndField))
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PulseItem(pulse: String, pressure: String) {
    Card(
        Modifier
            .background(colorResource(id = R.color.white))
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Column(
            Modifier.background(Color.Yellow)
        ) {
            Text(
                text = "Пульс: $pulse",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
            Text(
                text = "Давление: $pressure",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
            Text(
                text = Calendar.getInstance().time.toString(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 5.dp),
                textAlign = TextAlign.Right,
                fontSize = 14.sp
            )
        }
    }
}
