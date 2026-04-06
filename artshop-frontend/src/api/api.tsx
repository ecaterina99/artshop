const API_URL = "http://localhost:9091/api";

export const fetchPaintings = async () => {
    const response = await fetch(`${API_URL}/paintings`);

    if (!response.ok) {
        throw new Error("Failed to fetch paintings");
    }

    return response.json();
};

export const fetchPaintingById= async (id:number) => {
    const response = await fetch(`${API_URL}/paintings/${id}`);
    if (!response.ok) {
        throw new Error("Failed to fetch painting");
    }

    return response.json();
}