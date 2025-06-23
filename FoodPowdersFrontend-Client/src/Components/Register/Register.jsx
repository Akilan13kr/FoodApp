import React from 'react';
import './Register.css';
import { Link } from 'react-router-dom';

const Register = () => {
  return (
<div className="register-container">
    <div className="row justify-content-center">
        <div className="col-md-6">
            <div className="card mt-4">
                <div className="card-body">
                    <h3 className="card-title text-center">Register</h3>                  
                    <form>
                        <div className="mb-3">
                            <label htmlFor="name" className="form-label">Name</label>
                            <input type="text" className="form-control" id="text" placeholder='username' required/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="email" className="form-label">Email address</label>
                            <input type="email" className="form-control" id="email" placeholder='example@gmil.com' required/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="password" className="form-label">Password</label>
                            <input type="password" className="form-control" id="password" placeholder='password123' required/>
                        </div>
                        <div className="mb-3">
                            <label htmlFor="confirm-password" className="form-label">Confirm Password</label>
                            <input type="confirm-password" className="form-control" id="confirm-password" placeholder='password123' required/>
                        </div>
                        <div className="d-grid">
                            <button type="submit" className="btn btn-outline-primary">Login</button>
                        </div>
                        <div className="d-grid mt-2">
                            <button type="reset" className="btn btn-outline-danger">Reset</button>
                        </div>
                    </form>
                    <div className="text-center mt-3">
                        <div className='mt-4'>Already have an account?  <Link to='/register' className='text-decoration-none'>Sign in</Link></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
    
  );
}

export default Register;