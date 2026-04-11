import {configureStore} from "@reduxjs/toolkit";
import cartReducer from "./cartSlice";

// The store = the single source of truth for all shared state.
// Like Spring's ApplicationContext — one per app, holds everything.
//
// Each key in "reducer" becomes a top-level piece of state:
//   store.getState() → { cart: { cart: Cart | null, loading: boolean, error: string | null } }
//
// When you add more slices later (e.g. userSlice), add them here:
//   reducer: { cart: cartReducer, user: userReducer }

export const store = configureStore({
    reducer: {
        cart: cartReducer,
    },
});

//here we can add more reducers later


// These types are needed for typed hooks (store/hooks.ts)
// RootState = the shape of the entire store
// AppDispatch = the dispatch function type (knows about all thunks)
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
