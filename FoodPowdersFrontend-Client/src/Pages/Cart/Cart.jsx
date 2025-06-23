import React, { useContext } from 'react';
import './Cart.css';
import { StoreContext } from '../../Context/StoreContext';
import { Link, useNavigate } from 'react-router-dom';
import { calculateCartTotals } from '../../Util/CartUtil';

const Cart = () => {

    const navigate = useNavigate();

    const {foodPowderList, increaseQuantity, decreaseQuantity, removeFromCart, quantities} = useContext(StoreContext);

    const cartItems = foodPowderList.filter(powder => quantities[powder.id] > 0);

    const {subTotal, shippingCharger, tax, fullTotal }=calculateCartTotals(cartItems, quantities);

  return (
        <div className="container py-5">
            <h1 className="mb-5">Your Shopping Cart</h1>
            <div className="row">
                <div className="col-lg-8">
                    {/* <!-- Cart Items --> */}
                    {
                        cartItems.length === 0 ? (
                            <p>Your cart is Empty</p>
                        ) : (
                            <div className="card mb-4">
                                <div className="card-body">
                                    {
                                     cartItems.map((powder) => (
                                        <div key={powder.id}className="row cart-item mb-3">
                                        <div className="col-md-3">
                                            <img src={powder.imageUrl} alt={powder.name} className="img-fluid rounded" width={100}/>
                                        </div>
                                        <div className="col-md-5">
                                            <h5 className="card-title">{powder.name}</h5>
                                            <p className="text-muted">Category: {powder.category}</p>
                                        </div>
                                        <div className="col-md-2 ">
                                            <div className="d-flex align-items-center gap-2">
                                                <button className="btn btn-danger btn-sm" onClick={() => decreaseQuantity(powder.id)}>
                                                    <i className="bi bi-dash-circle"></i>
                                                </button>
                                                <span className="fw-bold">{quantities[powder.id]}</span>
                                                <button className="btn btn-success btn-sm" onClick={() => increaseQuantity(powder.id)}>
                                                    <i className="bi bi-plus-circle"></i>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="col-md-2 text-end">
                                            <p className="fw-bold">&#8377;{(quantities[powder.id] * powder.price).toFixed(2)}</p>
                                            <button className="btn btn-sm btn-outline-danger" onClick={() => removeFromCart(powder.id)} >
                                                    <i className="bi bi-trash"></i>
                                            </button>
                                        </div>
                                        <hr/>
                                    </div>
                                     ))
                                    }
                                    
                                </div>
                            </div>
                        )
                    }
                    {/* <!-- Continue Shopping Button --> */}
                    <div className="text-start mb-4">
                        <Link to={'/explore'} className="btn btn-outline-primary">
                            <i className="bi bi-arrow-left me-2"></i>Continue Shopping
                        </Link>
                    </div>
                </div>
                <div className="col-lg-4">
                    {/* <!-- Cart Summary --> */}
                    <div className="card cart-summary">
                        <div className="card-body">
                            <h5 className="card-title mb-4">Order Summary</h5>
                            <div className="d-flex justify-content-between mb-3">
                                <span>Subtotal</span>
                                <span>&#8377;{subTotal.toFixed(2)}</span>
                            </div>
                            <div className="d-flex justify-content-between mb-3">
                                <span>Shipping</span>
                                <span>&#8377;{shippingCharger.toFixed(2)}</span>
                            </div>
                            <div className="d-flex justify-content-between mb-3">
                                <span>Tax</span>
                                <span>{tax.toFixed(2)}</span>
                            </div>
                            <hr/>
                            <div className="d-flex justify-content-between mb-4">
                                <strong>Total</strong>
                                <strong>&#8377;{subTotal === 0 ? 0.0 : fullTotal.toFixed(2)}</strong>
                            </div>
                            <button className="btn btn-primary w-100" disabled={cartItems.length === 0} onClick={() => navigate('/order')}>Proceed to Checkout</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
  )
}

export default Cart