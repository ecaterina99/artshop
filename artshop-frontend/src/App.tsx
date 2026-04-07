import Navbar from "./components/Navbar";
import {Routes, Route} from 'react-router-dom'
import Home from "./pages/Home";
import PaintingsPage from "./pages/PaintingsPage";
import Artpiece from "./pages/Painting";
import Cart from "./pages/Cart";
import Profile from "./pages/Profile";
import ProtectedRoute from "./components/ProtectedRoute";
import '/src/App.css'


export default function App() {
    return (
        <>
            <Navbar/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/paintings" element={<PaintingsPage/>}/>
                <Route path="/paintings/:id" element={<Artpiece/>}/>
                <Route path="/cart" element={
                    <ProtectedRoute><Cart/></ProtectedRoute>
                }/>
                <Route path="/profile" element={
                    <ProtectedRoute><Profile/></ProtectedRoute>
                }/>
            </Routes>
        </>
    )
}

