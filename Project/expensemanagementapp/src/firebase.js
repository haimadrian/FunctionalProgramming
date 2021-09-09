import firebase from "firebase/compat";

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyDYtDGgcwGXYtvJqvD_gNlgn9xLO-WLYVM",
    authDomain: "expensemanagement-20843.firebaseapp.com",
    projectId: "expensemanagement-20843",
    storageBucket: "expensemanagement-20843.appspot.com",
    messagingSenderId: "38655771806",
    appId: "1:38655771806:web:24a81d681718b63ae5a78f"
};

const app = firebase.initializeApp(firebaseConfig, 'ExpenseManagement');
export const auth = app.auth();