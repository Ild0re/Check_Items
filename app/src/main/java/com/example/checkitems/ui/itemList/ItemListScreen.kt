package com.example.checkitems.ui.itemList

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.checkitems.R
import com.example.checkitems.domain.model.Item
import com.example.checkitems.ui.theme.CheckItemsTheme
import java.util.Calendar

@Composable
fun ItemListScreen() {
    Scaffold(
        topBar = { ItemToolbar() }
    ) { paddingValues ->
        MainContent(Modifier.padding(paddingValues))
    }
}

@Composable
fun ItemToolbar() {
    TopAppBar(
        title = {
            androidx.compose.material.Text(
                text = "Список товаров",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        },
        backgroundColor = Color(0xFF87CEEB)
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainContent(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    var searchText by remember { mutableStateOf("") }
    var showQuantityDialog by remember { mutableStateOf(false) }
    var selectedItemQuantity by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .clickable { focusManager.clearFocus() }
    ) {
        SearchBar(searchText = searchText, onSearchTextChanged = { searchText = it })

        ItemList(
            products = generateDummyItems(),
            onEditQuantity = { amount ->
                selectedItemQuantity = amount
                showQuantityDialog = true
                focusManager.clearFocus()
            },
            onDeleteItem = {
                // TODO: Delete product logic
            }
        )

        if (showQuantityDialog) {
            QuantityDialog(
                initialQuantity = selectedItemQuantity,
                onQuantityChanged = { amount ->
                    selectedItemQuantity = amount
                },
                onDismiss = { showQuantityDialog = false },
                onConfirm = {
                    // TODO: Save amount logic
                    showQuantityDialog = false
                }
            )
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .focusRequester(focusRequester),
        placeholder = { Text("Поиск товаров") },
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.Black
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onSearchTextChanged("") }) {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Clear",
                        tint = Color.Black
                    )
                }
            }
        },
        singleLine = true
    )

    LaunchedEffect(Unit) {
        focusRequester.freeFocus()
    }
}

@Composable
fun ItemList(products: List<Item>, onEditQuantity: (Int) -> Unit, onDeleteItem: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(products) { product ->
            ItemCard(product = product, onEditQuantity = onEditQuantity, onDeleteItem = onDeleteItem)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemCard(product: Item, onEditQuantity: (Int) -> Unit, onDeleteItem: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name.uppercase(),
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )

                Row {
                    androidx.compose.material.IconButton(onClick = { onEditQuantity(product.amount) }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = Color.Blue)
                    }
                    androidx.compose.material.IconButton(onClick = onDeleteItem) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                product.tags.forEach { tag ->
                    Chip(text = tag)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "На складе", color = Color.Black)
                    Text(text = product.amount.toString(), style = TextStyle(fontSize = 20.sp), color = Color.Black)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Дата добавления", color = Color.Black)
                    Text(text = product.time.toString(), color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Black),
        color = Color.White
    ) {
        androidx.compose.material.Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            color = Color.Black
        )
    }
}

@Composable
fun QuantityDialog(
    initialQuantity: Int,
    onQuantityChanged: (Int) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var amount by remember { mutableStateOf(initialQuantity) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_manage), // TODO: Replace with custom gear icon
                    contentDescription = "Settings",
                    modifier = Modifier.size(48.dp)
                )
                Text("Количество товара", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = { if (amount > 0) { amount-- ; onQuantityChanged(amount)} }, modifier = Modifier.background(Color.LightGray, CircleShape)) {
                        Icon(Icons.Filled.Clear, contentDescription = "Remove", tint = Color.Black)
                    }
                    Text(amount.toString(), style = TextStyle(fontSize = 24.sp))
                    IconButton(onClick = { amount++ ; onQuantityChanged(amount) }, modifier = Modifier.background(Color.LightGray, CircleShape)) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.Black)
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Отмена")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onConfirm) {
                        Text("Принять")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CheckItemsTheme {
        ItemListScreen()
    }
}

fun generateDummyItems(): List<Item> {
    val calendar = Calendar.getInstance()
    return listOf(
        Item(1, "Apple Watch Series 9", 1742457600000, mutableListOf("Electronics", "Wearable"), 10, ),
        Item(2, "Samsung Galaxy S24 Ultra", 1742457600000, mutableListOf("Electronics", "Smartphone"), 5),
        Item(3, "Sony WH-1000XM5", 1742457600000, mutableListOf("Electronics", "Headphones"), 12),
        Item(4, "Nike Air Max 90", 1742457600000, mutableListOf("Fashion", "Shoes"), 20),
        Item(5, "Levi's 501 Jeans", 1742457600000, mutableListOf("Fashion", "Clothing"), 15)
    )
}
