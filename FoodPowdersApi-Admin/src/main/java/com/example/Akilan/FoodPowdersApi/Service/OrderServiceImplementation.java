package com.example.Akilan.FoodPowdersApi.Service;

import com.example.Akilan.FoodPowdersApi.Config.RazorpayInitialization;
import com.example.Akilan.FoodPowdersApi.Entity.OrderEntity;
import com.example.Akilan.FoodPowdersApi.IO_Objects.OrderRequest;
import com.example.Akilan.FoodPowdersApi.IO_Objects.OrderResponse;
import com.example.Akilan.FoodPowdersApi.Repository.CartRepository;
import com.example.Akilan.FoodPowdersApi.Repository.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final RazorpayClient razorpayClient;
    private final CartRepository cartRepository;

    public OrderServiceImplementation(OrderRepository orderRepository, UserService userService, RazorpayInitialization razorpayInitialization, CartRepository cartRepository) throws RazorpayException {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;

        String keyId = razorpayInitialization.getKeyId();
        String secretKey = razorpayInitialization.getKeySecret();


        this.razorpayClient = new RazorpayClient(keyId, secretKey);
    }

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
        OrderEntity newOrder = convertToEntity(request);
        newOrder = orderRepository.save(newOrder); // save initial data

        //Create Razorpay order
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", newOrder.getAmount());
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        // Update order with Razorpay order ID and logged in user ID
        newOrder.setRazorpayOrderId(razorpayOrder.get("id"));
        String loggedInUserId = userService.findByUserId();
        newOrder.setUserId(loggedInUserId);
        newOrder = orderRepository.save(newOrder);

        return convertToResponse(newOrder);
    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
        String rayzorpayOrderId = paymentData.get("razorpay_order_id");
        OrderEntity existingOrder = orderRepository.findByRazorpayOrderId(rayzorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Order not Found."));
        existingOrder.setPaymentStatus(status);
        existingOrder.setRazorpaySignature(paymentData.get("razorpay_signature"));
        existingOrder.setRazorpayPaymentId(paymentData.get("razorpay_payment_id"));
        System.out.println("Before save: " + existingOrder.getOrderedItems());
        orderRepository.save(existingOrder);
        if("paid".equalsIgnoreCase(status)){
            cartRepository.deleteByUserId(existingOrder.getUserId());
        }
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        String loggedInUserId = userService.findByUserId();
        List<OrderEntity> listOfOrders = orderRepository.findByUserId(loggedInUserId);
        return listOfOrders.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {
        List<OrderEntity> listOfOrders = orderRepository.findAll();
        return listOfOrders.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found."));

        orderEntity.setOrderStatus(status);
        orderRepository.save(orderEntity);
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        return OrderEntity.builder()
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderedItems(request.getOrderedItems())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .build();
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .id(newOrder.getId())
                .amount(newOrder.getAmount())
                .userAddress(newOrder.getUserAddress())
                .userId(newOrder.getUserId())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .orderStatus(newOrder.getOrderStatus())
                .paymentStatus(newOrder.getPaymentStatus())
                .email(newOrder.getEmail())
                .phoneNumber(newOrder.getPhoneNumber())
                .orderedItems(newOrder.getOrderedItems())
                .build();
    }
}
