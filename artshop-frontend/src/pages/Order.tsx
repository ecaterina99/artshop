import {fetchMyOrders} from "../api/api";
import {useAuth} from "react-oidc-context";
import {useApi} from "../hooks/useApi";
import OrdersGrid from "../components/OrdersGrid";


export default function MyOrders() {
    const auth = useAuth();
    const token = auth.user?.access_token;

    const{data: orders, error, loading} = useApi(() => {
        if (!token) return Promise.resolve([]);
        return fetchMyOrders(token);
    }, [token])

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
            <OrdersGrid orders={orders} />
        </div>
    );
}
