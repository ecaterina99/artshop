import { Painting } from "../types/Painting";

interface PaintingCardProps {
    painting: Painting;
}

export default function PaintingCard({ painting }: PaintingCardProps) {
    return (
        <article className="painting-card">
            <div className="painting-card-img">
                <img src={painting.img} alt={painting.name} />
            </div>
            <div className="painting-card-body">
                <h3 className="painting-card-title">{painting.name}</h3>
                <div className="painting-card-details">
                    <span>{painting.high} × {painting.length} cm</span>
                </div>
                <div className="painting-card-footer">
                    <span className="painting-card-price">${painting.price}</span>
                </div>
            </div>
        </article>
    );
}
