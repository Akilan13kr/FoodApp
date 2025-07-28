import React from 'react';
import { Link } from 'react-router-dom';
import {assets} from '../../assets/assets'

const SideBar = ({sidebarVisible}) => {
  return (
    <div className={`border-end bg-white ${sidebarVisible?'':'d-none'}`} id="sidebar-wrapper">
        <div className="sidebar-heading border-bottom bg-light">
            <img src={assets.logo} alt="logo" height={48} width={48} />
        </div>
        <div className="list-group list-group-flush">
            <Link className="list-group-item list-group-item-action list-group-item-light p-3" to={"/addCategory"}>
                <i className="bi bi-plus-circle me-2"></i>
                Add Category
            </Link>
            <Link className="list-group-item list-group-item-action list-group-item-light p-3" to={"/listCategory"}>
                <i className="bi bi-list-task me-2"></i>
                List Category
            </Link>
            <Link className="list-group-item list-group-item-action list-group-item-light p-3" to={"/add"}>
                <i className="bi bi-plus-circle me-2"></i>
                Add Food Powder
            </Link>
            <Link className="list-group-item list-group-item-action list-group-item-light p-3" to={"/list"}>
                <i className="bi bi-list-task me-2"></i>
                List Food Powder
            </Link>
            <Link className="list-group-item list-group-item-action list-group-item-light p-3" to={"/orders"}>
                <i className="bi bi-cart3 me-2"></i>
                Orders
            </Link>
            
            
        </div>
    </div>
  )
}

export default SideBar;