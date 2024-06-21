package ge.homework.app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ge.homework.app.models.vm.PaymentStatus
import ge.homework.app.models.vm.ProductViewModel

@Composable
fun AdminHomeScreen(navController: NavController) {

    val view = remember {
        mutableIntStateOf(0)
    }
    
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { view.intValue = 0 }
            ) {
                Text(text = "Products")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = { view.intValue = 1 }
            ) {
                Text(text = "Cards")
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { view.intValue = 2 }
            ) {
                Text(text = "Transactions")
            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = { view.intValue = 3 }
            ) {
                Text(text = "Orders")
            }
        }

        when (view.intValue) {
            0 -> ProductsView()
            1 -> AddCardView()
            2 -> TransactionsView()
            else -> OrdersView()
        }
    }

}



@Composable
fun AddCardView(productViewModel: ProductViewModel = hiltViewModel()) {
    val userId = remember { mutableStateOf("") }
    val cardNumber = remember { mutableStateOf("") }
    val securityCode = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            fontSize = 24.sp,
            text = "Add Card"
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            label = { Text(text = "user id") },
            value = userId.value,
            onValueChange = {
                userId.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            label = { Text(text = "card number") },
            value = cardNumber.value,
            onValueChange = {
                cardNumber.value = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            label = { Text(text = "security code") },
            value = securityCode.value,
            onValueChange = {
                securityCode.value = it
            }
        )
        Spacer(modifier = Modifier.height(16.dp))


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { productViewModel.addCard(
                userId.value.toLong(), cardNumber.value, securityCode.value
            ) }
        ) {
            Text(text = "add card")
        }
    }
}


@Composable
fun ProductsView(productViewModel: ProductViewModel = hiltViewModel()) {

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val selectedOption = remember { mutableStateOf(FoodType.APPETIZER) }
        val price = remember { mutableStateOf("") }
        val name = remember { mutableStateOf("") }

        Text(
            fontSize = 24.sp,
            text = "Add Product"
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "product type:")
        ProductTypesSelector(selectedOption)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            label = { Text(text = "price")},
            value = price.value,
            onValueChange =  {
                price.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            label = { Text(text = "name")},
            value = name.value,
            onValueChange =  {
                name.value = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { productViewModel.addProduct(selectedOption.value, name.value, price.value) }
        ) {
            Text(text = "add product")
        }
    }
}

@Composable
fun ProductTypesSelector(selectedOption: MutableState<FoodType>) {
    val typeOptions = FoodType.entries

    Column {
        typeOptions.forEach { type ->
            Row (verticalAlignment = Alignment.CenterVertically)
            {
                RadioButton(
                    selected = (type == selectedOption.value),
                    onClick = { selectedOption.value = type }
                )
                Text(text = type.name)
            }
        }
    }
}

@Composable
fun TransactionsView(productViewModel: ProductViewModel = hiltViewModel()) {
    val items by productViewModel.pendingPayments.observeAsState(mutableListOf())
    
    LaunchedEffect(key1 = Unit) {
        productViewModel.fetchPendingPayments()
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = items) {item ->
            TransactionItemComposable(item, productViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Composable
fun TransactionItemComposable(id: Long, productViewModel: ProductViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {  }
    ) {
        Row (
            modifier = Modifier.padding( horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "transaction ID: $id"
            )

            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                onClick = { productViewModel.updatePaymentStatus(id, PaymentStatus.SUCCESS) },
            ) {
                Text(text = "success")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = { productViewModel.updatePaymentStatus(id, PaymentStatus.CANCELED) }
            ) {
                Text(text = "fail")
            }
        }
    }
}

@Composable
fun OrdersView(productViewModel: ProductViewModel = hiltViewModel()) {
    val items by productViewModel.pendingOrders.observeAsState(mutableListOf())

    LaunchedEffect(key1 = Unit) {
        productViewModel.fetchPendingOrders()
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items = items) {item ->
            AdminOrderItemComposable(item, productViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}


@Composable
fun AdminOrderItemComposable(id: Long, productViewModel: ProductViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {  }
    ) {
        Row (
            modifier = Modifier.padding( horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "order ID: $id"
            )

            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                onClick = { productViewModel.updateOrderStatus(id, OrderStatus.DELIVERED) },
            ) {
                Text(text = "delivered")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = { productViewModel.updateOrderStatus(id, OrderStatus.CANCELED) }
            ) {
                Text(text = "cancelled")
            }
        }
    }
}
