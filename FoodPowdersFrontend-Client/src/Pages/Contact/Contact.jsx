import React, { useState } from 'react';
import './Contact.css';
import { sentDetail } from '../../Service/ContactService';
import { toast } from 'react-toastify';

const Contact = () => {

  const [emailData, setEmailData] = useState({
    name:'',
    email:'',
    message:''
  })

  const onChangeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setEmailData(emailData => ({...emailData, [name]: value}));
}

const onSubmitHandler = async(event) => {
  event.preventDefault();
  try {
    await sentDetail(emailData);
    toast.success('sent Successfully.');
    setEmailData({
      name:'',
      email:'',
      message:''
    })
    onChangeHandler
  } catch (error) {
    toast.error('Error senting detail');
  }
}

  return (
    <div className="container mt-4">
      <div className="row justify-content-center">
        <div className="col-md-8">
          <div className="contact-form">
            <h2 className="mb-4 text-center">Contact Us</h2>
            <form onSubmit={onSubmitHandler}>
              <div className="mb-3">
                <label htmlFor="name" className="form-label">Your Name</label>
                <input type="text" className="form-control" id="name" name='name' onChange={onChangeHandler} value={emailData.name} required/>
              </div>
              <div className="mb-3">
                <label htmlFor="email" className="form-label">Your Email</label>
                <input type="email" className="form-control" id="email" name='email' onChange={onChangeHandler} value={emailData.email} required/>
              </div>
              <div className="mb-3">
                <label htmlFor="message" className="form-label">Your Message</label>
                <textarea className="form-control" id="message" rows="5" name='message' onChange={onChangeHandler} value={emailData.message} required></textarea>
              </div>
              <button type="submit" className="btn btn-primary">Send Message</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Contact;