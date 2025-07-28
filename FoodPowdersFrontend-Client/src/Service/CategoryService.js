import axios from "axios";

const API_URL = "https://foodapp-e3qb.onrender.com/category";

export const fetchCategoryList = async() => {

    try {
        const response = await axios.get(API_URL);
        return response.data;
    // console.log(response.data);
    } catch (error) {
        console.log('Error while getting fetching category:',error);
        throw error;
    }
    
};
