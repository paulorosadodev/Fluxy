import { Link } from "react-router-dom";

import { SignOut } from "phosphor-react";

import { AsideMenuWrapper } from "./styles";

import fluxyLogo from "../../assets/fluxy-icon (3).png";
import { useAuth } from "../../hooks/useAuth";


export default function AsideMenu() {

    const {signOut} = useAuth();

    return (
        <AsideMenuWrapper>
            <Link to="/">
                <img className="fluxy-logo" src={fluxyLogo} />
            </Link>
            <SignOut onClick={signOut} className="sign-out" size={32} color="white" weight="bold"/>
        </AsideMenuWrapper>
    );
}
