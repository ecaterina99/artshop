import {fetchMyOrders} from "../api/api";
import {useAuth} from "react-oidc-context";
import {useApi} from "../hooks/useApi";

export default function MyOrders() {

    const auth = useAuth();
    const token = auth.user?.access_token;

    const{data: orders, error, loading} = useApi(()=>fetchMyOrders(token!),[token])

    if (loading) return <p className="loading">Loading orders...</p>;
    if (error) return <p className="error">{error}</p>;

    if (!orders || orders.length === 0) {
        return (
            <div className="orders-page">
                <h1>My Orders</h1>
                <div className="empty-state">
                    <h2>No orders yet</h2>
                    <p>Your completed orders will appear here.</p>
                </div>
            </div>
        );
    }

    return (
        <div className="orders-page">
            <h1>My Orders</h1>
            {orders.map(o => (
                <div className="order-card" key={o.id}>
                    <div className="order-header">
                        <span className="order-id">Order #{o.id}</span>
                        <span className="order-status">{o.status}</span>
                    </div>
                    <ul className="order-items">
                        {o.orderItems.map(item => (
                            <li key={item.id}>Painting #{item.paintingId} × {item.quantity}</li>
                        ))}
                    </ul>
                    <p className="order-total">${(o.totalPrice ?? 0).toFixed(2)}</p>
                </div>
            ))}
        </div>
    );
}
