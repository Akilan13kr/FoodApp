import axios from "axios";

const API_URL = 'http://localhost:8080/api/cart';

export const addToCart = async(foodId, token) =>{
    try {
        await axios.post(API_URL, {foodId}, {headers: {'Authorization': `Bearer ${token}`}});
    } catch (error) {
        console.log('Error while add item or increase item in cart:', error);
        throw error;
    }
}

export const removeQuantityFromCart = async(foodId, token) =>{
    try {
        await axios.post(API_URL+'/remove', {foodId}, {headers: {'Authorization': `Bearer ${token}`}});
    } catch (error) {
        console.log('Error while decrease the quantity of the item in cart:', error);
        throw error;
    }
}

export const getCartData = async(token) =>{
    try {
        const response = await axios.get(API_URL, {headers: {'Authorization': `Bearer ${token}`}});
        return response.data.items
    } catch (error) {
        console.log('Error fetching the cart data:', error);
        throw error;
    }
}