package com.example.supfood.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.supfood.api.fetchRecipeData
import kotlinx.coroutines.runBlocking

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val paramQuery = intent.getStringExtra("paramQuery")
            if (paramQuery != null) {
                SearchScrollableColumn(paramQuery)
            }else{
                IfSearchEmpty()
            }
        }
    }
}

@Composable     //Show if search is empty
fun IfSearchEmpty(){
    Column(){
        val context = LocalContext.current
        Button(onClick = {
                val navigate = Intent(context, MainActivity::class.java)
                context.startActivity(navigate)
            },
            Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp, top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFF9F10),
                contentColor = Color(0xFFFFFFFF)
            ),
        ) {
            Text(text = "BACK", fontSize = 20.sp)
        }
        Text(text = "No data found", fontSize = 30.sp)
    }
}

@Composable    //Show search result
fun SearchScrollableColumn(paramQuery: String){
    val apiData = runBlocking {
        fetchRecipeData(paramQuery)
    }
    if(apiData.count == 0){
        IfSearchEmpty()
    } else {
        val results = apiData.results;

        Column() {
            val context = LocalContext.current
            Button(
                onClick = {
                    val navigate = Intent(context, MainActivity::class.java)
                    context.startActivity(navigate)
                },
                Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp, top = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF9F10),
                    contentColor = Color(0xFFFFFFFF)
                ),
            ) {
                Text(text = "BACK", fontSize = 20.sp)
            }
            LazyColumn {
                items(10) { index ->
                    SectionWithImageAndTitle(
                        imageUrl = "${results[index].featured_image}",
                        title = "${results[index].title}",
                        rating = "${results[index].rating}",
                        id = "${results[index].pk}"
                    )
                }
            }
        }
    }
}
