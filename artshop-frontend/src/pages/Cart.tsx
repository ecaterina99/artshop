import { useEffect, useState } from "react";
import { useAuth } from "react-oidc-context";
import { Cart as CartType } from "../types/Cart";
import { Order } from "../types/Order";
import { getCart, updateCartItem, removeCartItem, clearCart, checkout } from "../api/api";

export default function Cart() {
    const auth = useAuth();
    const token = auth.user?.access_token;

    const [cart, setCart] = useState<CartType | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [order, setOrder] = useState<Order | null>(null);

    useEffect(() => {
        if (!token) return;
        getCart(token)
            .then(setCart)
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, [token]);

    const handleQuantityChange = async (itemId: number, newQuantity: number) => {
        try {
            if (newQuantity <= 0) {
                const updated = await removeCartItem(itemId, token);
                setCart(updated);
            } else {
                const updated = await updateCartItem(itemId, { quantity: newQuantity }, token);
                setCart(updated);
            }
        } catch (err) {
            setError(err instanceof Error ? err.message : "Failed to update item");
        }
    };

    const handleClear = async () => {
        try {
            await clearCart(token);
            setCart(null);
        } catch (err) {
            setError(err instanceof Error ? err.message : "Failed to clear cart");
        }
    };

    const handleCheckout = async () => {
        try {
            const result = await checkout(token);
            setOrder(result);
            setCart(null);
        } catch (err) {
            setError(err instanceof Error ? err.message : "Checkout failed");
        }
    };

    if (loading) return <p>Loading cart...</p>;
    if (error) return <p>Error: {error}</p>;

    if (order) {
        return (
            <div className="cart-page">
                <h1>Order Placed!</h1>
                <p>Order #{order.id} — Status: {order.status}</p>
                <p>Total: ${order.totalPrice.toFixed(2)}</p>
                <ul>
                    {order.orderItems.map(item => (
                        <li key={item.id}>
                            Painting #{item.paintingId} x{item.quantity}
                        </li>
                    ))}
                </ul>
            </div>
        );
    }

    if (!cart || cart.items.length === 0) {
        return (
            <div className="cart-page">
                <h1>Your Cart</h1>
                <p>Your cart is empty.</p>
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
                                <button onClick={() => handleQuantityChange(item.id, item.quantity - 1)}>-</button>
                                <span> {item.quantity} </span>
                                <button onClick={() => handleQuantityChange(item.id, item.quantity + 1)}>+</button>
                            </td>
                            <td>${(item.paintingPrice * item.quantity).toFixed(2)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="cart-summary">
                <p><strong>Total: ${cart.totalPrice.toFixed(2)}</strong></p>
                <button onClick={handleClear}>Clear Cart</button>
                <button onClick={handleCheckout}>Checkout</button>
            </div>
        </div>
    );
}
