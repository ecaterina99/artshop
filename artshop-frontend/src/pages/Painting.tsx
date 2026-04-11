import {useState} from "react";
import {useParams} from "react-router-dom";
import {fetchPaintingById} from "../api/api";
import {useAuth} from "react-oidc-context";
import {useApi} from "../hooks/useApi";
import {useAppDispatch} from "../store/hooks";
import {addItemToCart} from "../store/cartSlice";

export default function PaintingDetail() {
    const {id} = useParams<{ id: string }>();
    const [added, setAdded] = useState(false);
    const auth = useAuth();
    const dispatch = useAppDispatch();

    // useApi still used here — painting data is NOT shared, only this page needs it
    const {data: painting, error, loading, setError} = useApi(() => fetchPaintingById(Number(id)), [id])

    const handleAddToCart = async () => {
        if (!auth.isAuthenticated) {
            auth.signinRedirect();
            return;
        }
        try {
            // Before: await addToCart({paintingId, quantity}, token)  ← direct API call, only this page knows
            // Now:    dispatch(addItemToCart(...))                     ← goes through Redux, Navbar updates too!
            await dispatch(addItemToCart({
                paintingId: painting!.id,
                quantity: 1,
                token: auth.user!.access_token!,
            })).unwrap();  // .unwrap() throws if the thunk was rejected, so catch block works
            setAdded(true);
            setTimeout(() => setAdded(false), 2000);
        } catch (err) {
            setError(err instanceof Error ? err.message : "Failed to add to cart");
        }
    }

    if (loading) return <p className="loading">Loading...</p>;
    if (error) return <p className="error">{error}</p>;
    if (!painting) return <p className="loading">Painting not found.</p>;

    return (
        <div className="artpiece">
            <div className="painting-img">
                <img src={painting.img} alt={painting.name}/>
            </div>
            <div className="painting-info">
                <span className="painting-card-style">{painting.style}</span>
                <h1 className="painting-title">{painting.name}</h1>
                <p className="painting-card-description">{painting.description}</p>
                <div className="painting-card-details">
                    <span>{painting.medium}</span>
                    <span>{painting.high} × {painting.length} cm</span>
                </div>
                <div className="painting-card-footer">
                    <span className="painting-card-price">${painting.price}</span>
                    <button className="btn-primary" onClick={handleAddToCart}>
                        {added ? "Added!" : "Add to Cart"}
                    </button>
                </div>
            </div>
        </div>
    )
}
