import {fetchMyOrders} from "../api/api";
import {useAuth} from "react-oidc-context";
import { Order } from "../types/Order";
import {useEffect, useState} from "react";

export default function MyOrders() {

    const auth = useAuth();
    const token = auth.user?.access_token;

    const [orders, setOrders] = useState<Order[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchMyOrders(token)
            .then(setOrders)
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, [token]);

    if (loading) return <p>Loading orders...</p>;
    if (error) return <p>Error: {error}</p>;
    if (orders.length === 0) return <h1>No orders yet</h1>;

    return (
        <>
            <h1>Orders:</h1>
            {orders.map(o => (
                <div key={o.id}>
                    <p>Order #{o.id} — Status: {o.status}</p>
                    <p>Total: ${(o.totalPrice ?? 0).toFixed(2)}</p>
                    <ul>
                        {o.orderItems.map(item => (
                            <li key={item.id}>
                                Painting #{item.paintingId} x{item.quantity}
                            </li>
                        ))}
                    </ul>
                </div>
            ))}
        </>
    );
}
