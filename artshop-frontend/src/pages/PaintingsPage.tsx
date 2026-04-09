import {useEffect, useState} from "react";
import {fetchPaintings} from "../api/api";
import {Painting} from "../types/Painting"
import {Link} from "react-router-dom";

export default function PaintingsPage() {
    const [paintings, setPaintings] = useState<Painting[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetchPaintings()
            .then(data => setPaintings(data))
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <p className="loading">Loading...</p>;
    if (error) return <p className="error">{error}</p>;
    if (paintings.length === 0) return <div className="empty-state"><h2>No paintings found</h2></div>;

    return (
        <div>
            <h2 className="page-title">Paintings</h2>
            <div className="paintings-grid">
                {paintings.map(p => (
                    <Link to={`/paintings/${p.id}`} className="painting-card-link" key={p.id}>
                        <article className="painting-card">
                            <div className="painting-card-img">
                                <img src={p.img} alt={p.name}/>
                            </div>
                            <div className="painting-card-body">
                                <h3 className="painting-card-title">{p.name}</h3>
                                <div className="painting-card-details">
                                    <span>{p.high} × {p.length} cm</span>
                                </div>
                                <div className="painting-card-footer">
                                    <span className="painting-card-price">${p.price}</span>
                                </div>
                            </div>
                        </article>
                    </Link>
                ))}
            </div>
        </div>
    );
}
