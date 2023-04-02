package com.example.supfood.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlinx.coroutines.runBlocking
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.supfood.api.fetchApiData
import com.example.supfood.api.fetchRecipeData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ScrollableColumn()
        }
    }
}

@Composable     // Create the page structure
fun ScrollableColumn(){
    val apiData = runBlocking {
        fetchApiData()
    }
    val results = apiData.results;
    Column(){
        SearchBar();
        LazyColumn{
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

@Composable     //Create the section structure
fun SectionWithImageAndTitle(imageUrl: String, title: String, rating: String, id: String) {
    val context = LocalContext.current
    Column {
        Image(painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = "Image $title",
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .size(400.dp, 400.dp)
        )
        Text(text = "$title",
            modifier = Modifier.padding(top = 0.dp, start = 10.dp),
            fontSize = 30.sp)
        Text(text = "${rating} / 100",
            modifier = Modifier.padding(top = 0.dp, start = 10.dp),
            fontSize = 20.sp)
        Button(onClick = {
                val navigate = Intent(context, DetailActivity::class.java)
                navigate.putExtra("paramId", id)
                context.startActivity(navigate)
                         },
            Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFFF9F10),
                contentColor = Color(0xFFFFFFFF)),
        ) {
            Text(text = "Voir la recette")
        }
        Spacer(modifier = Modifier.height(26.dp))
    }
}

@Composable    //Create the search bar
fun ScrollableButtonRow() {
    val buttons = listOf("Beef", "Chicken", "Soup", "Dessert", "Vegetarian", "Pasta")
    LazyRow {
        items(buttons) { buttonText ->
            ButtonWithText(text = buttonText)
        }
    }
}

@Composable   //Create the search bar button
fun ButtonWithText(text: String) {
    val context = LocalContext.current
    Button(onClick = {
            val navigate = Intent(context, SearchActivity::class.java)
            navigate.putExtra("paramQuery", text)
            context.startActivity(navigate)
                  },
        Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF9F10),
            contentColor = Color(0xFFFFFFFF)
        ),
    ) {
        Text(text = text)
    }
}

@Composable     //Create the search bar
fun SearchBar() {
    val focusManager = LocalFocusManager.current
    val query = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        Modifier
            .padding(start = 1.dp, end = 1.dp, top = 0.dp, bottom = 5.dp)
            .shadow(2.dp, RoundedCornerShape(3.dp))
            .fillMaxWidth()
    ){
        Row{
            OutlinedTextField(
                value = query.value,
                onValueChange = { newValue -> query.value = newValue },
                placeholder = { Text("Search") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { val navigate = Intent(context, SearchActivity::class.java)
                    navigate.putExtra("paramQuery", query.value)
                    context.startActivity(navigate)
                          },
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp)
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
        ScrollableButtonRow()
    }
}

fun main(){    //Testing the fetchApiData function and others
    val data = runBlocking {
        val query = "chicken"
        fetchRecipeData(query)
    }
    println(data)
}


