import {createSlice, createAsyncThunk} from "@reduxjs/toolkit";
import {Cart} from "../types/Cart";
import {getCart, addToCart, updateCartItem, removeCartItem, clearCart} from "../api/api";

// ─── 1. STATE TYPE ───
// Defines what data the cart slice holds in the store.
// This replaces the useState variables you had in Cart.tsx:
//
// Before:
//   const [cart, setCart] = useState<Cart | null>(null);
//   const [loading, setLoading] = useState(true);
//   const [error, setError] = useState<string | null>(null);
//
// Now it's one interface, shared across ALL components:
interface CartState {
    cart: Cart | null;
    loading: boolean;
    error: string | null;
}

const initialState: CartState = {
    cart: null,
    loading: false,
    error: null,
};

// ─── 2. ASYNC THUNKS ───
// Thunks = async actions that call the backend API.
// Think of them like @Service methods in Java.
//
// createAsyncThunk<ReturnType, ArgumentType>("actionName", async function)
//
// Redux automatically generates 3 action types for each thunk:
//   - "cart/fetchCart/pending"   → API call started
//   - "cart/fetchCart/fulfilled" → API call succeeded, result in action.payload
//   - "cart/fetchCart/rejected"  → API call failed, error in action.error
//
// You handle these in extraReducers below.

export const fetchCart = createAsyncThunk<Cart, string>(
    "cart/fetchCart",
    async (token) => {
        return await getCart(token);   // calls your existing api/api.tsx function
    }
);

export const addItemToCart = createAsyncThunk<Cart, { paintingId: number; quantity: number; token: string }>(
    "cart/addItem",
    async ({paintingId, quantity, token}) => {
        return await addToCart({paintingId, quantity}, token);  // backend returns updated Cart
    }
);

export const updateItem = createAsyncThunk<Cart, { itemId: number; quantity: number; token: string }>(
    "cart/updateItem",
    async ({itemId, quantity, token}) => {
        return await updateCartItem(itemId, {quantity}, token);
    }
);

export const removeItem = createAsyncThunk<Cart, { itemId: number; token: string }>(
    "cart/removeItem",
    async ({itemId, token}) => {
        return await removeCartItem(itemId, token);
    }
);

export const clearCartItems = createAsyncThunk<void, string>(
    "cart/clear",
    async (token) => {
        await clearCart(token);
    }
);

// ─── 3. THE SLICE ───
// createSlice bundles state + reducers together.
// Compare to Java:
//   name         = class name
//   initialState = field defaults
//   reducers     = synchronous methods (no API call)
//   extraReducers = handles async thunk results (pending/fulfilled/rejected)

const cartSlice = createSlice({
    name: "cart",
    initialState,

    // Synchronous reducers — instant state changes, no API call
    reducers: {
        resetCart(state) {
            state.cart = null;
            state.error = null;
        },
    },

    // Async reducers — react to thunk lifecycle (pending → fulfilled or rejected)
    // The "builder" pattern adds cases one by one
    extraReducers: (builder) => {
        builder
            // ── fetchCart: load cart from backend ──
            .addCase(fetchCart.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchCart.fulfilled, (state, action) => {
                state.cart = action.payload;  // action.payload = Cart returned by getCart()
                state.loading = false;
            })
            .addCase(fetchCart.rejected, (state, action) => {
                state.error = action.error.message ?? "Failed to fetch cart";
                state.loading = false;
            })

            // ── addItemToCart: backend returns the updated Cart ──
            .addCase(addItemToCart.fulfilled, (state, action) => {
                state.cart = action.payload;
            })
            .addCase(addItemToCart.rejected, (state, action) => {
                state.error = action.error.message ?? "Failed to add to cart";
            })

            // ── updateItem ──
            .addCase(updateItem.fulfilled, (state, action) => {
                state.cart = action.payload;
            })

            // ── removeItem ──
            .addCase(removeItem.fulfilled, (state, action) => {
                state.cart = action.payload;
            })

            // ── clearCartItems ──
            .addCase(clearCartItems.fulfilled, (state) => {
                state.cart = null;
            });
    },
});

// ─── 4. EXPORTS ───
// Export the sync action — components call: dispatch(resetCart())
export const {resetCart} = cartSlice.actions;

// Export the reducer — the store needs this
export default cartSlice.reducer;
