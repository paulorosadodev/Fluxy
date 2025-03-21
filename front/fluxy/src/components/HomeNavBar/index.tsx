import { useState } from "react";

import { ArrowCircleLeft } from "phosphor-react";
import { MenuIcon, NavBar } from "./styles";


export function HomeNavBar() {

    const [showMenu, setShowMenu] = useState(false);

    const handleShowMenu = () => {
        setShowMenu(() => {
            document.body.style.overflow = "hidden";
            return true;
        });
    };

    const handleCloseMenu = () => {
        setShowMenu(() => {
            document.body.style.overflow = "auto";
            return false;
        });
    };

    const handleCloseMenuB = () => {
        document.body.style.backgroundColor = "red";
        setShowMenu(() => {
            document.body.style.overflow = "auto";
            return false;
        });
    };

    return(
        <>
            <MenuIcon size={50} weight="bold" onClick={handleShowMenu} />
            <NavBar className={showMenu ? "active" : "deactive"}>
                {showMenu &&
                    <ArrowCircleLeft size={50} color="white" weight="bold" onClick={handleCloseMenuB} />
                }
                <ul className="active">
                    <li><a href="#about" onClick={handleCloseMenu}>Sobre</a></li>
                    <li><a href="#clients" onClick={handleCloseMenu}>Clientes</a></li>
                    <li><a href="#contact" onClick={handleCloseMenu}>Contato</a></li>
                    <li><a id="button" href="#" onClick={handleCloseMenu}>Comece agora</a></li>
                </ul>
            </NavBar>
        </>
    );

}
