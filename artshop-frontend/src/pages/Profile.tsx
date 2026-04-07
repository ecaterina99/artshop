import { useAuth } from "react-oidc-context";

export default function Profile() {
    const auth = useAuth();
    const profile = auth.user?.profile;

    return (
        <div className="profile">
            <h1>My Profile</h1>
            <div className="profile-info">
                <p><strong>Name:</strong> {profile?.name || `${profile?.given_name ?? ""} ${profile?.family_name ?? ""}`.trim() || "Not set"}</p>
                <p><strong>Email:</strong> {profile?.email}</p>
            </div>
            <a
                className="auth-btn"
                href="http://localhost:8080/realms/artshop/account"
                target="_blank"
                rel="noopener noreferrer"
            >
                Edit Profile
            </a>
            <button className="auth-btn" onClick={() => auth.signoutRedirect()}>
                Logout
            </button>
        </div>
    );
}
