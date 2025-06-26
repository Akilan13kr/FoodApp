import axios from "axios";

const API_URL = 'http://localhost:8080/api/cart';

export const addToCart = async(foodId, token) =>{
    try {
        await axios.post(API_URL, {foodId}, {headers: {'Authorization': `Bearer ${token}`}});
    } catch (error) {
        
    }
}

export const removeQuantityFromCart = async(foodId, token) =>{
    try {
        await axios.post('http://localhost:8080/api/cart/remove', {foodId}, {headers: {'Authorization': `Bearer ${token}`}});
    } catch (error) {
        
    }
}

export const getCartData = async(token) =>{
    try {
        
    } catch (error) {
        
    }
}