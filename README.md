# 🍃 FoodPowder Web Application 

A full-stack platform to showcase, manage, and purchase traditional food powders.

> 🟢 **[🌐 Live Client Demo](https://k-r-akilan-foodappclient.netlify.app/)**  
> 🎥 **[📽️ Admin + Backend Demo (YouTube)](https://youtu.be/your-video-link)**

---

## 📁 Project Structure

```

FoodApp/
├── FoodPowdersApi-Admin/           # Java Spring Boot API (Render + YouTube Demo)
├── FoodPowdersFrontend-Client/   # React app for users (Hosted on Netlify)
└── FoodPowdersFrontend-Admin/    # React app for admin dashboard (Demo on YouTube only)

````

---

## 🚀 Features

### 👥 User (frontend-client)
- Register/login (JWT-based)
- Browse & search food powders
- Each user have their own cart
- View image galleries (hosted on Firebase)
- Razorpay payment integration (in test mode.(use success@razorpay for upi test payment, Refer other type of payment in [Razorpay Document](https://razorpay.com/docs/payments/payments/test-card-details/)))
- View order history
- working contact form
- deployed to [Netlify](https://www.netlify.com/)

### 🔐 Admin (frontend-admin)
- Add/Delete Category with media
- Add/Delete food powder with media
- List food powder
- List category with food powder under it
- Manage users/orders
- Not deployed publicly for security
- Track/Update order

### ⚙️ Backend (Spring Boot)
- REST APIs for all modules
- Firebase integration
- Razorpay configuration
- JWT auth session handle
- Dockerized and deployed to [Render](https://render.com/)

---

## 🌐 Deployment Status

| Module          | Status | Link |
|----------------|--------|------|
| 🧠 Backend API   | ✅ Deployed via Render | 🎥 [YouTube Demo](https://youtu.be/your-video-link) |
| 👤 User Frontend | ✅ Deployed via Netlify | 🔗 [Live Demo](https://k-r-akilan-foodappclient.netlify.app/) |
| 🔐 Admin Frontend| 🚫 Not Public | 🎥 [Demo Video](https://youtu.be/your-video-link) |

---

## 🛠️ Setup Instructions

### Backend (Java Spring Boot)

#### 🔑 Required Environment Variables (on Render or `.env`)
| Variable             | Description                      |
|----------------------|----------------------------------|
| `GOOGLE_KEY_JSON`    | Base64-encoded Firebase key JSON |
| `RAZORPAY_KEY_JSON`  | Base64-encoded Razorpay secret   |
| `jwt.secret.key`     | JWT token signing secret         |

(or)

you can use API key in json file format to initiate Firebase and Razorpay sample razorpay json file is inside the resources directory inside src(not recommended due to security reasons)

#### 🧪 Local Run
```bash
cd FoodPowdersApi-Admin
./mvnw clean package
java -jar target/FoodPowdersApi-0.0.1-SNAPSHOT.jar
````

URL: `http://localhost:10000`

---

### Frontend – Client (User)

```bash
cd FoodPowdersFrontend-Client
npm install
npm run dev
```


URL: `http://localhost:5173`

---

### Frontend – Admin (Secure)

> ⚠️ Not deployed publicly. For security reasons, shown only via YouTube.

```bash
cd FoodPowdersFrontend-Admin
npm install
npm run dev
```

URL: `http://localhost:5174`

---

## 🎥 Demo Video

> 📽️ Watch the full walkthrough, including backend and admin panel here:
> [![YouTube](https://img.shields.io/badge/YouTube-Admin%20Demo-red?logo=youtube)](https://youtu.be/your-video-link)

---

## 🛠️ Tech Stack

| Layer      | Tech Stack                         |
| ---------- | ---------------------------------- |
| Frontend   | React, Vite, Bootstrap css          |
| Backend    | Spring Boot, Java 21, JWT          |
| Storage    | Firebase Cloud Storage             |
| Payments   | Razorpay API                       |
| Deployment | Render (backend), Netlify (client) |

---

## 🧠 Future Scope

* Add product reviews
* Enable multi-admin roles
* Add order tracking with SMS/email
* Add user verification with SMS
* Mobile app with same API backend
* Deploy it on a personal server(old laptop) and host it throw it
* comments for each foodpowder

---

## 👨‍💻 Author

Made with 💚 by [Akilan K.R](https://github.com/Akilan13kr)

