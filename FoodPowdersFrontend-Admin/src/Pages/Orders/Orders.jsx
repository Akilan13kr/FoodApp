import React, { useContext, useEffect, useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { assets } from '../../assets/assets';
import './Orders.css';
import { getAllOrders, updataOrderStatus } from '../../Services/OrderService';

const Orders = () => {


    const [data, setData] = useState([]);

    const fetchOrders = async () => {
        try {
            const response = await getAllOrders()
            setData(response);
        } catch (error) {
            toast.error("Error fetching order details.");
            throw error;   
        }
    }

    const updateStatus = async(Event, orderId) =>  {
      const response = await updataOrderStatus(Event, orderId)
      if(response.status === 200){
        await fetchOrders();
      }
    }

    useEffect(() => {
        fetchOrders();
    }, []);

  return (
    <div className="container">
        <div className="py-5 row justify-content-center">
            <div className="col-11 card">
                <table className='table table-responsive'>
                    <tbody>
                        {
                            data.map((order, index) => {
                                return(
                                    <tr key={index}>
                                        <td>
                                            <img src={assets.parcel} alt="" height={46} width={48} />
                                        </td>
                                        <td>
                                         <div>
                                            {
                                            order.orderedItems.map((item, index) => {
                                                if(index === order.orderedItems.length - 1){
                                                    return item.name + " x " + item.quantity
                                                } else {
                                                    return item.name + " x " + item.quantity + ", ";//for second line
                                                }
                                            })}
                                            <div>{order.userAddress}</div>
                                         </div>
                                        </td>
                                        <td>&#8377;{order.amount.toFixed(2)}</td>
                                        <td>Items: {order.orderedItems.length}</td>
                                        <td>
                                            <select  className="form-control" onChange={(Event) => updateStatus(Event, order.id)} value={order.orderStatus}>
                                              <option value="Food Preparinig">Food Preparing</option>
                                              <option value="Out for delivery">Out of delivery</option>
                                              <option value="Delivered">Delivered</option>
                                            </select>
                                        </td>
                                    </tr>
                                )
                            })
                        }
                    </tbody>
                </table>
            </div>
        </div>
    </div>
  )
}

export default Orders;