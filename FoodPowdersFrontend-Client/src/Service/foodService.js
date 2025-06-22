import axios from "axios";

const API_URL = "http://localhost:8080/api/foodpowders";

export const fetchFoodList = async() => {

    try {
        const response = await axios.get(API_URL);
        return response.data;
    // console.log(response.data);
    } catch (error) {
        console.log('Error',error);
    }
    
};