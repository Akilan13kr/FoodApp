export const calculateCartTotals = (cartItems, quantities) =>{
    const subTotal = cartItems.reduce((acc, powder) => acc + powder.price * quantities[powder.id], 0);
      // last zero is the initial value for the accumulator (acc)
    
      const shippingCharger = subTotal === 0 ? 0.0 :10;
    
      const tax = subTotal * 0.1;
    
      const fullTotal = subTotal + shippingCharger + tax;

      return{subTotal, shippingCharger, tax, fullTotal}
}