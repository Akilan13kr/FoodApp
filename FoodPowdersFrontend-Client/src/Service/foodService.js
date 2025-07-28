import axios from "axios";

const API_URL = "https://foodapp-e3qb.onrender.com/api/foodpowders";

export const fetchFoodPowderList = async() => {

    try {
        const response = await axios.get(API_URL);
        return response.data;
    // console.log(response.data);
    } catch (error) {
        console.log('Error while getting fetching foodpowders:',error);
        throw error;
    }
    
};

export const fetchFoodPowderDetails = async(id) =>{
    try {
        const response = await axios.get(API_URL+"/"+id);
        // console.log(response.data);
        return response.data;
        
    } catch (error) {
        console.log('Error while fetching foodpowder details:', error);
        throw error;
    }
        
};
