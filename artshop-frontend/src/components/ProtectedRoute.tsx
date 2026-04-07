import { useAuth } from "react-oidc-context";

export default function ProtectedRoute({ children }: { children: React.ReactNode }) {
    const auth = useAuth();

    if (auth.isLoading) return <p>Loading...</p>;

    if (!auth.isAuthenticated) {
        auth.signinRedirect();
        return <p>Redirecting to login...</p>;
    }

    return <>{children}</>;
}
