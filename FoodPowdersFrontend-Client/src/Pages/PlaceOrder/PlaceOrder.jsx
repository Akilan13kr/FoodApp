import React, { useContext } from 'react';
import './PlaceOrder.css';
import { StoreContext } from '../../Context/StoreContext';
import { assets } from '../../assets/assets';
import { calculateCartTotals } from '../../Util/CartUtil';

const PlaceOrder = () => {
  const { foodPowderList, quantities } = useContext(StoreContext);

  const cartItems = foodPowderList.filter(powder => quantities[powder.id] > 0);

  const {subTotal, shippingCharger, tax, fullTotal }=calculateCartTotals(cartItems, quantities);
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
                    console.log(item);
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
                <li class="list-group-item d-flex justify-content-between lh-sm">
                    <div>
                        <span>Tax (10%)</span>
                    </div>
                    <span class="text-muted">&#8377;{tax.toFixed(2)}</span>
                </li>
                <li class="list-group-item d-flex justify-content-between">
                    <div>
                        <span>Shipping Charges</span>
                    </div>
                    <span class="text-muted">&#8377;{subTotal === 0 ? 0.0 : shippingCharger.toFixed(2)}</span>
                </li>
                <li className="list-group-item d-flex justify-content-between">
                    <span>Total (INR)</span>
                    <strong>&#8377;{fullTotal.toFixed(2)}</strong>
                </li>
            </ul>
          </div>

          <div className="col-md-7 col-lg-8">
            <h4 className="mb-3">Billing address</h4>
            <form className="needs-validation" noValidate>
              <div className="row g-3">
                <div className="col-sm-6">
                  <label htmlFor="firstName" className="form-label">
                    First name
                  </label>
                  <input type="text" className="form-control" id="firstName" required />
                </div>

                <div className="col-sm-6">
                  <label htmlFor="lastName" className="form-label">
                    Last name
                  </label>
                  <input type="text" className="form-control" id="lastName" required />
                </div>

                <div className="col-12">
                  <label htmlFor="email" className="form-label">
                    Email
                  </label>
                  <div className="input-group has-validation">
                    <span className="input-group-text">@</span>
                    <input type="email" className="form-control" id="email" placeholder="Email" required />
                  </div>
                </div>

                <div className="col-12">
                  <label htmlFor="phone" className="form-label">
                    Phone Number
                  </label>
                  <input type="number" className="form-control" id="phone" placeholder="+91-9876543210" required />
                </div>

                <div className="col-12">
                  <label htmlFor="address" className="form-label">
                    Address
                  </label>
                  <input type="text" className="form-control" id="address" placeholder="1234 Main St" required />
                </div>

                <div className="col-md-4">
                  <label htmlFor="state" className="form-label">
                    State
                  </label>
                  <select className="form-select" id="state" required>
                    <option value="">Choose...</option>
                    <option value="Tamil Nadu">Tamil Nadu</option>
                  </select>
                </div>

                <div className="col-md-5">
                  <label htmlFor="country" className="form-label">
                    Country
                  </label>
                  <select className="form-select" id="country" required>
                    <option value="">Choose...</option>
                    <option value='India'>India</option>
                  </select>
                </div>

                <div className="col-md-3">
                  <label htmlFor="zip" className="form-label">
                    Zip
                  </label>
                  <input type="number" className="form-control" id="zip" placeholder="600-000" required />
                  <div className="invalid-feedback" >Zip code required.</div>
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
