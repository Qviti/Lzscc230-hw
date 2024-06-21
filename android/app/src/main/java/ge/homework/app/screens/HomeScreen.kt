package ge.homework.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ge.homework.app.models.vm.ProductViewModel
import ge.homework.app.retrofit.CartItem
import ge.homework.app.retrofit.OrderDTO

@Composable
fun HomeScreen(navController: NavController) {

    val view = remember {
        mutableIntStateOf(0)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Row {
            Button(onClick = { view.intValue = 0 }) {
                Text(text = "food")
            }
            Button(onClick = { view.intValue = 1 }) {
                Text(text = "cart")

            }
            Button(onClick = { view.intValue = 2 }) {
                Text(text = "order")
            }
        }

        when (view.intValue) {
            0 -> FoodView()
            1 -> CartView()
            else -> OrderView()
        }
    }

}

enum class FoodType {
    APPETIZER, MAIN_COURSE, DESSERT
}

data class FoodItem(
    val id: Long,
    val category: FoodType,
    val price: Int,
    val name: String,
)

@Preview
@Composable
fun FoodView(productViewModel: ProductViewModel = hiltViewModel()) {

    val items by productViewModel.foods.observeAsState()

    LaunchedEffect(Unit) {
        productViewModel.fetchFood()
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!items.isNullOrEmpty()) {
            items(items = items!!) { item ->
                FoodItemComposable(item, productViewModel)
            }
        }
    }
}

@Composable
fun FoodItemComposable(item: FoodItem, productViewModel: ProductViewModel) {

    var quantity by remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = {}
    ) {
        ItemPropertyComposable("name", item.name)
        ItemPropertyComposable("type", item.category.name)
        ItemPropertyComposable("price", item.price.toString())
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                label = { Text(text = "quantity") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                onValueChange = {
                    quantity = it
                },
                value = quantity,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier.padding(end = 12.dp),
                onClick = {
                    productViewModel.addItem(item.id, Integer.valueOf(quantity))
                }
            ) {
                Text(text = "Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ItemPropertyComposable(title: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(text = "$title: ")
        Text(text = value)
    }
}

data class CardItem(
    val id: Long,
    val foodId: Long,
    val foodName: String,
    val quantity: Int,
    val price: Int
)

@Composable
fun CartView(productViewModel: ProductViewModel = hiltViewModel()) {
    val items by productViewModel.items.observeAsState(mutableListOf())

    LaunchedEffect(key1 = Unit) {
        productViewModel.fetchItems()
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(items = items, key = CartItem::id) { item ->
            CartItemComposable(item, productViewModel)
        }

        if (items.isNotEmpty()) {
            item {
                Button(onClick = { productViewModel.order() }) {
                    Text(text = "Order")
                }
            }
        }
    }


}

@Composable
fun CartItemComposable(item: CartItem, productViewModel: ProductViewModel) {

    var quantity by remember {
        mutableStateOf(item.quantity.toString())
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        onClick = {}
    ) {
        CartItemPropertyComposable("name", item.foodName)
        CartItemPropertyComposable("price per item", item.price.toString())
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Text(text = "quantity: ")
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                modifier = Modifier.width(120.dp)
            )
            Button(onClick = { productViewModel.editItem(item.id, quantity.toInt()) }) {
                Text(text = "update")
            }
        }
        CartItemPropertyComposable("total price", item.total.toString())
        Button(onClick = { productViewModel.deleteItem(item.id) }) {
            Text(text = "delete item")
        }
    }
}

@Composable
fun CartItemPropertyComposable(title: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(text = "$title: ")
        Text(text = value)
    }

}


@Composable
fun OrderView(productViewModel: ProductViewModel = hiltViewModel()) {

    val orders by productViewModel.orders.observeAsState(mutableListOf())

    LaunchedEffect(key1 = Unit) {
        productViewModel.fetchOrders()
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = orders) { item ->
            OrderItemComposable(item, productViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

enum class OrderStatus {
    READY_TO_PAY, WAITING_FOR_PAYMENT_CONFIRM, IN_PROGRESS, DELIVERED, CANCELED
}

@Composable
fun OrderItemComposable(
    item: OrderDTO,
    productViewModel: ProductViewModel
) {
    val cardNumber = remember { mutableStateOf("") }
    val securityCode = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            ItemPropertyComposable("order number", item.id.toString())
            ItemPropertyComposable("order status", item.status.name)
            ItemPropertyComposable("total quantity", item.itemCount.toString())
            ItemPropertyComposable("total price", item.total.toString())

            if (item.status == OrderStatus.READY_TO_PAY) {
                OutlinedTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    label = { Text(text = "card number") },
                    value = cardNumber.value,
                    onValueChange = {
                        cardNumber.value = it
                    }
                )

                OutlinedTextField(
                    modifier = Modifier.padding(top = 16.dp),
                    label = { Text(text = "security code") },
                    value = securityCode.value,
                    onValueChange = {
                        securityCode.value = it
                    }
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    onClick = {
                        productViewModel.pay(
                            item.id,
                            cardNumber.value,
                            securityCode.value
                        )
                    }
                ) {
                    Text(text = "Pay")
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Order price is already payed !!!")
            }
        }
    }
}