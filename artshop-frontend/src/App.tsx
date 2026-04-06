import Navbar from "./components/Navbar";
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import Home from "./pages/Home";
import PaintingsPage from "./pages/PaintingsPage";
import Cart from "./pages/Cart";
import '/src/App.css'


export default function App(){
  return (
      <>
      <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/paintings" element={<PaintingsPage />} />
          <Route path="/cart" element={<Cart />} />
        </Routes>
      </>

  )
}

