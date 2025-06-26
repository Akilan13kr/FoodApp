import { createContext, useEffect, useState } from "react";
import { fetchFoodPowderList } from "../Service/foodService";


export const StoreContext = createContext(null);

export const StoreContextProvider = (props) =>{

    const [foodPowderList, setFoodPowderList]= useState([]);
    const [quantities, setQuantities] = useState({});
    const [token, setToken] = useState("");

    // to make quantity used in the entire app we placed it in the storecontext 
    const increaseQuantity = (foodPowderId) => {
        setQuantities((prev) => ({...prev,[foodPowderId]: (prev[foodPowderId] || 0)+1}));
    }

    const decreaseQuantity = (foodPowderId) =>{
        setQuantities((prev) => ({...prev, [foodPowderId]: prev[foodPowderId] > 0 ? prev[foodPowderId] - 1 : 0}));
    }

    const removeFromCart = (foodPowderId) =>{
        setQuantities((prev) => {
            const updateQuantities = {...prev};
            delete updateQuantities[foodPowderId];
            return updateQuantities
        })
    }


    const contextValue = {
        foodPowderList,
        increaseQuantity,
        decreaseQuantity,
        removeFromCart,
        quantities,
        token,
        setToken
    };

    useEffect(() =>{
        async function loadData(){
            const data = await fetchFoodPowderList();
            setFoodPowderList(data);
            // console.log(data);
            if(localStorage.getItem('token:')){
                setToken(localStorage.getItem('token:'));
            }
        }
        loadData();
    }, []);

    return(
        <StoreContext.Provider value={contextValue}>
            {props.children}
        </StoreContext.Provider>
    )
}