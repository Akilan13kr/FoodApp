import React, { useEffect, useState } from 'react';
import {assets} from '../../assets/assets';
import { addFoodPowder } from '../../Services/FoodService';
import { toast } from 'react-toastify';
import { getCategoryList } from '../../Services/categoryService';


const AddFood = () => {

  const [list, setList] = useState([]);
      const fetchList = async() => {
        try {
          const data = await getCategoryList();
          setList(data);
        } catch (error) {
          toast.error("Error while reading Category.");
        }
      }
    const [image, setImage] = useState(false);
    const [data, setData] = useState({
        name:'',
        description:'',
        price:'',
        category: ''
    });

    const onChangeHandler = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        setData(data => ({...data, [name]: value}));
    }

      // ✅ Run fetchList ONLY once on mount
      useEffect(() => {
        fetchList();
        // console.log(data)
      }, [data]); // <- empty dependency array means runs only once

      // ✅ After list is fetched, set category
      useEffect(() => {
        if (list.length > 0 && !data.category) { // set only if category not already set
          setData(prev => ({
            ...prev,
            category: list[0].categoryName
          }));
        }
      }, [list]);

    const onSubmitHandler = async(event) => {
        event.preventDefault();
        if(!image){
            toast.error('Please Select an Image.');
            return;
        }
    
        try {
          await addFoodPowder(data, image);
          toast.success('Food Added Successfully.');
          setData({ 
                name:'',
                description:'',
                price:'',
                category:'Health Mix'

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
            <input type="text" placeholder='Rasi Podi' className="form-control" id="foodname" required name='name' onChange={onChangeHandler} value={data.name}/>
          </div>
          <div className="mb-2">
            <label htmlFor="description" className="form-label">Description</label>
            <textarea className="form-control" placeholder='Description of Podi' id="description" rows="5" required name='description' onChange={onChangeHandler} value={data.description}></textarea>
          </div>
          <div className="mb-2">
            <label htmlFor="category" className="form-label">Category</label>
            <select name='category' id='category' className='form-control' onChange={onChangeHandler} value={data.category}>
                {/* <option value="Health Mix">Health Mix</option>
                <option value="Masala Powder">Masala Powder</option>
                <option value="Rice Powder">Rice Powder</option>{/* like rasam, sambar, lemon rice etc *}
                <option value="Chutney Powder">Chutney Powder</option>like podi, and etc */}
                {
                  list.map((category, index) =>{
                    // console.log(category)
                    return(
                      <option value={category.categoryName} key={index}>{category.categoryName}</option>
                    )
                  })
                }
            </select>
          </div>
          <div className="mb-2">
            <label htmlFor="price" className="form-label">Price</label>
            <input type="number" className="form-control" id="price" placeholder='&#8377;100' required name='price' onChange={onChangeHandler} value={data.price}/>
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