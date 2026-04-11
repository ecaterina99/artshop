import {Link} from 'react-router-dom'
import {useAuth} from "react-oidc-context";
import {useEffect} from "react";
import {useAppDispatch, useAppSelector} from "../store/hooks";
import {fetchCart} from "../store/cartSlice";

export default function Navbar() {
    const auth = useAuth();
    const dispatch = useAppDispatch();

    // useAppSelector reads from the Redux store
    // This component re-renders automatically when cart changes — anywhere in the app
    const cart = useAppSelector(state => state.cart.cart);

    // Fetch cart when user is authenticated
    // This runs once when the token becomes available
    useEffect(() => {
        if (auth.isAuthenticated && auth.user?.access_token) {
            dispatch(fetchCart(auth.user.access_token));
        }
    }, [auth.isAuthenticated]);

    // Count items in cart (0 if no cart yet)
    const itemCount = cart?.items.length ?? 0;

    return (
        <nav className="navbar">
            <div className="nav-links">
                <Link to="/" className="link">home</Link>
                <Link to="/paintings" className="link">paintings</Link>
                <Link to="/cart" className="link">
                    cart{itemCount > 0 && ` (${itemCount})`}
                </Link>
                <Link to="/orders" className="link">my orders</Link>
            </div>
            <div className="nav-auth">
                {auth.isAuthenticated ? (
                    <>
                        <Link to="/profile" className="link">{auth.user?.profile.email}</Link>
                        <button className="auth-btn" onClick={() => auth.signoutRedirect()}>Logout</button>
                    </>
                ) : (
                    <button className="auth-btn" onClick={() => auth.signinRedirect()}>Login</button>
                )}
            </div>
        </nav>
    )
}
