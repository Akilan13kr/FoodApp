import React from 'react';
import Menubar from './Components/Menubar/Menubar';
import { ToastContainer, toast } from 'react-toastify';
import {Route, Routes} from 'react-router-dom';
import Home from './Pages/Home/Home';
import Contact from './Pages/Contact/Contact';
import ExploreFoodPowder from './Pages/ExploreFoodPowder/ExploreFoodPowder';
import FoodPowderDetails from './Pages/FoodPowderDetails/FoodPowderDetails';
import Cart from './Pages/Cart/Cart';
import PlaceOrder from './Pages/PlaceOrder/PlaceOrder';
import Login from './Components/Login/Login';
import Register from './Components/Register/Register';



const App = () => {
  return (
    <div>
      <Menubar />
      <ToastContainer />
      <Routes>
        <Route path='/' element={<Home />}/>
        <Route path='/contact' element={<Contact />}/>
        <Route path='/explore' element={<ExploreFoodPowder />}/>
        <Route path='/food/:id' element={<FoodPowderDetails />}/>
        <Route path='/cart' element={<Cart />}/>
        <Route path='/order' element={<PlaceOrder />}/>
        <Route path='/login' element={<Login />}/>
        <Route path='/register' element={<Register />}/>
      </Routes>
    </div>
  )
}

export default App;