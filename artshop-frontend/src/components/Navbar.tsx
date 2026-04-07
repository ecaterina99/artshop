import {Link} from 'react-router-dom'
import { useAuth } from "react-oidc-context";

export default function Navbar(){
    const auth = useAuth();

    return (
        <nav className="navbar">
            <div className="nav-links">
                <Link to="/" className="link">home</Link>
                <Link to="/paintings" className="link">paintings</Link>
                <Link to="/cart" className="link">cart</Link>
            </div>
            <div className="nav-auth">
                {auth.isAuthenticated ? (
                    <>
                        <span className="nav-email">{auth.user?.profile.email}</span>
                        <button className="auth-btn" onClick={() => auth.signoutRedirect()}>Logout</button>
                    </>
                ) : (
                    <button className="auth-btn" onClick={() => auth.signinRedirect()}>Login</button>
                )}
            </div>
        </nav>
    )
}