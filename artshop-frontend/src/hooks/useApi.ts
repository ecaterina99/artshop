import {useState, useEffect} from "react";

export function useApi<T>(fetcher: () => Promise<T>, deps: unknown[] = []) {
    const [data, setData] = useState<T | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        setLoading(true);
        setError(null);
        fetcher()
            .then(setData)
            .catch(err => setError(err.message))
            .finally(() => setLoading(false));
    }, deps);

    return {data, loading, error, setData, setError, setLoading};
}
