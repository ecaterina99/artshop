import {useAuth} from "react-oidc-context";


export default function Home() {

    const auth = useAuth();
    return (

        auth.isAuthenticated ? (
                <>
                    <h1>Hello {useAuth().user.profile.name}</h1>
                </>
            ) :
            (<>
                    <h1>Become a member!</h1>
                </>
            )
    )
}