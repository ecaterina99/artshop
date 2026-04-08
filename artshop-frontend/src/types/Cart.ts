export interface CartItem {
  id: number;
  paintingId: number;
  paintingName: string;
  paintingPrice: number;
  quantity: number;
}

export interface Cart {
  id: number | null;
  userId: number | null;
  items: CartItem[];
  totalPrice: number;
}

export interface AddToCart {
  paintingId: number;
  quantity: number;
}

export interface UpdateCartItem {
  quantity: number;
}
