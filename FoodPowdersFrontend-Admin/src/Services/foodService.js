import axios from "axios";

const API_URL= "http://localhost:8080/api/foodpowders";

export const addFoodPowder = async (foodData, image) => {
    const formData = new FormData();
      formData.append('food', JSON.stringify(foodData));
      formData.append('file', image);
      
          try{
                await axios.post(API_URL, formData, {headers: {"Content-Type":"multipart/form-data"}});
          } catch(error){
            console.log("Error: ", error);
                throw error;
          } 
    
}

export const getFoodPowderList = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
    // console.log(response.data);
    // if(response.status === 200){
    //   setList(response.data);
    // } else {
    //   toast.error('Error While reading the FoodPowders.');
    // }
  } catch (error) {
     console.log("Error in fetch foodpowderlist,", error);
     throw error;
  }
}

export const deleteFoodPowder = async(foodid) =>{
  // console.log(foodid);
  try {
      const response = await axios.delete(API_URL+"/"+foodid);
      return response.status === 204;
  } catch (error) {
    console.log("Error while delete foodpowder,", error);
    throw error;
  }
      
      // if(reponse.status === 204){
      //    toast.success("FoodPowder Removed.")
      // } else {
      //   toast.error("Error occured while removing the FoodPowder.")
      // }

}