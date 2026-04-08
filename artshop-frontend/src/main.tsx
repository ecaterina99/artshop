import App from "./App";
import {createRoot} from "react-dom/client"
import { BrowserRouter } from "react-router-dom";
import { AuthProvider } from "react-oidc-context";

const oidcConfig = {
    authority: "http://localhost:8080/realms/artshop",
    client_id: "artshop-frontend",
    redirect_uri: "http://localhost:5173/",
    post_logout_redirect_uri: "http://localhost:5173/",
    scope: "openid profile email",
    automaticSilentRenew: true,
};


const root = createRoot(document.getElementById("root"))
root.render(
    <AuthProvider {...oidcConfig}>
    <BrowserRouter>
        <App />
    </BrowserRouter>
    </AuthProvider>
)