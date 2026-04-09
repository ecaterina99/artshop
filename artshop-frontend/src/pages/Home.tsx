import {useAuth} from "react-oidc-context";


export default function Home() {
    const auth = useAuth();

    return (
        <div className="home">
            {auth.isAuthenticated ? (
                <h1>Welcome back, {auth.user?.profile.name || auth.user?.profile.email}!</h1>
            ) : (
                <>
                    <h1>Welcome to ArtShop</h1>
                    <p>Browse our collection of paintings or sign in to start shopping.</p>
                    <button className="btn-primary" onClick={() => auth.signinRedirect()}>
                        Sign in
                    </button>
                </>
            )}
        </div>
    )
}
