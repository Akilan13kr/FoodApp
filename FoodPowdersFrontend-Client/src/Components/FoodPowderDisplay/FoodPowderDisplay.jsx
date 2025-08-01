import React, { useContext } from 'react'
import { StoreContext } from '../../Context/StoreContext'
import FoodPowderItem from '../FoodPowderItem/FoodPowderItem';

const FoodPowderDisplay = ({category, searchText}) => {

  const {foodPowderList} = useContext(StoreContext);

  const filterFoodPowders = foodPowderList.filter(powder =>
    (category === 'All' || powder.category === category) && 
    (powder.name.toLowerCase().includes(searchText.toLowerCase()))
  );
  
  return (
    <div className="container">
        <div className="row">
            {filterFoodPowders.length ? (
                filterFoodPowders.map((food, index) => {
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
                    <h4>No FoodPowder Found </h4>
                    <h4>(or)</h4>
                    <h4>Backend Server(Spring in Render server) may be in sleep due to its free tier. Please refresh the page after few min(Backend may take few mins to awake)</h4>
                </div>
            )}
            
        </div>
    </div>
  )
}

export default FoodPowderDisplay
