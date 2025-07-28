import React, { useContext, useState} from 'react';
import './PlaceOrder.css';
import { StoreContext } from '../../Context/StoreContext';
import { assets } from '../../assets/assets';
import { calculateCartTotals } from '../../Util/CartUtil';
import { toast } from 'react-toastify';
import { RAZORPAY_KEY } from '../../Util/Constant';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { createUserOrderWithPayment, deleteUserOrder, verifyUserPayment } from '../../Service/OrderService';
import { clearUserCart } from '../../Service/CartService';
// import Razorpay from 'razorpay';


const PlaceOrder = () => {
  const { foodPowderList, quantities, token, setQuantities} = useContext(StoreContext);

  const cartItems = foodPowderList.filter(powder => quantities[powder.id] > 0);

  const navigate = useNavigate();

  const {subTotal, shippingCharger, tax, fullTotal }=calculateCartTotals(cartItems, quantities);

  const [data, setData] = useState({
        firstName:"",
        lastName:"",
        email:"",
        phoneNumber:"",
        address:"",
        city:"",
        state:"",
        country:"",
        zipCode:""
      });

    const onChangeHandle = (Event) =>{
      const name = Event.target.name;
      const value = Event.target.value;
      setData(data => ({...data, [name]:value}));
    }

    const onSumbitHandler = async(Event) => {
        Event.preventDefault();
        // console.log(data);
        const orderData = {
          userAddress: `${data.firstName} ${data.lastName},
          ${data.address},
          ${data.city},
          ${data.state},
          ${data.country}-${data.zipCode}.
          phone:${data.phoneNumber}.`,
          phoneNumber: data.phoneNumber,
          email: data.email,
          orderedItems: cartItems.map(item => ({
              foodId: item.id,
              quantity: quantities[item.id],
              name:item.name,
              price: item.price * quantities[item.id],
              category: item.category,
              imageUrl: item.imageUrl,
              description: item.description
            })),
            amount: fullTotal.toFixed(2),
            orderStatus: "Preparing"
        };
        // console.log(orderData);

        try {
          const response = await createUserOrderWithPayment(token, orderData);
          if(response.status === 201 && response.data.razorpayOrderId){
            //initiate the payment
            initiateRazorpayPayment(response.data);
          } else {
            toast.error("Unable to place order, Please try again.");
          }
        } catch (error) {
          toast.error("Unable to place order, Please try again.");
          throw error;
        }
    };

    const initiateRazorpayPayment =  (order) => {
      const options = {
        key: RAZORPAY_KEY,
        amount: order.amount,
        currency: "INR",
        name: "Natual FoodPowders",
        description:"Food order payment",
        order_id: order.razorpayOrderId,
        handler: async function(razorpayResponse) {
          await verifyPayment(razorpayResponse);
        },
        prefill:{
          name:`${data.firstName} ${data.lastName}`,
          email: data.email,
          contact: data.phoneNumber
        },
        theme:{color: "#3399cc"},
        modal:{
          ondismiss: async function() {
            toast.error("Payment Cancelled.");
            await deleteOrder(order.id);
          }
        }
      };
      const razorpay = new window.Razorpay(options);
      razorpay.open();

    };

    const verifyPayment = async(razorpayResponse) => {
      const paymentData = {
        razorpay_payment_id: razorpayResponse.razorpay_payment_id,
        razorpay_order_id: razorpayResponse.razorpay_order_id,
        razorpay_signature: razorpayResponse.razorpay_signature
      };
      try {
        const response = await verifyUserPayment(token, paymentData);
      if(response.status === 200){
        toast.success("Payment Successfull");
        await clearCart();
        navigate('/myorders');
      }else {
        toast.error("Payment failed please try again.");
        navigate('/myorders');
      }
      } catch (error) {
        toast.error('Payment failed please try again.');
      }
    };

    const deleteOrder = async (orderId) =>{
      try {
        await deleteUserOrder(token, orderId);
      } catch (error) {
        toast.error("Something went wrong contact support.");
      }
    };

    const clearCart = async () => {
      try {
        await clearUserCart(token);
        setQuantities({});
      } catch (error) {
        toast.error("Error while clearing cart.");
      }
    };


  return (
    <div className="container">
      <main>
        <div className="py-5 text-center">
            <img className="d-block mx-auto mb-4" src={assets.logo} alt="" width="98" height="98"/>
        </div>
        <div className="row g-5">
          <div className="col-md-5 col-lg-4 order-md-last">
            <h4 className="d-flex justify-content-between align-items-center mb-3">
              <span className="text-primary">Your cart</span>
              <span className="badge bg-primary rounded-pill">{cartItems.length}</span>
            </h4>
            <ul className="list-group mb-3">
              {
                cartItems.map(item => {
                  // console.log(item);
                    return(
                    <li key={item.id} className="list-group-item d-flex justify-content-between lh-sm">
                        <div>
                            <h6 className="my-0">{item.name}</h6>
                            <small className="text-muted">Quantity: {quantities[item.id]}</small>
                        </div>
                        <span className="text-muted">&#8377;{(item.price *  quantities[item.id]).toFixed(2)}</span>
                    </li>)
                })
            }
                <li className="list-group-item d-flex justify-content-between lh-sm">
                    <div>
                        <span>Tax (10%)</span>
                    </div>
                    <span className="text-muted">&#8377;{tax.toFixed(2)}</span>
                </li>
                <li className="list-group-item d-flex justify-content-between">
                    <div>
                        <span>Shipping Charges</span>
                    </div>
                    <span className="text-muted">&#8377;{subTotal === 0 ? 0.0 : shippingCharger.toFixed(2)}</span>
                </li>
                <li className="list-group-item d-flex justify-content-between">
                    <span>Total (INR)</span>
                    <strong>&#8377;{fullTotal.toFixed(2)}</strong>
                </li>
            </ul>
          </div>

          <div className="col-md-7 col-lg-8">
            <h4 className="mb-3">Billing address</h4>
            <form className="needs-validation"  onSubmit={onSumbitHandler}>
              <div className="row g-3">
                <div className="col-sm-6">
                  <label htmlFor="firstName" className="form-label">
                    First name
                  </label>
                  <input type="text" className="form-control" id="firstName" name="firstName" onChange={onChangeHandle} value={data.firstName} required />
                </div>

                <div className="col-sm-6">
                  <label htmlFor="lastName" className="form-label">
                    Last name
                  </label>
                  <input type="text" className="form-control" id="lastName" name="lastName" onChange={onChangeHandle} value={data.lastName} required />
                </div>

                <div className="col-12">
                  <label htmlFor="email" className="form-label">
                    Email
                  </label>
                  <div className="input-group has-validation">
                    <span className="input-group-text">@</span>
                    <input type="email" className="form-control" id="email" placeholder="Email" name="email" onChange={onChangeHandle} value={data.email} required />
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="phone" className="form-label">
                    Phone Number
                  </label>
                  <input type="number" className="form-control" id="phone" placeholder="9876543210" name="phoneNumber" onChange={onChangeHandle} value={data.phoneNumber} required />
                </div>

                <div className="col-12">
                  <label htmlFor="address" className="form-label">
                    Address
                  </label>
                  <input type="text" className="form-control" id="address" placeholder="1234 Main St" name="address" onChange={onChangeHandle} value={data.address} required />
                </div>

                <div className="col-12">
                  <label htmlFor="city" className="form-label">
                    City
                  </label>
                  <input type="text" className="form-control" id="city" placeholder="trichy" name="city" onChange={onChangeHandle} value={data.city} required />
                </div>

                <div className="col-md-4">
                  <label htmlFor="state" className="form-label">
                    State
                  </label>
                  <select className="form-select" id="state" name="state" onChange={onChangeHandle} value={data.state} required>
                    <option value="">Choose...</option>
                    <option value="Tamil Nadu">Tamil Nadu</option>
                  </select>
                </div>

                <div className="col-md-5">
                  <label htmlFor="country" className="form-label">
                    Country
                  </label>
                  <select className="form-select" id="country" name="country" onChange={onChangeHandle} value={data.country} required>
                    <option value="">Choose...</option>
                    <option value='India'>India</option>
                  </select>
                </div>

                <div className="col-md-3">
                  <label htmlFor="zip" className="form-label">
                    Zip-Code
                  </label>
                  <input type="number" className="form-control" id="zipCode" placeholder="600-000" name="zipCode" onChange={onChangeHandle} value={data.zipCode} required />
                </div>
              </div>

              <hr className="my-4" />

              <button className="w-100 btn btn-primary btn-lg" type="submit" disabled={cartItems.length === 0}>
                Continue to checkout
              </button>
            </form>
          </div>
        </div>
      </main>
    </div>
  );
};

export default PlaceOrder;
