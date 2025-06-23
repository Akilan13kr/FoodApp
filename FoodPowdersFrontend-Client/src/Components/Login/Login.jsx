import React from 'react';
import './Login.css';
import { Link } from 'react-router-dom';

const Login = () => {
  return (
    <div className="login-container">
    <div className="row justify-content-center">
        <div className="col-md-6">
            <div className="card mt-4">
                <div className="card-body">
                    <h3 className="card-title text-center">Login</h3>                  
                    <form>
                        <div className="mb-3">
                            <label htmlFor="email" className="form-label">Email address</label>
                            <input type="email" className="form-control" id="email" placeholder='example@gmail.com' required/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="password" className="form-label">Password</label>
                            <input type="password" className="form-control" id="password" placeholder='password123' required/>
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