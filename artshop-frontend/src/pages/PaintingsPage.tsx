import {useEffect, useState} from "react";
import {fetchPaintings} from "../api/api";


export default function PaintingsPage() {
    const [paintings, setPaintings] = useState([]);
    const [loading, setLoading] = useState(true);


    useEffect(() => {
        fetchPaintings()
            .then(data => setPaintings(data))
            .catch(err => console.error(err))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <p>Loading...</p>;

    return (
        <div>
            <h2>Paintings</h2>
            {paintings.map(p => (
                <div className="all-paintings" key={p.id}>
                    {p.name} - ${p.price}
                </div>
            ))}
        </div>
    );
}


/**
 *   const [name, setName] = useState('mario')
 *     const handleClick = () => {
 *         setName('luigi')
 *     }
 *      <p>{name}</p>
 *             <button onClick={handleClick}>Click me</button>
 */