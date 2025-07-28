import axios from "axios";

const API_URL = "https://foodapp-e3qb.onrender.com/api/orders";

export const fetchOrdersOfUser = async (token) =>{
    try {
         const response = await axios.get(API_URL, {headers: {'Authorization': `Bearer ${token}`}});
         return response.data;
    } catch (error) {
        throw error;
    }
}

export const createUserOrderWithPayment = async(token, orderData) => {
    try {
        const response = await axios.post(API_URL + '/create', orderData, {headers: {'Authorization':`Bearer ${token}`}});
        return response;
    } catch (error) {
        throw error;
    }
}

export const verifyUserPayment = async (token, paymentData) => {
    try {
        const response = await axios.post(API_URL + '/verify', paymentData, {headers: {'Authorization': `Bearer ${token}`}});
        return response;
    } catch (error) {
        throw error;
    }
}

export const deleteUserOrder = async(token, orderId) => {
    try {
        await axios.delete(API_URL + '/' + orderId, {headers: {'Authorization': `Bearer ${token}`}});
    } catch (error) {
        throw error;
    }
}
