import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import './ListCategory.css';
import { deleteCategory, getCategoryList } from '../../Services/categoryService';

const ListCategory = () => {
  
    const [list, setList] = useState([]);
    const fetchList = async() => {
      try {
        const data = await getCategoryList();
        setList(data);
      } catch (error) {
        toast.error("Error while reading Category.");
      }
    }
  
    const removeCategory = async(foodid) =>{
      try {
        const success = deleteCategory(foodid);
        if(success){
          toast.success("Category Removed.");
          await fetchList();
        } else {
          toast.error("Error occured while Removing the Category");
        }
      } catch (error) {
        toast.error("Error occured while Removing the Category");
        
      }
    }
    useEffect(() => {
      fetchList();
    }, [])
  
    return (
      <div className='py-5 row justify-content-end'>
        <div className="col-li card">
        <h2 className="mb-1">List Of Category</h2>
        <hr/>
          <table className="table">
            <thead>
              <tr>
                <th>Image</th>
                <th>Category</th>
                <th>Count Of Food Under the Category</th>
                <th>List of Foods</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {
                list.map((category, index) => {
                  return(
                    <tr key={index}>
                      <td>
                        <img src={category.imageUrl} alt="" height={48} width={48} />
                      </td>
                      <td>{category.categoryName}</td>
                      <td>{category.countOfFoodUnderCategory}</td>
                      <td className="text-align-center">
                        <div className="dropdown d-inline-block">
                          <button
                            className="btn dropdown-toggle"
                            type="button"
                            id={`dropdownMenuButton-${index}`}
                            data-bs-toggle="dropdown"
                            aria-expanded="false"
                          >
                            View Foods
                          </button>

                          <ul className="dropdown-menu dropdown-menu-center cursor-pointer" aria-labelledby={`dropdownMenuButton-${index}`}>
                            {
                              category.countOfFoodUnderCategory>0 ?(
                                category.listOfFoodPowders &&
                                Object.keys(category.listOfFoodPowders).map((foodName, i) => (
                                  <li key={i} className="dropdown-item">{foodName}</li>
                                ))
                              ) : (
                                <li>No Food Available</li>
                              )
                            }
                          </ul>
                        </div>
                      </td>

                      <td className='text-danger' onClick={() => removeCategory(category.id)}><i className="bi bi-trash3"></i></td>
                    </tr>
                  )
                })
              }
            </tbody>
          </table>
        </div>
      </div>
    )
  }

export default ListCategory