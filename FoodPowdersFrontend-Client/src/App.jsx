import React from 'react';
import Menubar from './Components/Menubar/Menubar';
import {Route, Routes} from 'react-router-dom';
import Home from './Pages/Home/Home';
import Contact from './Pages/Contact/Contact';
import ExploreFoodPowder from './Pages/ExploreFoodPowder/ExploreFoodPowder';



const App = () => {
  return (
    <div>
      <Menubar />
      <Routes>
        <Route path='/' element={<Home />}/>
        <Route path='/contact' element={<Contact />}/>
        <Route path='/explore' element={<ExploreFoodPowder />}/>
      </Routes>
    </div>
  )
}

export default App;