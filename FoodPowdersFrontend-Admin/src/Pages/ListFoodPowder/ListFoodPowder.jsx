import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import './ListFoodPowder.css';
import { deleteFoodPowder, getFoodPowderList } from '../../Services/FoodService';


const ListFoodPowder = () => {
  const [list, setList] = useState([]);
  const fetchList = async() => {
    try {
      const data = await getFoodPowderList();
      setList(data);
    } catch (error) {
      toast.error("Error while reading FoodPowders.");
    }
  }

  const removeFood = async(foodid) =>{
    try {
      const success = deleteFoodPowder(foodid);
      if(success){
        toast.success("FoodPowder Removed.");
        await fetchList();
      } else {
        toast.error("Error occured while Removing the FoodPowder");
      }
    } catch (error) {
      toast.error("Error occured while Removing the FoodPowder");
      
    }
  }
  useEffect(() => {
    fetchList();
  }, [])

  return (
    <div className='py-5 row justify-content-center'>
      <div className="col-li card">
        <table className="table">
          <thead>
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {
              list.map((item, index) => {
                return(
                  <tr key={index}>
                    <td>
                      <img src={item.imageUrl} alt="" height={48} width={48} />
                    </td>
                    <td>{item.name}</td>
                    <td>{item.category}</td>
                    <td>&#8377;{item.price}.00</td>
                    <td className='text-danger' onClick={() => removeFood(item.id)}><i className="bi bi-trash3"></i></td>
                  </tr>
                )
              })
            }
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default ListFoodPowder;