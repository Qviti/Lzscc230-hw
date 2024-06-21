package ge.homework.app.retrofit

import ge.homework.app.models.vm.AddProductReq
import ge.homework.app.models.vm.PaymentReq
import ge.homework.app.models.vm.PaymentStatus
import ge.homework.app.screens.FoodItem
import ge.homework.app.screens.OrderStatus
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface OrderRepo {
    @GET("/foods")
    suspend fun fetchFoods(): MutableList<FoodItem>

    @POST("/cart/item/add")
    suspend fun addItem(@Body body: MutableMap<String, Number>)
    @PUT("/cart/item/edit")
    suspend fun editItem(@Query("foodId") id: Long, @Query("quantity") quantity: Int): MutableList<CartItem>

    @DELETE("/cart/item/delete")
    suspend fun deleteItem(@Query("foodId") id: Long): MutableList<CartItem>

    @GET("/cart/items")
    suspend fun fetchItems(): MutableList<CartItem>

    @POST("/order/place")
    suspend fun order()

    @GET("/order/fetch/all")
    suspend fun fetchOrders(): MutableList<OrderDTO>
    
    @POST("/order/pay")
    suspend fun pay(@Body paymentReq: PaymentReq)
    
    
    @POST("/foods/add")
    suspend fun addProduct(@Body addProductReq: AddProductReq)

    @POST("/cards")
    suspend fun addCard(@Body addProductReq: AddCardReq)
    
    @POST("/admin/update/payment/status")
    suspend fun updatePayment(@Body req: UpdatePaymentStatusReq)
    @GET("/admin/payments")
    suspend fun fetchPendingPayments(): MutableList<Long>
    
    @POST("/admin/update/order/status")
    suspend fun updateOrder(@Body req: UpdateOrderStatusReq)

    @GET("/admin/orders")
    suspend fun fetchPendingOrders(): MutableList<Long>
    
    
}

data class UpdatePaymentStatusReq(
    val paymentId: Long,
    val status: PaymentStatus,
)

data class UpdateOrderStatusReq(
    val orderId: Long,
    val status: OrderStatus,
)

data class CartItem(
    val price: Int,
    val quantity: Int,
    val id: Long,
    val foodName: String,
    val total: Int,
)

data class AddCardReq(
    val userId: Long,
    val cardNumber: String,
    val cardSecurityCode: String,
)

data class OrderDTO(
    val id: Long,
    val total: Int,
    val itemCount: Int,
    val status: OrderStatus,
    val items: List<OrderItemResp>,
)

data class OrderItemResp(
    val quantity: Int,
    val total: Int,
    val price: Long,
)