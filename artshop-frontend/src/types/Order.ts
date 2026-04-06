import {OrderItem} from "./OrderItem";

export interface Order {
    id: number;
    userId: number;
    totalPrice: number;
    status: string;
    orderItems: OrderItem[];
}
