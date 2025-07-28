import React, { useEffect, useState } from 'react';
import {assets} from '../../assets/assets';
import { toast } from 'react-toastify';
import { addCategory } from '../../Services/categoryService';

const AddCategory = () => {

    const [image, setImage] = useState(false);
    const [categoryData, setCategoryData] = useState({
        categoryName:''
    });

    const onChangeHandler = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setCategoryData(categoryData => ({...categoryData, [name]: value}));
    }

    useEffect(() => {
        console.log(categoryData);
    }, [categoryData])

    const onSubmitHandler = async(event) => {
        event.preventDefault();
        if(!image){
            toast.error('Please Select an Image.');
            return;
        }
    
        try {
          await addCategory(categoryData, image);
          toast.success('Category Added Successfully.');
          setCategoryData({ 
                categoryName:''
          })
          setImage(null);
        } catch (error) {
          toast.error('Error Adding Food');
        }
    }

  return (
    <div className="container mt-4">
      <div className="row">
        <div className="card col-md-4">
          <div className="card-body">
            <h2 className="mb-3">Add Food Category</h2>
            <form onSubmit={onSubmitHandler}>
            <div className="mb-3">
                <label htmlFor="image" className="form-label">
                    <img src={image ? URL.createObjectURL(image):assets.uploadImage} alt="uploadImage"  width={98}/>
                </label>
                <input type="file" className="form-control" id="image" required hidden onChange={(e) => setImage(e.target.files[0])}/>
              </div>
              <div className="mb-2">
                <label htmlFor="foodname" className="form-label">Category Name</label>
                <input type="text" placeholder='Category Name' className="form-control" id="foodname" required name='categoryName' onChange={onChangeHandler} value={categoryData.categoryName}/>
              </div>
              <button type="submit" className="btn btn-primary">Save</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default AddCategory;