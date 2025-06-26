import React, { useContext, useState } from 'react';
import './Login.css';
import { Link, useNavigate } from 'react-router-dom';
import { loginUser } from '../../Service/AuthService';
import { StoreContext } from '../../Context/StoreContext';
import { toast } from 'react-toastify';

const Login = () => {

    const navigate = useNavigate();

    const {setToken} = useContext(StoreContext);

    const [data, setData] = useState({
        email:'',
        password:''
    });

   const onChangeHandler = (Event) => {
        const name = Event.target.name;
        const value = Event.target.value;
        setData(data => ({...data, [name]: value}));
   };

   

   const onsubmitHandler = async(Event) => {
    Event.preventDefault();
    // console.log("data",data);
    try {
        const response = await loginUser(data);
        if(response.status === 200){
            toast.success('Logged In succuessfully');
            setToken(response.data.token);
            localStorage.setItem('token:',response.data.token);
            navigate("/");
        } else {
            toast.error('Please check your password and login.')
        }
    } catch (error) {
        toast.error('Unable to login, Please try again.')
        console.log('Error while login:', error)
    }

   };

  return (
    <div className="login-container">
    <div className="row justify-content-center">
        <div className="col-md-6">
            <div className="card mt-4">
                <div className="card-body">
                    <h3 className="card-title text-center">Login</h3>                  
                    <form onSubmit={onsubmitHandler}>
                        <div className="mb-3">
                            <label htmlFor="email" className="form-label">Email address</label>
                            <input type="email" className="form-control" id="email" placeholder='example@gmail.com' name='email' onChange={onChangeHandler} value={data.email} required/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="password" className="form-label">Password</label>
                            <input type="password" className="form-control" id="password" placeholder='password123' name='password' onChange={onChangeHandler} value={data.password} required/>
                        </div>
                        <div className="d-grid">
                            <button type="submit" className="btn btn-outline-primary">Login</button>
                        </div>
                        <div className="d-grid mt-2">
                            <button type="reset" className="btn btn-outline-danger">Reset</button>
                        </div>
                    </form>
                    <div className="text-center mt-3">
                        <div className='mt-4'>Don't have an account?  <Link to='/login' className='text-decoration-none'>Sign up</Link></div>
                        <a href="#" className="text-decoration-none">Forgot password?</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
  )
}

export default Login;