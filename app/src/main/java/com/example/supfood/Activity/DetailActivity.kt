package com.example.supfood.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.supfood.api.fetchDetailData
import kotlinx.coroutines.runBlocking

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val paramId = intent.getStringExtra("paramId")
            if (paramId != null) {
                DetailsView(paramId)
            }else{
                IfDetailIsEmpty()
            }
        }
    }
}

@Composable     //Display a message if the api does not return data
fun IfDetailIsEmpty(){
    Column(){
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
        Text(text = "No data found", fontSize = 30.sp)
    }
}

@Composable     //Show API result
fun DetailsView(paramId: String){
    val apiData = runBlocking {
        fetchDetailData(paramId)
    }
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
       SectionDetails(apiData.featured_image, apiData.title, apiData.publisher, apiData.ingredients)
    }
}

@Composable     //Show recipe details
fun SectionDetails(imageUrl: String, title: String, autor: String, ingredients : List<String>){
    Column {
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = "Image $title",
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .size(400.dp, 400.dp)
        )
        Text(text = "$title",
            modifier = Modifier.padding(top = 0.dp, start = 10.dp),
            fontSize = 30.sp)
        Text(text = "By ${autor}",
            modifier = Modifier.padding(top = 0.dp, start = 10.dp),
            fontSize = 20.sp)
        Spacer(modifier = Modifier.height(26.dp))
        LazyColumn{
            items(ingredients.size) { index ->
                Text(text = "${ingredients[index]}",
                    modifier = Modifier.padding(top = 0.dp, start = 10.dp),
                    fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(26.dp))
    }
}