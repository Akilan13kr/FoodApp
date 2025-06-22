import React, { useContext } from 'react'
import { StoreContext } from '../../Context/StoreContext'
import FoodPowderItem from '../FoodPowderItem/FoodPowderItem';

const FoodPowderDisplay = () => {

  const {foodList} = useContext(StoreContext);
  return (
    <div className="container">
        <div className="row">
            {foodList.length ? (
                foodList.map((food, index) => {
                    return(<FoodPowderItem 
                        key={index} 
                        name={food.name} 
                        description={food.description}
                        id={food.id}
                        imageUrl={food.imageUrl}
                        price={food.price} /> );
                })
            ) : (
                <div className="text-center mt-4">
                    <h4>No FoodPowder Found</h4>
                </div>
            )}
            
        </div>
    </div>
  )
}

export default FoodPowderDisplay