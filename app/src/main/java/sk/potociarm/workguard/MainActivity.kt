package sk.potociarm.workguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.compose.WorkGuardTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkGuardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                    //color = MaterialTheme.colorScheme.background
                ) {
                    WorkGuardApp()
                }
            }
        }
    }
}