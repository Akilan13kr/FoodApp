import React, { useContext, useRef }from 'react';
import './ExploreMenu.css';
import { StoreContext } from '../../Context/StoreContext';

const ExploreMenu = ({category, setCategory}) => {
  const {categoryList} = useContext(StoreContext);

  const menuRef = useRef(null);
  const scrollLeft = () => {
    if(menuRef.current){
      menuRef.current.scrollBy({left: -200, behavior:'smooth'})
    }
  };
  const scrollRight = () => {
    if(menuRef.current){
      menuRef.current.scrollBy({left: 200, behavior:'smooth'})
    }
  };

  return (
   <div className="explore-manu position-relative">
    <h1 className="d-flex align-items-center justify-content-between">
      Explore Our Menu
      <div className="d-flex">
      <i className="bi bi-arrow-left-circle scroll-icon" onClick={scrollLeft}></i>
      <i className="bi bi-arrow-right-circle scroll-icon" onClick={scrollRight}></i>
    </div>
    </h1>
    <p>Explore Lists of Powders from the Category</p>
    <div className="d-flex  gap-4 overflow-auto explore-menu-list" ref={menuRef}>{/* justify-content-between */}
      {
        categoryList.map((item, index) => {
          return(
            <div key={index} className="text-center explore-menu-list-item" onClick={() => setCategory(prev => prev === item.categoryName ? "All" : item.categoryName)}>
                <img src={item.imageUrl} alt="" className={item.category === category ? 'rounded-circle active': 'rounded-circle'} height={128} width={128} />
                <p className='mt-2 fw-bold'>{item.categoryName}</p>
            </div>
          )
        })
      }
    </div>
    <hr />
   </div>
  )
}

export default ExploreMenu;