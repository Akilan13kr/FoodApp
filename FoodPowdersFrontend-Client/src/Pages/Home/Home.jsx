import React, { useState } from 'react';
import Header from '../../Components/Header/Header';
import ExploreMenu from '../../Components/ExploreMenu/ExploreMenu';
import FoodPowderDisplay from '../../Components/FoodPowderDisplay/FoodPowderDisplay';

const Home = () => {
  const [category, setCategory] = useState('All');
  return (
    <main className='container'>
      <Header />
      <ExploreMenu category={category}  setCategory={setCategory}/>
      <FoodPowderDisplay category={category} searchText={''}/>
    </main>
  )
}

export default Home;