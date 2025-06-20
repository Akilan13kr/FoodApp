import React, { useEffect, useState } from 'react';
import {assets} from '../../assets/assets';
import axios from 'axios';

const AddFood = () => {
    const [image, setImage] = useState(false);
    const [data, setData] = useState({
        name:'',
        description:'',
        price:'',
        category:'Health Mix'
    });

    const onChangeHandler = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setData(data => ({...data, [name]: value}));
    }

    useEffect(() => {
        console.log(data);
    }, [data])

    const onSubmitHandler = async(event) => {
        event.preventDefault();
        if(!image){
            alert('Please Select an Image.');
            return;
        }
    
      const formData = new FormData();
      formData.append('food', JSON.stringify(data));
      formData.append('file', image);

      try{
        const response = await axios.post('http://localhost:8080/api/foodpowders', formData, {headers: {"Content-Type":"multipart/form-data"}});
        if(response.status === 201){
          alert('Food Added Successfully.');
          setData({name:'',description:'',price:'',category:'Health Mix'});
          setImage(null);
        }
      } catch(error){
        console.log(error);
        alert('Error Adding Food.');
      }
    }

  return (
    <div className="container mt-4">
  <div className="row">
    <div className="card col-md-4">
      <div className="card-body">
        <h2 className="mb-3">Add Food Powder</h2>
        <form onSubmit={onSubmitHandler}>
        <div className="mb-3">
            <label htmlFor="image" className="form-label">
                <img src={image ? URL.createObjectURL(image):assets.uploadImage} alt="uploadImage"  width={98}/>
            </label>
            <input type="file" className="form-control" id="image" required hidden onChange={(e) => setImage(e.target.files[0])}/>
          </div>
          <div className="mb-2">
            <label htmlFor="foodname" className="form-label">Food Name</label>
            <input type="text" className="form-control" id="foodname" required name='name' onChange={onChangeHandler} value={data.name}/>
          </div>
          <div className="mb-2">
            <label htmlFor="description" className="form-label">Description</label>
            <textarea className="form-control" id="description" rows="5" required name='description' onChange={onChangeHandler} value={data.description}></textarea>
          </div>
          <div className="mb-2">
            <label htmlFor="category" className="form-label">Category</label>
            <select name='category' id='category' className='form-control' onChange={onChangeHandler} value={data.category}>
                <option value="Health Mix">Health Mix</option>
                <option value="Masala Powder">Masala Powder</option>
                <option value="Rice Powder">Rice Powder</option>{/* like rasam, sambar, lemon rice etc */}
                <option value="Chutney Powder">Chutney Powder</option>{/* like podi, and etc */}
            </select>
          </div>
          <div className="mb-2">
            <label htmlFor="price" className="form-label">Price</label>
            <input type="number" className="form-control" id="price"  required name='price' onChange={onChangeHandler} value={data.price}/>
          </div>
          <button type="submit" className="btn btn-primary">Save</button>
        </form>
      </div>
    </div>
  </div>
</div>
  )
}

export default AddFood;