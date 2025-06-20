import React, { useState } from 'react';
import { Routes, Route } from 'react-router-dom';
import AddFood from './Pages/AddFood/AddFood';
import ListFood from './Pages/ListFoodPowder/ListFoodPowder';
import Orders from './Pages/Orders/Orders';
import SideBar from './Components/SideBar/SideBar';
import MenuBar from './Components/MenuBar/MenuBar';
import ListFoodPowder from './Pages/ListFoodPowder/ListFoodPowder';

const App = () => {
  const [sidebarVisible, setSidebarVisible] = useState(true);

  const toggleSidebar = () =>{
    setSidebarVisible(!sidebarVisible);
  }
  return (
    <div className="d-flex" id="wrapper">
            {/* <!-- Sidebar--> */}
            <SideBar sidebarVisible={sidebarVisible}/>
            {/* <!-- Page content wrapper--> */}
            <div id="page-content-wrapper">
                {/* <!-- Top navigation--> */}
                <MenuBar toggleSidebar={toggleSidebar}/>
                {/* <!-- Page content--> */}
                <div className="container-fluid">
                    <Routes>
                      <Route path="/add" element={<AddFood />} />
                      <Route path="/list" element={<ListFoodPowder />} />
                      <Route path="/orders" element={<Orders />}/>
                      <Route path="/" element={<ListFood />}/>
                    </Routes>
                </div>
            </div>
        </div>
  )
}

export default App;