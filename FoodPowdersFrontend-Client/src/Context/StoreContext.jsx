import { createContext, useEffect, useState } from "react";
import { fetchFoodPowderList } from "../Service/foodService";
import axios from "axios";
import { addToCart, getCartData, removeQuantityFromCart } from "../Service/CartService";


export const StoreContext = createContext(null);

export const StoreContextProvider = (props) =>{

    const [foodPowderList, setFoodPowderList]= useState([]);
    const [quantities, setQuantities] = useState({});
    const [token, setToken] = useState("");

    // to make quantity used in the entire app we placed it in the storecontext 
    const increaseQuantity = async(foodId) => {
        setQuantities((prev) => ({...prev,[foodId]: (prev[foodId] || 0)+1}));
        await addToCart(foodId, token);
    }

    const decreaseQuantity = async(foodId) =>{
        setQuantities((prev) => ({...prev, [foodId]: prev[foodId] > 0 ? prev[foodId] - 1 : 0}));
        await removeQuantityFromCart(foodId, token);
    }

    const removeFromCart = (foodId) =>{
        setQuantities((prev) => {
            const updateQuantities = {...prev};
            delete updateQuantities[foodId];
            return updateQuantities
        });
    };

    const loadCartData = async(token) => {
        const items = await getCartData(token);
        setQuantities(items);
    }


    const contextValue = {
        foodPowderList,
        increaseQuantity,
        decreaseQuantity,
        setQuantities,
        removeFromCart,
        quantities,
        token,
        setToken,
        loadCartData
    };



    useEffect(() =>{
        async function loadData(){
            const data = await fetchFoodPowderList();
            setFoodPowderList(data);
            // console.log(data);
            if(localStorage.getItem('token:')){
                setToken(localStorage.getItem('token:'));
                await loadCartData(localStorage.getItem('token:'));
                
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