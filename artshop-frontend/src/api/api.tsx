import {CreateOrderItem} from "../types/CreateOrderItem";


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


export const addToCart = async (p: CreateOrderItem, token?:string) => {
    const headers: Record<string, string> = { "Content-Type": "application/json" };
    if (token) headers["Authorization"] = `Bearer ${token}`;

    const response = await fetch(`${API_URL}/order-items`, {
                method: "POST",
                headers,
                body: JSON.stringify(p)
            }
        );
    if (!response.ok) {
        throw new Error("Failed to create order");
    }
    return response.json();
}