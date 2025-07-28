import axios from "axios";

const API_URL = 'https://foodapp-e3qb.onrender.com/api/contact';

export const sentDetail = async(emailData) =>{
    try {
        console.log(emailData)
        await axios.post(API_URL, emailData);
    } catch (error) {
        console.log('Error while sending contact:', error);
        throw error;
    }
}
