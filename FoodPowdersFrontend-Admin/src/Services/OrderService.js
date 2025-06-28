import axios from "axios";

const API_URL = "http://localhost:8080/api/orders";

export const getAllOrders = async () => {
    try {
        const response = await axios.get(API_URL + '/all' );
        return response.data;
    } catch (error) {
        throw error;
    }
}

export const updataOrderStatus = async (Event, orderId) => {
    try {
        const response = await axios.patch(API_URL + `/status/${orderId}?status=${Event.target.value}`);
        return response;
    } catch (error) {
        throw error;
    }
}
    