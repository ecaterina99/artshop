import Navbar from "./components/Navbar";
import {Routes, Route} from 'react-router-dom'
import Home from "./pages/Home";
import PaintingsPage from "./pages/PaintingsPage";
import Artpiece from "./pages/Painting";
import Cart from "./pages/Cart";
import '/src/App.css'


export default function App() {
    return (
        <>
            <Navbar/>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/paintings" element={<PaintingsPage/>}/>
                <Route path="/cart" element={<Cart/>}/>
                <Route path="/paintings/:id" element={<Artpiece/>}/>
            </Routes>
        </>

    )
}

