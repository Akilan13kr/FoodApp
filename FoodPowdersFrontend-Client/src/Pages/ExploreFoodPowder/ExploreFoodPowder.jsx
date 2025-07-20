import React, { useContext, useState } from 'react';
import FoodPowderDisplay from '../../Components/FoodPowderDisplay/FoodPowderDisplay';
import { StoreContext } from '../../Context/StoreContext';

const ExploreFoodPowder = () => {
  const {categoryList} = useContext(StoreContext);
  const [category , setCategory] = useState('All');
  const [searchText, setSearchText] = useState('');


  return (
    <>
    <div className="container">
      <div className="row justify-content-center">
        <div className='col-md-6'>
          <form onSubmit={(e) => e.preventDefault()}>
            {/* prevent default prevent the page from loading to get data which make the web refresh */}
            <div className="input-group mb-3">
              <select className="form-select mt-2" style={{"maxWidth":"150px"}} onChange={(e) => setCategory(e.target.value)}>
              {/* <option value="All">All</option>
                <option value="Health Mix">Health Mix</option>
                <option value="Masala Powder">Masala Powder</option>
                <option value="Rice Powder">Rice Powder</option>
                <option value="Chutney Powder">Chutney Powder</option> */}
                <option value="All">All</option>
                {
                  categoryList.map((category, index) =>{
                    return(<option value={category.categoryName} key={index}>{category.categoryName}</option>)
                  })
                }
              </select>
              <input type="text" className='form-control mt-2' placeholder='Search your foodpowder here.....' 
              onChange={(e) => setSearchText(e.target.value)} value={searchText}/>
              <button className='btn btn-primary mt-2' type='submit'>
                <i className='bi bi-search'></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <FoodPowderDisplay category={category} searchText={searchText}/>
    </>
  )
}

export default ExploreFoodPowder;