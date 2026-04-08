import {AddToCart, UpdateCartItem, Cart} from "../types/Cart";
import {Order} from "../types/Order";


const API_URL = "http://localhost:9091/api";

export const fetchPaintings = async () => {
    const response = await fetch(`${API_URL}/paintings`);

    if (!response.ok) {
        throw new Error("Failed to fetch paintings");
    }

    return response.json();
};


export const fetchPaintingById = async (id: number) => {
    const response = await fetch(`${API_URL}/paintings/${id}`);
    if (!response.ok) {
        throw new Error("Failed to fetch painting");
    }

    return response.json();
}

const authHeaders = (token?: string): Record<string, string> => {
    const headers: Record<string, string> = { "Content-Type": "application/json" };
    if (token) headers["Authorization"] = `Bearer ${token}`;
    return headers;
};

export const getCart = async (token?: string): Promise<Cart> => {
    const response = await fetch(`${API_URL}/cart`, {
        headers: authHeaders(token),
    });
    if (!response.ok) throw new Error("Failed to fetch cart");
    return response.json();
};

export const addToCart = async (dto: AddToCart, token?: string): Promise<Cart> => {
    const response = await fetch(`${API_URL}/cart/items`, {
        method: "POST",
        headers: authHeaders(token),
        body: JSON.stringify(dto),
    });
    if (!response.ok) throw new Error("Failed to add to cart");
    return response.json();
};

export const updateCartItem = async (itemId: number, dto: UpdateCartItem, token?: string): Promise<Cart> => {
    const response = await fetch(`${API_URL}/cart/items/${itemId}`, {
        method: "PUT",
        headers: authHeaders(token),
        body: JSON.stringify(dto),
    });
    if (!response.ok) throw new Error("Failed to update cart item");
    return response.json();
};

export const removeCartItem = async (itemId: number, token?: string): Promise<Cart> => {
    const response = await fetch(`${API_URL}/cart/items/${itemId}`, {
        method: "DELETE",
        headers: authHeaders(token),
    });
    if (!response.ok) throw new Error("Failed to remove cart item");
    return response.json();
};

export const clearCart = async (token?: string): Promise<void> => {
    const response = await fetch(`${API_URL}/cart`, {
        method: "DELETE",
        headers: authHeaders(token),
    });
    if (!response.ok) throw new Error("Failed to clear cart");
};

export const checkout = async (token?: string): Promise<Order> => {
    const response = await fetch(`${API_URL}/cart/checkout`, {
        method: "POST",
        headers: authHeaders(token),
    });
    if (!response.ok) throw new Error("Failed to checkout");
    return response.json();
};

export const fetchMyOrders = async (token?: string):Promise<Order[]> =>{
    const response = await fetch(`${API_URL}/orders/my`, {
        headers: authHeaders(token)
    });
    if(!response.ok) throw new Error("Failed to fetch my orders");
        return response.json();
}