import React from 'react';
import Header from '../../Components/Header/Header';
import ExploreMenu from '../../Components/ExploreMenu/ExploreMenu';
import FoodPowderDisplay from '../../Components/FoodPowderDisplay/FoodPowderDisplay';

const Home = () => {
  return (
    <main className='container'>
      <Header />
      <ExploreMenu />
      <FoodPowderDisplay />
    </main>
  )
}

export default Home;