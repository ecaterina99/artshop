import {Painting} from "../types/Painting";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {fetchPaintingById, addToCart} from "../api/api";
import {useAuth} from "react-oidc-context";

export default function PaintingDetail() {
    const {id} = useParams<{ id: string }>();
    const [painting, setPainting] = useState<Painting>();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [added, setAdded] = useState(false);
    const auth = useAuth();

    const handleAddToCart = async () => {
        if(!auth.isAuthenticated) {
            auth.signinRedirect();
            return;
        }
        try {
            await addToCart({paintingId: painting!.id, quantity: 1}, auth.user?.access_token);
            setAdded(true);
            setTimeout(() => setAdded(false), 2000);
        } catch (err) {
            setError(err instanceof Error ? err.message : "Failed to add to cart");
        }
    }

    useEffect(() => {
        fetchPaintingById(Number(id))
            .then(data => setPainting(data))
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, [id]);

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
