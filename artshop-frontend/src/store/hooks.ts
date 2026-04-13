import {useDispatch, useSelector} from "react-redux";
import type {RootState, AppDispatch} from "./store";

// Typed versions of React-Redux hooks.
// Use these instead of plain useDispatch/useSelector — they know your store's types.
//
// useAppDispatch — for dispatching actions:
//   const dispatch = useAppDispatch();
//   dispatch(fetchCart(token));  ← TypeScript knows fetchCart's argument type
//
// useAppSelector — for reading state:
//   const cart = useAppSelector(state => state.cart.cart);  ← TypeScript knows "cart" exists
//

export const useAppDispatch = useDispatch.withTypes<AppDispatch>();
export const useAppSelector = useSelector.withTypes<RootState>();
