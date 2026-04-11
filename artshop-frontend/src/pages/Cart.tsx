import {useState} from "react";
import {useAuth} from "react-oidc-context";
import {Order} from "../types/Order";
import {checkout} from "../api/api";
import {useAppDispatch, useAppSelector} from "../store/hooks";
import {updateItem, removeItem, clearCartItems, resetCart} from "../store/cartSlice";

export default function Cart() {
    const auth = useAuth();
    const token = auth.user?.access_token;

    // Before: const {data: cart, loading, error} = useApi(() => getCart(token!), [token])
    // Now:    read directly from Redux store — same data the Navbar sees
    const {cart, loading, error} = useAppSelector(state => state.cart);
    const dispatch = useAppDispatch();

    // order stays as local state — only this page needs it (not shared)
    const [order, setOrder] = useState<Order | null>(null);

    const handleQuantityChange = async (itemId: number, newQuantity: number) => {
        if (!token) return;
        // Before: const updated = await removeCartItem(itemId, token); setCart(updated);
        // Now:    dispatch the thunk — store updates automatically, Navbar updates too
        if (newQuantity <= 0) {
            dispatch(removeItem({itemId, token}));
        } else {
            dispatch(updateItem({itemId, quantity: newQuantity, token}));
        }
    };

    const handleClear = async () => {
        if (!token) return;
        dispatch(clearCartItems(token));
    };

    const handleCheckout = async () => {
        if (!token) return;
        try {
            const result = await checkout(token);
            setOrder(result);
            dispatch(resetCart());  // sync action — clears cart in store instantly
        } catch (err) {
            // checkout is not in Redux because the Order result is only used here
        }
    };

    if (loading) return <p className="loading">Loading cart...</p>;
    if (error) return <p className="error">{error}</p>;

    if (order) {
        return (
            <div className="cart-page">
                <h1>Order Placed!</h1>
                <div className="order-card">
                    <div className="order-header">
                        <span className="order-id">Order #{order.id}</span>
                        <span className="order-status">{order.status}</span>
                    </div>
                    <ul className="order-items">
                        {order.orderItems.map(item => (
                            <li key={item.id}>Painting #{item.paintingId} × {item.quantity}</li>
                        ))}
                    </ul>
                    <p className="order-total">Total: ${order.totalPrice.toFixed(2)}</p>
                </div>
            </div>
        );
    }

    if (!cart || cart.items.length === 0) {
        return (
            <div className="cart-page">
                <h1>Your Cart</h1>
                <div className="empty-state">
                    <h2>Cart is empty</h2>
                    <p>Browse paintings and add some to your cart.</p>
                </div>
            </div>
        );
    }

    return (
        <div className="cart-page">
            <h1>Your Cart</h1>
            <table className="cart-table">
                <thead>
                <tr>
                    <th>Painting</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Subtotal</th>
                </tr>
                </thead>
                <tbody>
                {cart.items.map(item => (
                    <tr key={item.id}>
                        <td>{item.paintingName}</td>
                        <td>${item.paintingPrice.toFixed(2)}</td>
                        <td>
                            <div className="cart-quantity">
                                <button className="btn-sm"
                                        onClick={() => handleQuantityChange(item.id, item.quantity - 1)}>−
                                </button>
                                <span>{item.quantity}</span>
                                <button className="btn-sm"
                                        onClick={() => handleQuantityChange(item.id, item.quantity + 1)}>+
                                </button>
                            </div>
                        </td>
                        <td>${(item.paintingPrice * item.quantity).toFixed(2)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div className="cart-summary">
                <p>Total: ${cart.totalPrice.toFixed(2)}</p>
                <div className="cart-actions">
                    <button className="btn-secondary" onClick={handleClear}>Clear Cart</button>
                    <button className="btn-primary" onClick={handleCheckout}>Checkout</button>
                </div>
            </div>
        </div>
    );
}
