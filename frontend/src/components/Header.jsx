import { Link, NavLink } from "react-router-dom";

export default function Header() {
    const activeStyles = {
        fontWeight: "700",
    }

    return (
        <header>
            <Link className="site-logo" to="/">LOGO</Link>
            <nav>
                <NavLink to="home" style={({isActive}) => isActive ? activeStyles : null} className={"navlink"}>
                    Home
                </NavLink>

                <NavLink to="messages" style={({isActive}) => isActive ? activeStyles : null} className={"navlink"}>
                    Messages
                </NavLink>

                <NavLink to="profile" style={({isActive}) => isActive ? activeStyles : null} className={"navlink"}>
                    Profile
                </NavLink>
            </nav>
        </header>
    )
}