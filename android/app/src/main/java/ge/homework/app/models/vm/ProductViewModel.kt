package ge.homework.app.models.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.homework.app.retrofit.AddCardReq
import ge.homework.app.retrofit.CartItem
import ge.homework.app.retrofit.OrderDTO
import ge.homework.app.retrofit.OrderRepo
import ge.homework.app.retrofit.UpdateOrderStatusReq
import ge.homework.app.retrofit.UpdatePaymentStatusReq
import ge.homework.app.screens.FoodItem
import ge.homework.app.screens.FoodType
import ge.homework.app.screens.OrderStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val orderRepo: OrderRepo, 
): ViewModel() {
    val orders = MutableLiveData(mutableListOf<OrderDTO>())
    val foods = MutableLiveData(mutableListOf<FoodItem>())
    val items = MutableLiveData(mutableListOf<CartItem>())
    
    fun fetchFood() {
        viewModelScope.launch { 
            val resp = orderRepo.fetchFoods()
            foods.postValue(resp)
        }
    }

    fun addItem(id: Long, quantity: Int) {
        viewModelScope.launch { 
            orderRepo.addItem(mutableMapOf(
                Pair("quantity", quantity),
                Pair("foodId", id),
            ))
        }
    }
    
    fun fetchItems() {
        viewModelScope.launch {
            val resp = orderRepo.fetchItems()
            items.postValue(resp)
        }
    }

    fun order() {
        viewModelScope.launch { 
            orderRepo.order()
            val resp = orderRepo.fetchItems()
            items.postValue(resp)
        }
    }

    fun fetchOrders() {
        viewModelScope.launch { 
            val resp = orderRepo.fetchOrders()
            orders.postValue(resp)
        }
    }

    fun pay(id: Long, cardNumber: String, securityCode: String) {
        viewModelScope.launch { 
            orderRepo.pay(
                PaymentReq(id, cardNumber, securityCode)
            )
            val resp = orderRepo.fetchOrders()
            orders.postValue(resp)
        }
    }

    fun addProduct(category: FoodType, name: String, price: String) {
        viewModelScope.launch { 
            orderRepo.addProduct(
                AddProductReq(category, name, price)
            )
        }
    }
    
    fun addCard(userId: Long, cardNumber: String, securityCode: String) {
        viewModelScope.launch { 
            orderRepo.addCard(
                AddCardReq(userId, cardNumber, securityCode)
            )
        }
    }
    
    val pendingOrders = MutableLiveData(mutableListOf<Long>())
    val pendingPayments = MutableLiveData(mutableListOf<Long>())
    
    fun fetchPendingOrders() {
        viewModelScope.launch { 
            
            val resp = orderRepo.fetchPendingOrders()
            pendingOrders.postValue(resp)
        }
    }
    
    fun fetchPendingPayments() {
        viewModelScope.launch {
            val resp = orderRepo.fetchPendingPayments()
            pendingPayments.postValue(resp)
        }
    }
    
    fun updateOrderStatus(id: Long, status: OrderStatus) {
        viewModelScope.launch {
            orderRepo.updateOrder(
                UpdateOrderStatusReq(id, status)
            )
            val resp = orderRepo.fetchPendingOrders()
            pendingOrders.postValue(resp)
        }
    }
    
    fun updatePaymentStatus(id: Long, status: PaymentStatus) {
        viewModelScope.launch {
            orderRepo.updatePayment(
                UpdatePaymentStatusReq(id, status)
            )
            val resp = orderRepo.fetchPendingPayments()
            pendingPayments.postValue(resp)
        }
    }

    fun editItem(id: Long, quantity: Int) {
        viewModelScope.launch { 
            val resp = orderRepo.editItem(id, quantity)
            items.postValue(resp)
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch { 
            val resp = orderRepo.deleteItem(id)
            items.postValue(resp)
        }
    }
}

data class AddProductReq(
    val category: FoodType,
    val name: String,
    val price: String
)

data class PaymentReq(
    val orderId: Long,
    val cardNumber: String,
    val securityCode: String,
)

enum class PaymentStatus {
    PENDING, SUCCESS, CANCELED;
}
