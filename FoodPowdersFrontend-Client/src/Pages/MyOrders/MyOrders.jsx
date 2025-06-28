import React, { useContext, useEffect, useState } from 'react';
import { StoreContext } from '../../Context/StoreContext';
import { toast } from 'react-toastify';
import { assets } from '../../assets/assets';
import './MyOrders.css';
import { fetchOrdersOfUser } from '../../Service/OrderService';

const MyOrders = () => {

    const {token} = useContext(StoreContext);

    const [data, setData] = useState([]);

    const fetchOrders = async () => {
        try {
            const response = await fetchOrdersOfUser(token);
            setData(response);
        } catch (error) {
            toast.error("Error fetching order details.");
            throw error;   
        }
    }

    useEffect(() => {
        if(token){
            fetchOrders();
        }
    }, [token]);

  return (
    <div className="container">
        <div className="py-5 row justify-content-center">
            <div className="col-11 card">
                <table className='table table-responsive'>
                    <tbody>
                        { !(data.length == 0) ?
                            data.map((order, index) => {
                                return(
                                    <tr key={index}>
                                        <td>
                                            <img src={assets.delivery} alt="" height={46} width={48} />
                                        </td>
                                        <td>{
                                            order.orderedItems.map((item, index) => {
                                                if(index === order.orderedItems.length - 1){
                                                    return item.name + " x " + item.quantity
                                                } else {
                                                    return item.name + " x " + item.quantity + ", ";//for second line
                                                }
                                            })
                                        }</td>
                                        <td>&#8377;{order.amount.toFixed(2)}</td>
                                        <td>Items: {order.orderedItems.length}</td>
                                        <td className='fw-bold text-capitalize'>&#x25cf;{order.orderStatus}</td>
                                        <td>
                                            <button className='btn btn-sm btn-warning' onClick={fetchOrders}>
                                                <i className='bi bi-arrow-clockwise'></i>
                                            </button>
                                        </td>
                                    </tr>
                                )
                            }) :
                            (
                                <p className='fw-bold'>No Order History</p>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </div>
    </div>
  )
}

export default MyOrders;